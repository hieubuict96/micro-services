package by.rom.customerservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {

    private String email;
    private String body;
    private String nameOfProduct;
    private int price;

}
