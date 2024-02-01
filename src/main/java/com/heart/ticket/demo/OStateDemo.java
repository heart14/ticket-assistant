package com.heart.ticket.demo;

/**
 * About:
 * Other:
 * Created: wfli on 2024/1/8 14:13.
 * Editored:
 */
public class OStateDemo {

    public static void main(String[] args) {

        OState oState = OState.cancelled;
        System.out.println(oState.name());
        System.out.println(oState.ordinal());
        

    }
}
