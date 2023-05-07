package pl.edu.pg.transport.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import pl.edu.pg.transport.entity.Flight;
import pl.edu.pg.transport.query.GetFlightDetailsQuery;
import pl.edu.pg.transport.repository.FlightRepository;

import java.util.Optional;

@Component
public class GetFlightDetailsQueryListener {
    private final static Logger logger = LoggerFactory.getLogger(GetFlightDetailsQueryListener.class);

    private final FlightRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public GetFlightDetailsQueryListener(FlightRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.getFlightDetailsQueue}")
    public void receiveMessage(Message<GetFlightDetailsQuery> message) {
        long transportId = message.getPayload().getId();
        String sourceQueue = message.getPayload().getSource();
        Optional<Flight> maybeTransport = repository.findById(transportId);
        if (maybeTransport.isPresent()) {
            rabbitTemplate.convertAndSend(sourceQueue, maybeTransport.get());
            logger.info("Transport with id {} was sent to the {} queue.", transportId, sourceQueue);
        } else {
            rabbitTemplate.convertAndSend(sourceQueue, "Not Found");
            logger.info("Transport with id {} does not exist.", transportId);
        }
    }
}
