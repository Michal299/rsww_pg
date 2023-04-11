package pl.edu.pg.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransportEventListener {
    private final static Logger logger = LoggerFactory.getLogger(TransportEventListener.class);

    private final TransportRepository repository;

    @Autowired
    public TransportEventListener(TransportRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(String message) {
        logger.info("I'm here!!! Your message: {}", message);
        repository.save(new Transport(LocalDateTime.now(), LocalDateTime.now(), message));
        logger.info("Pings so far: {}", repository.findAll());
    }
}
