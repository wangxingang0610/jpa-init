package com.jpa.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "JPA_ITEM")
@Entity
public class Item {

    private Integer id;

    private String itemName;

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ITEM_NAME")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    private Set<Category> categorys = new HashSet<>();

    @JoinTable(name = "JPA_ITEM_CATEGORY",
                joinColumns = {@JoinColumn(name = "ITEM_ID",referencedColumnName = "ID")},
                inverseJoinColumns = {@JoinColumn(name="CATEGORY_ID",referencedColumnName = "ID")})
    @ManyToMany
    public Set<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<Category> categorys) {
        this.categorys = categorys;
    }
}
