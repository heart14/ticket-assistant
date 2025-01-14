package com.heart.ticket.demo;

import com.alibaba.fastjson.JSONObject;

/**
 * About:
 * Other:
 * Created: wfli on 2023/12/19 16:34.
 * Editored:
 */
public class LongNumberDemo {

    private final static long EORDERID = 007;

    public static void main(String[] args) {

        System.out.println(EORDERID);


        String json = "{\"now\":1728365332343}";

        LongNumberTest longNumberTest = JSONObject.parseObject(json, LongNumberTest.class);

        System.out.println(longNumberTest);


        double d = 11.22;

        float f = (float)d;

        System.out.println(f);

    }
}
