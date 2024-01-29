package com.fiap.techchallenge.streamingtech.usecase;

import com.fiap.techchallenge.streamingtech.model.User;
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
                    existingVideo.setFavorite(video.isFavorite());
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
    public Mono<Video> markAsFavorite(String id, boolean isFavorite, String userId) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    if (isFavorite) {
                        user.getFavoriteVideos().add(id);
                    } else {
                        user.getFavoriteVideos().remove(id);
                    }
                    return userRepository.save(user);
                })
                .then(videoRepository.findById(id))
                .flatMap(video -> {
                    video.setFavorite(isFavorite);
                    return videoRepository.save(video);
                });
    }

    @Override
    public Mono<Video> markAsFavorite(String id, boolean isFavorite) {
        return videoRepository.findById(id)
                .flatMap(video -> {
                    video.setFavorite(isFavorite);
                    return videoRepository.save(video);
                });
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
    public Flux<Video> getRecommendedVideos(String userId) {
        return getUserFavoriteVideos(userId)
                .flatMap(video -> getVideosByCategory(video.getCategories().get(0)))
                .distinct();
    }

    @Override
    public Mono<VideoStats> getVideoStatistics() {
        return null;
    }

    private Flux<Video> getUserFavoriteVideos(String userId) {
        return userRepository.findById(userId)
                .map(User::getFavoriteVideos)
                .flatMapMany(videoRepository::findAllById);
    }

    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);
    }
}
