package com.heart.ticket.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * About:
 * Other:
 * Created: wfli on 2023/8/29 14:51.
 * Editored:
 */
public class JsonParseDemo {


    public static void main(String[] args) {

        JSONObject target = new JSONObject();
        JSONObject sub = new JSONObject();
        JSONArray thd = new JSONArray();
        JSONObject thd1 = new JSONObject();
        JSONObject thd2 = new JSONObject();
        thd1.put("third_key", 11);
        thd2.put("third_key", 22);
        thd.add(thd1);
        thd.add(thd2);

        sub.put("sub_key", "33");
        sub.put("sub_int", 44);

        target.put("str_key", "11");
        target.put("sub", sub);
        target.put("thds", thd);

        System.out.println(target);

        Target object = JSONObject.parseObject(target.toJSONString(), Target.class);
        System.out.println(object);

    }

















    static class Target{

        private String strKey;

        private Sub sub;

        private List<Thd> thds;

        public String getStrKey() {
            return strKey;
        }

        public void setStrKey(String strKey) {
            this.strKey = strKey;
        }

        public Sub getSub() {
            return sub;
        }

        public void setSub(Sub sub) {
            this.sub = sub;
        }

        public List<Thd> getThds() {
            return thds;
        }

        public void setThds(List<Thd> thds) {
            this.thds = thds;
        }

        @Override
        public String toString() {
            return "Target{" +
                    "strKey='" + strKey + '\'' +
                    ", sub=" + sub +
                    ", thds=" + thds +
                    '}';
        }
    }

    static class Sub{
        private String subKey;

        private Integer subInt;

        public String getSubKey() {
            return subKey;
        }

        public void setSubKey(String subKey) {
            this.subKey = subKey;
        }

        public Integer getSubInt() {
            return subInt;
        }

        public void setSubInt(Integer subInt) {
            this.subInt = subInt;
        }

        @Override
        public String toString() {
            return "Sub{" +
                    "subKey='" + subKey + '\'' +
                    ", subInt=" + subInt +
                    '}';
        }
    }

    static class Thd{
        private String thirdKey;

        public String getThirdKey() {
            return thirdKey;
        }

        public void setThirdKey(String thirdKey) {
            this.thirdKey = thirdKey;
        }

        @Override
        public String toString() {
            return "Thd{" +
                    "thirdKey='" + thirdKey + '\'' +
                    '}';
        }
    }
}
