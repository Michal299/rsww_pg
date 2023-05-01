package pl.edu.pg.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqInitializer {
    private final static Logger logger = LoggerFactory.getLogger(RabbitMqInitializer.class);

    private final Queue createFlightQueue;

    private final Queue eventDataStore;

    private final AmqpAdmin amqpAdmin;

    @Autowired
    public RabbitMqInitializer(Queue createFlightQueue, Queue eventDataStore, AmqpAdmin amqpAdmin) {
        this.createFlightQueue = createFlightQueue;
        this.eventDataStore = eventDataStore;
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    private synchronized void createQueues() {
        String queueName = amqpAdmin.declareQueue(createFlightQueue);
        logger.info("Queue {} is created.", queueName);
        queueName = amqpAdmin.declareQueue(eventDataStore);
        logger.info("Queue {} is created.", queueName);
    }
}
