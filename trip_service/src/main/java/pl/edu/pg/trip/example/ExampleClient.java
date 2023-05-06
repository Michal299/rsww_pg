package pl.edu.pg.trip.example;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import pl.edu.pg.trip.example.dto.PingDto;
import pl.edu.pg.trip.example.dto.PingResponseDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class ExampleClient {

    private final RabbitTemplate rabbitTemplate;
    public static final String ROUTING_KEY = "example.client";

    @Autowired
    public ExampleClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        PingDto ping = PingDto.builder()
                .message(message)
                .build();

        final var correlationId = UUID.randomUUID();
        MessagePostProcessor messagePostProcessor = message1 -> {
            final var properties = message1.getMessageProperties();
            properties.setReplyTo("example.client.response");
            properties.setCorrelationId(correlationId.toString());
            return message1;
        };
        System.out.println("Send: " + ping + " with id: " + correlationId);
        rabbitTemplate.convertAndSend("example.client", ping, messagePostProcessor);
    }
}
