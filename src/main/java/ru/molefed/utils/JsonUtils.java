package ru.molefed.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asObject(final String str, Class<T> clazz) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(str, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
