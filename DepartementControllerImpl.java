package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.soprasteria.mycompany.business.DepartementController;
import com.soprasteria.mycompany.business.rowMapper.DepartmentRowMapper;
import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Location;

public class DepartementControllerImpl extends EntityController implements DepartementController {

	private PreparedStatement stm;
	private ResultSet rs;

	public DepartementControllerImpl() {
		super();
	}

	public DepartementControllerImpl(Connection conn) {
		super(conn);
	}

	protected void initValues() {
		this.tableName = "departments";
		this.primaryKeyColumn = "department_id";
	}

	/**
	 * METHOD MANAGE THE PRINTING ABOUT DEPARTMENT TABLE
	 */
	public Departement getDepartement(int departementID) {
		Departement dep = new Departement();

		String sql = "select * from departments where department_id=?";
		try {
			openConnection();
			stm = conn.prepareStatement(sql);
			stm.setInt(1, departementID);
			rs = stm.executeQuery();

			if (rs.next()) {
				dep.setDepartementId(departementID);
				dep.setDepartementName(rs.getString("department_name"));

				// GESTIONE CHIAVI ESTERNE
				int managerId = rs.getInt("manager_id"); // creo un variabile che conterrà il valore recuperato dal DB
				EmployeeControllerImpl controller = new EmployeeControllerImpl(conn); // istanzio l'oggetto di tipo
																						// ControllerImpl
				Employee manager = controller.getManager(managerId); // nell'oggetto EMPLOYEE inserisco il valore
				// recuperato dal DB
				dep.setManager(manager); // passo l'oggetto MANAGER al metodo set

				// GESTIONE CHIAVI ESTERNE
				int locationId = rs.getInt("location_id"); // creo un variabile che conterrà il valore recuperato dal DB
				LocationControllerImpl loc = new LocationControllerImpl(conn); // istanzio l'oggetto di tipo
																				// ControllerImpl
				Location location = loc.getLocation(locationId); // nell'oggetto LOCATION
				// inserisco il valore recuperato
				// dal
				// DB

				dep.setLocation(location); // passo l'oggetto LOCATION al metodo set
			}
		} catch (Exception e) {
			e.printStackTrace();
			dep = null;
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
		return dep;
	}

	/**
	 * THIS METHOD CONTROL THE NULLABLE VARIABLES
	 * 
	 * @param dep
	 * @return
	 */
	public boolean validateDepartment(Departement dep, String op) {
		Boolean validate = true;

		if (op.equals("update")) {
			// CONTROLLO CAMPO DEPARTMENT_ID NULLABLE (OBBLIGATORIO)
			if (dep.getDepartementId() == 0) {
				System.out.print("\n" + "ERRORE: Campo Department_ID OBBLIGATORIO!!");
				validate = false;
				return validate;
			}
		}

		// CONTROLLO CAMPO DEPARTMENT_NAME NULLABLE (OBBLIGATORIO)
		if (dep.getDepartementName() == null) {
			System.out.print("\n" + "ERRORE: Campo Department_Name OBBLIGATORIO!!");
			validate = false;
			return validate;
		}

		return true;
	}

	/**
	 * CONTROL UNIQUE DEPARTMENT_NAME
	 * 
	 * @param dep
	 * @return
	 */
	public boolean controlDepartmentName(Departement dep) throws SQLException {
		Boolean control = true;

		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		try {
			// CONTROLLO ESISTENZA DI OGGETTI ALL'INTERNO DELLA TABELLA DEPARTMENT (UNIQUE)
			String controllo = "select count (*) from departments where department_name=?"; // COUNT ---> CONTA LA
																							// PRESENZA
																							// DI QUANTI
																							// DEPARTMENT_ID SONO
																							// PRESENTI ALL'INTERNO DEL
																							// DB
			stm = conn.prepareStatement(controllo);
			stm.setString(1, dep.getDepartementName()); // SETTO L'UGUAGLIANZA CHE PASSO ALL'INTERNO DELL'OGGETTO
														// EMPLOYEES
			rs = stm.executeQuery();

			if (rs.next()) {
				int cont = rs.getInt(1);
				if (cont > 0) { // SE IL CONTATORE E' MAGGIORE DI 0 SIGNIFICA CHE C'E' GIA' UNA MAIL PRESENTE
					// ALL'INTERNO DEL DB, QUINDI NON INSERISCO
					System.out.print("\n" + "Errore dell'UPDATE: DEPARTMENT_NAME GIA\' PRESENTE");
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
	 * METHOD MANAGE THE INSERT OF DEPARTMENT
	 */
	@Override
	public String createDepartement(Departement dep) {
		String ris = null;
		String op = "";

		validateDepartment(dep, op); // RICHIAMO METODO PER CONTROLLO CAMPI NULLABLE

		try {
			openConnection();
			if (controlDepartmentName(dep)) { // L'ISTRUZIONE DI INSERT VIENE ESEGUITA SE VIENE
				// SODDISFATTA LA CONDIZIONE DEL METODO
				// controlDepartmentName
				// INSERT DEPARTMENT
				String sql = "insert into departments (department_id,department_name,manager_id,location_id)"
						+ "values (departments_seq.nextval,?,?,?)";
				stm = conn.prepareStatement(sql);

				stm.setString(1, dep.getDepartementName()); // POS 1, DEPARTMENT_ID

				// CONTROLLO DEL CAMPO MANAGER_ID==NULL
				if (dep.getManager() == null) {
					stm.setNull(2, Types.INTEGER);
					return ris = null;
				} else {
					stm.setInt(2, dep.getManager().getEmployeeId()); // SE POPOLATO INSERISCO L'EMPLOYEE_ID, POS 2
				}

				// CONTROLLO DEL CAMPO LOCATION_ID==NULL
				if (dep.getLocation() == null) {
					stm.setNull(3, Types.INTEGER);
					return ris = null;
				} else {
					stm.setInt(3, dep.getLocation().getLocationId()); // SE POPOLATO INSERISCO LA LOCATION_ID, POS 3
				}

				stm.executeUpdate();
				ris = "\n" + "DEPARTMENT INSERITO!!";
			} else {
				System.out.print("ERRORE: DEPARTMENT_NAME GIA\' VALIDATO!!!");
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
	 * METHOD MANAGE THE UPDATE OF DEPARTMENT
	 */
	public String updateDepartement(Departement dep) {
		String ris = null;
		String op = "update";

		validateDepartment(dep, op);

		try {
			openConnection();

			boolean departmentExists = this.entityExists(dep.getDepartementId());
			if (departmentExists && controlDepartmentName(dep)) { // VIENE ESEGUITO L'UPDATE SE VIENE SODDISFATTA LA
				// CONDIZIONE DEL METODO controlDepartmentName
				String sql = "update departments "
						+ "set department_name=?, manager_id=?, location_id=? where department_id=?";
				stm = conn.prepareStatement(sql);

				stm.setString(1, dep.getDepartementName()); // SET DEPARTMENT_NAME
				stm.setInt(2, dep.getManager().getEmployeeId()); // SET EMPLOYEE_ID (CHIAVE ESTERNA)
				stm.setInt(3, dep.getLocation().getLocationId()); // SET LOCATION_ID (CHIAVE ESTERNA)
				stm.setInt(4, dep.getDepartementId()); // SET DEPARTMENT_ID (CONDIZIONE NEL WHERE)

				rs = stm.executeQuery();
				ris = "DEPARTMENT AGGIONRATO";
			} else {
				System.out.print("ERRORE: DEPARTMENT_NAME GIA\' VALIDATO!!");
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
	 * METHOD MANAGE THE DEPARTMENT DELETE
	 */
	public String deleteDepartement(Departement dep) {
		String ris = null;

		try {
			openConnection();

			boolean departmentExists = this.entityExists(dep.getDepartementId());

			EmployeeControllerImpl emp = new EmployeeControllerImpl(this.conn);
			JobHistoryControllerImpl jHistory = new JobHistoryControllerImpl(this.conn);
			boolean checkEmployeeForeignKey = emp.checkForeignKey("department_id", dep.getDepartementId());
			boolean checkHistoryForeignKey = jHistory.checkForeignKey("department_id", dep.getDepartementId());

			if (departmentExists && checkEmployeeForeignKey && checkHistoryForeignKey) {
				String sql = "delete from departments where department_id=?";
				stm = conn.prepareStatement(sql);
				stm.setInt(1, dep.getDepartementId());
				stm.executeUpdate();

				ris = "\n \n" + "DEPARTMENT ELIMINATO!!";
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
	public List<Departement> findDepartement(int depId, String depName) throws SQLException {

		openDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		if (depId == 0 && depName == null) {
			System.out.print("ERRORE: Campo nullo!!");
		}

		List<Departement> dep = (List<Departement>) jdbcTemplate.query(
				"select * from departments where department_id=? and department_name=?",
				new Object[] { depId, depName }, new DepartmentRowMapper());

		return dep;
	}
}
