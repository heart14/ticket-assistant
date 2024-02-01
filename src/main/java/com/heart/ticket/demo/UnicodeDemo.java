package com.heart.ticket.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * About:
 * Other:
 * Created: wfli on 2023/11/29 14:24.
 * Editored:
 */
public class UnicodeDemo {

    public static void main(String[] args) {

        String r = "{\"resTime\":\"2023-11-29 14:12:40\",\"mchntCode\":\"\",\"respDesc\":\"操作成功\",\"msgType\":\"chat.faqQuery\",\"data\":\"{\\n  \\\"msgBody\\\": {\\n    \\\"answer\\\": \\\"\\\\u60a8\\\\u597d\\\\uff0c\\\\u8bf7\\\\u786e\\\\u8ba4\\\\u4ee5\\\\u4e0b\\\\u70b9\\\\uff0c\\\\u4ecd\\\\u6709\\\\u95ee\\\\u9898\\\\uff0c\\\\u8bf7\\\\u8054\\\\u7cfb\\\\u5ba2\\\\u6237\\\\u7ecf\\\\u7406\\\\u6216\\\\u62e8\\\\u6253\\\\u5ba2\\\\u670d\\\\u70ed\\\\u7ebf95534\\\\u3002\\\\n1\\\\u3001\\\\u7535\\\\u6c60\\\\u7535\\\\u91cf\\\\u4e0d\\\\u4f4e\\\\u4e8e15%\\\\uff1b\\\\n2\\\\u3001\\\\u786e\\\\u4fdd\\\\u6253\\\\u5370\\\\u5361\\\\u69fd\\\\u65e0\\\\u5f02\\\\u7269\\\\uff0c\\\\u6253\\\\u5370\\\\u611f\\\\u5e94\\\\u5934\\\\u6e05\\\\u6d01\\\\u65e0\\\\u7070\\\\uff1b\\\\n3\\\\u3001\\\\u9876\\\\u90e8\\\\u663e\\\\u793a\\\\u7684\\\\u9ec4\\\\u8272\\\\u56fe\\\\u6807\\\\u8868\\\\u793a\\\\u7f3a\\\\u6253\\\\u5370\\\\u7eb8\\\\uff0c\\\\u88c5\\\\u7eb8\\\\u540e\\\\u786e\\\\u4fdd\\\\u6253\\\\u5370\\\\u7eb8\\\\u6b63\\\\u53cd\\\\u9762\\\\u5b89\\\\u88c5\\\\u6b63\\\\u786e\\\\uff0c\\\\u62c9\\\\u51fa\\\\u4e00\\\\u622a\\\\u7eb8\\\\uff0c\\\\u8ba9\\\\u68c0\\\\u6d4b\\\\u70b9\\\\u5145\\\\u5206\\\\u611f\\\\u5e94\\\\u5230\\\\u7eb8\\\\u5f20\\\\uff1b\\\",\\n    \\\"qaRecommend\\\": [],\\n    \\\"question\\\": \\\"\\\\u6807\\\\u51c6FAQ\\\\uff1a\\\\u6253\\\\u5370\\\\u673a\\\\u6545\\\\u969c\\\"\\n  },\\n  \\\"msgHead\\\": {\\n    \\\"errCode\\\": \\\"000000\\\",\\n    \\\"errMsg\\\": \\\"Success\\\"\\n  }\\n}\\n\",\"msgSrc\":\"OYQM\",\"sign\":\"2ea529d2bc88d0426cac03e2717a557990823324fe691d6181c860a1b4d00b64\",\"contextId\":\"346751fa9069424fa6288fee830299cd\",\"respCode\":\"000000\"}";
//        String r = "{\"resTime\":\"2023-11-29 14:35:43\",\"mchntCode\":\"\",\"respDesc\":\"操作成功\",\"msgType\":\"chat.faqQuery\",\"data\":\"{\\n  \\\"msgBody\\\": {\\n    \\\"answer\\\": \\\"\\\",\\n    \\\"qaRecommend\\\": [\\n      \\\"\\\\u6253\\\\u5370\\\\u673a\\\\u6545\\\\u969c\\\",\\n      \\\"\\\\u6253\\\\u5370\\\\u673a\\\\u6253\\\\u5370\\\\u6545\\\\u969c\\\",\\n      \\\"\\\\u6253\\\\u5370\\\\u673a\\\\u95ee\\\\u9898\\\"\\n    ]\\n  },\\n  \\\"msgHead\\\": {\\n    \\\"errCode\\\": \\\"000000\\\",\\n    \\\"errMsg\\\": \\\"Success\\\"\\n  }\\n}\\n\",\"msgSrc\":\"OYQM\",\"sign\":\"f38c619b65531077190440ce9d4e9acc09e95eb4b0092f0e77d17e12c252d2ed\",\"contextId\":\"02a7397145584d46935423bb3b9c27be\",\"respCode\":\"000000\"}";
        System.out.println(r);
        JSONObject result = JSON.parseObject(r);
        System.out.println(result);

        JSONObject dataObj = result.getJSONObject("data");
        JSONObject msgBodyObj = dataObj.getJSONObject("msgBody");
        String question = msgBodyObj.getString("question");
        String answer = msgBodyObj.getString("answer");
        String qaRecommend = msgBodyObj.getString("qaRecommend");
//        List<String> qaRecommend2 = msgBodyObj.getObject("qaRecommend", List.class);
//        List<String> qaRecommend3 = (List<String>) msgBodyObj.get("qaRecommend");
//        JSONArray qaRecommend4 = msgBodyObj.getJSONArray("qaRecommend");
//        System.out.println("question:" + question);
//        System.out.println("answer:" + answer);
//        System.out.println("qaRecommend:" + qaRecommend);
//        System.out.println("qaRecommend2:" + qaRecommend2);
//        System.out.println("qaRecommend3:" + qaRecommend3);
//        System.out.println("qaRecommend4:" + qaRecommend4);

        System.out.println("question:" + trans(question));
        System.out.println("answer:" + trans(answer));
        System.out.println("qaRecommend:" + trans(qaRecommend));
    }

    public static String trans(String unicodeString) {
        String replace = unicodeString.replace("\\u", "\\u00");
        byte[] bytes = replace.getBytes(StandardCharsets.UTF_8);
        String utf8string = new String(bytes, StandardCharsets.UTF_8);
//        System.out.println(utf8string);
        return utf8string;
    }
}
