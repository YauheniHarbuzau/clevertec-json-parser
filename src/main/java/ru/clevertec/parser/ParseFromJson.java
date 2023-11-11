package ru.clevertec.parser;

import lombok.SneakyThrows;
import ru.clevertec.util.ArrayAndCollectionUtil;
import ru.clevertec.util.FieldTypeUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static ru.clevertec.constants.Constants.KEYS_AND_VALUES;

/**
 * Класс для конвертирования из JSON в объект
 */
public class ParseFromJson {

    /**
     * Метод для конвертирования JSON в объект
     *
     * @param json          конвертируемый JSON в формате строки (String)
     * @param classOfObject класс объекта, в который происходит конвертирование
     * @return результат конвертирования
     */
    @SneakyThrows
    public <T> T parse(String json, Class<T> classOfObject) {
        var keysAndValuesList = executeKeysAndValuesList(json);
        var resultObject = classOfObject.getConstructor().newInstance();
        var fields = resultObject.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            for (String keyAndValue : keysAndValuesList) {
                if ((field.getName()).equals(getKey(keyAndValue))) {
                    field.set(resultObject, elementValue(keyAndValue, field.getType(), resultObject));
                }
            }
        }
        return resultObject;
    }


    /**
     * Метод для нахождения полей объекта (имя и значение) из JSON
     *
     * @param json конвертируемый JSON в формате строки (String)
     * @return List<String>, содержащий имена полей объекта и их значения
     */
    private List<String> executeKeysAndValuesList(String json) {
        List<String> keysAndValuesList = new ArrayList<>(0);
        json = json.trim();
        json = json.substring(1, json.length() - 1);

        var matcher = Pattern.compile(KEYS_AND_VALUES).matcher(json);

        while (matcher.find()) {
            var keyAndValue = matcher.group();

            if (keyAndValue.endsWith(",")) {
                keyAndValue = keyAndValue.substring(0, keyAndValue.length() - 1);
            }
            keysAndValuesList.add(keyAndValue);
        }
        return keysAndValuesList;
    }

    /**
     * Метод для нахождения имени поля объекта
     *
     * @param keyAndValue строка, содержащая имя поля объекта и его значение
     * @return имя поля объекта в формате строки (String)
     */
    private String getKey(String keyAndValue) {
        keyAndValue = keyAndValue.substring(0, keyAndValue.indexOf(":"));
        return keyAndValue.substring(1, keyAndValue.length() - 1);
    }

    /**
     * Метод для нахождения значения поля объекта
     *
     * @param keyAndValue строка, содержащая имя поля объекта и его значение
     * @return значение поля объекта в формате строки (String)
     */
    private String getValue(String keyAndValue) {
        return keyAndValue.substring(keyAndValue.indexOf(":") + 1);
    }

    /**
     * Приведение значения поля объекта к требуемому типу данных
     *
     * @param keyAndValue  имя и значение поля объекта в формате строки (String)
     * @param classOfField класс поля объекта, в который происходит конвертирование
     * @param resultObject результирующий объект
     * @return значение поля объекта требуемого типа данных
     */
    private Object elementValue(String keyAndValue, Class<?> classOfField, Object resultObject) {
        var value = getValue(keyAndValue);

        return switch (FieldTypeUtil.getFieldType(value, classOfField)) {
            case NULL -> valueToNull();
            case NUMBER -> valueToNumber(value, classOfField);
            case BOOLEAN -> valueToBoolean(value);
            case CHAR -> valueToChar(value);
            case STRING -> valueToString(value, classOfField);
            case ENUM -> valueToEnum(value, classOfField);
            case UUID -> valueToUuid(value);
            case LOCAL_DATE -> valueToLocalDate(value);
            case OFFSET_DATE_TIME -> valueToOffsetDateTime(value);
            case ARRAY -> valueToArray(value, classOfField, resultObject);
            case COLLECTION -> valueToCollection(keyAndValue, classOfField, resultObject);
            case MAP -> valueToMap();
            case OBJECT -> valueToObject(value, classOfField);
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Приведение значения поля объекта к null
     *
     * @return null
     */
    private Object valueToNull() {
        return null;
    }

    /**
     * Приведение значения поля объекта к числовому формату
     *
     * @param value        значение поля объекта в формате строки (String)
     * @param classOfField класс поля объекта
     * @return значение поля объекта числового формата
     */
    private Object valueToNumber(String value, Class<?> classOfField) {
        if (classOfField == Byte.class || classOfField == byte.class) {
            return Byte.parseByte(value);
        } else if (classOfField == Short.class || classOfField == short.class) {
            return Short.parseShort(value);
        } else if (classOfField == Integer.class || classOfField == int.class) {
            return Integer.parseInt(value);
        } else if (classOfField == Long.class || classOfField == long.class) {
            return Long.parseLong(value);
        } else if (classOfField == Float.class || classOfField == float.class) {
            return Float.parseFloat(value);
        } else if (classOfField == Double.class || classOfField == double.class) {
            return Double.parseDouble(value);
        } else if (classOfField == BigInteger.class) {
            return new BigInteger(value);
        } else if (classOfField == BigDecimal.class) {
            return new BigDecimal(value);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Приведение значения поля объекта к булеву формату
     *
     * @param value значение поля объекта в формате строки (String)
     * @return значение поля объекта в булевом формате
     */
    private Object valueToBoolean(String value) {
        return Boolean.parseBoolean(value);
    }

    /**
     * Приведение значения поля объекта к символьному формату
     *
     * @param value значение поля объекта в формате строки (String)
     * @return значение поля объекта в символьном формате
     */
    private Object valueToChar(String value) {
        return value.charAt(1);
    }

    /**
     * Приведение значения поля объекта к требуемому строковому формату
     *
     * @param value        значение поля объекта в формате строки (String)
     * @param classOfField класс поля объекта
     * @return значение поля объекта в требуемом строковом формате
     */
    private Object valueToString(String value, Class<?> classOfField) {
        if (classOfField == String.class) {
            return value.substring(1, value.length() - 1);
        } else if (classOfField == StringBuilder.class) {
            return new StringBuilder(value.substring(1, value.length() - 1));
        } else if (classOfField == StringBuffer.class) {
            return new StringBuffer(value.substring(1, value.length() - 1));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Приведение значения поля объекта к формату перечисляемого типа
     *
     * @param value        значение поля объекта в формате строки (String)
     * @param classOfField класс поля объекта
     * @return значение поля объекта в формате перечисляемого типа
     */
    private Enum<?> valueToEnum(String value, Class<?> classOfField) {
        return (Enum<?>) stream(classOfField.getEnumConstants())
                .filter(enumElement ->
                        value.substring(1, value.length() - 1)
                             .equals(enumElement.toString())
                ).findFirst()
                .orElseThrow();
    }

    /**
     * Приведение значения поля объекта к формату UUID
     *
     * @param value значение поля объекта в формате строки (String)
     * @return значение поля объекта в формате UUID
     */
    private UUID valueToUuid(String value) {
        return UUID.fromString(value.substring(1, value.length() - 1));
    }

    /**
     * Приведение значения поля объекта к формату LocalDate
     *
     * @param value значение поля объекта в формате строки (String)
     * @return значение поля объекта в формате LocalDate
     */
    private LocalDate valueToLocalDate(String value) {
        return LocalDate.parse(value.substring(1, value.length() - 1));
    }

    /**
     * Приведение значения поля объекта к формату OffsetDateTime
     *
     * @param value значение поля объекта в формате строки (String)
     * @return значение поля объекта в формате OffsetDateTime
     */
    private OffsetDateTime valueToOffsetDateTime(String value) {
        return OffsetDateTime.parse(value.substring(1, value.length() - 1));
    }

    /**
     * Приведение значения поля объекта к формату массива
     *
     * @param value        значение поля объекта в формате строки (String)
     * @param classOfField класс поля объекта
     * @param resultObject результирующий объект
     * @return значение поля объекта в формате массива
     */
    private Object valueToArray(String value, Class<?> classOfField, Object resultObject) {
        var array = ArrayAndCollectionUtil.getArray(value);
        var arraySize = array.length;
        var arrayType = classOfField.componentType();

        var newArray = newInstance(arrayType, arraySize);

        for (int i = 0; i < arraySize; i++) {
            Array.set(newArray, i, elementValue(array[i], arrayType, resultObject));
        }
        return newArray;
    }

    /**
     * Приведение значения поля объекта к формату Collection<E>
     *
     * @param keyAndValue  имя и значение поля объекта в формате строки (String)
     * @param resultObject результирующий объект
     * @return значение поля объекта в формате Collection<E>
     */
    @SneakyThrows
    private Collection<?> valueToCollection(String keyAndValue, Class<?> classOfField, Object resultObject) {
        var key = getKey(keyAndValue);
        var value = getValue(keyAndValue);
        var valueType = resultObject.getClass().getDeclaredField(key).getGenericType().getTypeName();
        var parameterType = Class.forName(valueType.substring(valueType.indexOf("<") + 1, valueType.lastIndexOf(">")));

        if (classOfField == List.class) {
            return ArrayAndCollectionUtil.getCollection(value)
                    .stream()
                    .map(element -> elementValue(element, parameterType, resultObject))
                    .collect(toList());
        } else if (classOfField == Set.class) {
            return ArrayAndCollectionUtil.getCollection(value)
                    .stream()
                    .map(element -> elementValue(element, parameterType, resultObject))
                    .collect(toSet());
        }
        return null;
    }

    /**
     * Приведение значения поля объекта к формату Map<K, V>
     * Метод-заглушка - функционал не реализован
     *
     * @return значение поля объекта в формате Map<K, V>
     */
    private Map<?, ?> valueToMap() {
        return Collections.emptyMap();
    }

    /**
     * Приведение значения поля объекта к формату другого объекта
     *
     * @param value        значение поля объекта в формате строки (String)
     * @param classOfField класс поля объекта
     * @return значение поля объекта в формате объекта
     */
    private Object valueToObject(String value, Class<?> classOfField) {
        return parse(value, classOfField);
    }
}
