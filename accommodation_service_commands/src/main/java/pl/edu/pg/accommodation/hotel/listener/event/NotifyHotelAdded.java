package pl.edu.pg.accommodation.hotel.listener.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import pl.edu.pg.accommodation.hotel.entity.HotelEntity;

import java.util.function.Function;

@Data
@Builder
@Jacksonized
public class NotifyHotelAdded {
    private long id;
    private String name;
    private String country;
    private String city;
    private int stars;
    private String description;
    private String photo;
    private String airport;
    private String food;
    public static Function<HotelEntity, NotifyHotelAdded> entityToDtoMapper() {
        return (entity) -> NotifyHotelAdded.builder()
                .id(entity.getId())
                .name(entity.getName())
                .country(entity.getCountry())
                .city(entity.getCity())
                .stars(entity.getStars())
                .description(entity.getDescription())
                .photo(entity.getPhoto())
                .airport(entity.getAirport())
                .food(entity.getFood())
                .build();
    }
}
