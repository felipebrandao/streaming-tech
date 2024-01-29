package com.fiap.techchallenge.streamingtech.controller;

import com.fiap.techchallenge.streamingtech.model.Video;
import com.fiap.techchallenge.streamingtech.model.VideoStats;
import com.fiap.techchallenge.streamingtech.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public Mono<Video> createVideo(@RequestBody Video video) {
        return videoService.createVideo(video);
    }

    @PutMapping("/{id}")
    public Mono<Video> updateVideo(@PathVariable String id, @RequestBody Video video) {
        return videoService.updateVideo(id, video);
    }

    @GetMapping
    public Flux<Video> getAllVideos(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "title") String sortBy) {
        return videoService.getAllVideos(page, size, sortBy);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteVideo(@PathVariable String id) {
        return videoService.deleteVideo(id);
    }

    @PutMapping("/{id}/favorite")
    public Mono<Video> markAsFavorite(@PathVariable String id, @RequestParam boolean isFavorite) {
        return videoService.markAsFavorite(id, isFavorite);
    }

    @GetMapping("/search")
    public Flux<Video> getVideosByTitleAndDate(@RequestParam String title, @RequestParam String date) throws ParseException {
        return videoService.getVideosByTitleAndDate(title, date);
    }

    @GetMapping("/categories")
    public Flux<Video> getVideosByCategory(@RequestParam String category) {
        return videoService.getVideosByCategory(category);
    }

    @GetMapping("/recommended")
    public Flux<Video> getRecommendedVideos(@RequestParam String userId) {
        return videoService.getRecommendedVideos(userId);
    }

    @GetMapping("/stats")
    public Mono<VideoStats> getVideoStatistics() {
        return videoService.getVideoStatistics();
    }
}
