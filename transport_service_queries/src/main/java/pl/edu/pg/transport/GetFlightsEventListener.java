package pl.edu.pg.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetFlightsEventListener {
    private final static Logger logger = LoggerFactory.getLogger(GetFlightsEventListener.class);

    private final TransportRepository repository;

    @Autowired
    public GetFlightsEventListener(TransportRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.getFlightsQueue}")
    public void receiveMessage(String message) {
        logger.info("I'm here!!! Your message: {}", message);
        logger.info("Flights so far: {}", repository.findAll());
    }
}
