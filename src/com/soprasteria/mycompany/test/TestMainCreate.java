package com.soprasteria.mycompany.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.soprasteria.mycompany.business.impl.CountryControllerImpl;
import com.soprasteria.mycompany.business.impl.DepartementControllerImpl;
import com.soprasteria.mycompany.business.impl.EmployeeControllerImpl;
import com.soprasteria.mycompany.business.impl.JobControllerImpl;
import com.soprasteria.mycompany.business.impl.JobHistoryControllerImpl;
import com.soprasteria.mycompany.business.impl.LocationControllerImpl;
import com.soprasteria.mycompany.business.impl.RegionControllerImpl;
import com.soprasteria.mycompany.entity.Country;
import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Job;
import com.soprasteria.mycompany.entity.Job_History;
import com.soprasteria.mycompany.entity.Location;
import com.soprasteria.mycompany.entity.Region;

public class TestMainCreate {

	/**
	 * THIS METHOD CONVERT FROM STRING A DATE A VARIABLE
	 * 
	 * @param dateString
	 * @return
	 */
	public Date convertStringToDate(String dateString) {
		Date startDate = null;
		DateFormat df = new SimpleDateFormat("dd-MMM-yy"); // INSERISCO IL FORMATO DELLA DATA CHE DOVRA' AVERE
		try {
			startDate = df.parse(dateString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return startDate;
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT COUNTRY TABLE INTO DB AND CALL THE
	 * METHOD INSERT COUNTRY INTO CountryControllerImpl CLASS
	 */
	public void testCountry() {
		Country country = new Country(); // COUNTRY OBJECT FOR CALL METHOD INTO Country CLASS
		CountryControllerImpl countryController = new CountryControllerImpl();// COUNTRY CONTROLLER OBJECT FOR CALL
																				// createCountry METHOD
		String ris = null;
		Region region = new Region(); // REGION OBJECT FOR MANAGE THE FOREIGN KEY

		// SET VARIABLES INTO COUNTRY
		country.setCountryId("TE"); // SET COUNTRY_ID
		country.setCountryName("Texas"); // SET COUNTRY_NAME

		// CHIAVE ESTERNA REGION_ID
		int reg = 2; // RECUPERO UN REGION_ID DALA TABELLA REGION
		region.setRegionId(reg); // SETTO IL VALORE DEL REGION ALL'INTERNO DELLA CLASSE REGION
		country.setRegion(region); // RICHIAMO IL METODO setRegion E PASSO IL RISULTATO DELLA setRegionId DELLA
									// CALSSE REGION

		ris = countryController.createCountry(country); // CALL METHOD INSERT IN EmployeeControllerImpl CLASS
		System.out.print(ris);
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT DEPARTEMENT TABLE INTO DB AND CALL THE
	 * METHOD INSERT DEPARTMENT INTO DepartementControllerImpl CLASS
	 */
	public void testDepartement() {
		DepartementControllerImpl depControl = new DepartementControllerImpl();// DEPARTMENT CONTROLLER OBJECT FOR CALL
																				// createDepartement METHOD
		Departement dep = new Departement(); // DEPARTMENT OBJECT FOR CALL METHOD INTO Departement CLASS
		Location location = new Location(); // LOCATION OBJECT FOR MANAGE THE FOREIGN KEY
		Employee manager = new Employee(); // EMPLOYEE OBJECT FOR MANAGE THE FOREIGN KEY
		String ris = null;

		// SET VARIABLES DEPARTMENT
		dep.setDepartementId(300); // SET DEPARTMENT_ID (when insert the department_id, it will insert into the
									// table with the department sequence)
		dep.setDepartementName("ROZZANGELES_CITY"); // SET DEPARTMENT_NAME

		// CHIAVE ESTERNA MANAGER_ID
		int employeeId = 110; // RECUPERO UN MANAGER_ID DALL'EMPLOYEE
		manager.setEmployeeId(employeeId); // SETTO IL VALORE DELL'EMPLOYEE_ID ALL'INTERNO DELLA CLASSE EMPLOYEE
		dep.setManager(manager); // RICHIAMO IL METODO setManager E PASSO IL RISULTATO DELLA setEmployeeId DELLA
									// CLASSE DEPARTMENT

		// CHIAVE ESTERNA LOCATION_ID
		int locId = 1200; // RECUPERO UNA LOCATION_ID DALLA TABELLA LOCATION
		location.setLocationId(locId); // SETTO IL VALORE DELLA LOCATION_ID ALL'INTERNO DELLA CLASSE LOCATION
		dep.setLocation(location); // RICHIAMO IL METODO setLocation E PASSO IL RISULTATO DELLA setLocationId DELLA
									// CLASSE DEPARTMENT

		ris = depControl.createDepartement(dep); // CALL METHOD INSERT IN EmployeeControllerImpl CLASS
		System.out.print(ris);
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT EMPLOYEES TABLE INTO DB AND CALL THE
	 * METHOD INSERT EMPLOYEE INTO EmployeeControllerImpl CLASS
	 */
	public void testEmployeeCreate() {
		Employee emp = new Employee(); // EMPLOYEE OBJECT FOR CALL METHOD INTO Employee CLASS
		EmployeeControllerImpl controller = new EmployeeControllerImpl(); // EMPLOYEE CONTROLLER OBJECT FOR CALL
																			// createEmployee METHOD
		Job jobId = new Job(); // JOB OBJECT FOR MANAGE THE FOREIGN KEY
		Employee manager = new Employee(); // EMPLOYEE OBJECT FOR MANAGE THE FOREIGN KEY (MANAGER_ID)
		Departement departement = new Departement(); // DEPARTMENT OBJECT FOR MANAGE THE FOREIGN KEY

		String ris = null;
		Date newDate;

		// SET VARIABLES INTO EMPLOYEE
		emp.setFirstName("Gabriele"); // SET FIRST NAME
		emp.setLastName("Granata"); // SET LAST NAME
		emp.setEmail("gabgrana@gmail.com"); // SET EMAIL
		emp.setPhoneNumber("3201754097"); // SET PHONE NUMBER

		String dateString = "17-MAG-96"; // SET DATE WITH CONVERSION
		newDate = convertStringToDate(dateString);
		emp.setHireDate(newDate);

		// CHIAVE ESTERNA JOB_ID
		String jId = "FI_MGR"; // RECUPERO UN JOB_ID DAL DB
		jobId.setJobId(jId); // SETTO IL VALORE DEL JOB_ID ALL'INTERNO DELLA CALSSE JOB
		emp.setJob(jobId); // RICHIAMO IL METODO setJob E PASSO IL RISULTATO DELLA setJobId DELLA CLASSE
							// JOB

		emp.setSalary((double) 800); // SET SALARY
		emp.setCommissionPCT(0.4); // SET COMMISSION PCT

		// CHIAVE ESTERNA MANAGER_ID
		int employeeId = 120; // RECUPERO UN MANAGER_ID DAL DB
		manager.setEmployeeId(employeeId); // SETTO IL VALORE DELL'EMPLOYEE_ID ALL'INTERNO DELLA CLASSE EMPLOYEE
		emp.setManager(manager); // RICHIAMO IL METODO setManager E PASSO IL RISULTATO DELLA setEmployeeId DELLA
									// CLASSE EMPLOYEE

		// CHIAVE ESTERNA DEPARTMENT_ID
		int depId = 130; // RECUPERO UN DEPARTMENT_ID DAL DB
		departement.setDepartementId(depId); // SETTO IL VALORE DEL DEPARTMENT_ID ALL'INTERNO DELLA CALSSE DEPARTMENT
		emp.setDepartement(departement); // RICHIAMO IL METODO setDepartement E PASSO IL RISULTATO DELLA
											// setDepartementId DELLA CLASSE DEPARTEMENT

		ris = controller.createEmployee(emp); // CALL METHOD INSERT IN EmployeeControllerImpl CLASS
		System.out.print("\n" + ris);
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT JOB TABLE INTO DB AND CALL THE METHOD
	 * INSERT JOB INTO JobControllerImpl CLASS
	 */
	public void testJob() {
		Job job = new Job(); // JOB OBJECT FOR CALL METHOD INTO Job CLASS
		JobControllerImpl jobController = new JobControllerImpl(); // JOB CONTROLLER OBJECT FOR CALL
																	// createJob METHOD
		String ris = null;

		// SET VARIABLES JOB
		job.setJobId("SO_STR"); // SET JOB_ID
		job.setJobTitle(""); // SET JOB_TITLE
		job.setMinSalary(1300); // SET MIN_SALARY
		job.setMaxSalary(2000); // SET MAX_SALARY

		ris = jobController.createJob(job); // INSERT JOB
		System.out.print("\n" + ris);
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT JOB_HISTORY TABLE INTO DB AND CALL THE
	 * METHOD INSERT JOB_HISTORY INTO JobHistoryControllerImpl CLASS
	 */
	public void testJobHistory() {
		JobHistoryControllerImpl jController = new JobHistoryControllerImpl(); // JOB HISTORY CONTROLLER OBJECT FOR CALL
																				// createJobHistory METHOD
		Employee employee = new Employee(); // EMPLOYEE OBJECT FOR MANAGE THE FOREIGN KEY
		Job_History jHistory = new Job_History(); // JOB HISTORY OBJECT FOR CALL METHOD IN THE Job_History CLASS
		Job job = new Job(); // JOB OBJECT FOR MANAGE THE FOREIGN KEY
		Departement dep = new Departement(); // DEPARTMENT OBJECT FOR MANAGE THE FOREIGN KEY
		Date newDate;
		String ris = null;

		// CHIAVE ESTERNA MANAGER_ID
		int employeeId = 200; // RECUPERO UN MANAGER_ID DAL DB
		employee.setEmployeeId(employeeId); // SETTO IL VALORE DELL'EMPLOYEE_ID ALL'INTERNO DELLA CLASSE EMPLOYEE
		jHistory.setEmployee(employee); // RICHIAMO IL METODO setManager E PASSO IL RISULTATO DELLA setEmployeeId DELLA
		// CLASSE EMPLOYEE

		String dateStart = "25-MAR-17"; // SET DATE-START WITH CONVERSION
		newDate = convertStringToDate(dateStart);
		jHistory.setStartDate(newDate);

		String dateFinish = "29-DIC-17"; // SET DATE-FINISH WITH CONVERSION
		newDate = convertStringToDate(dateFinish);
		jHistory.setEndDate(newDate);

		// CHIAVE ESTERNA JOB_ID
		String jId = "AC_MGR"; // RECUPERO UN JOB_ID DALLA TABELLA JOB
		job.setJobId(jId); // SETTO IL VALORE DEL JOB_ID ALL'INTERNO DELLA CLASSE JOB
		jHistory.setJob(job); // RICHIAMO IL METODO setJob E PASSO IL RISULTATO DELLA setJobId DELLA
								// CLASSE JOB

		// CHIAVE ESTERNA DEPARTMENT_ID
		dep.setDepartementId(20); // SETTO IL VALORE DEL DEPARTMENT_ID ALL'INTERNO DELLA CLASSE DEPARTMENT
		jHistory.setDepartment(dep); // RICHIAMO IL METODO setDepartement E PASSO IL RISULTATO DELLA setDepartementId
										// DELLA
										// CLASSE DEPARTEMENT

		ris = jController.createJobHistory(jHistory); // INSERT JOB_HISTORY
		System.out.print(ris);
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT LOCATION TABLE INTO DB AND CALL THE
	 * METHOD INSERT LOCATION INTO LocationControllerImpl CLASS
	 */
	public void testLocation() {
		Location loc = new Location();// LOCATION OBJECT FOR CALL METHOD IN THE Location CLASS
		LocationControllerImpl locController = new LocationControllerImpl();// LOCATION CONTROLLER OBJECT FOR CALL
																			// createLocation METHOD
		Country country = new Country(); // COUNTRY OBJECT FOR MANAGE THE FOREIGN KEY
		String ris = null;

		// SET PARAMATERS ABOUT LOCATION
		loc.setLocationId(1300); // SET LOCATION_ID (when insert into the table, it will insert with the location
									// sequence)
		loc.setStreetAdress("Via 4 Novembre 37"); // SET STREET_ADDRESS
		loc.setPostalCode("20094"); // SET POSTAL_CODE
		loc.setCity("Corsico"); // SET CITY
		loc.setStateProvince("Milan"); // SET STATE_PROVINCE

		// SET FOREIGN KEY COUNTRY ID
		country.setCountryId("IT"); // SET THE VALE OF COUNTRY_ID INTO setCountryId METHOD
		loc.setCountry(country); // PASS THE RESULT OF setCountryId METHOD INTO setCountry METHOD IN LOCATION

		ris = locController.createLocation(loc); // INSERT LOCATION
		System.out.print(ris);
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT REGION TABLE INTO DB AND CALL THE METHOD
	 * INSERT REGION INTO RegionControllerImpl CLASS
	 */
	public void testRegion() {
		Region region = new Region(); // REGION OBJECT FOR CALL THE Region METHOD CLASS
		RegionControllerImpl regionController = new RegionControllerImpl();// REGION CONTROLLER OBJECT FOR CALL
																			// createRegion METHOD
		String ris = null;

		// SET VARIABLES INTO REGION TABLE
		region.setRegionId(5); // SET REGION_ID
		region.setRegionName("SOUTH_AMERICA"); // SET REGION_NAME

		ris = regionController.createRegion(region); // INSERT REGION
		System.out.print(ris);
	}

	public static void main(String[] args) {

		TestMainCreate testMainCreate = new TestMainCreate();
		int scelta = 0;
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);

		do {
			System.out.print("\n");
			System.out.print("\n");
			
			System.out.print("----------- INSERT RECORD ------------");
			System.out.print("\n" + "1- INSERT RECORD INTO COUNTRY TABLE");
			System.out.print("\n" + "2- INSERT RECORD INTO DEPARTMENT TABLE");
			System.out.print("\n" + "3- INSERT RECORD INTO EMPLOYEE TABLE");
			System.out.print("\n" + "4- INSERT RECORD INTO JOB TABLE");
			System.out.print("\n" + "5- INSERT RECORD INTO JOB HISTORY TABLE");
			System.out.print("\n" + "6- INSERT RECORD INTO LOCATION TABLE");
			System.out.print("\n" + "7- INSERT RECORD INTO REGION TABLE");
			System.out.print("\n" + "8- EXIT FROM INSERT RECORD");
			System.out.print("\n" + "----------- INSERT RECORD ------------");

			try {
				String nLett = tastiera.readLine();
				scelta = Integer.valueOf(nLett).intValue();
			} catch (Exception e) {
				System.out.println("\n" + "Incorrect character");
			}

			switch (scelta) {
			case 1:
				testMainCreate.testCountry();
				break;
			case 2:
				testMainCreate.testDepartement();
				break;
			case 3:
				testMainCreate.testEmployeeCreate();
				break;
			case 4:
				testMainCreate.testJob();
				break;
			case 5:
				testMainCreate.testJobHistory();
				break;
			case 6:
				testMainCreate.testLocation();
				break;
			case 7:
				testMainCreate.testRegion();
				break;
			case 8:
				System.out.print("PROGRAM COMPLETED");
				break;
			}

		} while (scelta != 8);

	}
}
