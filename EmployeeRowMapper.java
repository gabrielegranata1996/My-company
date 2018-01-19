package com.soprasteria.mycompany.business.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Job;

public class EmployeeRowMapper implements RowMapper<Object> {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee emp = new Employee();
		Departement dep = new Departement();
		Job job = new Job();

		emp.setEmployeeId(rs.getInt("employee_id"));
		emp.setFirstName(rs.getString("first_name"));
		emp.setLastName(rs.getString("last_name"));
		emp.setEmail(rs.getString("email"));
		emp.setPhoneNumber(rs.getString("phone_number"));
		emp.setHireDate(rs.getDate("hire_date"));
		emp.setSalary(rs.getInt("salary"));
		emp.setCommissionPCT((double) rs.getInt("commission_pct"));

		dep.setDepartementId(rs.getInt("department_id"));
		emp.setDepartement(dep);
		job.setJobId(rs.getString("job_id"));
		emp.setJob(job);

		int managerIdInner = rs.getInt("manager_id");
		Employee manager = new Employee();
		manager.setEmployeeId(managerIdInner);
		return emp;
	}

}
