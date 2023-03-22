package pl.edu.pg.accommodation.ping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PingEventListener {
    private final static Logger logger = LoggerFactory.getLogger(PingEventListener.class);
    @RabbitListener(queues = "${spring.rabbitmq.ping.queue}")
    public void receiveMessage(String message) {
        logger.info("I'm here!!! Your message: {}", message);
    }
}
