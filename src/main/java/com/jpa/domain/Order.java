package com.jpa.domain;

import javax.persistence.*;

@Table(name = "JPA_ORDER")
@Entity
public class Order {

    private Integer id;

    private String orderName;


    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ORDER_NAME")
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    private Customer customer;

    /**
     * 映射单项 n-1 的关联关系（Customer 和 Order，Order 中有 Customer 属性，而 Customer 中没有 Order 属性）
     *
     * 使用 @ManyToOne 来映射多对一的关联关系
     *
     * 使用 @JoinColumn 来映射外键
     *
     * 可以使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
     *
     * @return
     */
    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
