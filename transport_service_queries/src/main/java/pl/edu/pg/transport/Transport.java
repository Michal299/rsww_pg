package pl.edu.pg.transport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transports")
@Getter
public class Transport {

    @Id
    private Long id;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "departure_country")
    private String departureCountry;

    @Column(name = "departure_city")
    private String departureCity;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "arrival_country")
    private String arrivalCountry;

    @Column(name = "arrival_city")
    private String arrivalCity;

    @Column(name = "mean_of_transport")
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
