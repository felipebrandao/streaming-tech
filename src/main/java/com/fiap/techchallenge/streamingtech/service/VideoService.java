package com.fiap.techchallenge.streamingtech.service;

import com.fiap.techchallenge.streamingtech.model.Video;
import com.fiap.techchallenge.streamingtech.model.VideoStats;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

public interface VideoService {
    Mono<Video> createVideo(Video video);
    Mono<Video> updateVideo(String id, Video video);
    Flux<Video> getAllVideos(int page, int size, String sortBy);
    Mono<Void> deleteVideo(String id);
    Mono<Video> markAsFavorite(String id, boolean isFavorite, String userId);

    Mono<Video> markAsFavorite(String id, boolean isFavorite);

    Flux<Video> getVideosByTitleAndDate(String title, String date) throws ParseException;
    Flux<Video> getVideosByCategory(String category);
    Flux<Video> getRecommendedVideos(String userId);
    Mono<VideoStats> getVideoStatistics();
}
