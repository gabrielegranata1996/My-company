package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.soprasteria.mycompany.business.JobController;
import com.soprasteria.mycompany.business.rowMapper.JobRowMapper;
import com.soprasteria.mycompany.entity.Job;

public class JobControllerImpl extends EntityController implements JobController {

	private PreparedStatement stm;
	private ResultSet rs;

	public JobControllerImpl() {
		super();
		closeConnection = true;
	}

	public JobControllerImpl(Connection conn) {
		super();
		this.conn = conn;
		closeConnection = false;
	}

	protected void initValues() {
		this.tableName = "jobs";
		this.primaryKeyColumn = "job_id";
	}

	/**
	 * METHOD MANAGE THE PRINTING ABOUT JOB TABLE
	 */
	@Override
	public Job getJob(String jobId) {
		Job job = new Job();

		String sql = "select * from JOBS where job_id=? ";
		try {
			openConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, jobId);
			rs = stm.executeQuery();

			if (rs.next()) {
				job.setJobId(jobId);
				job.setJobTitle(rs.getString("job_title"));
				job.setMaxSalary(rs.getInt("min_salary"));
				job.setMaxSalary(rs.getInt("max_salary"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			job = null;
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
				closeConnection();
			} catch (Exception e) {

			}
		}
		return job;
	}

	/**
	 * THIS METHOD CONTROL THE NULLABLE VARIABLES
	 * 
	 * @param job
	 * @return
	 */
	public boolean validateJob(Job job) {
		Boolean validate = true;

		// CONTROLLO CAMPO JOB_ID NULLABLE (OBBLIGATORIO)
		if (job.getJobId() == null) {
			System.out.print("ERRORE: Campo Job_ID OBBLIGATORIO!!");
			validate = false;
			return validate;
		}

		// CONTROLLO CAMPO JOB_TITLE NULLABLE (OBBLIGATORIO)
		if (job.getJobTitle() == null) {
			System.out.print("ERRORE: Campo Job_Title OBBLIGATORIO!!");
			validate = false;
			return validate;
		}
		return validate;
	}

	/**
	 * THIS METHOD CONTROL JOB_ID EXIST
	 * 
	 * @param job
	 * @return
	 */
	public boolean controlJobTitle(Job job) throws SQLException {
		Boolean control = true;

		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		try {
			// CONTROLLO ESISTENZA DI OGGETTI ALL'INTERNO DELLA TABELLA (UNIQUE)
			String controllo = "select count (*) from jobs where job_title=?";

			stm = conn.prepareStatement(controllo);
			stm.setString(1, job.getJobTitle()); // POS 2, JOB_TITLE
			rs = stm.executeQuery();

			if (rs.next()) {
				int cont = rs.getInt(1);
				if (cont > 0) {
					System.out.print("\n" + "ERRORE: Job_title già presente nella tabella");
					control = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			control = null;
		}
		return control;
	}

	/**
	 * THIS METHOD MANAGE THE INSERT INTO JOB TABLE
	 */
	@Override
	public String createJob(Job job) {
		String ris = null;

		validateJob(job); // CALL METHOD VALIDATE FOR NULLABLE ATTRIBUTES

		try {
			openConnection();
			if (controlJobTitle(job)) { // VIENE ESEGUITA LA INSERT SE E SOLO SE VIENE SODDISFATTA LA
				// CONDIZIONE NEL METODO controlJobTitle
				// INSERT JOB TABLE
				String sql = "insert into jobs (job_id,job_title,min_salary,max_salary)" + " values (?,?,?,?)";

				stm = conn.prepareStatement(sql);

				stm.setString(1, job.getJobId()); // POS 1, JOB_ID
				stm.setString(2, job.getJobTitle()); // POS 2, JOB_TITLE

				// CONTROLLO CAMPO MIN_SALARY==0
				if (job.getMinSalary() == 0) {
					stm.setNull(3, Types.INTEGER);
					return ris = "Inserito Min salary: " + null;
				} else {
					stm.setInt(3, job.getMinSalary()); // SE CAMPO PIENO INSERT DEL MIN_SALARY POS 3
				}

				// CONTROLLO CAMPO MAX_SALARY==0
				if (job.getMaxSalary() == 0) {
					stm.setNull(4, Types.INTEGER);
					return ris = "Inserito Max Salary: " + null;
				} else {
					stm.setInt(4, job.getMaxSalary()); // SE CAMPO PIENO INSERT DEL MAX_SALARY POS 4
				}

				stm.executeUpdate(); // ESEGUO QUERY
				ris = "JOB INSERITO";

			} else {
				System.out.print("ERRORE: JOB_ID GIA\' VALIDATO!!");
				ris = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ris = null;
		} finally {
			try {
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
				closeConnection();
			} catch (Exception e) {

			}
		}

		return ris;
	}

	/**
	 * 
	 */
	@Override
	public String updateJob(Job job) {
		String ris = null;

		validateJob(job); // RICHIAMO METODO PER VALIDARE GLI ATTRIBUTI

		try {
			openConnection();

			boolean jobExists = this.entityExists(job.getJobId());
			if (jobExists && controlJobTitle(job)) { // VIENE ESEGUITA L'UPDATE SOLO SE VIENE SODDISFATTA LA CONDIZIONE
				// NEL METODO controlJobTitle
				String sql = "update jobs " + "set job_title=?, min_salary=?, max_salary=? where job_id=?";
				stm = conn.prepareStatement(sql);

				stm.setString(1, job.getJobTitle()); // SET JOB_TITLE
				stm.setInt(2, job.getMinSalary()); // SET MIN_SALARY
				stm.setInt(3, job.getMaxSalary()); // SET MAX_SALARY
				stm.setString(4, job.getJobId()); // SET JOB_ID (CONDIZIONE NEL WHERE)

				stm.executeUpdate();
				ris = "JOB AGGIORNATO!!";
			} else {
				System.out.print("ERRORE: JOB_ID GIA\' VALIDATO!!");
				ris = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ris = null;
		} finally {
			try {
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
				closeConnection();
			} catch (Exception e2) {

			}
		}

		return ris;
	}

	@Override
	public String deleteJob(Job job) {
		String ris = null;

		try {
			openConnection();

			boolean jobExists = this.entityExists(job.getJobId());
			EmployeeControllerImpl empController = new EmployeeControllerImpl(this.conn);
			boolean checkEmployeeForeignKey = empController.checkForeignKey("job_id", job.getJobId());

			if (jobExists && checkEmployeeForeignKey) {
				String sql = "delete from jobs where job_id=?";
				stm = conn.prepareStatement(sql);
				stm.setString(1, job.getJobId());
				stm.executeUpdate();

				ris = "\n \n" + "JOB ELIMINATO!!";
			} else {
				System.out.print("\n \n" + "IMPOSSIBILE EFFETTUARE LA DELETE!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ris = null;
		} finally {
			try {
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
				closeConnection();
			} catch (Exception e2) {

			}
		}
		return ris;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Job> findJob(String jobId, String jobTitle) throws SQLException {

		openDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		if (jobId == null && jobTitle == null) {
			System.out.print("ERRORE: Campo nullo!!!");
		}

		List<Job> job = (List<Job>) jdbcTemplate.query("select * from jobs where job_id=? and job_title=?",
				new Object[] { jobId, jobTitle }, new JobRowMapper());

		return job;
	}

}
