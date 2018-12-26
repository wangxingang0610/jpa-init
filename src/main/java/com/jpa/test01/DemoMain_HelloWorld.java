package com.jpa.test01;

import com.jpa.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class DemoMain_HelloWorld {
    public static void main(String[] args) {

        //1.创建 EntityManagerFactory
        String persistenceUintName = "jpa";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUintName);


        //2.创建 EntityManager，类似于 Hibernate 的 SessionFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //4.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //5.进行持久化操作
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("wangxiao");
        customer.setBirth(new Date());
        customer.setEmail("wangxiao@163.com");
        customer.setAge(20);
        entityManager.persist(customer);

        //6.提交事务
        transaction.commit();

        //7.关闭 EntityManager
        entityManager.close();

        //8.关闭 EntityManagerFactory
        entityManagerFactory.close();


    }
}
