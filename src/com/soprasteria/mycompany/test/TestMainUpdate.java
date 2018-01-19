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

public class TestMainUpdate {

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
	 * METHOD UPDATE COUNTRY INTO CountryControllerImpl CLASS
	 */
	public void testUpdateCountry() {
		CountryControllerImpl countryController = new CountryControllerImpl();
		Country country = new Country();
		Region region = new Region();

		country.setCountryId("CN"); // SET COUNTRY_ID
		country.setCountryName("CHINA"); // SET COUNTRY_NAME

		int regId = 1; // RECUPERO UN REGION_ID DAL DB
		region.setRegionId(regId); // SETTO IL VALORE DENTRO IL METODO setRegionId DELLA CLASSE REGION
		country.setRegion(region); // PASSO IL RISULTATO AL METODO setRegion DELLA CLASSE COUNTRY

		if (!country.compareEquals(country)) {
			System.out.print("\n" + countryController.updateCountry(country));
		} else {
			System.out.print("\n Impossibile effettuare l'update");
		}
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT DEPARTMENT TABLE INTO DB AND CALL THE
	 * METHOD UPDATE DEPARTMENT INTO DepartmentControllerImpl CLASS
	 */
	public void testUpdateDepartment() {
		DepartementControllerImpl depController = new DepartementControllerImpl();
		Departement dep = new Departement();

		Employee manager = new Employee();
		Location loc = new Location();

		dep.setDepartementId(10); // SET DEPARTMENT_ID
		dep.setDepartementName("Sopra_Steria"); // SET DEPARTMENT_NAME

		// CHIAVE ESTERNA MANAGER_ID
		int employeeId = 110; // RECUPERO UN MANAGER_ID DALL'EMPLOYEE
		manager.setEmployeeId(employeeId); // SETTO IL VALORE DELL'EMPLOYEE_ID ALL'INTERNO DELLA CLASSE EMPLOYEE
		dep.setManager(manager); // RICHIAMO IL METODO setManager E PASSO IL RISULTATO DELLA setEmployeeId DELLA
									// CLASSE DEPARTMENT

		// CHIAVE ESTERNA LOCATION_ID
		loc.setLocationId(1300);
		dep.setLocation(loc);

		if (!dep.compareEquals(dep)) {
			System.out.print("\n" + depController.updateDepartement(dep));
		} else {
			System.out.print("\n Impossibile effettuare l'update");
		}

	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT EMPLOYEES TABLE INTO DB AND CALL THE
	 * METHOD UPDATE EMPLOYEE INTO EmployeeControllerImpl CLASS
	 */
	public void testUpdateEmployee() {
		EmployeeControllerImpl empController = new EmployeeControllerImpl();
		Employee emp = new Employee();
		Job job = new Job();
		Departement dep = new Departement();
		Employee manager = new Employee();
		Date newDate;

		emp.setEmployeeId(30000); // SET EMPLOYEE_ID
		emp.setFirstName("Gabriele"); // SET FIRST_NAME
		emp.setLastName("Paolino"); // SET LAST_NAME
		emp.setEmail("PAPERINOOOOO"); // SET EMAIL
		emp.setPhoneNumber("6365514445645"); // SET PHONE_NUMBER

		String dateString = "27-GEN-04"; // SET DATE WITH CONVERSION
		newDate = convertStringToDate(dateString);
		emp.setHireDate(newDate);

		// CHIAVE ESTERNA JOB_ID
		String jId = "SA_REP"; // RECUPERO UN JOB_ID DAL DB
		job.setJobId(jId); // SETTO IL VALORE DEL JOB_ID ALL'INTERNO DELLA CALSSE JOB
		emp.setJob(job); // RICHIAMO IL METODO setJob E PASSO IL RISULTATO DELLA setJobId DELLA CLASSE
							// JOB

		emp.setSalary(4200);
		emp.setCommissionPCT(0);

		// CHIAVE ESTERNA MANAGER_ID
		int employeeId = 101; // RECUPERO UN MANAGER_ID DAL DB
		manager.setEmployeeId(employeeId); // SETTO IL VALORE DELL'EMPLOYEE_ID ALL'INTERNO DELLA CLASSE EMPLOYEE
		emp.setManager(manager); // RICHIAMO IL METODO setManager E PASSO IL RISULTATO DELLA setEmployeeId DELLA
									// CLASSE EMPLOYEE

		// CHIAVE ESTERNA DEPARTMENT_ID
		int depId = 50; // RECUPERO UN DEPARTMENT_ID DAL DB
		dep.setDepartementId(depId); // SETTO IL VALORE DEL DEPARTMENT_ID ALL'INTERNO DELLA CALSSE DEPARTMENT
		emp.setDepartement(dep); // RICHIAMO IL METODO setDepartement E PASSO IL RISULTATO DELLA
									// setDepartementId DELLA CLASSE DEPARTEMENT

		if (!emp.compareEquals(emp)) {
			System.out.print("\n" + empController.updateEmployee(emp));
		} else {
			System.out.print("\n Impossibile effettuare l'update");
		}
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT JOB TABLE INTO DB AND CALL THE METHOD
	 * UPDATE JOB INTO JobControllerImpl CLASS
	 */
	public void testUpdateJob() {
		JobControllerImpl jobController = new JobControllerImpl();
		Job job = new Job();

		job.setJobId("PPREP"); // SET JOB_ID
		job.setJobTitle("Prep_Gabry"); // SET JOB_TITLE
		job.setMinSalary(50000); // SET MIN_SALARY
		job.setMaxSalary(100000); // SET MAX_SALARY

		if (!job.compareEquals(job)) {
			System.out.print("\n" + jobController.updateJob(job));
		} else {
			System.out.print("\n Impossibile effettuare l'update");
		}
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT JOB_HISTORY TABLE INTO DB AND CALL THE
	 * METHOD UPDATE JOB_HISTORY INTO JobHistoryControllerImpl CLASS
	 */
	@SuppressWarnings("unused")
	public void testUpdateJobHistory() {
		JobHistoryControllerImpl jHistoryControl = new JobHistoryControllerImpl();
		Job_History jHistory = new Job_History();
		Job job = new Job();
		Departement dep = new Departement();
		Employee emp = new Employee();

		// GESTIONE CHIAVE ESTERNA EMPLOYEE_ID
		emp.setEmployeeId(200);
		jHistory.setEmployee(emp);

		String dateString = "30-DIC-97"; // SET DATE WITH CONVERSION
		Date newDate = convertStringToDate(dateString);
		jHistory.setStartDate(newDate);

		String dateString1 = "27-DIC-17"; // SET DATE WITH CONVERSION
		Date newDate2 = convertStringToDate(dateString);
		jHistory.setEndDate(newDate2);

		// GESTIONE CHIAVE ESTERNA JOB_ID
		job.setJobId("IT_PROG");
		jHistory.setJob(job);

		// GESTIONE CHIAVE ESTERNA DEPARTMENT_ID
		dep.setDepartementId(150);
		jHistory.setDepartment(dep);

		if (!jHistory.compareEquals(jHistory)) {
			System.out.print("\n" + jHistoryControl.updateJobHistory(jHistory));
		} else {
			System.out.print("Impossibile effettuare l'update");
		}
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT LOCATION TABLE INTO DB AND CALL THE
	 * METHOD UPDATE LOCATION INTO LocationControllerImpl CLASS
	 */
	public void testUpdateLocation() {
		LocationControllerImpl locController = new LocationControllerImpl();
		Location loc = new Location();
		Country country = new Country();

		loc.setLocationId(1300); // SET LOCATION_ID
		loc.setStreetAdress("VIA 4 NOVEMBRE"); // SET STREET_ADDRESS
		loc.setPostalCode("20094"); // SET POSTAL_CODE
		loc.setCity("CORSICO"); // SET CITY
		loc.setStateProvince("ITALY"); // SET STATE_PROVINCE

		// GESTIONE CHIAVE ESTERNA COUNTRY_ID
		country.setCountryId("IT"); // SETTO CHIAVE ESTERNA COUNTRY_ID
		loc.setCountry(country);

		if (!loc.comopareEquals(loc)) {
			System.out.print("\n" + locController.updateLocation(loc));
		} else {
			System.out.print("\n Impossibile effettuare l'update");
		}
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT REGION TABLE INTO DB AND CALL THE METHOD
	 * UPDATE REGION INTO RegionControllerImpl CLASS
	 */
	public void testUpdateRegion() {
		RegionControllerImpl regController = new RegionControllerImpl();
		Region region = new Region();

		region.setRegionId(4); // SET REGION_ID
		region.setRegionName("MACEDONIA"); // SET REGION_NAME

		if (!region.compareEquals(region)) {
			System.out.print("\n" + regController.updateRegion(region));
		} else {
			System.out.print("\n" + "Impossibile effettuare l'update");
		}
	}

	public static void main(String[] args) {
		TestMainUpdate testMainUpdate = new TestMainUpdate();
		int scelta = 0;
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);

		do {
			System.out.print("\n");
			System.out.print("\n");

			System.out.print("----------- UPDATE RECORD FROM TABLE ------------");
			System.out.print("\n" + "1- UPDATE RECORD FROM COUNTRY TABLE");
			System.out.print("\n" + "2- UPDATE RECORD FROM DEPARTMENT TABLE");
			System.out.print("\n" + "3- UPDATE RECORD FROM EMPLOYEE TABLE");
			System.out.print("\n" + "4- UPDATE RECORD FROM JOB TABLE");
			System.out.print("\n" + "5- UPDATE RECORD FROM JOB HISTORY TABLE");
			System.out.print("\n" + "6- UPDATE RECORD FROM LOCATION TABLE");
			System.out.print("\n" + "7- UPDATE RECORD FROM REGION TABLE");
			System.out.print("\n" + "8- EXIT FROM RECORD UPDATE");
			System.out.print("\n" + "----------- UPDATE RECORD FROM TABLE ------------");

			try {
				String nLett = tastiera.readLine();
				scelta = Integer.valueOf(nLett).intValue();
			} catch (Exception e) {
				System.out.println("\n" + "Carattere non corretto");
			}

			switch (scelta) {
			case 1:
				testMainUpdate.testUpdateCountry();
				break;
			case 2:
				testMainUpdate.testUpdateDepartment();
				break;
			case 3:
				testMainUpdate.testUpdateEmployee();
				break;
			case 4:
				testMainUpdate.testUpdateJob();
				break;
			case 5:
				testMainUpdate.testUpdateJobHistory();
				break;
			case 6:
				testMainUpdate.testUpdateLocation();
				break;
			case 7:
				testMainUpdate.testUpdateRegion();
				break;
			case 8:
				System.out.print("\n" + "PROGRAM COMPLETED");
				break;
			}

		} while (scelta != 8);

	}

}
