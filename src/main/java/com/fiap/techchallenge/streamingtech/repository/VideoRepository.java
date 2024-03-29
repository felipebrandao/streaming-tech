package com.fiap.techchallenge.streamingtech.repository;

import com.fiap.techchallenge.streamingtech.model.Video;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

public interface VideoRepository extends ReactiveMongoRepository<Video, String> {
    Flux<Video> findByTitleContainingIgnoreCaseAndPublicationDateAfter(String title, Date date);
    Flux<Video> findByCategoriesContaining(String category);
    @Query("{ 'categories' : { $in : ?0 }, 'id' : { $nin : ?1 } }")
    Flux<Video> findByCategoriesInAndIdNotIn(List<String> categories, List<String> excludedVideoIds);
}
