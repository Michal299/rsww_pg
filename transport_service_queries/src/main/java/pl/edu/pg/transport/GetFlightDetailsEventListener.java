package pl.edu.pg.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetFlightDetailsEventListener {
    private final static Logger logger = LoggerFactory.getLogger(GetFlightDetailsEventListener.class);

    private final TransportRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public GetFlightDetailsEventListener(TransportRepository repository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * A message should be in a correct format, for example:
     * {
     *      "id": 1,
     *      "source": "a queue name"
     * }
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue.getFlightDetailsQueue}")
    public void receiveMessage(String message) {
        try {
            GetFlightDetailsEvent getFlightDetailsEvent = objectMapper.readValue(message, GetFlightDetailsEvent.class);
            Optional<Transport> transport = repository.findById(getFlightDetailsEvent.getId());
            if (transport.isPresent()) {
                rabbitTemplate.convertAndSend("", getFlightDetailsEvent.getSource(), objectMapper.writeValueAsString(transport.get()));
                logger.info("Transport with id {} was sent to the {} queue.", getFlightDetailsEvent.getId(), getFlightDetailsEvent.getSource());
            } else {
                rabbitTemplate.convertAndSend("", getFlightDetailsEvent.getSource(), "Not Found");
                logger.info("Transport with id {} does not exist.", getFlightDetailsEvent.getId());
            }
        } catch (JsonProcessingException e) {
            logger.error("An error occurred during parsing GetFlightDetailsEvent: {}", e.getMessage());
        }
    }
}
