package pl.edu.pg.trip.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PingDto {
    private String message;
}
