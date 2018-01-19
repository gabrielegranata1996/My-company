package com.soprasteria.mycompany.entity;

public class Country {
	private String countryId; // PRIMARY KEY
	private String countryName;
	private Region regionId; // FOREIGN KEY

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Region getRegion() {
		return regionId;
	}

	public void setRegion(Region regionId) {
		this.regionId = regionId;
	}

	@Override
	/**
	 * TO STRING WITH STRING BUILDER METHOD
	 */
	public String toString() {
		StringBuilder strBuild = new StringBuilder();

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- PRINTING COUNTRY(" + countryId + ") --------");
		if (getCountryId() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" COUNTRY ID: " + countryId);
		}
		if (getCountryName() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" COUNTRY NAME: " + countryName);
		}
		if (getRegion() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getRegion().toString());
		}
		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- END PRINTING COUNTRY(" + countryId + ") --------");
		strBuild.append(System.getProperty("line.separator"));

		return strBuild.toString();
	}

	/**
	 * THIS METHOD COMPARE AND CONTROL THE NULL VALUE ABOUT OBJECT ATTRIBUTE
	 * 
	 * @param country
	 * @return
	 */
	public boolean compareEquals(Country country) {
		Boolean risCompare = true;

		// CONTROLLO UGUAGLIANZA DEL SET DELL'ID E CONTROLLO DEL VALORE NULL
		if (country.getCountryId() != null && country.getCountryId().equals(getCountryId())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA DEL SET COUNTRY_NAME E CONTROLLO DEL VALORE NULL
		if (country.getCountryName() != null && country.getCountryName().equals(getCountryName())) {
			return risCompare = false;
		}

		// CONTROLLO UGUAGLIANZA FRL SET REGION_ID E CONTROLLO DEL VALORE NULL
		if (country.getRegion() != null && country.getRegion().equals(getRegion())) {
			return risCompare = false;
		}
		return risCompare;
	}
}
