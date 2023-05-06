package pl.edu.pg.accommodation.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GetHotelsResponseEvent implements Event {
    private Long id;
    private String name;
    private int stars;
    private String place;
    private String photo;

}
