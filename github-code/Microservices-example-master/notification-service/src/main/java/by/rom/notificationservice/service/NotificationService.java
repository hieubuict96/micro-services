package by.rom.notificationservice.service;

import by.rom.notificationservice.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    @RabbitListener(queues = "notification_queue")
    public void email(NotificationDto notificationDto){
        log.info("sending email {}", notificationDto);
    }
}
