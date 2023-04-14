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

    private final Queue queue;

    private final AmqpAdmin amqpAdmin;

    @Autowired
    public RabbitMqInitializer(Queue queue, AmqpAdmin amqpAdmin) {
        this.queue = queue;
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    private synchronized void createQueue() {
        String queueName = amqpAdmin.declareQueue(queue);
        logger.info("Queue {} is created.", queueName);
    }
}
