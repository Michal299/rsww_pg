package pl.edu.pg.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateFlightEventListener {
    private final static Logger logger = LoggerFactory.getLogger(CreateFlightEventListener.class);

    private final TransportRepository repository;

    private final ObjectMapper objectMapper;

    @Autowired
    public CreateFlightEventListener(TransportRepository repository) {
        this.repository = repository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * A message should be in a correct format, for example:
     * {
     *      "departureDate": "2023-04-24T14:15:00",
     *      "departureCountry": "Spain",
     *      "departureCity": "Barcelona",
     *      "arrivalDate": "2023-04-24T18:15:00",
     *      "arrivalCountry": "Greece",
     *      "arrivalCity": "Athens"
     * }
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue.createFlightQueue}")
    public void receiveMessage(String message) {
        try {
            TransportDto transportDto = objectMapper.readValue(message, TransportDto.class);
            Transport transport = TransportDto.dtoToEntity(transportDto);
            repository.save(transport);
            logger.info("Created transport: {}", transport);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred during parsing CreateFlightEvent to TransportDto: {}", e.getMessage());
        }
    }
}
