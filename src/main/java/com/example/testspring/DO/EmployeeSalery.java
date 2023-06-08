package com.example.testspring.DO;


import javax.persistence.*;

@Entity
@Table(name = "employee_salary")
public class EmployeeSalery {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name = "employee_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Employee employee;
    @Column(name = "salary")
    private Double salary;
    @Column(name = "ac_no")
    private String acNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}


