package com.ra.session01.model.service.employee;

import com.ra.session01.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee save(Employee employee);
    void delete(Employee employee);
    Employee findById(long id);
}
