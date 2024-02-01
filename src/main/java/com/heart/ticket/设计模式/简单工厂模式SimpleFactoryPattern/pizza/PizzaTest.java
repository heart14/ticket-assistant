package com.heart.ticket.设计模式.简单工厂模式SimpleFactoryPattern.pizza;

/**
 * @author wfli
 * @since 2024/1/29 15:16
 */
public class PizzaTest {

    /**
     * 简单工厂模式：对相同的需求，有不同的操作
     * 比如售卖sale，bbq披萨卖8块milk披萨卖4块
     * 抽象出来一个pizza类，定义sale接口
     * 然后bbq milk披萨分别实现这个sale接口，以完成自己的售卖操作
     * 通过工厂类，根据披萨type的不同，来创建相应的pizza子类
     * 然后通过子类中调用sale()方法
     * 完成不同操作
     *
     * @param args
     */
    public static void main(String[] args) {

        // 简单工厂模式
        SimplePizzaFactory simplePizzaFactory = new SimplePizzaFactory();

        Pizza pizza = simplePizzaFactory.create("bbq");
        pizza.sale();

        // 不使用工厂模式
        String type = "fromContext";
        Pizza pizza4;
        if ("milk".equals(type)) {
            pizza4 = new MilkPizza();
        } else if ("bbq".equals(type)) {
            pizza4 = new BbqPizza();
        } else {
            System.out.println("不会做");
            pizza4 =  null;
        }
        pizza4.sale();

    }
}
