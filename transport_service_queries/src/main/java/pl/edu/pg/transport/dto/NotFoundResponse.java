package pl.edu.pg.transport.dto;

public class NotFoundResponse implements Response {
    private final String message = "Not Found";

    public String getMessage() {
        return message;
    }
}
