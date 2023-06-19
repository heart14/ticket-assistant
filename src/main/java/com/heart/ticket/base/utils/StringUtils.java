package com.heart.ticket.base.utils;

import java.util.UUID;

/**
 * About:
 * Other:
 * Created: wfli on 2023/6/19 14:18.
 * Editored:
 */
public class StringUtils {

    public static String UuidUpperCase() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String UuidLowerCase() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static String UuidWithSplit() {
        return UUID.randomUUID().toString();
    }

}
