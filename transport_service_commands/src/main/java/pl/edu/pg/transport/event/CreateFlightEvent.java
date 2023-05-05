package pl.edu.pg.transport.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edu.pg.transport.entity.Transport;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CreateFlightEvent {
    private LocalDateTime departureDate;
    private String departureCountry;
    private String departureCity;
    private LocalDateTime arrivalDate;
    private String arrivalCountry;
    private String arrivalCity;

    public static Transport eventToEntity(CreateFlightEvent createFlightEvent) {
        return new Transport(createFlightEvent.departureDate, createFlightEvent.departureCountry, createFlightEvent.departureCity,
                createFlightEvent.arrivalDate, createFlightEvent.arrivalCountry, createFlightEvent.arrivalCity);
    }
}
