package com.jpa.test04;

import com.jpa.domain.Customer;
import com.jpa.domain.Order;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;


/**
 * 映射单向 1-n 的关联关系 Customer ：Order  1 ： n，Customer 中有 Order 的 Set 集合属性，Order 中没有 Customer的属性
 *
 * 在 Customer 中添加 Order 的 Set 集合属性，并映射 1-n 关联关系，重新生成数据表
 */
public class JPATest_OneToMany {

    String persistenceUintName = "jpa";

    EntityManagerFactory entityManagerFactory = null;

    EntityManager entityManager = null;

    EntityTransaction transaction = null;

    @Before
    public void before(){
        //1.EntityManagerFactory
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUintName);

        //2.EntityManager
        entityManager = entityManagerFactory.createEntityManager();

        //3.开启事务
        transaction = entityManager.getTransaction();

        transaction.begin();

    }

    /**
     * 1. 保存操作（persist）：总会多出 UPDATE 语句，n 的一端在插入时不会同时插入外键列
     */
    @Test
    public void testOneToManyPersist(){
        Customer customer = new Customer();
        customer.setName("qwqw");
        customer.setCreateTime(new Date());
        customer.setBirth(new Date());
        customer.setEmail("qwqw@126.com");
        customer.setAge(20);

        Order order1 = new Order();
        order1.setOrderName("ss001");

        Order order2 = new Order();
        order2.setOrderName("ss002");

        //映射关系
        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        //持久化操作
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);


        //提交事务
        transaction.commit();

    }

    /**
     * 2.查询操作（find）:默认使用懒加载
     */
    @Test
    public void testOneToManyFind(){
        Customer customer = entityManager.find(Customer.class, 9);
        System.out.println(customer.getName());

        System.out.println(customer.getOrders().size());
    }

    /**
     * 删除操作（remove）：默认情况下，若删除 1 的一端，会先把关联的 n 的一端的外键置空，然后再进行删除，
     * 可以通过 @OneToMany 的 cascade 属性修改默认的删除策略（CascadeType.REMOVE 为级联删除）
     */
    @Test
    public void testOneToManyRemove(){
        Customer customer = entityManager.find(Customer.class, 9);

        entityManager.remove(customer);
        transaction.commit();
    }








    @After
    public void after(){
        //6.关闭 EntityManager
        entityManager.close();

        //7.关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
