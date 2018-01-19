package com.soprasteria.mycompany.entity;

import java.util.Date;

public class Employee {
	private int employeeId; // PRIMARY KEY
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Date hireDate;
	private double salary;
	private double commissionPCT;
	private Job job; // FOREIGN KEY
	private Employee manager; // FOREIGN KEY
	private Departement departementId; // FOREIGN KEY

	public Departement getDepartement() {
		return departementId;
	}

	public void setDepartement(Departement departementId) {
		this.departementId = departementId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Double getCommissionPCT() {
		return commissionPCT;
	}

	public void setCommissionPCT(double commissionPCT) {
		this.commissionPCT = commissionPCT;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	/**
	 * TO STRING WITH STRING BUILDER METHOD
	 */
	@Override
	public String toString() {

		StringBuilder strBuild = new StringBuilder();

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- PRINTING EMPLOYEE(" + employeeId + ") --------");
		if (getEmployeeId() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" EMPLOYEE ID: " + employeeId);
		}
		if (getFirstName() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" FIRST NAME: " + firstName);
		}
		if (getLastName() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" LAST NAME: " + lastName);
		}
		if (getEmail() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" EMAIL: " + email);
		}
		if (getPhoneNumber() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" PHONE NUMBER: " + phoneNumber);
		}
		if (getHireDate() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" HIRE DATE: " + hireDate);
		}
		if (getSalary() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" SALARY: " + salary);
		}
		if (getCommissionPCT() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" COMMISSION PCT: " + commissionPCT);
		}

		if (getJob() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getJob().toString());
		}
		if (getManager() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(manager.toString());
		}
		if (getDepartement() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getDepartement().toString());
		}

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- END PRINTING EMPLOYEE(" + employeeId + ") --------");
		strBuild.append(System.getProperty("line.separator"));

		return strBuild.toString();
	}

	/**
	 * THIS METHOD COMPARE AND CONTROL THE NULL VALUE ABOUT OBJECT ATTRIBUTE
	 * TESTMAINUPDATE
	 * 
	 * @param employee
	 * @return
	 */
	public boolean compareEquals(Employee emp) {
		Boolean risCompare = true;

		// "test".equals(emp.getFirstName());
		// emp.getFirstName() != null && emp.getFirstName().equals("test");

		// CONTROLLO UGUAGLIANZA DEL SET NOME E CONTROLLO DEL VALORE NULL
		if (emp.getFirstName() != null && emp.getFirstName().equals(getFirstName())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET LAST NAME E CONTROLLO DEL VALORE NULL
		if (emp.getLastName() != null && emp.getLastName().equals(getLastName())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET EMAIL E CONTROLLO DEL VALORE NULL
		if (emp.getEmail() != null && emp.getEmail().equals(getEmail())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET PHONE_NUMBER E CONTROLLO DEL VALORE NULL
		if (emp.getPhoneNumber() != null && emp.getPhoneNumber().equals(getPhoneNumber())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET HIRE_DATE E CONTROLLO DEL VALORE NULL
		if (emp.getHireDate() != null && emp.getHireDate().equals(getHireDate())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET JOB_ID E CONTROLLO DEL VALORE NULL
		if (emp.getJob() != null && emp.getJob().equals(getJob())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET SALARY E CONTROLLO DEL VALORE NULL
		if (emp.getSalary() != 0 && emp.getSalary().equals(getSalary())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA SET COMMISSION_PCT E CONTROLLO DEL VALORE NULL
		if (emp.getSalary() != 0 && emp.getCommissionPCT().equals(getCommissionPCT())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET MANAGER_ID E CONTROLLO DEL VALORE NULL
		if (emp.getManager() != null && emp.getManager().equals(getManager())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET DEPARTMENT_ID E CONTROLLO DEL VALORE NULL
		if (emp.getDepartement() != null && emp.getDepartement().equals(getDepartement())) {
			return risCompare = false;
		}
		return risCompare;
	}
}
