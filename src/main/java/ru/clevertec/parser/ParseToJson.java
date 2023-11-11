package ru.clevertec.parser;

import ru.clevertec.util.FieldTypeUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static ru.clevertec.constants.Constants.TOKEN_FOR_NULL_TYPE;

/**
 * Класс для конвертирования объекта в JSON
 */
public class ParseToJson {

    /**
     * Метод для конвертирования объекта в JSON
     *
     * @param object объект для конвертирования
     * @return JSON в формате строки (String)
     */
    public String parse(Object object) {
        var fieldAndValueMap = executeFieldsNamesAndValuesMap(object);
        var stringBuilder = new StringBuilder();

        for (Map.Entry<String, Object> entry : fieldAndValueMap.entrySet()) {
            stringBuilder.append("\"")
                         .append(entry.getKey())
                         .append("\":")
                         .append(valueToString(entry.getValue()))
                         .append(",");
        }
        return String.format("{%s}", stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()));
    }

    /**
     * Метод для нахождения полей объекта (имя и объект поля)
     *
     * @param object объект для конвертирования
     * @return Map<String, Object>, содержащая имена полей объекта (key) и их значения (value)
     */
    private Map<String, Object> executeFieldsNamesAndValuesMap(Object object) {
        return stream(object.getClass().getDeclaredFields())
                .collect(toMap(
                        Field::getName,
                        field -> {
                            Object value = new Object();
                            field.setAccessible(true);

                            try {
                                value = field.get(object);
                            } catch (IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                            return Objects.requireNonNullElse(value, TOKEN_FOR_NULL_TYPE);
                        },
                        (k, v) -> k, LinkedHashMap::new
                ));
    }


    /**
     * Приведение значения поля объекта к формату строки (String)
     *
     * @param value значение поля объекта
     * @return значение поля объекта в формате строки (String)
     */
    private String valueToString(Object value) {
        var fieldType = FieldTypeUtil.getFieldType(value);

        return switch (fieldType) {
            case NULL -> "null";
            case NUMBER, BOOLEAN -> String.valueOf(value);
            case CHAR, STRING, ENUM, UUID, LOCAL_DATE, OFFSET_DATE_TIME -> "\"" + value + "\"";
            case ARRAY -> arrayToString(value);
            case COLLECTION -> collectionToString(value);
            case MAP -> mapToString(value);
            case OBJECT -> objectToString(value);
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Приведение значения поля объекта - из формата массива (Array) к формату строки (String)
     *
     * @param value значение поля объекта в формате массива (Array)
     * @return значение поля объекта в формате строки (String)
     */
    private String arrayToString(Object value) {
        var arrayLength = Array.getLength(value);
        var array = new Object[arrayLength];
        var stringBuilder = new StringBuilder();

        for (int i = 0; i < arrayLength; i++) {
            array[i] = Array.get(value, i);
        }

        for (Object element : array) {
            stringBuilder.append(valueToString(element))
                         .append(",");
        }
        return String.format("[%s]", stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()));
    }

    /**
     * Приведение значения поля объекта - из формата коллекции (Collection<E>) к формату строки (String)
     *
     * @param value значение поля объекта в формате коллекции (Collection<E>)
     * @return значение поля объекта в формате строки (String)
     */
    private String collectionToString(Object value) {
        var collection = (Collection<?>) value;
        var stringBuilder = new StringBuilder();

        for (Object element : collection) {
            stringBuilder.append(valueToString(element))
                         .append(",");
        }
        return String.format("[%s]", stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()));
    }

    /**
     * Приведение значения поля объекта - из формата карты (Map<K, V>) к формату строки (String)
     *
     * @param value значение поля объекта в формате карты (Map<K, V>)
     * @return значение поля объекта в формате строки (String)
     */
    private String mapToString(Object value) {
        var map = (Map<?, ?>) value;
        var stringBuilder = new StringBuilder();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            stringBuilder.append("\"")
                         .append(entry.getKey())
                         .append("\":")
                         .append(valueToString(entry.getValue()))
                         .append(",");
        }
        return String.format("{%s}", stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()));
    }

    /**
     * Приведение значения поля объекта - из формата объекта (Object) к формату строки (String)
     *
     * @param value значение поля объекта в формате объекта (Object)
     * @return значение поля объекта в формате строки (String)
     */
    private String objectToString(Object value) {
        return parse(value);
    }
}
