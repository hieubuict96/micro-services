package by.rom.customerservice.client;

import by.rom.customerservice.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationClient {

    private final WebClient.Builder webClient;

    public void sendEmail(EmailDto emailDto){
        log.info("sending email to {} with text: {}", emailDto.getEmail(), emailDto.getBody());

        webClient.build().post()
                .uri("http://notification-service/api/notification/email")
                .body(Mono.just(emailDto), EmailDto.class)
                .retrieve()
                .bodyToMono(EmailDto.class)
                .block();

    }
}
