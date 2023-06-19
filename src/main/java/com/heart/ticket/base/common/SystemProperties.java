package com.heart.ticket.base.common;


import com.heart.ticket.base.config.SysPropertyLoader;

/**
 * About:
 * Other:
 * Created: wfli on 2022/9/27 16:47.
 * Editored:
 */
public class SystemProperties {

    private static final SysPropertyLoader propertyLoader;

    static {
        propertyLoader = SysPropertyLoader.getInstance();
    }

    public static final String APPLICAITON_NAME = propertyLoader.getSysProperty("application.name");


    public static void main(String[] args) {
        System.out.println(APPLICAITON_NAME);
    }

}
