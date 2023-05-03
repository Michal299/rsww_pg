package pl.edu.pg.transport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transports")
@NoArgsConstructor
@Getter
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @Enumerated(EnumType.STRING)
    private MeanOfTransport meanOfTransport;

    private int seats;

    public Transport(LocalDateTime departureDate, String departureCountry, String departureCity,
                     LocalDateTime arrivalDate, String arrivalCountry, String arrivalCity) {
        this.departureDate = departureDate;
        this.departureCountry = departureCountry;
        this.departureCity = departureCity;
        this.arrivalDate = arrivalDate;
        this.arrivalCountry = arrivalCountry;
        this.arrivalCity = arrivalCity;
        this.meanOfTransport = MeanOfTransport.PLANE;
        this.seats = 40;
    }
}
