package com.fiap.techchallenge.streamingtech.repository;

import com.fiap.techchallenge.streamingtech.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    @Query("{ 'favoriteVideos' : { $exists : true, $ne : null } }")
    Mono<Long> countByFavoriteVideosIsNotNull();
}
