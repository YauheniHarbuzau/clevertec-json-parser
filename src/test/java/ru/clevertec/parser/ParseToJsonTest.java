package ru.clevertec.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.clevertec.adapter.LocalDateAdapter;
import ru.clevertec.adapter.OffsetDateTimeAdapter;
import ru.clevertec.entity.Customer;
import ru.clevertec.entity.Order;
import ru.clevertec.entity.Product;
import ru.clevertec.testdatautil.CustomerTestData;
import ru.clevertec.testdatautil.OrderTestData;
import ru.clevertec.testdatautil.ProductTestData;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.clevertec.testdatautil.TestConstants.TEST_UUID_1;
import static ru.clevertec.testdatautil.TestConstants.TEST_UUID_2;
import static ru.clevertec.testdatautil.TestConstants.TEST_UUID_3;
import static ru.clevertec.testdatautil.TestConstants.TEST_UUID_4;

/**
 * Тестовый класс для {@link ParseToJson}
 */
class ParseToJsonTest {

    private final ParseToJson parser = new ParseToJson();

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter())
            .create();

    @Test
    void checkParseMethodReturnCorrectResult() {
        // given
        Product firstProduct = ProductTestData.builder()
                .withId(TEST_UUID_1)
                .withName("Telephone")
                .build().buildProduct();
        Product secondProduct = ProductTestData.builder()
                .withId(TEST_UUID_2)
                .withName("Car")
                .build().buildProduct();
        Order order = OrderTestData.builder()
                .withId(TEST_UUID_3)
                .withProducts(List.of(firstProduct, secondProduct))
                .build().buildOrder();
        Customer customer = CustomerTestData.builder()
                .withId(TEST_UUID_4)
                .withOrders(List.of(order))
                .build().buildCustomer();

        // when
        String expectedJson = gson.toJson(customer);
        String actualJson = parser.parse(customer);

        // than
        assertEquals(expectedJson, actualJson);
    }
}
