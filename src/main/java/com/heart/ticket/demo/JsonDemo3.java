package com.heart.ticket.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * About:
 * Other:
 * Created: wfli on 2024/1/16 17:20.
 * Editored:
 */
public class JsonDemo3 {

    public static void main(String[] args) {

        String data = "{\"aa\":\"test1\"}";
        JSONObject jsonObject = JSONObject.parseObject(data);
        jsonObject.put("is", "bbb");
        System.out.println(data);
        System.out.println(jsonObject.toJSONString());

    }
}
