package com.heart.ticket.demo;


import cn.hutool.json.JSONObject;

/**
 * About:
 * Other:
 * Created: wfli on 2023/11/14 10:14.
 * Editored:
 */
public class JsonParseDemo2 {

    public static void main(String[] args) {


        JSONObject jsonObject = new JSONObject();
        jsonObject.set("sign", "7cfc167cafffa40cecaecb5430f7951eab170ad3baa3afef46fe878c7975ffb0");
        jsonObject.set("nonce", "bdf56018ee2c4b95a3f5c2233126c4dd");
        JSONObject contentObj = new JSONObject();
        JSONObject dataObj = new JSONObject();
        dataObj.set("username", "QMrEPXuuNP9fhltKTq5TlkiJmg5HD0f7riPbNtWNIKNQEC19cEjbh4RDivate1jslRUKUWpEE244jiod6W13z3Tymff+6gg6o+glyjdCZC1X2thArjjve1zPraOK+7QzpfSmYvQF37nfxXgg2lto2mMdIm9ipQ2cHVqgkFDTZ1YdrTwPqPsjcOrv0ysLbW2BPxVektTsXQm1T4dPc26sBJGpj9P/EYMS+TQXbtrVDmlOQ0U+eBKdRCnllWYX5AASoIVbAj8lBMnE5OQyLtJxcNZRR97mN/AVj/6BWHSQ/66qtJdkpVGHtf436/O8Da49s4jB94v7Qya7Z9s8qvJEFQ==");
        contentObj.set("data", dataObj.toString());
        contentObj.set("resCode", "000000");
        contentObj.set("resMsg", "成功");
        jsonObject.set("content", contentObj.toString());
        jsonObject.set("timestamp", "1699927875492");


        System.out.println(jsonObject);
        String username = jsonObject.getJSONObject("content").getJSONObject("data").getStr("username");
        System.out.println(username);

    }
}
