package pl.edu.pg.transport;

import lombok.Getter;

@Getter
public class GetFlightDetailsEvent {
    private long id;
    private String source;
}
