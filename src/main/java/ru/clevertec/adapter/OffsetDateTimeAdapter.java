package ru.clevertec.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;

import static ru.clevertec.constants.Constants.OFFSET_DATE_TIME_FORMATTER;

/**
 * Адаптер для библиотеки Gson и класса OffsetDateTime
 */
public class OffsetDateTimeAdapter implements JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {

    @Override
    public JsonElement serialize(final OffsetDateTime date, final Type typeOfSrc, final JsonSerializationContext context) {
        return new JsonPrimitive(date.format(OFFSET_DATE_TIME_FORMATTER));
    }

    @Override
    public OffsetDateTime deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return OffsetDateTime.parse(json.getAsString(), OFFSET_DATE_TIME_FORMATTER);
    }
}
