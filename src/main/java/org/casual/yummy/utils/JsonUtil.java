package org.casual.yummy.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T obj2pojo(Object obj, Class<T> clazz) {
        return mapper.convertValue(obj, clazz);
    }

    public static String obj2json(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T json2pojo(String jsonStr, Class<T> clazz) {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> json2list(String jsonArrStr, Class<T> clazz) {
        JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        List<T> list = null;
        try {
            list = mapper.readValue(jsonArrStr, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
