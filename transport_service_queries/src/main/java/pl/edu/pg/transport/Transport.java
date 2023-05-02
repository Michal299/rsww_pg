package pl.edu.pg.transport;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document("transports")
public class Transport {

    @Id
    private Long id;

    private LocalDateTime departureDate;
    private String departureCountry;
    private String departureCity;
    private LocalDateTime arrivalDate;
    private String arrivalCountry;
    private String arrivalCity;
    private String meanOfTransport;
    private int seats;

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", departureDate=" + departureDate +
                ", departureCountry='" + departureCountry + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalDate=" + arrivalDate +
                ", arrivalCountry='" + arrivalCountry + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", meanOfTransport='" + meanOfTransport + '\'' +
                ", seats=" + seats +
                '}';
    }
}
