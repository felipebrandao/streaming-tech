package com.fiap.techchallenge.streamingtech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    private String id;
    private String title;
    private String description;
    private String url;
    private LocalDate publicationDate;
    private List<String> categories;
    private Long averageViews = 0L;
}