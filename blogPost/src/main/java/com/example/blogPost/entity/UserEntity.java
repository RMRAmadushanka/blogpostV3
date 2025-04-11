package com.example.blogPost.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table("users")
public class UserEntity {
    @Id
    private Long id;

    private String username;
    private String email;

    private Long subscriptionId;

    @Transient
    private List<BlogPostEntity> blogPosts;

    @Transient
    private SubscriptionPlanEntity subscriptionPlan;
}
