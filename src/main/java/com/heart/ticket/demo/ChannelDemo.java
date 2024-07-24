package com.heart.ticket.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wfli
 * @since 2024/7/24 14:45
 */
public class ChannelDemo {

    private static final Map<String, OrderFrom> CHANNEL_MAPPING = new HashMap<>();
    static {
        CHANNEL_MAPPING.put("0", OrderFrom.ELEME);
        CHANNEL_MAPPING.put("ELEME", OrderFrom.ELEME);

        CHANNEL_MAPPING.put("1", OrderFrom.MEITUAN);
        CHANNEL_MAPPING.put("MEITUAN", OrderFrom.MEITUAN);
    }
    /**
     * 检验是否来自美团或者饿了么渠道，true是 false不是
     * @param orderFrom
     * @return
     */
    private static boolean checkChannel(String orderFrom) {
        return CHANNEL_MAPPING.containsKey(orderFrom);
    }

    /**
     * 将参数值转化为OrderFrom枚举
     * @param orderFrom
     * @return
     */
    private static OrderFrom transChannel(String orderFrom) {
        return CHANNEL_MAPPING.getOrDefault(orderFrom, null);
    }

    public static void main(String[] args) {
        boolean check = checkChannel("ELE2ME");

        System.out.println(check);

        OrderFrom trans = transChannel("MEITUAN");

        System.out.println(trans);
    }
}
