package com.rival.rivalrecipes.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by darren on 2/11/18.
 */

public class JsonUtils {

    public static Map<String, Object> jsonToMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (jsonObject != JSONObject.NULL) {
            retMap = toMap(jsonObject);
        }

        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = jsonObject.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = jsonObject.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }

        return map;
    }

    public static List<Object> toList(JSONArray jsonArray) throws JSONException {
        List<Object> list = new ArrayList<Object>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }

        return list;
    }

}
