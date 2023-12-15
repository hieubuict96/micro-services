package by.rom.customerservice.dto;

import lombok.Data;

@Data
public class OrderDto {

    private Long customerId;
    private String information;
    private String nameProduct;
    private int countOfProduct;
}
