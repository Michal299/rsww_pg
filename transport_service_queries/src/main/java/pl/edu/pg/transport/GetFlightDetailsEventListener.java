package pl.edu.pg.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetFlightDetailsEventListener {
    private final static Logger logger = LoggerFactory.getLogger(GetFlightDetailsEventListener.class);

    private final TransportRepository repository;

    @Autowired
    public GetFlightDetailsEventListener(TransportRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.getFlightDetailsQueue}")
    public void receiveMessage(String message) {
        logger.info("I'm here!!! Your message: {}", message);
        logger.info("Flight: {}", repository.findById(Long.parseLong(message)));
    }
}
