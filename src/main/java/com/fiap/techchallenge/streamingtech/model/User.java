package com.fiap.techchallenge.streamingtech.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String username;
    private String password;
    private List<Video> favoriteVideos = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addFavoriteVideo(Video video) {
        this.favoriteVideos.add(video);
    }
}
