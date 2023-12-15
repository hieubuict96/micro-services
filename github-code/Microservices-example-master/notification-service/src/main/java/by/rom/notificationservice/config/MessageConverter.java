package by.rom.notificationservice.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConverter {

    @Bean
    public org.springframework.amqp.support.converter.MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
}
