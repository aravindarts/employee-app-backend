package com.example.testspring.DO;


import javax.persistence.*;

@Entity
@Table(schema = "public",name ="testemployee")
public class    Employee {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "employee",fetch = FetchType.LAZY)
    private EmployeeSalery employeeSalery;




    public Employee() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    public EmployeeSalery getEmployeeSalery() {
        return employeeSalery;
    }

    public void setEmployeeSalery(EmployeeSalery employeeSalery) {
        this.employeeSalery = employeeSalery;
    }
}
