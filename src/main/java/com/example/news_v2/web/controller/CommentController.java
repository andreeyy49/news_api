package com.example.news_v2.web.controller;

import com.example.news_v2.aop.Author;
import com.example.news_v2.aop.ThisUser;
import com.example.news_v2.entity.User;
import com.example.news_v2.mapper.CommentMapper;
import com.example.news_v2.entity.Comment;
import com.example.news_v2.service.CommentService;
import com.example.news_v2.service.UserService;
import com.example.news_v2.web.model.comment.CommentResponse;
import com.example.news_v2.web.model.comment.UpsertCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                commentMapper.commentToResponse(
                        commentService.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        Comment newComments = commentMapper.requestToComment(request);
        User user = userService.findByUsername(userDetails.getUsername());
        newComments.setUser(user);
        newComments = commentService.save(newComments);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                commentMapper.commentToResponse(
                        newComments
                )
        );
    }

    @Author
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId,
                                                   @RequestBody @Valid UpsertCommentRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails){
        Comment updatedComments = commentMapper.requestToComment(commentId, request);
        User user = userService.findByUsername(userDetails.getUsername());
        updatedComments.setUser(user);
        updatedComments = commentService.update(updatedComments);

        return ResponseEntity.ok(
                commentMapper.commentToResponse(updatedComments)
        );
    }

    @ThisUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        commentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
