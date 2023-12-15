package by.rom.notificationservice.dto;

import lombok.Data;

@Data
public class NotificationDto {

    private String email;
    private String body;
    private String nameOfProduct;
    private int price;
}
