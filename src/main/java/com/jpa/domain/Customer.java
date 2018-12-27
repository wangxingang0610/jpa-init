package com.jpa.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Table(name = "JPA_CUSTOMER")
@Entity
@Cacheable
public class Customer {

    private Integer id;

    private String name;

    private String email;

    private Integer age;

    private Date birth;

    private Date createTime;

    private Set<Order> orders = new HashSet<>();

    /**
     * 一对多
     * 在 Customer 中添加 Order 的 Set 集合属性，并映射 1-n 关联关系，重新生成数据表
     * @return
     */
    //@JoinColumn(name = "CUSTOMER_ID")
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE}, mappedBy = "customer")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "NAME",length = 20,nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                ", createTime=" + createTime +
                '}';
    }
}
