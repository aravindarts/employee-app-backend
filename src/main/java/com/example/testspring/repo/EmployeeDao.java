package com.example.testspring.repo;

import com.example.testspring.DO.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee,Integer>, JpaSpecificationExecutor<Object> {


//    List<Employee> findByName(String name);
//    List<Employee> findByNameAndAge(String name,Integer age);


    @Query(value = "select * from testemployee where name =?1",nativeQuery = true )
    List<Employee> findByName(String name);

    @Query(value = "select * from testemployee where age =?2 and name =?1",nativeQuery = true )
    List<Employee> findByNameAndAge(String name,Integer age);

//    @Modifying
//    @Query(value = "delete from testemployee where id  =?1",nativeQuery = true)
//    void deleteById(Integer id);

}
