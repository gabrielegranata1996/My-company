package com.soprasteria.mycompany.business.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.soprasteria.mycompany.entity.Job;

@SuppressWarnings("rawtypes")
public class JobRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Job job= new Job();
		
		job.setJobId(rs.getString("job_id"));
		job.setJobTitle(rs.getString("job_title"));
		job.setMinSalary(rs.getInt("min_salary"));
		job.setMaxSalary(rs.getInt("max_salary")); 
		
		return job;
	}

}
