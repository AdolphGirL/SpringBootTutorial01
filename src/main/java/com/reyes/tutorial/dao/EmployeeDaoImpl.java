package com.reyes.tutorial.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reyes.tutorial.entities.Department;
import com.reyes.tutorial.entities.Employee;

@Repository
public class EmployeeDaoImpl {
	
	private static Map<Integer, Employee> employees = null;
	
	@Autowired
	private DepartmentDaoImpl departmentDao;
	
	private static Integer initId = 1006;
	
	static{
		employees = new HashMap<Integer, Employee>();
		
		employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1, Date.valueOf("2015-06-16"), new Department(101, "D-AA")));
		employees.put(1002, new Employee(1002, "E-BB", "bb@163.com", 0, Date.valueOf("2016-06-16"), new Department(102, "D-BB")));
		employees.put(1003, new Employee(1003, "E-CC", "cc@163.com", 1, Date.valueOf("2017-06-16"), new Department(103, "D-CC")));
		employees.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0, Date.valueOf("2018-06-16"), new Department(104, "D-DD")));
		employees.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1, Date.valueOf("2019-06-16"), new Department(105, "D-EE")));
	}
	
	public void save(Employee employee){
		if(employee.getId() == null){
			employee.setId(initId++);
		}
		
		employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
		employees.put(employee.getId(), employee);
	}
	
	public Collection<Employee> getEmployees(){
		return employees.values();
	}
	
	public Employee getEmployee(Integer id){
		return employees.get(id);
	}
	
	public void deleteEmployee(Integer id){
		employees.remove(id);
	}
}
