package com.jpa.test09.jpql;

import com.jpa.domain.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;


/**
 * 映实体：Item 和 Category ，一个类别有多个商品，一个商品对应多个类别。
 *
 *  * 双方都包含对方的 Set 集合。创建实体类，添加对应的注解，生成数据表。）
 *
 */
public class JPATest_JPQL {

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
     *
     */
    @Test
    public void testJPQLFirst(){

        List<Customer> customers= entityManager.createQuery("select c from Customer c").getResultList();
        System.out.println(customers.size());
    }

    /**
     * getSingleResult查询不到时，  报javax.persistence.NoResultException: No entity found for query
     */
    @Test
    public void testNamedQuery(){
        Query query = entityManager.createNamedQuery("testNamedQuery").setParameter(1, 2);
        Customer customer = (Customer) query.getSingleResult();
        System.out.println(customer);

    }




    @After
    public void after(){
        //6.关闭 EntityManager
        entityManager.close();

        //7.关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
