package com.soprasteria.mycompany.entity;

public class Region {
	private int regionId; // PRIMARY KEY
	private String regionName;

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Override
	/**
	 * TO STRING WITH STRING BUILDER METHOD
	 */
	public String toString() {
		StringBuilder strBuild = new StringBuilder();

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- PRINTING REGION(" + regionId + ") --------");

		if (getRegionId() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" REGION ID: " + regionId);
		}
		if (getRegionName() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" REGION NAME: " + regionName);
		}

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- END PRINTING REGION(" + regionId + ") --------");
		strBuild.append(System.getProperty("line.separator"));

		return strBuild.toString();
	}

	/**
	 * THIS METHOD CONTROL THE GET AND NULL VALUE ABOUT REGION METHOD
	 * 
	 * @param region
	 * @return
	 */
	public boolean compareEquals(Region region) {
		Boolean control = true;

		if (region.getRegionName() != null && region.getRegionName().equals(getRegionName())) {
			control = false;
		}

		return control;
	}
}
