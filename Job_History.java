package com.soprasteria.mycompany.entity;

import java.util.Date;

public class Job_History {
	private Employee employeeId; // PRIMARY KEY
	private Date startDate;
	private Date endDate;
	private Job jobId; // FOREIGN KEY
	private Departement departmentId; // FOREIGN KEY

	public Employee getEmployee() {
		return employeeId;
	}

	public void setEmployee(Employee employeeId) {
		this.employeeId = employeeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Job getJob() {
		return jobId;
	}

	public void setJob(Job jobId) {
		this.jobId = jobId;
	}

	public Departement getDepartment() {
		return departmentId;
	}

	public void setDepartment(Departement departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	/**
	 * TO STRING WITH STRING BUILDER METHOD
	 */
	public String toString() {
		StringBuilder strBuild = new StringBuilder();

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- PRINTING JOB HISTORY(" + employeeId + ") --------");
		if (getEmployee() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" EMPLOYEE ID: " + employeeId);
		}
		if (getStartDate() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" START DATE: " + startDate);
		}
		if (getEndDate() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" END DATE: " + endDate);
		}
		if (getJob() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getJob().toString());
		}
		if (getDepartment() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getDepartment().toString());
		}

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- END PRINTING JOB HISTORY(" + employeeId + ") --------");
		strBuild.append(System.getProperty("line.separator"));
		return strBuild.toString();
	}

	/**
	 * THIS METHOD COMPARE GET AND CONTROL THE NULL VALUE
	 * 
	 * @param jHistory
	 * @return
	 */
	public boolean compareEquals(Job_History jHistory) {
		Boolean risCompare = true;

		if (jHistory.getEmployee() != null && jHistory.getEmployee().equals(getEmployee())) {
			risCompare = false;
		}

		if (jHistory.getStartDate() != null && jHistory.getStartDate().equals(getStartDate())) {
			risCompare = false;
		}

		if (jHistory.getEndDate() != null && jHistory.getEndDate().equals(getEndDate())) {
			risCompare = false;
		}

		if (jHistory.getJob() != null && jHistory.getJob().equals(getJob())) {
			risCompare = false;
		}

		if (jHistory.getDepartment() != null && jHistory.getDepartment().equals(getDepartment())) {
			risCompare = false;
		}
		return risCompare;
	}

}