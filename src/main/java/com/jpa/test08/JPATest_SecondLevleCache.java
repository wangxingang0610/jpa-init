package com.jpa.test08;

import com.jpa.domain.Category;
import com.jpa.domain.Customer;
import com.jpa.domain.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


/**
 * 映实体：Item 和 Category ，一个类别有多个商品，一个商品对应多个类别。
 *
 *  * 双方都包含对方的 Set 集合。创建实体类，添加对应的注解，生成数据表。）
 *
 */
public class JPATest_SecondLevleCache {

    String persistenceUintName = "jpa";

    EntityManagerFactory entityManagerFactory = null;

    EntityManager entityManager = null;

    EntityTransaction transaction = null;

    @Before
    public void before(){
        //1.EntityManagerFactory
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa");

        //2.EntityManager
        entityManager = entityManagerFactory.createEntityManager();

        //3.开启事务
        transaction = entityManager.getTransaction();

        transaction.begin();

    }


    /**
     * 查询一条同样的记录，在第一次查询后关闭 EntityManager、提交事务后，再重新获取 EntityManager 并开启事务再查询同样的记录，
     * 因为有二级缓存的存在也会只发送一条记录
     */
    @Test
    public void testSecondLevelCache(){

        Customer customer = entityManager.find(Customer.class, 1);

        transaction.commit();
        entityManager.close();
        System.out.println("========================================");
//
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer2 = entityManager.find(Customer.class,1);
        System.out.println(customer2.getName());

    }

    @After
    public void after(){
        //6.关闭 EntityManager
        entityManager.close();

        //7.关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
