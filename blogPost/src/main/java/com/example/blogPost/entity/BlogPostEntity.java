package com.example.blogPost.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "blogpost")
public class BlogPostEntity {
    @Id
    private Long id;

    private String title;

    @Column("content")
    private String content;

    @Column("user_id")
    private Long userId;

    @Column("creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column("image_url")
    private String imageUrl;

    private int views = 0;
    private int likes = 0;
}
