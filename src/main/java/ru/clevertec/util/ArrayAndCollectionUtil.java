package ru.clevertec.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.stream;

/**
 * Утилитарный класс для работы с массивами и коллекциями
 */
@UtilityClass
public class ArrayAndCollectionUtil {

    public String[] getArray(String source) {
        source = source.substring(1, source.length() - 1);

        return source.startsWith("{") ?
                getListOfObjects(source).toArray(String[]::new) :
                source.split(",");
    }

    public Collection<String> getCollection(String source) {
        source = source.substring(1, source.length() - 1);

        return source.startsWith("{") ?
                getListOfObjects(source) :
                stream(source.split(",")).toList();
    }

    private Collection<String> getListOfObjects(String source) {
        int openBrackets = 0;
        int closeBrackets = 0;
        int position = 0;

        List<String> listOfObjects = new ArrayList<>(0);

        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == '{') {
                openBrackets++;
            } else if (source.charAt(i) == '}') {
                closeBrackets++;
            }

            if (openBrackets == closeBrackets && openBrackets != 0) {
                listOfObjects.add(source.substring(i - position, i + 1));
                openBrackets = 0;
                closeBrackets = 0;
                position = -2;
            }
            position++;
        }
        return listOfObjects;
    }
}
