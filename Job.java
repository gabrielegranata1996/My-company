package com.soprasteria.mycompany.entity;

public class Job {
	private String jobId; // PRIMARY KEY
	private String jobTitle;
	private int minSalary;
	private int maxSalary;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}

	public int getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(int maxSalary) {
		this.maxSalary = maxSalary;
	}

	@Override
	/**
	 * TO STRING WITH STRING BUILDER METHOD
	 */
	public String toString() {
		StringBuilder strBuild = new StringBuilder();

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- PRINTING JOB(" + jobId + ") --------");

		if (getJobId() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" JOB ID: " + jobId);
		}
		if (getJobTitle() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" JOB TITLE: " + jobTitle);
		}
		if (getMinSalary() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" MIN SALARY: " + minSalary);
		}
		if (getMaxSalary() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" MAX SALARY: " + maxSalary);
		}
		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- END PRINTING JOB(" + jobId + ") --------");
		strBuild.append(System.getProperty("line.separator"));

		return strBuild.toString();
	}

	/**
	 * THIS METHOD COMPARE AND CONTROL THE NULL VALUE ABOUT OBJECT ATTRIBUTE
	 * 
	 * @param job
	 * @return
	 */
	public boolean compareEquals(Job job) {
		Boolean risComp = true;

		if (job.getJobId() != null && job.getJobId().equals(getJobId())) {
			return risComp = false;
		}

		if (job.getJobTitle() != null && job.getJobTitle().equals(getJobTitle())) {
			return risComp = false;
		}

		if (job.getMaxSalary() != 0 && job.getMaxSalary() == (getMaxSalary())) {
			return risComp = false;
		}

		if (job.getMinSalary() != 0 && job.getMinSalary() == (getMinSalary())) {
			return risComp = false;
		}
		return risComp;
	}

}
