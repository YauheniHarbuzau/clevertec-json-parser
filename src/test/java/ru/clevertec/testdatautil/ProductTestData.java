package ru.clevertec.testdatautil;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.entity.Product;

import java.util.UUID;

/**
 * Класс для подготовки тестовых данных для {@link Product}
 */
@Data
@Builder(setterPrefix = "with")
public class ProductTestData {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Builder.Default
    private String name = "Product_Name";

    @Builder.Default
    private Double price = 100.0;

    public Product buildProduct() {
        return new Product(id, name, price);
    }
}
