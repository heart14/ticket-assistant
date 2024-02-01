package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pizza;

/**
 * @author wfli
 * @since 2024/1/29 14:55
 */
public class SimplePizzaFactory {

    public Pizza create(String type) {
        if ("milk".equals(type)) {
            return new MilkPizza();
        } else if ("bbq".equals(type)) {
            return new BbqPizza();
        } else {
            System.out.println("不会做");
            return null;
        }
    }

}
