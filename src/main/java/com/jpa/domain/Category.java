package com.jpa.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "JPA_CATEGORY")
@Entity
public class Category {

    private Integer id;

    private String categoryName;

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "CATEGORY_NAME")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private Set<Item> items = new HashSet<>();


    @ManyToMany(mappedBy = "categorys")
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
