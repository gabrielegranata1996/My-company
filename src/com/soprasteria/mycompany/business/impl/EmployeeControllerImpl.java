package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.soprasteria.mycompany.business.EmployeeController;
import com.soprasteria.mycompany.business.rowMapper.EmployeeRowMapper;
import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Job;
import com.soprasteria.mycompany.entity.RecordFilter;
import com.soprasteria.mycompany.entity.RecordFilter.JoinCondition;
import com.soprasteria.mycompany.entity.RecordFilter.Operator;
import com.soprasteria.mycompany.entity.EntityFilter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * THIS CLASS IMPLEMENT EMPLOYEE CONTROLLER DML METHOD: CREATE, READ, UPDATE AND
 * DELETE
 * 
 * @author ggranata
 *
 */
public class EmployeeControllerImpl extends EntityController implements EmployeeController {

	private PreparedStatement stm;
	private ResultSet rs;

	public EmployeeControllerImpl() {
		super();
	}

	// SETTO I VALORI DELLA TABELLA E DELLA COLONNA
	protected void initValues() {
		this.tableName = "employees";
		this.primaryKeyColumn = "employee_id";
	}

	/**
	 * @param conn
	 */
	public EmployeeControllerImpl(Connection conn) {
		super(conn);

	}

	/**
	 * METHOD MANAGE THE MANAGER ID FOREIGN KEY
	 * 
	 * @param managerId
	 * @return
	 */
	public Employee getManager(int managerId) {
		Employee emp = new Employee();

		String query = "select * from employees where employee_id=?";

		try {
			openConnection();
			stm = conn.prepareStatement(query);
			stm.setInt(1, managerId);
			rs = stm.executeQuery();

			if (rs.next()) {
				emp.setEmployeeId(managerId);
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setSalary((double) rs.getInt("salary"));
				emp.setCommissionPCT((double) rs.getInt("commission_pct"));
				// GESTIONE CHIAVI ESTERNE
				int departementId = rs.getInt("department_id"); // creo un variabile che conterrà il valore recuperato
																// dal DB
				Departement dep = new Departement(); // nell'oggetto DEPARTEMENT inserisco il
				// valore recuperato dal DB

				dep.setDepartementId(departementId);
				emp.setDepartement(dep);

				// GESTIONE CHIAVI ESTERNE
				String jobId = rs.getString("job_id"); // creo una variabile che conterrà il valore recuperato dal DB
				Job job = new Job();
				job.setJobId(jobId); // nell'oggetto JOB inseirsco il valore recuperato dal DB
				emp.setJob(job);

				// GESTIONE CHIAVI ESTERNE
				int managerIdInner = rs.getInt("manager_id"); // creo un variabile che conterrà il valore recuperato dal
																// DB

				Employee manager = new Employee();
				manager.setEmployeeId(managerIdInner); // nell'oggetto MANAGER inserisco il valore
				// recuperato dal DB
				emp.setManager(manager);

			}
		} catch (Exception e) {
			e.printStackTrace();
			emp = null;
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
		return emp;
	}

	/**
	 * METHOD MANAGE THE PRINTING ABOUT EMLOYEE TABLE
	 */
	@Override
	public Employee getEmployee(int employeeID) {
		Employee emp = new Employee();

		String query = "select * from employees where employee_id=?";

		try {
			openConnection();
			stm = conn.prepareStatement(query);
			stm.setInt(1, employeeID);
			rs = stm.executeQuery();

			if (rs.next()) {
				emp.setEmployeeId(employeeID);
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setSalary((double) rs.getInt("salary"));
				emp.setCommissionPCT((double) rs.getInt("commission_pct"));
				// GESTIONE CHIAVI ESTERNE
				int departementId = rs.getInt("department_id"); // creo un variabile che conterrà il valore recuperato
																// dal DB
				DepartementControllerImpl controller = new DepartementControllerImpl(conn); // istanzio l'oggetto di
				// tipo
				// ControllerImpl
				Departement dep = controller.getDepartement(departementId); // nell'oggetto DEPARTEMENT inserisco il
				// valore recuperato dal DB

				emp.setDepartement(dep);

				// GESTIONE CHIAVI ESTERNE
				String jobId = rs.getString("job_id");
				Job job = new Job();
				job.setJobId(jobId);
				emp.setJob(job);

				// GESTIONE CHIAVI ESTERNE
				int managerId = rs.getInt("manager_id"); // creo un variabile che conterrà il valore recuperato dal DB
				EmployeeControllerImpl control = new EmployeeControllerImpl(conn); // istanzio l'oggetto di tipo
																					// ControllerImpl
				Employee manager = control.getEmployee(managerId); // nell'oggetto MANAGER inserisco il valore
																	// recuperato dal DB

				emp.setManager(manager);

			}
		} catch (Exception e) {
			e.printStackTrace();
			emp = null;
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
		return emp;
	}

	/**
	 * CONTROL EMAIL UNIQUE
	 * 
	 * @param emp
	 * @return
	 */
	public boolean controlEmail(Employee emp) throws SQLException {
		Boolean control = true;

		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		try {
			// CONTROLLO ESISTENZA DI OGGETTI ALL'INTERNO DELLA TABELLA (UNIQUE)
			String controllo = "select count (*) from employees where email=?"; // COUNT ---> CONTA LA PRESENZA DI
																				// QUANTE
																				// MAIL SONO PRESENTI ALL'INTERNO DEL DB
			stm = conn.prepareStatement(controllo);
			stm.setString(1, emp.getEmail()); // SET EMAIL (CONDIZIONE LE WHERE)
			rs = stm.executeQuery();

			if (rs.next()) {
				int contEmail = rs.getInt(1);
				if (contEmail > 0) { // SE IL CONTATORE E' MAGGIORE DI 0 SIGNIFICA CHE C'E' GIA' UNA MAIL PRESENTE
					// ALL'INTERNO DELLA TABELLA, QUINDI NON INSERISCO
					System.out.print("Errore: EMPLOYEE GIA\' PRESENTE");
					control = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			control = false;
		}
		return control;
	}

	/**
	 * THIS METHOD CONTROL THE NULLABLE VARIABLES
	 * 
	 * @param emp
	 * @return
	 */
	public boolean validateEmployee(Employee emp, String operation) {
		Boolean validate = true;

		if (operation.equals("update")) {
			// CONTROLLO CAMPO EMPLOYEE_ID NULLABLE (OBBLIGATORIO)
			if (emp.getEmployeeId() == 0) {
				System.out.print("\n" + "ERRORE: Campo Employee_ID OBBLIGATORIO!!");
				validate = false;
				return validate;
			}
		}

		// CONTROLLO CAMPO LAST_NAME NULLABLE (OBBLIGATORIO)
		if (emp.getLastName() == null) {
			System.out.print("\n" + "ERRORE: Campo Last Name OBBLIGATORIO!!!");
			validate = false;
			return validate;
		}

		// CONTROLLO CAMPO EMAIL NULLABLE (OBBLIGATORIO)
		if (emp.getEmail() == null) {
			System.out.print("\n" + "ERRORE: Campo Email OBBLIGATORIO!!");
			validate = false;
			return validate;
		}

		// CONTROLLO CAMPO HIRE_DATE NULLABLE (OBBLIGATORIO)
		if (emp.getHireDate() == null) {
			System.out.print("\n" + "ERRORE: Campo Hire Date OBBLIGATORIO!!");
			validate = false;
			return validate;
		}

		// CONTROLLO CAMPO JOB_ID NULLABLE (OBBLIGATORIO)
		if (emp.getJob().getJobId() == null) {
			System.out.print("\n" + "ERRORE: Campo Job ID OBBLIGATORIO!!");
			validate = false;
			return validate;
		}

		return validate;

	}

	/**
	 * METHOD MANAGE THE INSERT OF EMPLOYEES
	 */
	@Override
	public String createEmployee(Employee employee) {
		String ris = null;
		String op = "insert";

		validateEmployee(employee, op);

		try {
			openConnection();
			if (controlEmail(employee)) { // VIENE ESEGUITA LA INSERT SE VIENE SODDISFATTA LA CONDIZONE
											// NEL METODO controlMail
											// QUERY DI INSERIMENTO NELLA TABELLA EMPLOYEE

				String sql = "insert into employees (employee_id,first_name,last_name,email,phone_number,hire_date,job_id,salary,commission_pct,"
						+ "manager_id,department_id) values (employees_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
				stm = conn.prepareStatement(sql);

				// CONTROLLO DEL CAMPO FIRST NAME==null
				if (employee.getFirstName() == null) {
					stm.setNull(1, Types.VARCHAR);
					ris = null;
				} else {
					stm.setString(1, employee.getFirstName()); // INSERT FIRST_NAME
				}

				stm.setString(2, employee.getLastName()); // INSERT LAST_NAME

				stm.setString(3, employee.getEmail()); // INSERT EMAIL

				// CONTROLLO DEL CAMPO PHONE_NUMBER==null
				if (employee.getPhoneNumber() == null) {
					stm.setNull(4, Types.VARCHAR);
					ris = null;
				} else {
					stm.setString(4, employee.getPhoneNumber()); // INSERT PHONE_NUMBER
				}

				// SETTAGGIO DELLA DATA
				java.util.Date utilDate = new java.util.Date(); // CREATE JAVA.UTIL.DATE OBJECT
				utilDate = employee.getHireDate();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				stm.setDate(5, sqlDate); // INSERT DATE AFTER THE CONVERSIONIN JAVA.UTIL.DATE

				stm.setString(6, employee.getJob().getJobId()); // INSERT JOB_ID

				// CONTROLLO DEL CAMPO SALARY==0
				if (employee.getSalary() == 0) {
					stm.setNull(7, Types.INTEGER);
					ris = null;
				} else {
					stm.setDouble(7, employee.getSalary()); // INSERT SALARY
				}

				// CONTROLLO DEL CAMPO COMMISSION_PCT==0
				if (employee.getCommissionPCT() == 0) {
					stm.setNull(8, Types.INTEGER);
					ris = null;
				} else {
					stm.setDouble(8, employee.getCommissionPCT()); // INSERT COMMISSION_PCT
				}

				// CONTROLLO DEL CAMPO MANAGER_ID==null
				if (employee.getManager() == null) {
					stm.setNull(9, Types.INTEGER);
					ris = null;
				} else {
					stm.setInt(9, employee.getManager().getEmployeeId()); // INSERT MANAGER_ID
				}

				// CONTROLLO DEL CAMPO DEPARTMENT_ID==null
				if (employee.getDepartement() == null) {
					stm.setNull(10, Types.VARCHAR);
					ris = null;
				} else {
					stm.setInt(10, employee.getDepartement().getDepartementId()); // INSERT DEPARTMENT_ID
				}

				stm.executeUpdate();
				ris = "\n" + "EMPLOYEE INSERITO!!";

			} else {
				System.out.print("\n" + "ERRORE NELLA MAIL: GIA\' VALIDATA");
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
	 * METHOD MANAGE THE UPDATE RECORD INTO EMPLOYEES
	 */
	@Override
	public String updateEmployee(Employee employee) {
		String ris = null;
		String op = "update";

		validateEmployee(employee, op);

		// CONTROLLO ESISTENZA DELL'EMPLOYEE_ID PRIMA DELL'UPDATE
		try {
			openConnection();
			boolean employeeExists = this.entityExists(employee.getEmployeeId());

			if (employeeExists && controlEmail(employee)) { // VIENE ESEGUITO L'UPDATE SOLO SE VIENE SODDISFATTA LA
				// CONDIZONE NEL METODO controlMail
				String sql = "update employees "
						+ "set first_name=?, last_name=?, email=?, phone_number=?, hire_date=?,"
						+ "job_id=?, salary=?, commission_pct=?, manager_id=?, department_id=?" + "where employee_id=?";
				// try {
				// openConnection();
				stm = conn.prepareStatement(sql);

				stm.setString(1, employee.getFirstName()); // INSERISCO E AGGIORNO FIRST_NAME
				stm.setString(2, employee.getLastName()); // INSERISCO E AGGIORNO LAST_NAME
				stm.setString(3, employee.getEmail()); // INSERISCO E AGGIORNO EMAIL
				stm.setString(4, employee.getPhoneNumber()); // INSERISCO E AGGIORNO PHONE_NUMBER

				// SETTAGGIO DELLA DATA
				java.util.Date utilDate = new java.util.Date(); // CREATE JAVA.UTIL.DATE OBJECT
				utilDate = employee.getHireDate();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				stm.setDate(5, sqlDate); // INSERT DATE AFTER THE CONVERSIONIN JAVA.UTIL.DATE

				stm.setString(6, employee.getJob().getJobId()); // INSERT JOB_ID
				stm.setDouble(7, employee.getSalary()); // INSERISCO E AGGIORNO SALARY
				stm.setDouble(8, employee.getCommissionPCT()); // INSERISCO E AGGIORNO COMMISSION_PCT
				stm.setInt(9, employee.getManager().getEmployeeId()); // INSERISCO E AGGIORNO EMPLOYEE_ID
				stm.setInt(10, employee.getDepartement().getDepartementId()); // INSERISCO E AGGIORNO DEPARTMENT_ID

				stm.setInt(11, employee.getEmployeeId());
				stm.executeUpdate();

				ris = "EMPLOYEE AGGIORNATO!!";
			} else {
				System.out.print("\n" + "ERRORE: MAIL GIA\' UTILIZZATA!!");
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
	 * METHOD MANAGE THE UPDATE RECORD INTO EMPLOYEES
	 */
	@Override
	public String deleteEmployee(Employee employee) {
		String ris = null;

		try {
			openConnection();
			boolean employeeEsists = this.entityExists(employee.getEmployeeId());
			JobHistoryControllerImpl jHistory = new JobHistoryControllerImpl(this.conn);
			DepartementControllerImpl dep = new DepartementControllerImpl(this.conn);
			boolean checkHistoryForeignKey = jHistory.checkForeignKey("employee_id", employee.getEmployeeId());
			boolean checkManagerForeignKey = this.checkForeignKey("manager_id", employee.getEmployeeId());
			boolean checkDepartmentForeignKey = dep.checkForeignKey("manager_id", employee.getEmployeeId());

			if (employeeEsists && checkDepartmentForeignKey) {
				if (checkHistoryForeignKey && checkManagerForeignKey) {
					String sql = "delete from employees where employee_id=?";
					stm = conn.prepareStatement(sql);
					stm.setInt(1, employee.getEmployeeId());
					stm.executeUpdate();

					ris = "\n \n" + "EMPLOYEE ELIMINATO!!";
				}

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

	/**
	 * METHOD FIND EMPLOYEE
	 */
	@Override
	public Employee findEmployee(String emailColumnValue, String firstNameColumnValue, String lastNameColumnValue) {
		Employee employee = new Employee();

		try {
			openDataSource();
			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			jdbcTemplate.setDataSource(dataSource);

			if (emailColumnValue == null && firstNameColumnValue == null && lastNameColumnValue == null) {
				System.out.print("ERRORE: Campi nulli!!");
			}

			// Query di un singolo oggetto
			employee = (Employee) jdbcTemplate.queryForObject(
					"select * from employees where 1=1 and email=? and first_name=? and last_name=?",
					new Object[] { emailColumnValue, firstNameColumnValue, lastNameColumnValue },
					new EmployeeRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			employee = null;
		}
		return employee;
	}

	// ALTERNATIVE FIND EMPLOYEE WITH NEW CLASSES (EntityFilter AND RecordFilter)
	public List<Employee> findEmployee(EntityFilter entityFilter) {
		List<Employee> listEmployee = new ArrayList<Employee>();

		try {
			openConnection();
			String sql = "select employee_id,first_name,last_name,email,phone_number,hire_date,salary,commission_pct,department_id,"
					+ "job_id,manager_id from employees where 1=1";

			sql += " " + JoinCondition.and.getCondition();

			for (RecordFilter record : entityFilter.getRecordFilter())
				if (record.getColumnName() != null) {
					sql += " " + record.getColumnName();
				}

			sql += Operator.equals.getCondition();

			for (RecordFilter record : entityFilter.getRecordFilter())
				if (record.getValue() != null) {
					sql += "'" + record.getValue() + "'";
				}

			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			for (int i = 0; i < entityFilter.getRecordFilter().size(); i++) {
				while (rs.next()) {
					Employee emp = new Employee();
					Departement dep = new Departement();
					Job job = new Job();

					emp.setEmployeeId(rs.getInt("employee_id"));
					emp.setFirstName(rs.getString("first_name"));
					emp.setLastName(rs.getString("last_name"));
					emp.setEmail(rs.getString("email"));
					emp.setPhoneNumber(rs.getString("phone_number"));
					emp.setHireDate(rs.getDate("hire_date"));
					emp.setSalary((double) rs.getInt("salary"));
					emp.setCommissionPCT((double) rs.getInt("commission_pct"));

					dep.setDepartementId(rs.getInt("department_id"));
					emp.setDepartement(dep);
					job.setJobId(rs.getString("job_id"));
					emp.setJob(job);

					int managerInner = rs.getInt("manager_id");
					Employee manager = new Employee();
					manager.setEmployeeId(managerInner);
					emp.setManager(manager);

					listEmployee.add(emp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			listEmployee.add(null);
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

		return listEmployee;

	}
}
