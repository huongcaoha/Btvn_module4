package com.ra.session01.controller;

import com.ra.session01.model.entity.Employee;
import com.ra.session01.model.service.employee.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String index(Model model,HttpSession session){
        List<Employee> employees = employeeService.findAll();
        String search = (String) session.getAttribute("searchEmployee");
        if(search != null){
           employees = employees.stream().filter(employee -> employee.getFullName().contains(search) ||
                    employee.getDepartmentName().contains(search)).collect(Collectors.toList());
           model.addAttribute("search",search);
        }
        model.addAttribute("employees",employees);
        return "/employee/employee";
    }

    @GetMapping("/create")
    public String add(Model model){
        model.addAttribute("employee",new Employee());
        return "/employee/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("employee") Employee employee , RedirectAttributes redirectAttributes , Model model){
        Employee employee1 = employeeService.save(employee);
        if(employee1 != null){
            redirectAttributes.addFlashAttribute("rsCreate" , true);
            return "redirect:/employee";
        }else {
            model.addAttribute("rsCreate",false);
            return "/employee/employee";
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable long id , Model model){
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee",employee);
        return "/employee/update";
    }

    @PostMapping("/update/{id}")
    public String edit(@PathVariable long  id ,@ModelAttribute("employee") Employee employee,
                       RedirectAttributes redirectAttributes ,Model model){
        employee.setId(id);
        Employee rs = employeeService.save(employee);
        if(rs != null){
            redirectAttributes.addFlashAttribute("rsUpdate",true);
            return "redirect:/employee";
        }else {
            model.addAttribute("employee",employee);
            model.addAttribute("rsUpdate",false);
            return "/employee/update";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id){
        Employee employee = employeeService.findById(id);
        if(employee != null){
            employeeService.delete(employee);
        }
        return "redirect:/employee";
    }

    @PostMapping("search")
    public String search(@RequestParam("search") String search,HttpSession session){
        session.setAttribute("searchEmployee",search);
        return "redirect:/employee";
    }
}
