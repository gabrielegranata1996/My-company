package com.soprasteria.mycompany.business;

import java.sql.SQLException;
import java.util.List;

import com.soprasteria.mycompany.entity.Job;

public interface JobController {

	/**
	 * GET JOB METHOD
	 * 
	 * @param jobId
	 * @return
	 */
	public Job getJob(String jobId);

	/**
	 * INSERT JOB METHOD
	 * 
	 * @param job
	 * @return
	 */
	public String createJob(Job job);

	/**
	 * UPDATE JOB METHOD
	 * 
	 * @param job
	 * @return
	 */
	public String updateJob(Job job);

	/**
	 * DELETE JOB METHOD
	 * 
	 * @param job
	 * @return
	 */
	public String deleteJob(Job job);

	/**
	 * FIND JOB METHOD
	 * 
	 * @param job
	 * @return
	 * @throws SQLException 
	 */
	public List<Job> findJob(String jobId, String jobTitle) throws SQLException;
}
