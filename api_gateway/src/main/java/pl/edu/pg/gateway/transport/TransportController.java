package pl.edu.pg.gateway.transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.gateway.transport.dto.GetFlightDetailsResponse;

import java.util.List;

@RestController
@RequestMapping("api/transports")
class TransportController {
    private final TransportService transportService;

    @Autowired
    TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @CrossOrigin(origins = "http://localhost:80")
    @GetMapping("{id}")
    ResponseEntity<GetFlightDetailsResponse> getFlightDetails(@PathVariable Long id) {
        return transportService.getFlightDetails(id);
    }

    @CrossOrigin(origins = "http://localhost:80")
    @GetMapping
    ResponseEntity<List<GetFlightDetailsResponse>> getFlights() {
        return transportService.getFlights();
    }

}
