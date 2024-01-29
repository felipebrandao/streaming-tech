package com.fiap.techchallenge.streamingtech.model;

import lombok.Data;

@Data
public class VideoStats {
    private long totalVideos;
    private long favoritedVideos;
    private double averageViews;

    public VideoStats(long totalVideos, long favoritedVideos, double averageViews) {
        this.totalVideos = totalVideos;
        this.favoritedVideos = favoritedVideos;
        this.averageViews = averageViews;
    }
}