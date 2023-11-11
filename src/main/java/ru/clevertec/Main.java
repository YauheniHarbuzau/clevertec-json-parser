package ru.clevertec;

import ru.clevertec.entity.Customer;
import ru.clevertec.entity.Order;
import ru.clevertec.entity.Product;
import ru.clevertec.service.ParserService;
import ru.clevertec.service.impl.ParserServiceImpl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static ru.clevertec.constants.Constants.UUID_1;
import static ru.clevertec.constants.Constants.UUID_2;
import static ru.clevertec.constants.Constants.UUID_3;
import static ru.clevertec.constants.Constants.UUID_4;

/**
 * Main-класс
 */
public class Main {

    public static void main(String[] args) {

        ParserService service = new ParserServiceImpl();

        String json = service.toJson(getCustomerData());
        System.out.println(json);

        Product product = service.fromJson(getProductJson(), Product.class);
        System.out.println(product);
    }

    public static Customer getCustomerData() {
        Product firstProduct = Product.builder()
                .withId(UUID_1)
                .withName("Telephone")
                .withPrice(100.0)
                .build();
        Product secondProduct = Product.builder()
                .withId(UUID_2)
                .withName("Car")
                .withPrice(100.0)
                .build();
        Order order = Order.builder()
                .withId(UUID_3)
                .withProducts(List.of(firstProduct, secondProduct))
                .withCreateDate(OffsetDateTime.of(2023, 10, 24, 17, 50, 30, 5470749, ZoneOffset.ofHours(3)))
                .build();
        Customer customer = Customer.builder()
                .withId(UUID_4)
                .withFirstName("Reuben")
                .withLastName("Martin")
                .withDateBirth(LocalDate.of(2003, 11, 3))
                .withOrders(List.of(order))
                .build();
        return customer;
    }

    public static String getProductJson() {
        return "{\"id\":\"52ab49a5-7d5c-41b2-9743-be60b65470fe\",\"name\":\"Telephone\",\"price\":100.0}";
    }
}
