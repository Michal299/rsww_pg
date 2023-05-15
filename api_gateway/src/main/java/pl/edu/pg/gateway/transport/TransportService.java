package pl.edu.pg.gateway.transport;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pg.gateway.transport.dto.GetFlightDetailsQuery;
import pl.edu.pg.gateway.transport.dto.GetFlightDetailsResponse;

@Service
class TransportService {
    private final RabbitTemplate rabbitTemplate;
    private static final String GET_FLIGHT_DETAILS_QUEUE = "GetFlightDetailsQueue";

    @Autowired
    TransportService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    ResponseEntity<GetFlightDetailsResponse> getFlightDetails(Long id) {
        GetFlightDetailsQuery query = GetFlightDetailsQuery.builder()
                .id(id)
                .build();
        GetFlightDetailsResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                GET_FLIGHT_DETAILS_QUEUE,
                query,
                new ParameterizedTypeReference<>() {
                });
        if (response != null && response.getId() == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
