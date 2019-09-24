package com.bochat.app.model.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cookie on 2017/5/22 15:18.
 */

public class JsonConvert {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
            .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
            .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
            .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
            .registerTypeAdapter(Long.class, new LongDefault0Adapter())
            .registerTypeAdapter(long.class, new LongDefault0Adapter())
            .registerTypeAdapter(String.class, new StringDefault0Adapter())
            .create();
    private static final JsonParser jsonParser = new JsonParser();


    public static String toJson(Object object,Type type){
        return gson.toJson(object,type);
    }

    public static <T> T analysisJson(String result, Type type) {
        return gson.fromJson(result, type);
    }

    /**
     * @param result
     *            后台返回json
     * @param type
     *            解析结果对象
     * @param key
     *            解析json中哪段json
     * @return
     * @author:clj
     */
    public static <T> T analysisJson(String result, Type type, String key) {
        JsonElement targerElement = null;
        JsonElement jsonElement = jsonParser.parse(result);
        try {
            Set<Map.Entry<String, JsonElement>> setJsons = jsonElement.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> set : setJsons) {
                String name = set.getKey();
                if (name.equals(key)) {
                    targerElement = set.getValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.fromJson(targerElement, type);
    }

/*    public static <T> T analysisJson(String result, String key) {
        JSONObject json = JSON.parseObject(result);
        return (T) json.get(key);
    }*/

    public static <T> T analysisJson(String result, Class<T> cls) {
        return gson.fromJson(result, cls);
    }

    public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                                                     Type listType){
        return gson.fromJson(jsonData, listType);
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    public static class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为int类型,如果后台返回""或者null,则返回0
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为double类型,如果后台返回""或者null,则返回0.00
                    return 0.00;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsDouble();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为long类型,如果后台返回""或者null,则返回0
                    return 0L;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsLong();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class StringDefault0Adapter implements JsonSerializer<String>, JsonDeserializer<String> {


        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为long类型,如果后台返回""或者null,则返回0
                    return null;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsString();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }
}
