package com.study.springstudy.springmvc.chap01;

public class OrderDTO {

    private int orderNum;
    private String goods;
    private int amount;
    private int price;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderNum=" + orderNum +
                ", goods='" + goods + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
