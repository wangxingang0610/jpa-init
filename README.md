
参考：
http://www.cnblogs.com/crawl/p/7703679.html


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
 
4.映射双向一对一的关联关系
  find
  
5.多对多双向关联映射，指定一方维护关系

6.双向关联时，考虑由一方来维护关系即可


6.二级缓存配置步骤

    a. pom.xml:
    
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-ehcache</artifactId>
            <version>4.1.9.Final</version>
        </dependency>
        
    b. persistence.xml 文件中配置二级缓存相关
    
            <!-- 配置二级缓存的策略 
                    ALL：所有的实体类都被缓存
                    NONE：所有的实体类都不被缓存. 
                    ENABLE_SELECTIVE：标识 @Cacheable(true) 注解的实体类将被缓存 
                    DISABLE_SELECTIVE：缓存除标识 @Cacheable(false) 以外的所有实体类
                    UNSPECIFIED：默认值，JPA 产品默认值将被使用 -->
            <shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
            
            
            <!-- 二级缓存相关 -->
            <properties>
            
                <property name="hibernate.cache.use_second_level_cache"
                    value="true" />
                <property name="hibernate.cache.region.factory_class"
                    value="org.hibernate.cache.ehcache.EhCacheRegionFactory" />
                <property name="hibernate.cache.use_query_cache" value="true" />
            </properties>
            
    c.  导入 ehcache 的 jar 包和配置文件 ehcache.xml 
      内容省略
    
    d.  具体使用
    
        @Table(name = "JPA_CUSTOMER")
        @Entity
        @Cacheable
        public class Customer {
        
        }
    