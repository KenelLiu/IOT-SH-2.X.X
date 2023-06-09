package com.reactor.example.r2dbc;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

interface PostRepository extends R2dbcRepository<Post, UUID> {

    @Query("SELECT * FROM posts where title like :title")
    public Flux<Post> findByTitleContains(String title);

    public Mono<Long> countByTitleContaining(String title);

    public Flux<PostSummary> findByTitleLike(String title, Pageable pageable);
}

