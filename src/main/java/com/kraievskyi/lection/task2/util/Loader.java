package com.kraievskyi.lection.task2.util;

import com.kraievskyi.lection.task2.annotaion.Property;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Loader {

    public static <T> T loadFromProperties(Class<T> cls, Path path) {

        Map<String, String> map = hashMapFromProperties(path);
        T model;

        try {
            model = cls.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        try {

            Field stringPropertyField = cls.getDeclaredField("stringProperty");
            Field myNumberField = cls.getDeclaredField("myNumber");
            Field timePropertyField = cls.getDeclaredField("timeProperty");

            stringPropertyField.setAccessible(true);
            myNumberField.setAccessible(true);
            timePropertyField.setAccessible(true);

            stringPropertyField.set(model, map.get("stringProperty"));
            myNumberField.setInt(model, Integer.parseInt(map.get("numberProperty")));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofPattern(timePropertyField.getAnnotation(Property.class)
                            .format());
            LocalDateTime localDateTime =
                    LocalDateTime.parse(map.get("timeProperty"), dateTimeFormatter);
            timePropertyField.set(model, localDateTime.toInstant(ZoneOffset.UTC));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

    private static Map<String, String> hashMapFromProperties(Path path) {

        File file = path.toFile();
        Map<String, String> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("=");

                String key = parts[0].trim();
                String value = parts[1].trim();

                if (!key.equals("") && !value.equals("")) {
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
