package pl.edu.pg.transport.dto;

import lombok.Getter;

@Getter
public class NotFoundResponse implements Response {
    private final String message = "Not Found";
}
