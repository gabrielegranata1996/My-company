package com.soprasteria.mycompany.business;

import java.sql.SQLException;

import com.soprasteria.mycompany.entity.Employee;

/**
 * THIS INTERFACE DECLARES THE SIGNATURES METHODS, THERE'RE IMPLEMENTS IN
 * EMPLOYEE CONTROLLER IMPL
 * 
 * @author ggranata
 *
 */
public interface EmployeeController {

	/**
	 * GET EMPLOYEE METHOD
	 * 
	 * @param employeeId
	 * @return
	 */
	public Employee getEmployee(int employeeId);

	/**
	 * INSERT EMPLOYEE METHOD
	 * 
	 * @param employee
	 * @return
	 */
	public String createEmployee(Employee employee);

	/**
	 * UPDATE EMPLOYEE METHOD
	 * 
	 * @param employee
	 * @return
	 */
	public String updateEmployee(Employee employee);

	/**
	 * DELETE EMPLOYEE METHOD
	 * 
	 * @param employee
	 * @return
	 */
	public String deleteEmployee(Employee employee);

	/**
	 * FIND EMPLOYEE METHOD
	 * 
	 * @param emailColumnValue
	 * @param firstNameColumnValue
	 * @param lastNameColumnValue
	 * @return
	 * @throws SQLException
	 */
	public Employee findEmployee(String emailColumnValue, String firstNameColumnValue, String lastNameColumnValue)
			throws SQLException;

}
