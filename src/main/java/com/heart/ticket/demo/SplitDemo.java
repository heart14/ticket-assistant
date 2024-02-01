package com.heart.ticket.demo;

/**
 * About:
 * Other:
 * Created: wfli on 2024/1/16 15:04.
 * Editored:
 */
public class SplitDemo {

    public static void main(String[] args) {

        String s1 = "code3";
        String s2 = "code1,code3,code2";

        String[] split = s1.split(",");
        for (String s : split) {
            System.out.println(s);
        }
        System.out.println("----------");
        split = s2.split(",");
        for (String s : split) {
            System.out.println(s);
        }

    }
}
