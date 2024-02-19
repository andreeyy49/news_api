package com.example.news_v2.web.controller;

import com.example.news_v2.mapper.UserMapper;
import com.example.news_v2.model.User;
import com.example.news_v2.service.UserService;
import com.example.news_v2.web.model.filter.ObjectFilter;
import com.example.news_v2.web.model.user.UpsertUserRequest;
import com.example.news_v2.web.model.user.UserListResponse;
import com.example.news_v2.web.model.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(@Valid ObjectFilter filter) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(
                        userService.findAll(filter)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        userService.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userMapper.userToResponse(newUser)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody @Valid UpsertUserRequest request){
        User upatedUser = userService.update(userMapper.requestToUser(request));

        return ResponseEntity.ok(
                userMapper.userToResponse(
                        upatedUser
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
