package by.rom.notificationservice.controller;

import by.rom.notificationservice.dto.NotificationDto;
import by.rom.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/email")
    public ResponseEntity<Void> email(@RequestBody NotificationDto notificationDto){
        notificationService.email(notificationDto);
        return ResponseEntity.ok().build();
    }
}
