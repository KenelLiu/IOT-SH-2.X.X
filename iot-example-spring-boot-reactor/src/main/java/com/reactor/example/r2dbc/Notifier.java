package com.reactor.example.r2dbc;

import io.r2dbc.postgresql.api.PostgresqlConnection;
import io.r2dbc.postgresql.api.PostgresqlResult;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;

@Component
@Log4j2
class Notifier {

    @Autowired
    @Qualifier("pgConnectionFactory")
    ConnectionFactory pgConnectionFactory;

    PostgresqlConnection sender;

    @PostConstruct
    public void initialize() throws InterruptedException {
        sender = Mono.from(pgConnectionFactory.create())
                     .cast(PostgresqlConnection.class)
                     .block();
    }

    public Mono<Void> send() {
        return sender.createStatement("NOTIFY mymessage, 'Hello world at " + LocalDateTime.now() + "'")
                     .execute()
                     .flatMap(PostgresqlResult::getRowsUpdated)
                     .log("sending notification::")
                     .then();
    }

    @PreDestroy
    public void destroy() {
        sender.close().subscribe();
    }
}
