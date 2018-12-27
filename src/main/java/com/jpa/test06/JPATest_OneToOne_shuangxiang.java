package com.jpa.test06;

import com.jpa.domain.Customer;
import com.jpa.domain.Department;
import com.jpa.domain.Manager;
import com.jpa.domain.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.IOP.ENCODING_CDR_ENCAPS;

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
public class JPATest_OneToOne_shuangxiang {

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
     * 先保存不维护关联关系的一方，即没有外键的一方，这样不会多出update语句
     */
    @Test
    public void testManyToOneShuangxiangPersist(){
        Manager manager = new Manager();
        manager.setManagerName("sunquan");

        Department department = new Department();
        department.setDepartmentName("吴国");

        //映射关系
        manager.setDepartment(department);
        department.setManager(manager);

        //持久化
        entityManager.persist(manager);
        entityManager.persist(department);


        //提交事务
        transaction.commit();

    }

    @Test
    public void testOneToOneFind1(){
        Manager manager = entityManager.find(Manager.class, 1);
        System.out.println(manager.getManagerName());
        System.out.println(manager.getDepartment().getDepartmentName());
    }

    @Test
    public void testOneToOneFind2(){
        Department department = entityManager.find(Department.class, 1);
        System.out.println(department.getDepartmentName());
        System.out.println(department.getManager().getManagerName());

    }









    @After
    public void after(){
        //6.关闭 EntityManager
        entityManager.close();

        //7.关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
