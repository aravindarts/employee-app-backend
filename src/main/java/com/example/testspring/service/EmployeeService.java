package com.example.testspring.service;


import com.example.testspring.BO.Pagination;
import com.example.testspring.BO.SearchCriteria;
import com.example.testspring.DO.Employee;
import com.example.testspring.DO.EmployeeSalery;
import com.example.testspring.Spec.CustomSpecification;
import com.example.testspring.repo.EmployeeDao;
import com.example.testspring.repo.EmployeeSaleryDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final EmployeeSaleryDao employeeSaleryDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao, EmployeeSaleryDao employeeSaleryDao) {
        this.employeeDao = employeeDao;
        this.employeeSaleryDao = employeeSaleryDao;
    }

    public JSONObject findAll() {
        List<Employee> employeeList= employeeDao.findAll();
        JSONArray jsonArray =constructEmployeeJsonArray(employeeList);
        JSONObject response =new JSONObject();
        response.put("data",jsonArray);
        response.put("status","success");
        System.out.println(employeeList);
        return response;
    }

    public JSONObject find(String name,Integer age) {



        List<Employee> employeeList=null;
        if(age!=null){
            employeeList=employeeDao.findByNameAndAge(name,age);
        }else{
            employeeList= employeeDao.findByName(name);
        }
        JSONArray jsonArray = constructEmployeeJsonArray(employeeList);
        JSONObject response =new JSONObject();
        response.put("data",jsonArray);
        response.put("status","success");
        System.out.println(employeeList);
        return response;
    }

    private JSONArray  constructEmployeeJsonArray(List<Employee> employeeList) {
        JSONArray jsonArray = new JSONArray();
        for(Employee employee:employeeList){
            EmployeeSalery employeeSalery = employee.getEmployeeSalery();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",employee.getId());
            jsonObject.put("name",employee.getName());
            jsonObject.put("mail",employee.getEmail());
            jsonObject.put("age",employee.getAge());
            jsonObject.put("salery",employeeSalery!=null?employeeSalery.getSalary():null);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public JSONObject addEmployee(JSONObject json) {
        Employee employee = new Employee();
        employee.setName((String) json.get("name"));
        employee.setEmail((String) json.get("email"));
        employee.setAge((Integer) json.get("age"));
        employee.setId((Integer) json.get("id"));
        employeeDao.save(employee);
        EmployeeSalery employeeSalery = new EmployeeSalery();
        employeeSalery.setAcNo((String) json.get("acNo"));
        employeeSalery.setSalary((Double) json.get("salary"));
        employeeSalery.setEmployee(employee);
        employeeSaleryDao.save(employeeSalery);
        JSONObject response =new JSONObject();
        response.put("data","Saved successfully");
        response.put("status","success");
        return response;
    }

    public JSONObject deleteEmployee(Integer id) {
        employeeSaleryDao.deleteByemployeeId(id);
        employeeDao.deleteById(id);
        JSONObject response =new JSONObject();
        response.put("data","Delete successfully");
        response.put("statuss","success");
        return response;
    }


    public List<SearchCriteria> searchCriteriasFromJson (JSONObject jsonObject) {
        List<SearchCriteria> searchCriterias= null;
        if (jsonObject != null) {
            Type type = new TypeToken<List<SearchCriteria>>() {}.getType();
            searchCriterias = new Gson().fromJson( jsonObject.get("criterias").toString(), type);
        }
        return searchCriterias;
    }

    public JSONObject searchEmployees(List<SearchCriteria> searchCriterias, Pagination pagination) {
        CustomSpecification spec =new CustomSpecification(searchCriterias);
        Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getSize());
        Page<Employee> productsResult=(Page<Employee>)(Page<?>) employeeDao.findAll(Specification.where(spec),pageable);
        JSONArray jsonArray = constructEmployeeJsonArray(productsResult.toList());
        System.out.println(productsResult);
        JSONObject response =new JSONObject();
        response.put("data",jsonArray);
        response.put("totalpages",productsResult.getTotalPages());
        response.put("total",productsResult.getTotalElements());
        response.put("status","success");
        return response;
    }

    public Pagination getpaginationFromJson(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.get("pagination").toString(),Pagination.class);
    }
}
