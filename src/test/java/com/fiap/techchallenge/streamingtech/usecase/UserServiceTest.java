package com.fiap.techchallenge.streamingtech.usecase;

import com.fiap.techchallenge.streamingtech.model.User;
import com.fiap.techchallenge.streamingtech.model.Video;
import com.fiap.techchallenge.streamingtech.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_Success() {
        User testUser = new User("username", "password");

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(testUser));

        Mono<User> result = userService.createUser(testUser);

        StepVerifier.create(result)
                .expectNext(testUser)
                .verifyComplete();

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void updateUserPassword_Success() {
        User existingUser = new User("username", "password");

        when(userRepository.findById(anyString())).thenReturn(Mono.just(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(existingUser));

        Mono<User> result = userService.updateUserPassword("userId", existingUser);

        StepVerifier.create(result)
                .expectNext(existingUser)
                .verifyComplete();

        verify(userRepository, times(1)).findById("userId");
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void getUserById_Success() {
        String userId = "userId";
        User testUser = new User("username", "password");

        when(userRepository.findById(userId)).thenReturn(Mono.just(testUser));

        Mono<User> result = userService.getUserById(userId);

        StepVerifier.create(result)
                .expectNext(testUser)
                .verifyComplete();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void deleteUser_Success() {
        String userId = "userId";

        when(userRepository.deleteById(userId)).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteUser(userId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void addVideoToFavorites_Success() {
        String userId = "userId";
        User existingUser = new User("username", "password");
        Video video = new Video("videoId", "Video Title", "Video Description", "https://www.example.com", null, Collections.emptyList(), 0L);
        existingUser.addFavoriteVideo(video);

        when(userRepository.findById(userId)).thenReturn(Mono.just(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(existingUser));

        Mono<User> result = userService.addVideoToFavorites(userId, video);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getFavoriteVideos().contains(video))
                .verifyComplete();

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }
}

