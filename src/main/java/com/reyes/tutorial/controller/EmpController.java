package com.reyes.tutorial.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.reyes.tutorial.dao.DepartmentDaoImpl;
import com.reyes.tutorial.dao.EmployeeDaoImpl;
import com.reyes.tutorial.entities.Department;
import com.reyes.tutorial.entities.Employee;

@Controller
public class EmpController {

	@Autowired
	private EmployeeDaoImpl employeeDaoImpl;
	
	@Autowired
	private DepartmentDaoImpl departmentDaoImpl;
	
	@GetMapping(value = "/emps")
	public String listEmps(Model model){
		model.addAttribute("emps", employeeDaoImpl.getEmployees());
		return "list";
	}
	
	@GetMapping(value = "/emp/{id}")
	public String listOneExistEmp(@PathVariable("id") Integer id, Model model){
		Employee emp = employeeDaoImpl.getEmployee(id);
		model.addAttribute("emp", emp);
		
		Collection<Department> depts = departmentDaoImpl.getDepartments();
		model.addAttribute("depts", depts);
		return "emps/emp";
	}
	
	@GetMapping(value = "/emp")
	public String listOneEmp(Model model){
		Collection<Department> depts = departmentDaoImpl.getDepartments();
		model.addAttribute("depts", depts);
		return "emps/emp";
	}
	
	/**
	 * 用一个实体bean来接收表单整体数据，注意bean的属性要和表单name一致
	 * 只有表单name与bean中属性值一致才会正确赋值，否则bean获取到的值为null
	 * 可以不添加任何注解
	 * 也可以添加注解 @ModelAttribute,括号里的别名可以任意取，也可以不填
	 */
//	@RequestMapping(value="/bean/form/post/or/get",method={RequestMethod.POST,RequestMethod.GET})
//	public void formPost(@ModelAttribute UserInfo userInfo,String testParam){
//		logger.info("form表单提交，通过实体类接收,映射不到的属性,通过参数名接收");
//		logger.info("userInfo:"+userInfo);
//		logger.info("testParam:"+testParam);
//	}
	@PostMapping(value = "/emp")
	public String addOneEmp(Employee employee){
		employeeDaoImpl.save(employee);
		return "redirect:/emps";
	}
	
	@PutMapping(value = "/emp")
	public String updateOneEmp(Employee employee){
		employeeDaoImpl.save(employee);
		return "redirect:/emps";
	}
	
	@DeleteMapping(value = "/emp/{id}")
	public String deleteOneEmp(@PathVariable("id") Integer id){
		employeeDaoImpl.deleteEmployee(id);
		return "redirect:/emps";
	}
}
