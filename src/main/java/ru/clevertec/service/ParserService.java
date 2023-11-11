package ru.clevertec.service;

/**
 * Сервис для работы с парсером
 */
public interface ParserService {

    String toJson(Object object);

    <T> T fromJson(String json, Class<T> classOfObject);
}
