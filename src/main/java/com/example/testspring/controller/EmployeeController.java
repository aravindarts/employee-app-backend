package com.example.testspring.controller;


import com.example.testspring.BO.Pagination;
import com.example.testspring.BO.SearchCriteria;
import com.example.testspring.DO.Employee;
import com.example.testspring.service.EmployeeService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/findAll")
    public JSONObject findAllEmployee(){
        JSONObject json =employeeService.findAll();
        return json;
    }
    @PostMapping(path = "/find")
    public JSONObject find(@RequestBody String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        List<SearchCriteria> searchCriterias =employeeService.searchCriteriasFromJson(jsonObject);
        Pagination pagination =employeeService.getpaginationFromJson(jsonObject);
        JSONObject response =employeeService.searchEmployees(searchCriterias,pagination);
        System.out.println(searchCriterias);
      return response;
    }

    @PostMapping(path = "/save")
    public JSONObject save(@RequestBody String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        return employeeService.addEmployee(jsonObject);
    }

    @DeleteMapping(path = "/delete/{id}")
    public JSONObject delete(@PathVariable Integer id){
        return employeeService.deleteEmployee(id);
    }




}
