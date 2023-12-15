package by.rom.customerservice.controller;

import by.rom.customerservice.dto.CustomerDto;
import by.rom.customerservice.dto.OrderDto;
import by.rom.customerservice.model.Order;
import by.rom.customerservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    @CircuitBreaker(name = "order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<Order>> getOrders(@RequestBody CustomerDto customerDto){
        return ResponseEntity.ok(orderService.getOrders(customerDto));
    }
}
