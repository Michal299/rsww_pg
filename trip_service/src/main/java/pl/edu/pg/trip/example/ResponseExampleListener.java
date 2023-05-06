package pl.edu.pg.trip.example;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.edu.pg.trip.example.dto.PingResponseDto;

@Component
public class ResponseExampleListener {

    @RabbitListener(queues = "example.client.response")
    public void receiveResponse(PingResponseDto responseDto, Message message) {
        final var correlationId = message.getMessageProperties().getCorrelationId();
        System.out.println("Received " + responseDto + " with id: " + correlationId);
    }
}
