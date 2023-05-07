package pl.edu.pg.transport.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document("flights")
public class Flight {

    @Id
    private Long id;

    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private int travelTime;
    private int placesCount;
    private int placesOccupied;
}
