package pl.edu.pg.transport.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import pl.edu.pg.transport.entity.Flight;
import pl.edu.pg.transport.repository.FlightRepository;

@Component
public class EventDataStoreListener {
    private final static Logger logger = LoggerFactory.getLogger(EventDataStoreListener.class);

    private final FlightRepository repository;

    @Autowired
    public EventDataStoreListener(FlightRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.addFlightDataStore}")
    public void saveFlight(Message<Flight> message) {
        repository.save(message.getPayload()); // TODO: also send NotifyFlightAdded to trip_service
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.confirmFlightReservationDataStore}")
    public void updateFlight(Message<Flight> message) {
        repository.save(message.getPayload());
    }
}
