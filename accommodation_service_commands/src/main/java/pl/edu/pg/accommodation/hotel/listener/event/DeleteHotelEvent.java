package pl.edu.pg.accommodation.hotel.listener.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import pl.edu.pg.accommodation.event.Event;

@Data
@Builder
@Jacksonized
public class DeleteHotelEvent implements Event {
    private Long hotelId;
}
