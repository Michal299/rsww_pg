package pl.edu.pg.accommodation.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

    private final String queue;
    private final String username;
    private final String password;
    private final String host;

    public RabbitMQConfig(@Value("${spring.rabbitmq.ping.queue}") String queue,
                          @Value("${spring.rabbitmq.username}") String username,
                          @Value("${spring.rabbitmq.password}") String password,
                          @Value("${spring.rabbitmq.host}") String host) {

        this.queue = queue;
        this.username = username;
        this.password = password;
        this.host = host;
    }

    @Bean
    Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }
}
