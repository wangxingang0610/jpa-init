package com.jpa.test09.jpql;

import com.jpa.domain.Customer;
import com.jpa.domain.Order;
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
     *  1.
     */
    @Test
    public void testJPQLFirst(){

        List<Customer> customers= entityManager.createQuery("select c from Customer c").getResultList();
        System.out.println(customers.size());
    }

    /**
     *  2.getSingleResult查询不到时，  报javax.persistence.NoResultException: No entity found for query
     */
    @Test
    public void testNamedQuery(){
        Query query = entityManager.createNamedQuery("testNamedQuery").setParameter(1, 2);
        Customer customer = (Customer) query.getSingleResult();
        System.out.println(customer);

    }

    /**
     * 3.本地sql查询
     */
    @Test
    public void testCreateNativeQuery(){

        String sql = "select  * from jpa_customer where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(sql).setParameter(1,5);
        Object result = nativeQuery.getSingleResult();

        Customer customer = (Customer) result;//java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to com.jpa.domain.Customer
        System.out.println(customer);
    }

    /**
     * 4.ORDER BY
     */
    @Test
    public void testOrderBy(){

        String jpql = "FROM Customer c WHERE c.age > ? ORDER BY c.age DESC";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,10);

        List list = query.getResultList();
        System.out.println(list.size());
    }


    /**
     * Group by :
     *
     * //查询 order 数量大于 2 的那些 Customer
     */
    @Test
    public void testGroupBy(){
        String jqpl = "SELECT o.customer FROM Order o GROUP BY o.customer HAVING count(o.id) > 2";
        Query query = entityManager.createQuery(jqpl);
        List list = query.getResultList();
        System.out.println(list.size());

    }

    /**
     * 子查询
     */
    @Test
    public void testSubQuery(){
        //查询所有 Customer 的 name 为 YY 的 Order
        String jpql = "SELECT o FROM Order o"
                + " WHERE o.customer = (SELECT c FROM Customer c WHERE c.name = ?)";
        List<Order> orders = entityManager.createQuery(jpql).setParameter(1, "YY").getResultList();
        System.out.println(orders.size());
    }


    @After
    public void after(){
        //6.关闭 EntityManager
        entityManager.close();

        //7.关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
