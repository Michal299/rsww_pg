package pl.edu.pg.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetFlightsEventListener {
    private final static Logger logger = LoggerFactory.getLogger(GetFlightsEventListener.class);

    private final TransportRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public GetFlightsEventListener(TransportRepository repository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * A message should be in a correct format, for example:
     * {
     *      "source": "a queue name"
     * }
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue.getFlightsQueue}")
    public void receiveMessage(String message) {
        try {
            GetFlightsEvent getFlightsEvent = objectMapper.readValue(message, GetFlightsEvent.class);
            List<Transport> transports = repository.findAll();
            rabbitTemplate.convertAndSend("", getFlightsEvent.getSource(), objectMapper.writeValueAsString(transports));
            logger.info("All transports were sent to the {} queue.", getFlightsEvent.getSource());
        } catch (JsonProcessingException e) {
            logger.error("An error occurred during parsing GetFlightsEvent: {}", e.getMessage());
        }
    }
}
