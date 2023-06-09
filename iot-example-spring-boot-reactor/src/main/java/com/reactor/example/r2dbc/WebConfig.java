package com.reactor.example.r2dbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

@Configuration
class WebConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(
            PostHandler postController,
            CommentHandler commentHandler,
            Notifier notifier) {
        return route()
                .GET("/hello", request -> notifier
                        .send()
                        .flatMap((v) -> noContent().build())
                )
                .path("/posts", () -> route()
                        .nest(
                                path(""),
                                () -> route()
                                        .GET("", postController::all)
                                        .POST("", postController::create)
                                        .build()
                        )
                        .nest(
                                path("{id}"),
                                () -> route()
                                        .GET("", postController::get)
                                        .PUT("", postController::update)
                                        .DELETE("", postController::delete)
                                        .nest(
                                                path("comments"),
                                                () -> route()
                                                        .GET("", commentHandler::getByPostId)
                                                        .POST("", commentHandler::create)
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
                ).build();
    }
}
