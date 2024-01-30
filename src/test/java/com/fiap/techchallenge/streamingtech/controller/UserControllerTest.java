package com.fiap.techchallenge.streamingtech.controller;

import com.fiap.techchallenge.streamingtech.model.User;
import com.fiap.techchallenge.streamingtech.model.Video;
import com.fiap.techchallenge.streamingtech.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;


    @Test
    void createUser_Success() {
        User testUser = new User("username", "password");

        when(userService.createUser(any(User.class))).thenReturn(Mono.just(testUser));

        StepVerifier.create(userController.createUser(testUser))
                .expectSubscription()
                .expectNextMatches(responseEntity -> {
                    User responseBody = responseEntity.getBody();
                    return responseBody != null && responseBody.equals(testUser);
                })
                .verifyComplete();
    }

    @Test
    void getUserById_Success() {
        String userId = "userId";
        User testUser = new User("username", "password");

        when(userService.getUserById(userId)).thenReturn(Mono.just(testUser));

        StepVerifier.create(userController.getUserById(userId))
                .expectSubscription()
                .expectNextMatches(responseEntity -> {
                    User responseBody = responseEntity.getBody();
                    return responseBody != null && responseBody.equals(testUser);
                })
                .verifyComplete();
    }

    @Test
    void deleteUser_Success() {
        String userId = "userId";

        when(userService.deleteUser(userId)).thenReturn(Mono.empty());

        StepVerifier.create(userController.deleteUser(userId))
                .expectSubscription()
                .expectNext(ResponseEntity.noContent().<Void>build())
                .verifyComplete();
    }

    @Test
    void addVideoToFavorites_Success() {
        String userId = "userId";
        User existingUser = new User("username", "password");
        Video video = new Video("videoId", "Video Title", "Video Description", "https://www.example.com", null, null, 0L);

        when(userService.addVideoToFavorites(anyString(), any(Video.class))).thenReturn(Mono.just(existingUser));

        StepVerifier.create(userController.addVideoToFavorites(userId, video))
                .expectSubscription()
                .expectNextMatches(responseEntity -> {
                    User responseBody = responseEntity.getBody();
                    return responseBody != null && responseBody.equals(existingUser);
                })
                .verifyComplete();
    }
}