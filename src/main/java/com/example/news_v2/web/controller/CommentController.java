package com.example.news_v2.web.controller;

import com.example.news_v2.aop.Author;
import com.example.news_v2.mapper.CommentMapper;
import com.example.news_v2.model.Comment;
import com.example.news_v2.repository.CommentRepository;
import com.example.news_v2.service.CommentService;
import com.example.news_v2.web.model.comment.CommentListResponse;
import com.example.news_v2.web.model.comment.CommentResponse;
import com.example.news_v2.web.model.comment.UpsertCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comments;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                commentMapper.commentToResponse(
                        commentService.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request){
        Comment newComments = commentService.save(commentMapper.requestToComment(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                commentMapper.commentToResponse(
                        newComments
                )
        );
    }

    @Author
    @PutMapping("/{id}&{userId}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId,
                                                   @RequestBody @Valid UpsertCommentRequest request){
        Comment updatedComments = commentService.update(commentMapper.requestToComment(commentId, request));

        return ResponseEntity.ok(
                commentMapper.commentToResponse(updatedComments)
        );
    }

    @Author
    @DeleteMapping("/{id}&{userId}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        commentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
