package com.example.news_v2.mapper;

import com.example.news_v2.model.Comment;
import com.example.news_v2.web.model.comment.CommentListResponse;
import com.example.news_v2.web.model.comment.CommentResponse;
import com.example.news_v2.web.model.comment.UpsertCommentRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment requestToComment(UpsertCommentRequest request);

    @Mapping(source = "commentsId", target = "id")
    Comment requestToComment(Long commentsId, UpsertCommentRequest request);

    CommentResponse commentToResponse(Comment comments);

    default CommentListResponse commentListToResponseList(List<Comment> comments){
        CommentListResponse response = new CommentListResponse();

        response.setComments(comments.stream()
                .map(this::commentToResponse)
                .collect(Collectors.toList()));

        return response;
    }
}
