package com.example.news_v2.web.controller;

import com.example.news_v2.aop.ThisUser;
import com.example.news_v2.entity.Role;
import com.example.news_v2.entity.RoleType;
import com.example.news_v2.mapper.UserMapper;
import com.example.news_v2.entity.User;
import com.example.news_v2.service.UserService;
import com.example.news_v2.web.model.filter.ObjectFilter;
import com.example.news_v2.web.model.user.UpsertUserRequest;
import com.example.news_v2.web.model.user.UserListResponse;
import com.example.news_v2.web.model.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserListResponse> findAll(@Valid ObjectFilter filter) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(
                        userService.findAll(filter)
                )
        );
    }

    @GetMapping("/{id}")
    @ThisUser
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        userService.findById(id)
                )
        );
    }

    @PostMapping("/account")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request, @RequestParam RoleType roleType) {
        User newUser = userService.save(userMapper.requestToUser(request), roleType);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userMapper.userToResponse(newUser)
                );
    }

    @PutMapping("/{id}")
    @ThisUser
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId,
                                               @RequestBody @Valid UpsertUserRequest request){
        User upatedUser = userMapper.requestToUser(request);
        upatedUser.setId(userId);
        upatedUser = userService.update(upatedUser);

        return ResponseEntity.ok(
                userMapper.userToResponse(
                        upatedUser
                )
        );
    }

    @DeleteMapping("/{id}")
    @ThisUser
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
