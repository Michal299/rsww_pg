package pl.edu.pg.gateway.transport;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pg.gateway.transport.dto.GetFlightDetailsDto;

@Service
class TransportService {
    private final RabbitTemplate rabbitTemplate;
    private static final String ROUTING_KEY = "GetFlightDetailsQueue";

    @Autowired
    TransportService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    ResponseEntity<Object> getFlightDetails(Long id) {
        GetFlightDetailsDto getFlightDetailsDto = GetFlightDetailsDto.builder()
                .id(id)
                .build();
        var getFlightDetailsResponse = rabbitTemplate.convertSendAndReceiveAsType(
                ROUTING_KEY,
                getFlightDetailsDto,
                new ParameterizedTypeReference<>() {
                });
        return new ResponseEntity<>(getFlightDetailsResponse, HttpStatus.OK);
    }
}
