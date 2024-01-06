package com.stochastic.programming.askfaqsservice.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class JsonUtils {
//    public static Map<String, Object> toMap(final Object inputObject) {
//        final Gson gson = new Gson();
//        final String jsonString = gson.toJson(inputObject);
//        java.lang.reflect.Type type = new TypeToken<Map<String, Object>>() {}.getType();
//        return gson.fromJson(jsonString, type);
//    }

    public static <T> Optional<T> jsonToObject(String jsonString, Class<T> valueType) {
        if (ObjectUtils.isEmpty(jsonString)) {
            return Optional.empty();
        }

        try {
            Gson gson = new Gson();
            return Optional.of(gson.fromJson(jsonString, valueType));
        } catch (Exception e) {
            log.error("Error parsing json string: {}, reason: {}", jsonString, e.getMessage(), e);
            throw new RuntimeException("Error parsing json string: " + jsonString);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        final Gson gson = new Gson();
        return gson.toJson(object);
    }
}
