package pl.edu.pg.reservation.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.reservation.command.CancelFlightReservationCommand;
import pl.edu.pg.reservation.command.ConfirmFlightReservationCommand;
import pl.edu.pg.reservation.command.ReserveFlightCommand;
import pl.edu.pg.reservation.entity.Reservation;
import pl.edu.pg.reservation.repository.ReservationRepository;
import pl.edu.pg.transport.entity.Flight;
import pl.edu.pg.transport.repository.FlightRepository;

import java.util.Optional;

@Component
public class ReservationCommandsListener {
    private final static Logger logger = LoggerFactory.getLogger(ReservationCommandsListener.class);

    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ReservationCommandsListener(FlightRepository flightRepository, ReservationRepository reservationRepository, RabbitTemplate rabbitTemplate) {
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.reserveFlightQueue}")
    public void reserveFlightListener(ReserveFlightCommand message) {
        Optional<Flight> maybeFlight = flightRepository.findById(message.getFlightId());
        if (maybeFlight.isEmpty()) {
            logger.info("Cannot create a reservation for flight that does not exist. FlightId: {}", message.getFlightId());
        } else { // what if there are no seats
            Reservation reservation = ReserveFlightCommand.commandToEntityMapper(message, maybeFlight.get());
            Reservation savedReservation = reservationRepository.save(reservation);
            logger.info("Created reservation: {}", savedReservation);
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.cancelFlightReservationQueue}")
    public void cancelFlightReservationListener(CancelFlightReservationCommand message) {
        Optional<Reservation> maybeReservation = reservationRepository.findById(message.getReservationId());
        if (maybeReservation.isEmpty()) {
            logger.info("Cannot cancel a reservation that does not exist. ReservationId: {}", message.getReservationId());
        } else {
            reservationRepository.delete(maybeReservation.get());
            logger.info("Canceled reservation: {}", message.getReservationId());
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.confirmFlightReservationQueue}")
    public void confirmFlightReservationListener(ConfirmFlightReservationCommand message) {
        // TODO
    }
}
