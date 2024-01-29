package com.fiap.techchallenge.streamingtech.usecase;

import com.fiap.techchallenge.streamingtech.model.User;
import com.fiap.techchallenge.streamingtech.repository.UserRepository;
import com.fiap.techchallenge.streamingtech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUserPassword(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existinUser -> {
                    existinUser.setPassword(user.getPassword());
                    return userRepository.save(existinUser);
                });
    }

    @Override
    public Mono<User> updateUserFavoriteVideos(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setFavoriteVideos(user.getFavoriteVideos());
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id).then();
    }
}
