package com.jpa.test03;

import com.jpa.domain.Customer;
import com.jpa.domain.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.Date;


/**
 * 关联关系  多对一
 */
public class JPATest_ManyToOne {

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
     * 1.单向多对一的保存（persist）：保存多对一时，建议先保存 1 的一端，后保存 n 的一端，这样不会多出额外的 UPDATE 语句
     */
    @Test
    public void testManyToOnePersist(){

        Customer customer = new Customer();
        customer.setName("曹操");
        customer.setAge(27);
        customer.setEmail("caocao@163.com");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order1 = new Order();
        order1.setOrderName("s0001");

        Order order2 = new Order();
        order2.setOrderName("s0002");


        Order order3 = new Order();
        order3.setOrderName("s0003");

        //Customer关联Order
        order1.setCustomer(customer);
        order2.setCustomer(customer);
        order3.setCustomer(customer);


        //持久化操作
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(order3);

        transaction.commit();
    }

    /**
     * 2.获取操作（find）：默认情况下使用左外连接的方式来获取 n 的一端的对象和其关联的 1 的一端的对象，
     *
     * 可以使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
     *
     */
    @Test
    public void testManyToOneFind(){
        Order order = entityManager.find(Order.class,1);
        System.out.println("订单名称：" + order.getOrderName());

        System.out.println("该订单的用户名：" + order.getCustomer().getName());
    }

    /**
     * 3.删除操作（remove）：不能直接删除 1 的一端，因为有外键约束:
     *
     * Cannot delete or update a parent row: a foreign key constraint fails
     */
    @Test
    public void testManyToOneRemove(){
//        Order order = entityManager.find(Order.class,1);
//        entityManager.remove(order);
//        transaction.commit();

        Customer customer = entityManager.find(Customer.class, 9);
        entityManager.remove(customer);
        transaction.commit();
    }

    /**
     * 4.修改操作： update
     */
    @Test
    public void testManyToOneUpdate(){
        Order order = entityManager.find(Order.class, 2);
        order.getCustomer().setName("aaaaaaa");
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
