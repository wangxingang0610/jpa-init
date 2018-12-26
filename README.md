
1.同时设置如下两项会报错

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    public Integer getId() {
        return id;
    }
    
2.src/META-INF/persistence.xml 是否可以自己定义路径