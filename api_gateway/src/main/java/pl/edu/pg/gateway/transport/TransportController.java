package pl.edu.pg.gateway.transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transports")
class TransportController {
    private final TransportService transportService;

    @Autowired
    TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping("{id}")
    ResponseEntity<Object> getFlightDetails(@PathVariable Long id) {
        return transportService.getFlightDetails(id);
    }

}
