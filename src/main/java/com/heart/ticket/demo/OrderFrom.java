package com.heart.ticket.demo;

/**
 * @author wfli
 * @since 2024/7/24 14:45
 */
public enum OrderFrom {
    /**
     * 饿了么渠道
     */
    ELEME(0, "饿了么"),
    /**
     * 美团渠道
     */
    MEITUAN(1, "美团");

    private int num;
    private String instructions;

    OrderFrom(int num, String instructions) {
        this.num = num;
        this.instructions = instructions;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
