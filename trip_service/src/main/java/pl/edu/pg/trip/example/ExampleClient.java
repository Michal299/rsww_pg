package pl.edu.pg.trip.example;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import pl.edu.pg.trip.example.dto.PingDto;

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
        final var responsePingMessage = rabbitTemplate.convertSendAndReceiveAsType(
                ROUTING_KEY,
                ping,
                new ParameterizedTypeReference<>() {
                });
        System.out.println(responsePingMessage);
    }
}
