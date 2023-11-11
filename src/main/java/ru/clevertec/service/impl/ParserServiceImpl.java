package ru.clevertec.service.impl;

import ru.clevertec.parser.ParseFromJson;
import ru.clevertec.parser.ParseToJson;
import ru.clevertec.service.ParserService;

/**
 * Имплементация сервиса для работы с парсером
 */
public class ParserServiceImpl implements ParserService {

    private final ParseToJson parseToJson = new ParseToJson();
    private final ParseFromJson parseFromJson = new ParseFromJson();

    @Override
    public String toJson(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        } else {
            return parseToJson.parse(object);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfObject) {
        if (json == null || classOfObject == null) {
            throw new IllegalArgumentException();
        } else {
            return parseFromJson.parse(json, classOfObject);
        }
    }
}
