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

        JSONObject dataObj = new JSONObject();

        JSONArray verifyResults = new JSONArray();

        JSONObject obj1 = new JSONObject();
        obj1.put("obj1_key1", "aaa");
        verifyResults.add(obj1);

        JSONObject obj2 = new JSONObject();
        obj2.put("obj2_key1", "bbb");
        verifyResults.add(obj2);

        JSONObject obj3 = new JSONObject();
        obj3.put("obj2_key1", "ccc");
        verifyResults.add(obj3);

        dataObj.put("data", dataObj);
        System.out.println(verifyResults.toJSONString());
        System.out.println(dataObj.toJSONString());

        System.out.println("---------");

        for (int i = 0; i < verifyResults.size(); i++) {
            JSONObject verifyResult = verifyResults.getJSONObject(i);

            verifyResult.put("coupon_code", "ddd" + i);
        }

        System.out.println(verifyResults.toJSONString());
        System.out.println(dataObj.toJSONString());


    }
}
