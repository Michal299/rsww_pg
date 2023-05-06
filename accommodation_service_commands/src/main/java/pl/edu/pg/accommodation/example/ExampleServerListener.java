package pl.edu.pg.accommodation.example;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.edu.pg.accommodation.example.dto.PingDto;
import pl.edu.pg.accommodation.example.dto.PingResponseDto;

@Component
public class ExampleServerListener {

    @RabbitListener(queues = "example.client")
    public PingResponseDto pingListener(PingDto request) {
        System.out.println("[EXAMPLE]: " + request);
        final var response = PingResponseDto.builder()
                .responseMessage(request.getMessage() + " from server")
                .build();
        System.out.println("[RESPONSE] " + response);
        return response;
    }
}
