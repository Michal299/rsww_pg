package pl.edu.pg.accommodation.hotel.listener.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import pl.edu.pg.accommodation.event.Event;
import pl.edu.pg.accommodation.hotel.entity.HotelEntity;

import java.util.Set;
import java.util.function.Function;

@Data
@Builder
@Jacksonized
public class AddHotelEvent implements Event {

    private String hotelName;
    private String city;
    private String country;
    private int stars;
    private String photo;
    private Set<Room> rooms;

    public static Function<AddHotelEvent, HotelEntity> toEntityMapper() {
        return (event) -> {
            final var entity = new HotelEntity();
            entity.setName(event.getHotelName());
            entity.setCity(event.getCity());
            entity.setCountry(event.getCountry());
            entity.setStars(event.getStars());
            return entity;
        };
    }

    @Data
    @Builder
    @Jacksonized
    public static class Room {
        private int capacity;
        private String name;
        private String features;
        private int numberOfRooms;
    }
}
