package pl.edu.pg.transport.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edu.pg.transport.entity.Flight;

import java.time.LocalDateTime;
import java.util.function.Function;

@Builder
@Getter
public class GetFlightDetailsResponse {
    private final String departureAirport;
    private final String arrivalAirport;
    private final LocalDateTime departureDate;
    private final LocalDateTime arrivalDate;
    private final int travelTime;
    private final int placesCount;
    private final int placesOccupied;

    public static Function<Flight, GetFlightDetailsResponse> entityToDtoMapper() {
        return flight -> GetFlightDetailsResponse
                .builder()
                .departureAirport(flight.getDepartureAirport())
                .arrivalAirport(flight.getArrivalAirport())
                .departureDate(flight.getDepartureDate())
                .arrivalDate(flight.getArrivalDate())
                .travelTime(flight.getTravelTime())
                .placesCount(flight.getPlacesCount())
                .placesOccupied(flight.getPlacesOccupied())
                .build();
    }
}
