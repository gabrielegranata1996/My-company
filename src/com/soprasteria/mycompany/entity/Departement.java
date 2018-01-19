package com.soprasteria.mycompany.entity;

public class Departement {
	private int departementId; // PRIMARY KEY
	private String departementName;
	private Employee managerId; // FOREIGN KEY
	private Location locationId; // FOREIGN KEY

	public int getDepartementId() {
		return departementId;
	}

	public void setDepartementId(int departementId) {
		this.departementId = departementId;
	}

	public String getDepartementName() {
		return departementName;
	}

	public void setDepartementName(String departementName) {
		this.departementName = departementName;
	}

	public Employee getManager() {
		return managerId;
	}

	public void setManager(Employee managerId) {
		this.managerId = managerId;
	}

	public Location getLocation() {
		return locationId;
	}

	public void setLocation(Location locationId) {
		this.locationId = locationId;
	}

	/**
	 * TO STRING WITH STRING BUILDER METHOD
	 */
	@Override
	public String toString() {
		StringBuilder strBuild = new StringBuilder();

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- PRINTING DEPARTMENT(" + departementId + ") --------");
		if (getDepartementId() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" DEPARTMENT ID: " + departementId);
		}
		if (getDepartementName() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" DEPARTMENT NAME: " + departementName);
		}
		if (getLocation() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getLocation().toString());
		}
		if (getManager() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getManager().toString());
		}
		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- END PRINTING DEPARTMENT(" + departementId + ") --------");
		strBuild.append(System.getProperty("line.separator"));

		return strBuild.toString();
	}

	/**
	 * THIS METHOD COMPARE AND CONTROL THE NULL VALUE ABOUT OBJECT ATTRIBUTE
	 * 
	 * @param department
	 * @return
	 */
	public boolean compareEquals(Departement department) {
		Boolean risComp = true;

		if (department.getDepartementName() != null && department.getDepartementName().equals(getDepartementName())) {
			return risComp = false;
		}

		if (department.getLocation() != null && department.getLocation().equals(getLocation())) {
			return risComp = false;
		}

		if (department.getManager() != null && department.getManager().equals(getManager())) {
			return risComp = false;
		}

		return risComp;
	}
}
