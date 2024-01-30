package com.fiap.techchallenge.streamingtech.usecase;

import com.fiap.techchallenge.streamingtech.model.Video;
import com.fiap.techchallenge.streamingtech.model.VideoStats;
import com.fiap.techchallenge.streamingtech.repository.UserRepository;
import com.fiap.techchallenge.streamingtech.repository.VideoRepository;
import com.fiap.techchallenge.streamingtech.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<Video> createVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Mono<Video> updateVideo(String id, Video video) {
        return videoRepository.findById(id)
                .flatMap(existingVideo -> {
                    existingVideo.setTitle(video.getTitle());
                    existingVideo.setDescription(video.getDescription());
                    existingVideo.setUrl(video.getUrl());
                    existingVideo.setPublicationDate(video.getPublicationDate());
                    existingVideo.setCategories(video.getCategories());
                    return videoRepository.save(existingVideo);
                });
    }

    @Override
    public Flux<Video> getAllVideos(int page, int size, String sortBy) {
        return videoRepository.findAll()
                .skip((long) page * size)
                .take(size)
                .sort((v1, v2) -> switch (sortBy.toLowerCase()) {
                    case "title" -> v1.getTitle().compareTo(v2.getTitle());
                    case "date" -> v1.getPublicationDate().compareTo(v2.getPublicationDate());
                    default -> 0;
                });
    }

    @Override
    public Mono<Void> deleteVideo(String id) {
        return videoRepository.deleteById(id).then();
    }



    @Override
    public Flux<Video> getVideosByTitleAndDate(String title, String date) throws ParseException {
        return videoRepository.findByTitleContainingIgnoreCaseAndPublicationDateAfter(title, parseDate(date));
    }

    @Override
    public Flux<Video> getVideosByCategory(String category) {
        return videoRepository.findByCategoriesContaining(category);
    }

    @Override
    public Mono<Video> getVideoWithIncrementedViews(String id) {
        return videoRepository.findById(id)
                .flatMap(video -> {
                    video.setAverageViews(video.getAverageViews() + 1);
                    return videoRepository.save(video);
                });
    }

    public Mono<VideoStats> getVideoStats() {
        Mono<Long> totalVideos = videoRepository.count();
        Mono<Long> favoritedVideos = userRepository.countByFavoriteVideosIsNotNull();
        Flux<Video> allVideos = videoRepository.findAll();

        return Mono.zip(totalVideos, favoritedVideos, calculateAverageViews(allVideos))
                .map(tuple -> new VideoStats(tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private Mono<Double> calculateAverageViews(Flux<Video> videos) {
        return videos
                .map(Video::getAverageViews)
                .reduce(0L, Long::sum)
                .map(totalViews -> totalViews / (double) videos.count().block());
    }

    public Flux<Video> getRecommendedVideos(String userId) {
        return userRepository.findById(userId)
                .flatMapMany(user -> {
                    List<String> favoriteVideoIds = user.getFavoriteVideos().stream()
                            .map(Video::getId)
                            .collect(Collectors.toList());
                    return videoRepository.findByCategoriesInAndIdNotIn(user.getFavoriteVideos().stream()
                                    .flatMap(video -> video.getCategories().stream())
                                    .collect(Collectors.toList()), favoriteVideoIds)
                            .take(10);
                });
    }

    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);
    }
}
