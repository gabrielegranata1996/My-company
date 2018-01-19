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
import com.soprasteria.mycompany.business.impl.LocationControllerImpl;
import com.soprasteria.mycompany.business.impl.RegionControllerImpl;
import com.soprasteria.mycompany.entity.Country;
import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Job;
import com.soprasteria.mycompany.entity.Location;
import com.soprasteria.mycompany.entity.Region;

public class TestMainDelete {

	/**
	 * THIS METHOD CONVERT FROM STRING A VARIABLE DATE
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
	 * METHOD DELETE COUNTRY INTO CountryControllerImpl CLASS
	 */
	public void testDeleteCountry() {
		CountryControllerImpl countryController = new CountryControllerImpl();
		Country country = new Country();

		country.setCountryId("DK");
		System.out.print("\n" + countryController.deleteCountry(country));
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT DEPARTMENT TABLE INTO DB AND CALL THE
	 * METHOD DELETE DEPARTMENT INTO DepartementControllerImpl CLASS
	 */
	public void testDeleteDepartment() {
		Departement dep = new Departement();
		DepartementControllerImpl depController = new DepartementControllerImpl();
		int departementId = 290;

		dep.setDepartementId(departementId);
		System.out.print("\n" + depController.deleteDepartement(dep));
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT EMPLOYEES TABLE INTO DB AND CALL THE
	 * METHOD DELETE EMPLOYEE INTO EmployeeControllerImpl CLASS
	 */
	public void testDeleteEmployee() {
		Employee emp = new Employee();
		EmployeeControllerImpl empController = new EmployeeControllerImpl();
		int employeeId = 137;

		emp.setEmployeeId(employeeId);
		System.out.print("\n" + empController.deleteEmployee(emp));
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT JOB TABLE INTO DB AND CALL THE METHOD
	 * DELETE JOB INTO JobControllerImpl CLASS
	 */
	public void testDeleteJob() {
		Job job = new Job();
		JobControllerImpl jController = new JobControllerImpl();
		String jobId = "PU_MAN";

		job.setJobId(jobId);
		System.out.print("\n" + jController.deleteJob(job));
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT LOCATION TABLE INTO DB AND CALL THE
	 * METHOD DELETE LOCATION INTO LocationControllerImpl CLASS
	 */
	public void testDeleteLocation() {
		Location loc = new Location();
		LocationControllerImpl locController = new LocationControllerImpl();
		int locationId = 1700;

		loc.setLocationId(locationId);
		System.out.print("\n" + locController.deleteLocation(loc));
	}

	/**
	 * THIS METHOD SET THE VARIABILES ABOUT REGION TABLE INTO DB AND CALL THE METHOD
	 * DELETE REGION INTO RegionControllerImpl CLASS
	 */
	public void testDeleteRegion() {
		Region reg = new Region();
		RegionControllerImpl regControl = new RegionControllerImpl();
		int regionId = 2;

		reg.setRegionId(regionId);
		System.out.print("\n" + regControl.deleteRegion(reg));
	}

	public static void main(String[] args) {
		TestMainDelete testDelete = new TestMainDelete();
		int scelta = 0;
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);

		do {
			System.out.print("\n");
			System.out.print("\n");
			
			System.out.print("----------------- DELETE RECORD FROM TABLE ---------------");
			System.out.print("\n" + "1- DELETE RECORD FROM COUNTRY TABLE");
			System.out.print("\n" + "2- DELETE RECORD FROM DEPARTMENT TABLE");
			System.out.print("\n" + "3- DELETE RECORD FROM EMPLOYEE TABLE");
			System.out.print("\n" + "4- DELETE RECORD FROM JOB TABLE");
			System.out.print("\n" + "5- DELETE RECORD FROM LOCATION TABLE");
			System.out.print("\n" + "6- DELETE RECORD FROM REGION TABLE");
			System.out.print("\n" + "7- EXIT FROM DELETE RECORD TABLE");
			System.out.print("\n" + "----------------- DELETE RECORD FROM TABLE ---------------");

			try {
				String nLett = tastiera.readLine();
				scelta = Integer.valueOf(nLett).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n" + "Carattere non corretto");
			}

			switch (scelta) {
			case 1:
				testDelete.testDeleteCountry();
				break;

			case 2:
				testDelete.testDeleteDepartment();
				break;

			case 3:
				testDelete.testDeleteEmployee();
				break;

			case 4:
				testDelete.testDeleteJob();
				break;

			case 5:
				testDelete.testDeleteLocation();
				break;

			case 6:
				testDelete.testDeleteRegion();
				break;
			case 7:
				System.out.print("PROGRAM COMPLETED");
				break;

			}

		} while (scelta != 7);

	}
}
