# CLEVERTEC JSON PARSER

**Автор Евгений Гарбузов**

***

### Параметры приложения, библиотеки и зависимости

- Java 17
- Gradle 8.0
- Lombok Plugin 6.5.1
- JUnit 5.9.2
- Gson 2.10.1

***

### Типы данных, с которыми работает парсер

|<!-->|from Object to JSON|from JSON to Object|
|:----|:----|:----|
|Примитивные типы и их классы обёртки|+|+|
|String, StringBuilder, StringBuffer |+|+|
|BigInteger, BigDecimal              |+|+|
|UUID                                |+|+|
|LocalDate, OffsetDateTime           |+|+|
|Перечисления Enum                   |+|+|
|Массивы одномерные                  |+|+|
|Массивы n-мерные                    |+|-|
|List\<E>, Set\<E>                   |+|-|
|Map\<K, V>                          |+|-|
|Кастомные Entity                    |+|+|
|Null                                |+|+|
|Предусмотрена вложенность объектов (рекурсия)|

***

### Entities и JSON, используемые в тестах

```java
public class Product {

    private UUID id;
    private String name;
    private Double price;
}

public class Order {

    private UUID id;
    private List<Product> products;
    private OffsetDateTime createDate;
}

public class Customer {

    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateBirth;
    private List<Order> orders;
}
```

```json
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
```

***