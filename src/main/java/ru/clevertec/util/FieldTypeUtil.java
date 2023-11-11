package ru.clevertec.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static ru.clevertec.constants.Constants.TOKEN_FOR_NULL_TYPE;
import static ru.clevertec.util.FieldTypeUtil.FieldType.ARRAY;
import static ru.clevertec.util.FieldTypeUtil.FieldType.BOOLEAN;
import static ru.clevertec.util.FieldTypeUtil.FieldType.CHAR;
import static ru.clevertec.util.FieldTypeUtil.FieldType.COLLECTION;
import static ru.clevertec.util.FieldTypeUtil.FieldType.ENUM;
import static ru.clevertec.util.FieldTypeUtil.FieldType.LOCAL_DATE;
import static ru.clevertec.util.FieldTypeUtil.FieldType.MAP;
import static ru.clevertec.util.FieldTypeUtil.FieldType.NULL;
import static ru.clevertec.util.FieldTypeUtil.FieldType.NUMBER;
import static ru.clevertec.util.FieldTypeUtil.FieldType.OBJECT;
import static ru.clevertec.util.FieldTypeUtil.FieldType.OFFSET_DATE_TIME;
import static ru.clevertec.util.FieldTypeUtil.FieldType.STRING;
import static ru.clevertec.util.FieldTypeUtil.FieldType.UUID;

/**
 * Утилитарный класс для получения типов полей объектов
 */
@UtilityClass
public class FieldTypeUtil {

    public FieldType getFieldType(Object objectOfField) {
        if (TOKEN_FOR_NULL_TYPE.equals(objectOfField)) {
            return NULL;
        } else if (objectOfField instanceof Number) {
            return NUMBER;
        } else if (objectOfField instanceof Boolean) {
            return BOOLEAN;
        } else if (objectOfField instanceof Character) {
            return CHAR;
        } else if (objectOfField instanceof CharSequence) {
            return STRING;
        } else if (objectOfField instanceof Enum<?>) {
            return ENUM;
        } else if (objectOfField instanceof UUID) {
            return UUID;
        } else if (objectOfField instanceof LocalDate) {
            return LOCAL_DATE;
        } else if (objectOfField instanceof OffsetDateTime) {
            return OFFSET_DATE_TIME;
        } else if (objectOfField.getClass().getName().charAt(0) == '[') {
            return ARRAY;
        } else if (objectOfField instanceof Collection<?>) {
            return COLLECTION;
        } else if (objectOfField instanceof Map<?, ?>) {
            return MAP;
        } else {
            return OBJECT;
        }
    }

    public FieldType getFieldType(String value, Class<?> classOfField) {
        if (isNull(value)) {
            return NULL;
        } else if (isNumber(classOfField)) {
            return NUMBER;
        } else if (isBoolean(classOfField)) {
            return BOOLEAN;
        } else if (isChar(classOfField)) {
            return CHAR;
        } else if (isString(classOfField)) {
            return STRING;
        } else if (isEnum(classOfField)) {
            return ENUM;
        } else if (isUuid(classOfField)) {
            return UUID;
        } else if (isLocalDate(classOfField)) {
            return LOCAL_DATE;
        } else if (isOffsetDateTime(classOfField)) {
            return OFFSET_DATE_TIME;
        } else if (isArray(classOfField)) {
            return ARRAY;
        } else if (isCollection(classOfField)) {
            return COLLECTION;
        } else if (isMap(classOfField)) {
            return MAP;
        } else {
            return OBJECT;
        }
    }

    private boolean isNull(String value) {
        return "null".equals(value);
    }

    private boolean isNumber(Class<?> classOfField) {
        return classOfField == Byte.class || classOfField == byte.class ||
                classOfField == Short.class || classOfField == short.class ||
                classOfField == Integer.class || classOfField == int.class ||
                classOfField == Long.class || classOfField == long.class ||
                classOfField == Float.class || classOfField == float.class ||
                classOfField == Double.class || classOfField == double.class ||
                classOfField == BigInteger.class || classOfField == BigDecimal.class;
    }

    private boolean isBoolean(Class<?> classOfField) {
        return classOfField == Boolean.class || classOfField == boolean.class;
    }

    private boolean isChar(Class<?> classOfField) {
        return classOfField == Character.class || classOfField == char.class;
    }

    private boolean isString(Class<?> classOfField) {
        return classOfField == String.class || classOfField == StringBuilder.class || classOfField == StringBuffer.class;
    }

    private boolean isEnum(Class<?> classOfField) {
        return classOfField.isEnum();
    }

    private boolean isUuid(Class<?> classOfField) {
        return classOfField == UUID.class;
    }

    private boolean isLocalDate(Class<?> classOfField) {
        return classOfField == LocalDate.class;
    }

    private boolean isOffsetDateTime(Class<?> classOfField) {
        return classOfField == OffsetDateTime.class;
    }

    private boolean isArray(Class<?> classOfField) {
        return classOfField.isArray();
    }

    private boolean isCollection(Class<?> classOfField) {
        return classOfField == List.class || classOfField == Set.class;
    }

    private boolean isMap(Class<?> classOfField) {
        return classOfField == Map.class;
    }

    public enum FieldType {
        NULL,
        NUMBER,
        BOOLEAN,
        CHAR,
        STRING,
        ENUM,
        UUID,
        LOCAL_DATE,
        OFFSET_DATE_TIME,
        ARRAY,
        COLLECTION,
        MAP,
        OBJECT
    }
}
