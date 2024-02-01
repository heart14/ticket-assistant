package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pizza;

/**
 * @author wfli
 * @since 2024/1/29 15:10
 */
public class BbqPizza implements Pizza {

    private String name;

    private String material;

    BbqPizza() {
        this.name = "bbq pizza";
        this.material = "bbq";
    }

    @Override
    public void sale() {
        System.out.println("bbq pizza sales for $8");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
