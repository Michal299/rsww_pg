package pl.edu.pg.gateway.transport.query;

import lombok.Builder;

@Builder
public class GetFlightDetailsQuery {
    private long id;

    public long getId() {
        return id;
    }
}
