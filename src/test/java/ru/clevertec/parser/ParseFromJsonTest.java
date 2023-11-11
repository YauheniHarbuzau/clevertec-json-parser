package ru.clevertec.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.clevertec.adapter.LocalDateAdapter;
import ru.clevertec.adapter.OffsetDateTimeAdapter;
import ru.clevertec.entity.Customer;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для {@link ParseFromJson}
 */
class ParseFromJsonTest {

    private final ParseFromJson parser = new ParseFromJson();

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter())
            .create();

    @Test
    void checkParseMethodReturnCorrectResult() {
        // given
        String json = """
                {
                  "id": "2927e0e0-7282-4007-a909-7256cd756a14",
                  "firstName": "Reuben",
                  "lastName": "Martin",
                  "dateBirth": "2003-11-03",
                  "orders": [
                    {
                      "id": "eae781d8-f4bf-41b9-9c97-6f47988ed358",
                      "products": [
                        {
                          "id": "52ab49a5-7d5c-41b2-9743-be60b65470fe",
                          "name": "Telephone",
                          "price": 100.0
                        },
                        {
                          "id": "413b62f0-46f8-4811-ae0d-7bacbf31f807",
                          "name": "Car",
                          "price": 100.0
                        }
                      ],
                      "createDate": "2023-10-24T17:50:30.005470749+03:00"
                    }
                  ]
                }
                """;

        // when
        Customer expectedCustomer = gson.fromJson(json, Customer.class);
        Customer actualCustomer = parser.parse(json.replace(" ", ""), Customer.class);

        // than
        assertAll(
                () -> assertEquals(expectedCustomer.getId(), actualCustomer.getId()),
                () -> assertEquals(expectedCustomer.getFirstName(), actualCustomer.getFirstName()),
                () -> assertEquals(expectedCustomer.getLastName(), actualCustomer.getLastName()),
                () -> assertEquals(expectedCustomer.getDateBirth(), actualCustomer.getDateBirth())
        );
    }
}
