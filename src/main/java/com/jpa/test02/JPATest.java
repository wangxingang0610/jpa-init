package com.jpa.test02;

import com.jpa.domain.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class JPATest {

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
     * 类似于 Hibernate 中的 Session 的 get() 方法，在执行 find 方法时就发送 SQL 语句
     */
    @Test
    public void testFind(){
        //4.进行操作
        Customer customer = entityManager.find(Customer.class, 1);
        //System.out.println(customer.toString());

    }

    /**
     * 类似于 Hibernate 的 Session 的 load() 方法,
     * 若不使用查询的对象则返回一个代理对象，到真正使用时才发送 SQL 语句查询
     * 可能会发生懒加载异常
     */
    @Test
    public void testGetReference(){
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println(customer.getAge());

        System.out.println("----------------------------------");

//        transaction.commit();
//        entityManager.close();

        System.out.println(customer.getAge());

    }

    /**
     * 类似于 Hibernate 的 save() 方法，与 Hibernate 的 save() 方法不同的是其不能插入一个有 id 属性的对象
     *
     * 类似于 Hibernate 的 save 方法，使对象由临时状态变为持久化对象
     *
     * 和 Hibernate 的 save 方法的区别为若有 id 属性，则不会执行插入操作而会抛出异常:
     *          Caused by: org.hibernate.PersistentObjectException: detached entity passed to persist:
     */
    @Test
    public void testPersistence(){
        Customer customer = new Customer();
        //customer.setId(2); //
        customer.setAge(29);
        customer.setName("bb");
        customer.setEmail("bb@189.com");

        entityManager.persist(customer);
        transaction.commit();
        System.out.println(customer);
    }

    /**
     * 类似于 Hibernate 中 Session 的 delete 方法，但是其不能删除 游离化对象（仅有 id），执行 5，6行会抛出异常，因为 5 行的 customer 对象为游离化对象
     *
     * 类似于 Hibernate Session 的 delete 方法，把对象对应的记录从数据库中删除
     *
     * 注：该方法只能移出 持久化 对象，而 Hibernate 的 delete 方法可以移除游离对象
     */
    @Test
    public void testRemove(){

        //Customer customer = new Customer();
        //customer.setId(2);

        Customer customer = entityManager.find(Customer.class, 3);
        entityManager.remove(customer);
        transaction.commit();

    }

    /**
     * 类似于 Hibernate 中 Session 的 saveOrUpdate() 方法
     *
     * ① 传入的是一个临时对象（没有 id）：会创建一个新的对象，把临时对象的属性复制到新的对象中，然后对新的对象执行持久化操作，
     *      13行执行了 merge() 方法，传入了一个临时对象，返回了一个新的对象，产看 15，16 行的结果可知，
     *      新的对象有 id，传入的对象木有id，说明是将新的对象插入了数据库
     */
    @Test
    public void testMerge1(){
        Customer customer = new Customer();
        customer.setAge(23);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("cc@126.com");
        customer.setName("CC");


        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer's id:" + customer.getId());// null
        System.out.println("customer's id:" + customer2.getId());// 2

        transaction.commit();
    }


    /**
     * 类似于 Hibernate 中 Session 的 saveOrUpdate() 方法
     *
     * 传入的是一个游离对象（有 ID）：若在 EntityManager 缓存中没有该对象，在数据库中也没有对应的记录，JPA 会创建一个新的对象，
     *
     * 把当前游离对象的属性复制到新的对象中，对新创建的对象执行 insert 操作，楼主的数据库对应的表中并没有 id 为 9 customer，15 行
     *
     * 同样返回了一个新的对象，根据返回结果可知 ，确实插入的是新的对象
     *
     *
     */
    @Test
    public void testMerge2(){
        Customer customer = new Customer();
        customer.setAge(23);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("cc@126.com");
        customer.setName("CC");


        customer.setId(9);//
        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer's id:" + customer.getId());//9
        System.out.println("customer's id:" + customer2.getId());// 2

        transaction.commit();
    }


    /**
     * 传入的是游离对象，即传入的对象有 OID，缓存中没有，但数据库中有对应的对象：JPA 会查询对应的记录，
     *
     * 然后返回该记录对应的对象把当前游离对象的属性复制到查询到的对象中，对查询到的对象执行 update 操作
     *
     *  1 　　 //3.若传入的是一个游离对象，即传入的对象有 OID
     *  2     //若在 EntityManager 缓存中没有该对象，在数据库中有对应的记录，JPA 会查询对应的记录，然后返回该记录对应的对象
     *  3     //把当前游离对象的属性复制到查询到的对象中，对查询到的对象执行 update 操作
     */
    @Test
    public void testMerge3(){
        Customer customer = new Customer();
        customer.setAge(13);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("aaaaaaa@126.com");
        customer.setName("CC");

        customer.setId(7);//

        Customer customer2 = entityManager.merge(customer);

        System.out.println(customer == customer2);
        transaction.commit();
    }


    /**
     * ④ 传入的是游离对象，即传入的对象有 OID，EntityManager 缓存中有对应的对象：JPA 会把当前游离对象的属性复制到查询到的 EntityManager 缓存中的对象，
     * 对 EntityManager 缓存中的对象执行 update 操作
     */
    //4.若传入的是一个游离对象，即传入的对象有 OID
    //若在 EntityManager 缓存中有对应的对象，JPA 会把当前游离对象的属性复制到查询到的 EntityManager 缓存中的对象，
    //对 EntityManager 缓存中的对象执行 update 操作
    @Test
    public void testMerge4() {
        Customer customer = new Customer();
        customer.setAge(23);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("dd@126.com");
        customer.setName("DD");

        customer.setId(5);
        Customer customer2 = entityManager.find(Customer.class, 5);

        entityManager.merge(customer);

        System.out.println(customer == customer2); //false
    }




    @After
    public void after(){
        //6.关闭 EntityManager
        entityManager.close();

        //7.关闭 EntityManagerFactory
        entityManagerFactory.close();
    }
}
