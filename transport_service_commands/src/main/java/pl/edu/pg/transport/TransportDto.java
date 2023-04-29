package pl.edu.pg.transport;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class TransportDto {
    private LocalDateTime departureDate;
    private String departureCountry;
    private String departureCity;
    private LocalDateTime arrivalDate;
    private String arrivalCountry;
    private String arrivalCity;

    public static Transport dtoToEntity(TransportDto transportDto) {
        return new Transport(transportDto.departureDate, transportDto.departureCountry, transportDto.departureCity,
                transportDto.arrivalDate, transportDto.arrivalCountry, transportDto.arrivalCity);
    }
}
