package com.fiap.techchallenge.streamingtech.service;

import com.fiap.techchallenge.streamingtech.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> createUser(User user);
    Mono<User> updateUserPassword(String id, User user);
    Mono<User> updateUserFavoriteVideos(String id, User user);
    Mono<User> getUserById(String id);
    Mono<Void> deleteUser(String id);
}
