package com.jpa.domain;

import javax.persistence.*;

@Table(name = "JPA_MANAGER")
@Entity
public class Manager {

    private Integer id;

    private String managerName;


    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "MANAGER_NAME")
    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    private Department department;

    @OneToOne(mappedBy = "manager")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
