package by.rom.customerservice.config;

import by.rom.customerservice.model.Customer;
import by.rom.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class BootstrapConfig {

//    private final CustomerRepository customerRepository;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void createCustomer() {
//        Customer customer = new Customer();
//        customer.setName("John Snow");
//        customer.setEmail("john@gmail.com");
//        customer.setPhoneNumber("7897899");
//        customerRepository.save(customer);
//    }
}
