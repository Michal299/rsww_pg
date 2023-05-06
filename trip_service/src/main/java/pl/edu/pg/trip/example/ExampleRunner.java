package pl.edu.pg.trip.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class ExampleRunner implements CommandLineRunner {

    private final ExampleClient exampleClient;

    @Autowired
    public ExampleRunner(ExampleClient exampleClient) {
        this.exampleClient = exampleClient;
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < 10; i++) {
            exampleClient.send("Message " + i);
        }
    }
}
