package com.example.news_v2.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "comments")
@FieldNameConstants
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @ToString.Exclude
    private News news;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
