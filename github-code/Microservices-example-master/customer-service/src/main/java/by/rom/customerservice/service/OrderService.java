package by.rom.customerservice.service;

import by.rom.customerservice.client.InventoryClient;
import by.rom.customerservice.config.MessageConfig;
import by.rom.customerservice.dto.CustomerDto;
import by.rom.customerservice.dto.EmailDto;
import by.rom.customerservice.dto.InventoryResponse;
import by.rom.customerservice.dto.OrderDto;
import by.rom.customerservice.exception.NotFoundException;
import by.rom.customerservice.model.Customer;
import by.rom.customerservice.model.Order;
import by.rom.customerservice.repository.CustomerRepository;
import by.rom.customerservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RabbitTemplate rabbitTemplate;
    private final InventoryClient inventoryClient;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        Customer customer = findCustomer(orderDto);

        log.info("creating order: {}", orderDto);

        Order order = Order.builder()
                .information(orderDto.getInformation())
                .customer(customer)
                .nameProduct(orderDto.getNameProduct())
                .countOfProduct(orderDto.getCountOfProduct())
                .build();

        InventoryResponse response = findProduct(order.getNameProduct());

        boolean hasProduct = response.isInStock();

        log.info("saving order to the DB: {}", orderDto);

        Order savedOrder;

        if (!hasProduct){
            order.setPrice(response.getPrice());
            savedOrder  = orderRepository.save(order);
            log.info("saved order {}", savedOrder);
        }
        else {
            throw new IllegalArgumentException("Product isn't in stock, please try again later");
        }

        sendEmail(savedOrder, customer);

        return savedOrder;
    }

    public List<Order> getOrders(CustomerDto customerDto) {
        List<Order> list = orderRepository.findOrderByCustomerNumberAndEmail(customerDto.getPhoneNumber());
        if (list.isEmpty()){
            throw new NotFoundException("Customer doesn't have any orders, try again.");
        }
        else
            return list;
    }

    private InventoryResponse findProduct(String nameProduct) {
        InventoryResponse result = inventoryClient.sendRequest(nameProduct);

        log.info("result from inventory service: {}", result);

        return result;
    }

    private Customer findCustomer(OrderDto orderDto) {
        return customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new NotFoundException("customer not found with id: " + orderDto.getCustomerId()));
    }

    private void sendEmail(Order savedOrder, Customer customer) {
        EmailDto emailDto = EmailDto.builder()
                .email(customer.getEmail())
                .nameOfProduct(savedOrder.getNameProduct())
                .price(savedOrder.getPrice())
                .body(String.format("Thanks %s for your order.", customer.getName()))
                .build();

        log.info("sending email {}" , emailDto);

        rabbitTemplate.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY, emailDto);
    }
}
