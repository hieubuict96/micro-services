package by.rom.customerservice.repository;

import by.rom.customerservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.customer.phoneNumber= :number")
    List<Order> findOrderByCustomerNumberAndEmail(@Param("number") String number);
}
