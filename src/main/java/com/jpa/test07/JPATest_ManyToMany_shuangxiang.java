package com.jpa.test07;

import com.jpa.domain.Category;
import com.jpa.domain.Department;
import com.jpa.domain.Item;
import com.jpa.domain.Manager;
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
public class JPATest_ManyToMany_shuangxiang {

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
     *
     */
    @Test
    public void testManyToManyShuangxiangPersist(){
        Item item1 = new Item();
        item1.setItemName("item-001");

        Item item2 = new Item();
        item2.setItemName("item-002");

        Item item3 = new Item();
        item3.setItemName("item-003");

        Category category1 = new Category();
        category1.setCategoryName("Category-001");

        Category category2 = new Category();
        category2.setCategoryName("Category-002");


        //关联映射
        item1.getCategorys().add(category1);
        item2.getCategorys().add(category2);


        category1.getItems().add(item1);
        category2.getItems().add(item2);

        //持久化操作
        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(category1);
        entityManager.persist(category2);

        //提交事务
        transaction.commit();

    }


    /**
     * 懒加载
     */
    @Test
    public void testManyToManyFind(){
//        Item item = entityManager.find(Item.class, 1);
//        System.out.println(item.getItemName());
//
//        System.out.println(item.getCategorys().size());

        Category category = entityManager.find(Category.class, 7);
        System.out.println(category.getCategoryName());
        System.out.println(category.getItems().size());


    }









    @After
    public void after(){
        //6.关闭 EntityManager
        entityManager.close();

        //7.关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
