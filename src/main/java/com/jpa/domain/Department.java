package com.jpa.domain;

import javax.persistence.*;

@Table(name = "JPA_DEPARTMENT")
@Entity
public class Department {

    private Integer id;

    private String departmentName;

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "DEPARTMENT_NAME")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    private Manager manager;

    @JoinColumn(name = "MANAGER_ID",unique = true)
    @OneToOne
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
