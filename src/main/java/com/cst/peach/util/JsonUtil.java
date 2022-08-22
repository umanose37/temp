package com.cst.peach.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Json Util class
 * @author jinwoolee
 */
public class JsonUtil {

    /*
     * json 객체로 파싱
     */
    public static JSONObject parseJsonObject(String jsonData) {
        JSONObject result = null;

        try {
            result = new JSONObject(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /*
     * json 객체에서 특정키로 json 배열 반환
     */
    public static JSONArray getJsonArray(JSONObject jsonObj, String key) {
        JSONArray result = null;

        try {
            result = jsonObj.getJSONArray(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
