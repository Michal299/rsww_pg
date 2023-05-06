package pl.edu.pg.accommodation.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PingResponseDto {
    private String responseMessage;
}
