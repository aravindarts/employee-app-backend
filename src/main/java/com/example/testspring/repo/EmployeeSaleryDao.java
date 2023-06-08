package com.example.testspring.repo;

import com.example.testspring.DO.EmployeeSalery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeSaleryDao extends JpaRepository<EmployeeSalery, Integer>, JpaSpecificationExecutor<EmployeeSalery> {

    @Modifying
    @Query(value = "delete from employee_salary where employee_id  =?1",nativeQuery = true)
    void deleteByemployeeId(Integer id);
}
