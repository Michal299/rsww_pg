package pl.edu.pg.gateway.trip;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import pl.edu.pg.gateway.trip.dto.GetDeparturesRequest;
import pl.edu.pg.gateway.trip.dto.GetDeparturesResponse;
import pl.edu.pg.gateway.trip.dto.GetDestinationRequest;
import pl.edu.pg.gateway.trip.dto.GetDestinationsResponse;
import pl.edu.pg.gateway.trip.dto.NotificationResponse;
import pl.edu.pg.gateway.trip.dto.TripDetailsRequest;
import pl.edu.pg.gateway.trip.dto.TripDetailsResponse;
import pl.edu.pg.gateway.trip.dto.TripsRequest;
import pl.edu.pg.gateway.trip.dto.TripsResponse;
import pl.edu.pg.gateway.trip.dto.reservation.PostReservationRequest;
import pl.edu.pg.gateway.trip.dto.reservation.PostReservationResponse;
import pl.edu.pg.gateway.trip.dto.reservation.TripReservationPayment;
import pl.edu.pg.gateway.trip.dto.reservation.TripReservationPaymentResponse;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TripService {
    private final Logger log = LoggerFactory.getLogger(TripService.class);
    private final RabbitTemplate rabbitTemplate;
    private final String getTripsQueueName;
    private final String getTripDetailsQueueName;
    private final String getDestinationsQueueName;
    private final String getDeparturesQueueName;
    private final String reserveTripQueueName;
    private final String rollbackReservationTripQueueName;
    private final String paymentQueue;
    private final int timeoutInSeconds;
    private final List<Long> reservedIdTrips;
    private final Map<Long, TripDetailsResponse> cacheForGetTrip;

    @Autowired
    TripService(final RabbitTemplate rabbitTemplate,
                @Value("${spring.rabbitmq.queue.trip.get.all}") final String getTripsQueueName,
                @Value("${spring.rabbitmq.queue.trip.get.details}") final String getTripDetailsQueueName,
                @Value("${spring.rabbitmq.queue.hotel.destinations}") final String getDestinationsQueueName,
                @Value("${spring.rabbitmq.queue.hotels.departures}") final String getDeparturesQueueName,
                @Value("${spring.rabbitmq.queue.trips.reserve}") final String reserveTripQueueName,
                @Value("${spring.rabbitmq.queue.trips.reserve.rollback}") final String rollbackReservationTripQueueName,
                @Value("${spring.rabbitmq.queue.trips.reservations.payment}") final String paymentQueue,
                @Value("${spring.rabbitmq.timeout}") final String timeoutInSeconds) {
        this.rabbitTemplate = rabbitTemplate;
        this.getTripsQueueName = getTripsQueueName;
        this.getTripDetailsQueueName = getTripDetailsQueueName;
        this.getDestinationsQueueName = getDestinationsQueueName;
        this.getDeparturesQueueName = getDeparturesQueueName;
        this.reserveTripQueueName = reserveTripQueueName;
        this.rollbackReservationTripQueueName = rollbackReservationTripQueueName;
        this.paymentQueue = paymentQueue;
        this.timeoutInSeconds = Integer.parseInt(timeoutInSeconds);
        this.reservedIdTrips = new ArrayList<>();
        this.cacheForGetTrip = new HashMap<>();
    }

    public Optional<TripsResponse> getTrips(final SearchParams searchParams) {
        final TripsRequest request = SearchParams.requestMapper().apply(searchParams);
        final TripsResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                getTripsQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        return Optional.ofNullable(response);
    }

    public Optional<TripDetailsResponse> getTrip(final Long id) {
        final var request = TripDetailsRequest.builder().tripId(id).build();
        final TripDetailsResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                getTripDetailsQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        if (response != null) {
            cacheForGetTrip.put(id, response);
        }
        return Optional.ofNullable(response);
    }

    public List<String> getDestinations() {
        final var request = GetDestinationRequest.builder().build();
        final GetDestinationsResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                getDestinationsQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getDestinations();
    }

    public List<String> getPossibleDepartures() {
        final var request = GetDeparturesRequest.builder().build();
        final GetDeparturesResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                getDeparturesQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getDepartures();
    }

    public boolean reserve(final Long tripId, PostReservationRequest reservationRequest) {
        final PostReservationResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                reserveTripQueueName,
                reservationRequest,
                new ParameterizedTypeReference<>() {
                }
        );
        boolean reserved = response.isReserved();
        if (reserved) {
            reservedIdTrips.add(tripId);
        }
        return reserved;
    }

    public boolean rollbackReservation(final Long tripId, PostReservationRequest reservationRequest) {
        final PostReservationResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                rollbackReservationTripQueueName,
                reservationRequest,
                new ParameterizedTypeReference<>() {
                }
        );
        return true;
    }

    public boolean payForTrip(Integer reservationId, Long userId) {
        TripReservationPayment dto = TripReservationPayment.builder()
                .reservationId(reservationId)
                .userId(userId)
                .build();
        final TripReservationPaymentResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                paymentQueue,
                dto,
                new ParameterizedTypeReference<>() {
                });


        return response.getStatus() >= 200 && response.getStatus() < 300;//        try {
//
//
//        } catch (ExecutionException | InterruptedException | NullPointerException e) {
//            log.error("Exception during processing the payment communication: ", e);
//        }
//        return false;
    }

    public List<NotificationResponse> getNotifications(Long tripId) {
        if (reservedIdTrips.contains(tripId)) {
            new Thread(new DeletionAfterTimeout(reservedIdTrips, Collections.singletonList(tripId), timeoutInSeconds * 1_000)).start();
            return Collections.singletonList(NotificationResponse
                    .builder()
                    .notification("Właśnie ta wycieczka została zarezerwowana lub kupiona przez kogoś innego")
                    .build()
            );
        }
        return Collections.emptyList();
    }

    public List<NotificationResponse> getNotifications(String destination) {
        List<Long> reservedIdTripsToRemove = new ArrayList<>();
        List<TripDetailsResponse> reservedTrips = new ArrayList<>();
        for (Long key : cacheForGetTrip.keySet()) {
            if (reservedIdTrips.contains(key)) {
                reservedTrips.add(cacheForGetTrip.get(key));
                reservedIdTripsToRemove.add(key);
            }
        }
        if (!destination.equals("all")) {
            reservedTrips = reservedTrips.stream().filter(reservedTrip -> reservedTrip.getHotel().getCountry().equals(destination)).collect(Collectors.toList());
        }
        String parsedDestination = destination.equals("all") ? "" : " do " + destination;
        new Thread(new DeletionAfterTimeout(reservedIdTrips, reservedIdTripsToRemove, timeoutInSeconds * 1_000)).start();
        return reservedTrips
                .stream()
                .map(reservedTrip -> NotificationResponse
                        .builder()
                        .notification("Właśnie kupiono lub zarezerwowano wycieczkę" + parsedDestination + " w hotelu " + reservedTrip.getHotel().getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Data
    @Builder
    @Jacksonized
    public static class SearchParams {
        private String destination;
        private String departure;
        private String startDate;
        private Integer adults;
        private Integer people3To9;
        private Integer people10To17;

        public static Function<SearchParams, TripsRequest> requestMapper() {
            return params -> TripsRequest.builder()
                    .adults(params.adults)
                    .departure(params.departure)
                    .destination(params.destination)
                    .people10To17(params.people10To17)
                    .people3To9(params.people3To9)
                    .startDate(params.startDate)
                    .build();
        }
    }
}
