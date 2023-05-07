package pl.edu.pg.transport.command;

import lombok.Getter;
import pl.edu.pg.transport.entity.Flight;

import java.time.LocalDateTime;

@Getter
public class CreateFlightCommand {
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private int travelTime;
    private int placesCount;

    public static Flight commandToEntity(CreateFlightCommand command) {
        return new Flight(command.getDepartureAirport(), command.getArrivalAirport(),
                command.getDepartureDate(), command.getArrivalDate(),
                command.getTravelTime(), command.getPlacesCount());
    }
}
