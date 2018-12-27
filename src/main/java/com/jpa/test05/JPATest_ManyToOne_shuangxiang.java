package com.jpa.test05;

import com.jpa.domain.Customer;
import com.jpa.domain.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;


/**
 * 映射双向多对一的关联关系（注：双向多对一 同 双向一对多）
 *
 * 实体：Customer 中有 Order 的 Set 集合属性，Order 中有 Customer 的属性，注两个实体映射的外键列必须一致，都为 CUSTOMER_ID
 */
public class JPATest_ManyToOne_shuangxiang {

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
     * 保存操作（persist）：
     */
    @Test
    public void testManyToOneShuangxiangPersist(){
        Customer customer = new Customer();
        customer.setName("zhao");
        customer.setAge(10);
        customer.setEmail("zhaozhao@126.com");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order1 = new Order();
        order1.setOrderName("zhaozhao00001");

        Order order2 = new Order();
        order2.setOrderName("zhaozhao00002");

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        //执行持久化操作呢
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);


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
