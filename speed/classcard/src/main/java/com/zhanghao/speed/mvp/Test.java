package com.zhanghao.speed.mvp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者： zhanghao on 2018/1/4.
 * 功能：${des}
 */

public class Test {

    public static void testJson() {
        JSONObject js = new JSONObject();
        try {
            js.put("userId", "70008");
            js.put("source", "88");
            js.put("userType", 1 + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(js.toString());
    }

    public static void main(String args[]) {
        testJson();
    }
}
