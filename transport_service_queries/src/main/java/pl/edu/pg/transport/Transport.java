package pl.edu.pg.transport;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transports")
@NoArgsConstructor
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "mean_of_transport")
    @Enumerated(EnumType.STRING)
    private MeanOfTransport meanOfTransport;

    private String message;

    public Transport(LocalDateTime departureDate, LocalDateTime arrivalDate, String message) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.meanOfTransport = MeanOfTransport.PLANE;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", meanOfTransport=" + meanOfTransport +
                ", message='" + message + '\'' +
                '}';
    }
}
