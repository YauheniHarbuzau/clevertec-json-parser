package ru.clevertec.testdatautil;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.entity.Order;
import ru.clevertec.entity.Product;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Класс для подготовки тестовых данных для {@link Order}
 */
@Data
@Builder(setterPrefix = "with")
public class OrderTestData {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Builder.Default
    private List<Product> products = Collections.emptyList();

    @Builder.Default
    private OffsetDateTime createDate = OffsetDateTime.of(2023, 10, 24, 17, 50, 30, 5470749, ZoneOffset.ofHours(3));

    public Order buildOrder() {
        return new Order(id, products, createDate);
    }
}
