package com.reactor.example.r2dbc;

import lombok.Value;

import java.util.UUID;

@Value
class PostSummary {
    UUID id;
    String title;
}