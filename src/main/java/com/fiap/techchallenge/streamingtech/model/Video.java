package com.fiap.techchallenge.streamingtech.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
public class Video {
    @Id
    private String id;
    private String title;
    private String description;
    private String url;
    private LocalDate publicationDate;
    private List<String> categories;
    private boolean isFavorite;
}