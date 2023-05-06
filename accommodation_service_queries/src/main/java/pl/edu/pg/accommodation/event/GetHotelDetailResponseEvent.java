package pl.edu.pg.accommodation.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Data
@Builder
@Jacksonized
public class GetHotelDetailResponseEvent implements Event {
    private String id;
    private String country;
    private String city;
    private int stars;
    private String description;
    private String photo;
    private Set<Room> rooms;
    private String airport;
    private String food;

    @Data
    @Builder
    @Jacksonized
    public static final class Room {
        private int capacity;
        private String name;
        private String features;
    }
}
