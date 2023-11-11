package ru.clevertec.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;

import static ru.clevertec.constants.Constants.DATE_TIME_FORMATTER;

/**
 * Адаптер для библиотеки Gson и класса LocalDate
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public JsonElement serialize(final LocalDate date, final Type typeOfSrc, final JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DATE_TIME_FORMATTER));
    }

    @Override
    public LocalDate deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.parse(json.getAsString(), DATE_TIME_FORMATTER);
    }
}
