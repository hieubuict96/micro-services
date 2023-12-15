package by.rom.customerservice.service;

import by.rom.customerservice.dto.CustomerDto;
import by.rom.customerservice.model.Customer;
import by.rom.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerDto customerDto) {
        return customerRepository.save(Customer.builder()
                .email(customerDto.getEmail())
                .name(customerDto.getName())
                .phoneNumber(customerDto.getPhoneNumber())
                .build());
    }
}
