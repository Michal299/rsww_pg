package pl.edu.pg.trip.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(
            RabbitTemplate rabbitTemplate){
        return new AsyncRabbitTemplate(rabbitTemplate);
    }

    @Bean(name = "exampleResponseQueue")
    public Queue exampleResponseQueue() {
        return new Queue("example.client.response", true);
    }
}
