package com.fiap.techchallenge.streamingtech.controller;

import com.fiap.techchallenge.streamingtech.model.Video;
import com.fiap.techchallenge.streamingtech.model.VideoStats;
import com.fiap.techchallenge.streamingtech.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.text.ParseException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VideoControllerTest {

    @InjectMocks
    private VideoController videoController;

    @Mock
    private VideoService videoService;

    @Test
    void createVideo_Success() {
        Video testVideo = new Video();
        when(videoService.createVideo(any(Video.class))).thenReturn(Mono.just(testVideo));

        StepVerifier.create(videoController.createVideo(testVideo))
                .expectSubscription()
                .expectNext(testVideo)
                .verifyComplete();
    }

    @Test
    void updateVideo_Success() {
        String videoId = "videoId";
        Video updatedVideo = new Video();
        when(videoService.updateVideo(eq(videoId), any(Video.class))).thenReturn(Mono.just(updatedVideo));

        StepVerifier.create(videoController.updateVideo(videoId, updatedVideo))
                .expectSubscription()
                .expectNext(updatedVideo)
                .verifyComplete();
    }

    @Test
    void getAllVideos_Success() {
        when(videoService.getAllVideos(anyInt(), anyInt(), anyString())).thenReturn(Flux.empty());

        StepVerifier.create(videoController.getAllVideos(0, 10, "title"))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void deleteVideo_Success() {
        String videoId = "videoId";
        when(videoService.deleteVideo(videoId)).thenReturn(Mono.empty());

        StepVerifier.create(videoController.deleteVideo(videoId))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void getVideosByTitleAndDate_Success() throws ParseException {
        when(videoService.getVideosByTitleAndDate(anyString(), anyString())).thenReturn(Flux.empty());

        StepVerifier.create(videoController.getVideosByTitleAndDate("title", "2024-01-29"))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void getVideosByCategory_Success() {
        when(videoService.getVideosByCategory(anyString())).thenReturn(Flux.empty());

        StepVerifier.create(videoController.getVideosByCategory("category"))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void getVideoWithIncrementedViews_Success() {
        String videoId = "videoId";
        when(videoService.getVideoWithIncrementedViews(videoId)).thenReturn(Mono.empty());

        StepVerifier.create(videoController.getVideoWithIncrementedViews(videoId))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    void getVideoStats_Success() {
        VideoStats testStats = new VideoStats();
        when(videoService.getVideoStats()).thenReturn(Mono.just(testStats));

        StepVerifier.create(videoController.getVideoStats())
                .expectSubscription()
                .expectNext(testStats)
                .verifyComplete();
    }

    @Test
    void getRecommendedVideos_Success() {
        String userId = "userId";
        when(videoService.getRecommendedVideos(userId)).thenReturn(Flux.empty());

        StepVerifier.create(videoController.getRecommendedVideos(userId))
                .expectSubscription()
                .verifyComplete();
    }
}
