package com.reactor.example.webFlux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class GreetingHandler {


	public Mono<ServerResponse> hello(ServerRequest request) {
		System.out.println("GreetingHandler...");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(new Greeting("Hello, Spring!")));
	}

	@GetMapping("/hello2")
	public Mono<String> hello() {
		System.out.println("hello2 GreetingHandler...");
		return Mono.just("hello").delayElement(Duration.ofMillis(1000));
	}

}
