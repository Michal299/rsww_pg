package pl.edu.pg.transport;

public class GetFlightDetailsEvent {
    private long id;
    private String source;

    public long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }
}
