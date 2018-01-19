package com.soprasteria.mycompany.business;

import java.sql.SQLException;
import java.util.List;

import com.soprasteria.mycompany.entity.Job_History;

public interface JobHistoryController {

	/**
	 * GET JOB HISTORY METHOD
	 * 
	 * @param employeeID
	 * @return
	 * @throws SQLException 
	 */
	public Job_History getJobHistory(int employeeID) throws SQLException;

	/**
	 * INSERT JOB HISTORY METHOD
	 * 
	 * @param jhistory
	 * @return
	 */
	public String createJobHistory(Job_History jhistory);

	/**
	 * UPDATE JOB HISTORY METHOD
	 * 
	 * @param jhistory
	 * @return
	 */
	public String updateJobHistory(Job_History jhistory);

	/**
	 * FIND JOB HISTORY METHOD
	 * 
	 * @param employeeId
	 * @param jobId
	 * @return
	 * @throws SQLException 
	 */
	public List<Job_History> findJobHistory(int employeeId, int departmentId) throws SQLException;
}
