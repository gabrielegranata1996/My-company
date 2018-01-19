package com.soprasteria.mycompany.business.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Job;
import com.soprasteria.mycompany.entity.Job_History;

@SuppressWarnings("rawtypes")
public class JobHistoryRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Job_History jobHistory= new Job_History();
		Employee emp = new Employee();
		Job job = new Job();
		Departement dep = new Departement();

		emp.setEmployeeId(rs.getInt("employee_id"));
		jobHistory.setEmployee(emp);

		jobHistory.setStartDate(rs.getDate("start_date"));
		jobHistory.setEndDate(rs.getDate("end_date"));

		job.setJobId(rs.getString("job_id"));
		jobHistory.setJob(job);

		dep.setDepartementId(rs.getInt("employee_id"));
		jobHistory.setDepartment(dep);
		
		return rs.getString(1);
//		return jobHistory;
	}

}
