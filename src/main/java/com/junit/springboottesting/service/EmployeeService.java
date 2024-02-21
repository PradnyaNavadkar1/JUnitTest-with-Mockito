package com.junit.springboottesting.service;

import com.junit.springboottesting.model.Employee;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployee();
    Employee getEmployeeById(long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(long id);
}
