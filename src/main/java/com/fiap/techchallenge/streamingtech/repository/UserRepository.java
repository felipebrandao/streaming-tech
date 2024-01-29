package com.fiap.techchallenge.streamingtech.repository;

import com.fiap.techchallenge.streamingtech.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
