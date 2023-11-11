package ru.clevertec.constants;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Класс для хранения констант
 */
public class Constants {

    public static final String KEYS_AND_VALUES = "((?=\\[)\\[[^]]*]|(?=\\{)\\{[^}]*}|\"[^\"]*\"|(?=\\d)\\d*.\\d*|(?=\\w)\\w*):+" +
                                                 "((?=\\[)\\[[^]]*]|(?=\\{)\\{[^}]*}|\"[^\"]*\"|(?=\\d)\\d*.\\d*|(?=\\w)\\w*)";

    public static final String TOKEN_FOR_NULL_TYPE = "f7454da0-533b-48bf-9d4d-ed92811fa724";

    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSXXXXX");

    public static final UUID UUID_1 = UUID.fromString("52ab49a5-7d5c-41b2-9743-be60b65470fe");
    public static final UUID UUID_2 = UUID.fromString("413b62f0-46f8-4811-ae0d-7bacbf31f807");
    public static final UUID UUID_3 = UUID.fromString("eae781d8-f4bf-41b9-9c97-6f47988ed358");
    public static final UUID UUID_4 = UUID.fromString("2927e0e0-7282-4007-a909-7256cd756a14");
}
