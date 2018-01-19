package com.soprasteria.mycompany.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;

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

public class TestMainGet {

	/**
	 * METHOD TESTING COUNTRY
	 * 
	 * @param countryId
	 */
	// METODO PER TEST DELLA TABELLA COUNTRY
	private void testCountry(String countryId) {
		CountryControllerImpl countryController = new CountryControllerImpl();
		Country country = countryController.getCountry(countryId);

		System.out.print("\n" + country.toString());
	}

	/**
	 * METHOD TESTING DEPARTMENT
	 * 
	 * @param departmentId
	 */
	// METODO PER TEST DELLA TABELLA DEPARTMENT
	private void testDepartement(int departmentId) {
		DepartementControllerImpl departmentController = new DepartementControllerImpl();
		Departement department = departmentController.getDepartement(departmentId);

		System.out.print("\n" + department.toString());
	}

	/**
	 * METHOD TESTING JOB
	 * 
	 * @param jobId
	 */
	// METODO PER TEST DELLA TABELLA JOB
	private void testJob(String jobId) {
		JobControllerImpl jobController = new JobControllerImpl();
		Job job = jobController.getJob(jobId);

		System.out.print("\n" + job.toString());
	}

	/**
	 * METHOD TESTING EMPLOYEE
	 * 
	 * @param employeeId
	 */
	// METODO PER TEST DELLA TABELLA EMPLOYEE
	private void testEmployee(int employeeId) {
		EmployeeControllerImpl employeeController = new EmployeeControllerImpl();
		Employee employee = employeeController.getEmployee(employeeId);

		System.out.print("\n" + employee.toString());

	}

	/**
	 * METHOD TESTING JOB HISTORY
	 * 
	 * @param employeeId
	 * @throws SQLException
	 */
	// METODO PER TEST DELLA TABELLA JOB HISTORY
	private void testJobHistory(int employeeId) throws SQLException {
		JobHistoryControllerImpl jhistoryController = new JobHistoryControllerImpl();
		Job_History jhistory = jhistoryController.getJobHistory(employeeId);

		System.out.print("\n" + jhistory.toString());

	}

	/**
	 * METHOD TESTING LOCATION
	 * 
	 * @param locationId
	 */
	// METODO PER TEST DELLA TABELLA LOCATIONS
	private void testLocations(int locationId) {
		LocationControllerImpl locationController = new LocationControllerImpl();
		Location location = locationController.getLocation(locationId);

		System.out.print("\n" + location.toString());
	}

	/**
	 * METHOD TESTING REGION
	 * 
	 * @param regionId
	 */
	// METODO PER TEST DELLA TABELLA REGION
	private void testRegion(int regionId) {
		RegionControllerImpl regionController = new RegionControllerImpl();
		Region region = regionController.getRegion(regionId);

		System.out.print("\n" + region.toString());
	}

	/**
	 * METHOD MAIN
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		TestMainGet testMain = new TestMainGet();
		int scelta = 0;
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);

		do {
			System.out.print("\n");
			System.out.print("\n");

			System.out.print("---------------- GET RECORD FROM TABLE ------------------");
			System.out.print("\n" + "1- GET RECORD FROM COUNTRY TABLE");
			System.out.print("\n" + "2- GET RECORD DEPARTMENT TABLE");
			System.out.print("\n" + "3- GET RECORD EMPLOYEE TABLE");
			System.out.print("\n" + "4- GET RECORD JOB HISTORY TABLE");
			System.out.print("\n" + "5- GET RECORD JOB TABLE");
			System.out.print("\n" + "6- GET RECORD LOCATION TABLE");
			System.out.print("\n" + "7- GET RECORD REGION TABLE");
			System.out.print("\n" + "8- EXIT FROM GET RECORD");
			System.out.print("\n" + "---------------- GET FROM TABLE ------------------");

			try {
				String nLett = tastiera.readLine();
				scelta = Integer.valueOf(nLett).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n" + "Carattere non corretto");
			}

			switch (scelta) {
			case 1:
				testMain.testCountry("BE");
				break;
			case 2:
				testMain.testDepartement(120);
				break;
			case 3:
				testMain.testEmployee(102);
				break;
			case 4:
				testMain.testJobHistory(201);
				break;
			case 5:
				testMain.testJob("SA_MAN");
				break;
			case 6:
				testMain.testLocations(1000);
				break;
			case 7:
				testMain.testRegion(3);
				break;
			case 8:
				System.out.print("\n" + "PROGRAM COMPLETED");
				break;
			}

		} while (scelta != 8);

	}
}
