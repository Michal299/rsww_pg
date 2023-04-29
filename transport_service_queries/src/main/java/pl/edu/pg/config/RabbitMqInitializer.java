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

    private final Queue getFlightsQueue;

    private final Queue getFlightDetailsQueue;

    private final AmqpAdmin amqpAdmin;

    @Autowired
    public RabbitMqInitializer(Queue getFlightsQueue, Queue getFlightDetailsQueue, AmqpAdmin amqpAdmin) {
        this.getFlightsQueue = getFlightsQueue;
        this.getFlightDetailsQueue = getFlightDetailsQueue;
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    private synchronized void createQueues() {
        String queueName = amqpAdmin.declareQueue(getFlightsQueue);
        logger.info("Queue {} is created.", queueName);
        queueName = amqpAdmin.declareQueue(getFlightDetailsQueue);
        logger.info("Queue {} is created.", queueName);
    }
}
