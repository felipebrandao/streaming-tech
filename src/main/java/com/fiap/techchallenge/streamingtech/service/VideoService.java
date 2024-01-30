package com.fiap.techchallenge.streamingtech.service;

import com.fiap.techchallenge.streamingtech.model.Video;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

public interface VideoService {
    Mono<Video> createVideo(Video video);
    Mono<Video> updateVideo(String id, Video video);
    Flux<Video> getAllVideos(int page, int size, String sortBy);
    Mono<Void> deleteVideo(String id);
    Flux<Video> getVideosByTitleAndDate(String title, String date) throws ParseException;
    Flux<Video> getVideosByCategory(String category);
    Mono<Video> getVideoWithIncrementedViews(String id);
}
