package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.soprasteria.mycompany.business.JobHistoryController;
import com.soprasteria.mycompany.business.rowMapper.JobHistoryRowMapper;
import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Job;
import com.soprasteria.mycompany.entity.Job_History;
import com.soprasteria.mycompany.entity.RecordFilter.JoinCondition;

public class JobHistoryControllerImpl extends EntityController implements JobHistoryController {

	private PreparedStatement stm;
	private ResultSet rs;

	public JobHistoryControllerImpl() {
		super();
	}

	public JobHistoryControllerImpl(Connection conn) {
		super(conn);
	}

	protected void initValues() {
		this.tableName = "job_history";
		this.primaryKeyColumn = "nd";
	}

	/**
	 * METHOD MANAGE THE PRINTING ABOUT JOB HISTORY TABLE
	 * 
	 * @throws SQLException
	 */
	@Override
	public Job_History getJobHistory(int employeeID) throws SQLException {
		Job_History jhistory = new Job_History();

		String sql = "select * from job_history where employee_id=?";
		try {
			openConnection();
			stm = conn.prepareStatement(sql);

			stm.setInt(1, employeeID);

			rs = stm.executeQuery();

			if (rs.next()) {
				// GESTIONE CHIAVE ESTERNE
				int empId = rs.getInt("employee_id");
				EmployeeControllerImpl empController = new EmployeeControllerImpl();
				Employee emp = empController.getEmployee(empId);
				jhistory.setEmployee(emp);

				jhistory.setStartDate(rs.getDate("start_date"));
				jhistory.setEndDate(rs.getDate("end_date"));

				// GESTIONE CHIAVE ESTERNA
				String jobId = rs.getString("job_id"); // creo una variabile che conterrà il valore recuperato dalla
														// query
				JobControllerImpl controller = new JobControllerImpl();
				Job job = controller.getJob(jobId);
				jhistory.setJob(job);

				// GESTIONE CHIAVI ESTERNE
				int depId = rs.getInt("department_id");
				DepartementControllerImpl loc = new DepartementControllerImpl();
				Departement dep = loc.getDepartement(depId);
				jhistory.setDepartment(dep);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jhistory = null;
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
			} catch (Exception e2) {

			}
		}
		return jhistory;

	}

	/**
	 * THIS METHOD CONTROLE THE NULLABLE VARIABLES
	 * 
	 * @param jHistory
	 * @return
	 */
	public boolean validateJobHistory(Job_History jHistory) {
		Boolean validate = true;

		// CONTROLLO CAMPO EMPLOYEE_ID NULLABLE (OBBLIGATORIO)
		if (jHistory.getEmployee() == null) {
			System.out.print("\n" + "ERRORE: Campo Employee_ID OBBLIGATORIO!!");
			return validate = false;
		}

		// CONTROLLO CAMPO START DATE NULLABLE (OBBLIGATORIO)
		if (jHistory.getStartDate() == null) {
			System.out.print("\n" + "ERRORE: Campo Start_Date OBBLIGATORIO!!");
			return validate = false;
		}

		// CONTROLLO CAMPO END DATE NULLABLE (OBBLIGATORIO)
		if (jHistory.getEndDate() == null) {
			System.out.print("\n" + "ERRORE: Campo End_Date OBBLIGATORIO!!");
			return validate = false;
		}

		// CONTROLLO CAMPO JOB_ID NULLABLE (OBBLIGATORIO)
		if (jHistory.getJob() == null) {
			System.out.print("\n" + "ERRORE: Campo Job_ID OBBLIGATORIO!!");
			return validate = false;
		}
		return validate;
	}

	/**
	 * METHOD CHECK THE START AND END DATE DURING AN UPDATE
	 * 
	 * @param jHistory
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean controlStartEndDate(Job_History jHistory) throws SQLException {
		Boolean risControl = false;
		int i = 0;

		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		try {
			// CONTROLLO SULLA DATA DI INIZIO E DATA DI FINE
			i = jHistory.getStartDate().compareTo(jHistory.getEndDate()); // METODO compareTo CHE
			if (i < 0) {// RESTITUISCE UN VALORE
						// MAGGIORE DI 0 SE L'OGGETTO CHE INVOCA IL
						// METODO compareTo HA UNA DATA ANTECEDENTE A
						// QUELLA PASSATA COME ARGOMENTO
				System.out.print("\n" + "La START_DATE e\' minore della END_DATE");

				// CONTROLLO ESISTENZA DI OGGETTI ALL'INTERNO DELLA TABELLA (UNIQUE)
				String controllo = "select count(*) \r\n" + "from JOB_HISTORY " + "where employee_id=? \r\n"
						+ "and ((? between START_DATE and END_DATE)" + "        or (? between START_DATE and END_DATE)"
						+ "        or (?<START_DATE and ?>END_DATE))";

				stm = conn.prepareStatement(controllo);

				stm.setInt(1, jHistory.getEmployee().getEmployeeId()); // POS 1, EMPLOYEE_ID

				java.util.Date utilDate = new java.util.Date();
				utilDate = jHistory.getStartDate(); // METHOD START_DATE
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				stm.setDate(2, sqlDate); // INSERT DATE AFTER YHE CONVERSIONIN JAVA.UTIL.DATE

				java.util.Date utilDate1 = new java.util.Date();
				utilDate = jHistory.getEndDate(); // METHOD END_DATE
				java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
				stm.setDate(3, sqlDate); // INSERT DATE AFTER YHE CONVERSIONIN JAVA.UTIL.DATE

				java.util.Date utilDate4 = new java.util.Date();
				utilDate = jHistory.getStartDate(); // METHOD START_DATE
				java.sql.Date sqlDate4 = new java.sql.Date(utilDate.getTime());
				stm.setDate(4, sqlDate); // INSERT DATE AFTER YHE CONVERSIONIN JAVA.UTIL.DATE

				java.util.Date utilDate5 = new java.util.Date();
				utilDate = jHistory.getEndDate(); // METHOD END_DATE
				java.sql.Date sqlDate5 = new java.sql.Date(utilDate.getTime());
				stm.setDate(5, sqlDate); // INSERT DATE AFTER YHE CONVERSIONIN JAVA.UTIL.DATE

				rs = stm.executeQuery();

				if (rs.next()) {
					int cont = rs.getInt(1);

					if (cont > 0) {
						System.out.print("\n" + "Errore: impossibile inserire questa Job History!!");
						risControl = false;
					}
				}
			} else { // RESTITUISCE UN VALORE MAGGIORE DI 0 SE L'OGGETTO CHE INVOCA IL METODO HA UNA
				// DATA SUCCESSIVA A QUELLA PASSATA COME ARGOMENTO
				System.out.print("La START_DATE e\' maggiore della END_DATE");
				risControl = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			risControl = false;
		}
		return risControl;
	}

	/**
	 * METHOD MANAGE THE INSERT OF JOB HISTORY
	 */
	@Override
	public String createJobHistory(Job_History jHistory) {
		String ris = null;

		validateJobHistory(jHistory);

		try {
			openConnection();
			if (controlStartEndDate(jHistory)) { // VIENE ESEGUITA LA INSERT SE E SOLO SE VIENE
				// SODDISFATTA LA CONDIZIONE DEL METODO
				// controlStartEndDate
				// INSERT JOB_HISTORY
				String sql = "insert into job_history (employee_id,start_date,end_date,job_id,department_id)"
						+ " values (?,?,?,?,?)";

				stm = conn.prepareStatement(sql);

				stm.setInt(1, jHistory.getEmployee().getEmployeeId()); // POS 1, EMPLOYEE_ID

				java.util.Date utilDate = new java.util.Date();
				utilDate = jHistory.getStartDate();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				stm.setDate(2, sqlDate); // INSERT DATE AFTER YHE CONVERSION JAVA.UTIL.DATE

				java.util.Date utilDate1 = new java.util.Date();
				utilDate1 = jHistory.getEndDate();
				java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
				stm.setDate(3, sqlDate1); // INSERT DATE AFTER YHE CONVERSION JAVA.UTIL.DATE

				stm.setString(4, jHistory.getJob().getJobId()); // POS 4, JOB_ID

				if (jHistory.getDepartment() == null) {
					stm.setNull(5, Types.INTEGER);
					return ris = "INSERIMENTO CON DEPARTMENT_ID null";
				} else {
					stm.setInt(5, jHistory.getDepartment().getDepartementId()); // SE POPOLATO INSERT DEPARTMENT_ID, POS
																				// 5
				}
				stm.executeUpdate();
				ris = "\n" + "JOB_HISTORY INSERITO!!";
			} else {
				System.out.print("\n" + "ERRORE NELLA DATA!!");
				ris = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ris = null;
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
	 * THIS METHOD MANAGE THE UPDATE INTO JOB HISTORY TABLE
	 */
	@Override
	public String updateJobHistory(Job_History jhistory) {
		String ris = null;

		validateJobHistory(jhistory);

		try {
			openConnection();
			if (controlStartEndDate(jhistory)) {
				String sql = "update job_history set start_date=?, end_date=?, job_id=?, department_id=?"
						+ "where employee_id=?";

				stm = conn.prepareStatement(sql);

				java.util.Date utilDate = new java.util.Date();
				utilDate = jhistory.getStartDate();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				stm.setDate(1, sqlDate); // INSERT DATE AFTER YHE CONVERSION JAVA.UTIL.DATE

				java.util.Date utilDate1 = new java.util.Date();
				utilDate1 = jhistory.getEndDate();
				java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
				stm.setDate(2, sqlDate1); // INSERT DATE AFTER YHE CONVERSION JAVA.UTIL.DATE

				stm.setString(3, jhistory.getJob().getJobId());
				stm.setInt(4, jhistory.getDepartment().getDepartementId());
				stm.setInt(5, jhistory.getEmployee().getEmployeeId());

				stm.executeQuery();
				ris = "JOB HISTORY AGGIORNATA!!";
			} else {
				System.out.print("\n" + "ERRORE NELLA DATA!!");
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

	/**
	 * METHOD MANAGE THE FIND INTO JOB HISTORY TABLE
	 * 
	 * @throws SQLException
	 */
	@Override
	public List<Job_History> findJobHistory(int employeeId, int departmentId) throws SQLException {

		openDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		if (employeeId == 0 && departmentId == 0) {
			System.out.print("ERRORE: Campo nullo!!!");
		}

		@SuppressWarnings("unchecked")
		List<Job_History> jHistory = (List<Job_History>) jdbcTemplate.query(
				"select * from job_history where employee_id=? "+JoinCondition.and+" department_id=?",
				new Object[] { employeeId, departmentId }, new JobHistoryRowMapper());

		return jHistory;
	}
}
