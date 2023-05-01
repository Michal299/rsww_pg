package pl.edu.pg.transport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDataStoreListener {
    private final static Logger logger = LoggerFactory.getLogger(EventDataStoreListener.class);

    private final TransportRepository repository;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventDataStoreListener(TransportRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.eventDataStore}")
    public void receiveMessage(String message) {
        try {
            Transport transport = objectMapper.readValue(message, Transport.class);
            repository.save(transport);
        } catch (JsonProcessingException e) {
            logger.error("An error occurred during parsing EventDataStore to Transport: {}", e.getMessage());
        }
    }
}