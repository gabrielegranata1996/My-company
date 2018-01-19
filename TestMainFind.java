package com.soprasteria.mycompany.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.soprasteria.mycompany.entity.EntityFilter;
import com.soprasteria.mycompany.entity.Job;
import com.soprasteria.mycompany.entity.Job_History;
import com.soprasteria.mycompany.entity.Location;
import com.soprasteria.mycompany.entity.RecordFilter;
import com.soprasteria.mycompany.entity.Region;
import com.soprasteria.mycompany.entity.RecordFilter.JoinCondition;
import com.soprasteria.mycompany.entity.RecordFilter.Operator;

public class TestMainFind {

	public void testFindCountry() throws SQLException {
		CountryControllerImpl countryController = new CountryControllerImpl();

		List<Country> country = countryController.findCountry("AU", "Australia");
		System.out.print("\n" + country.toString());
	}

	public void testFindDepartment() throws SQLException {
		DepartementControllerImpl depController = new DepartementControllerImpl();
		List<Departement> dep = new ArrayList<Departement>();

		dep = depController.findDepartement(300, "ROZZANGELES_CITY");
		System.out.print("\n" + dep.toString());
	}

	public void testFindEmployee() throws SQLException {
		EmployeeControllerImpl empController = new EmployeeControllerImpl();
		Employee emp = new Employee();

		emp = empController.findEmployee("gabrielegranata", "Gabriele", "Granata");
		System.out.print("\n" + emp.toString());
	}

	public void testFindEmployeeVariant() {
		EmployeeControllerImpl employeeControl = new EmployeeControllerImpl();
		List<Employee> emp = new ArrayList<Employee>();

		EntityFilter entityFilter = new EntityFilter();
		RecordFilter record = new RecordFilter();

		record.setColumnName("email");
		record.setJoinCondition(JoinCondition.and);
		record.setOperator(Operator.equals);
		record.setValue("gabrielegranata");

		List<RecordFilter> listRecord = new ArrayList<RecordFilter>();
		listRecord.add(record);
		entityFilter.setRecordFilter(listRecord);

		emp = employeeControl.findEmployee(entityFilter);
		System.out.print("\n" + emp.toString());
	}

	public void testFindJob() throws SQLException {
		JobControllerImpl jobController = new JobControllerImpl();
		List<Job> job = new ArrayList<Job>();

		job = jobController.findJob("AD_PRES", "PRESIDENT");
		System.out.print("\n" + job.toString());
	}

	public void testFindJobHistory() throws SQLException {
		JobHistoryControllerImpl jHistoryController = new JobHistoryControllerImpl();
		List<Job_History> jHistory = new ArrayList<Job_History>();

		jHistory = jHistoryController.findJobHistory(186, 50);
		System.out.print("\n" + jHistory.toString());

	}

	public void testFindLocation() throws SQLException {
		LocationControllerImpl locController = new LocationControllerImpl();
		List<Location> loc = new ArrayList<Location>();

		loc = locController.findLocation(1200, "2017 Shinjuku-ku", "1689");
		System.out.print("\n" + loc.toString());
	}

	public void testFindRegion() throws SQLException {
		RegionControllerImpl regionController = new RegionControllerImpl();
		List<Region> region = new ArrayList<Region>();

		region = regionController.findRegion("Europe");
		System.out.print("\n" + region.toString());
	}

	public static void main(String[] args) throws SQLException {
		TestMainFind testFind = new TestMainFind();

		int scelta = 0;
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);

		do {
			System.out.print("\n");
			System.out.print("\n");
			
			System.out.print("----------------- FIND RECORD FROM TABLE ---------------");
			System.out.print("\n" + "1- FIND RECORD FROM COUNTRY TABLE");
			System.out.print("\n" + "2- FIND RECORD FROM DEPARTMENT TABLE");
			System.out.print("\n" + "3- FIND RECORD FROM EMPLOYEE TABLE");
			System.out.print("\n" + "4- FIND RECORD FROM JOB TABLE");
			System.out.print("\n" + "5- FIND RECORD FROM LOCATION TABLE");
			System.out.print("\n" + "6- FIND RECORD FROM REGION TABLE");
			System.out.print("\n" + "7- FIND RECORD FROM JOB-HISTORY TABLE");
			System.out.print("\n" + "8- FIND RECORD FROM EMPLOYEE--2 TABLE");
			System.out.print("\n" + "9- EXIT FIND RECORD  ");
			System.out.print("\n" + "----------------- FIND RECORD FROM TABLE ---------------");

			try {
				String nLett = tastiera.readLine();
				scelta = Integer.valueOf(nLett).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n" + "Carattere non corretto");
			}

			switch (scelta) {
			case 1:
				testFind.testFindCountry();
				break;

			case 2:
				testFind.testFindDepartment();
				break;

			case 3:
				testFind.testFindEmployee();
				break;

			case 4:
				testFind.testFindJob();
				break;

			case 5:
				testFind.testFindLocation();
				break;

			case 6:
				testFind.testFindRegion();
				break;

			case 7:
				testFind.testFindJobHistory();
				break;
			case 8:
				testFind.testFindEmployeeVariant();
				break;
			case 9:
				System.out.print("\n" + "PROGRAM COMPLETED");
				break;
			}

		} while (scelta != 9);
	}
}
