package pl.edu.pg.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateFlightEventListener {
    private final static Logger logger = LoggerFactory.getLogger(CreateFlightEventListener.class);

    private final TransportRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue eventDataStore;
    private final ObjectMapper objectMapper;

    @Autowired
    public CreateFlightEventListener(TransportRepository repository, RabbitTemplate rabbitTemplate, Queue eventDataStore) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.eventDataStore = eventDataStore;
        this.objectMapper = new ObjectMapper();
        configureObjectMapper();
    }

    private void configureObjectMapper() {
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * A message should be in a correct format, for example:
     * {
     *      "departureDate": "2023-04-24T14:15",
     *      "departureCountry": "Spain",
     *      "departureCity": "Barcelona",
     *      "arrivalDate": "2023-04-24T18:15",
     *      "arrivalCountry": "Greece",
     *      "arrivalCity": "Athens"
     * }
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue.createFlightQueue}")
    public void receiveMessage(String message) {
        try {
            TransportDto transportDto = objectMapper.readValue(message, TransportDto.class);
            Transport transport = TransportDto.dtoToEntity(transportDto);
            Transport savedTransport = repository.save(transport);
            rabbitTemplate.convertAndSend("", eventDataStore.getName(), objectMapper.writeValueAsString(savedTransport));
        } catch (JsonProcessingException e) {
            logger.error("An error occurred during parsing CreateFlightEvent to TransportDto: {}", e.getMessage());
        }
    }
}
