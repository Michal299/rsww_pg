package pl.edu.pg.trip.example;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import pl.edu.pg.trip.example.dto.PingDto;
import pl.edu.pg.trip.example.dto.PingResponseDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class ExampleClient {

    private final AsyncRabbitTemplate rabbitTemplate;
    public static final String ROUTING_KEY = "example.client";

    @Autowired
    public ExampleClient(AsyncRabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        PingDto ping = PingDto.builder()
                .message(message)
                .build();
        CompletableFuture<PingResponseDto> responseDtoCompletableFuture = rabbitTemplate.convertSendAndReceiveAsType(
                ROUTING_KEY,
                ping,
                new ParameterizedTypeReference<>() {
                });

        System.out.println("Not waiting for the response for " + message);

        try {
            final var response = responseDtoCompletableFuture.get();
            System.out.println("Received " + response);
        } catch (InterruptedException | ExecutionException ignore) {

        }
    }
}
