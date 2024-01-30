package com.fiap.techchallenge.streamingtech.controller;

import com.fiap.techchallenge.streamingtech.model.User;
import com.fiap.techchallenge.streamingtech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
        return userService.createUser(user)
                .map(createdUser -> ResponseEntity.ok().body(createdUser))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}/updatePassword")
    public Mono<ResponseEntity<User>> updateUserPassword(@PathVariable String id, @RequestBody User user) {
        return userService.updateUserPassword(id, user)
                .map(updatedUser -> ResponseEntity.ok().body(updatedUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/updateFavoriteVideos")
    public Mono<ResponseEntity<User>> updateUserFavoriteVideos(@PathVariable String id, @RequestBody User user) {
        return userService.updateUserFavoriteVideos(id, user)
                .map(updatedUser -> ResponseEntity.ok().body(updatedUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
