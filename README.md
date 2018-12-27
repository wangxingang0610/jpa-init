
1.同时设置如下两项会报错

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    public Integer getId() {
        return id;
    }
    
2.src/META-INF/persistence.xml 是否可以自己定义路径

3.映射双向多对一的关联关系
  
  ①. 推荐保存时，先保存1的一方，再保存多的一方，否则会多出update 1*n 条语句
  ②. 通过@OneToMany的mappedBy 属性制定由n的一方customer来维护关联关系，而1的一方放弃维护
      注：使用了mappedBy属性就不能再使用@JoinColumn注解
  