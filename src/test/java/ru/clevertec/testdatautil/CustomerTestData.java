package ru.clevertec.testdatautil;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.entity.Customer;
import ru.clevertec.entity.Order;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Класс для подготовки тестовых данных для {@link Customer}
 */
@Data
@Builder(setterPrefix = "with")
public class CustomerTestData {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Builder.Default
    private String firstName = "Reuben";

    @Builder.Default
    private String lastName = "Martin";

    @Builder.Default
    private LocalDate dateBirth = LocalDate.of(2003, 11, 3);

    @Builder.Default
    private List<Order> orders = Collections.emptyList();

    public Customer buildCustomer() {
        return new Customer(id, firstName, lastName, dateBirth, orders);
    }
}
