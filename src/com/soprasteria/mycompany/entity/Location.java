package com.soprasteria.mycompany.entity;

public class Location {
	private int locationId; // PRIMARY KEY
	private String streetAdress;
	private String postalCode;
	private String city;
	private String stateProvince;
	private Country countryId; // FOREIGN KEY

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getStreetAdress() {
		return streetAdress;
	}

	public void setStreetAdress(String streetAdress) {
		this.streetAdress = streetAdress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public Country getCountry() {
		return countryId;
	}

	public void setCountry(Country countryId) {
		this.countryId = countryId;
	}

	@Override
	/**
	 * TO STRING WITH STRING BUILDER METHOD
	 */
	public String toString() {
		StringBuilder strBuild = new StringBuilder();

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- PRINTING LOCATION(" + locationId + ") --------");

		if (getLocationId() != 0) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" LOCATION ID: " + locationId);
		}
		if (getStreetAdress() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" STREET ADDRESS: " + streetAdress);
		}
		if (getStateProvince() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" STATE PROVINCE: " + stateProvince);
		}
		if (getCity() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" CITY: " + city);
		}
		if (getPostalCode() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(" POSTAL CODE: " + postalCode);
		}
		if (getCountry() != null) {
			strBuild.append(System.getProperty("line.separator"));
			strBuild.append(getCountry().toString());
		}

		strBuild.append(System.getProperty("line.separator"));
		strBuild.append("-------- END PRINTING LOCATION(" + locationId + ") --------");
		strBuild.append(System.getProperty("line.separator"));

		return strBuild.toString();
	}

	/**
	 * THIS METHOD COMPARE THE GET METHOD AND CONTROL THE NULL VALUE
	 * 
	 * @param loc
	 * @return
	 */
	public boolean comopareEquals(Location loc) {
		Boolean compare = true;
		if (loc.getStreetAdress() != null && loc.getStreetAdress().equals(getStreetAdress())) {
			compare = false;
		}

		if (loc.getPostalCode() != null && loc.getPostalCode().equals(getPostalCode())) {
			compare = false;
		}

		if (loc.getCity() != null && loc.getCity().equals(getCity())) {
			compare = false;
		}

		if (loc.getStateProvince() != null && loc.getStateProvince().equals(getStateProvince())) {
			compare = false;
		}

		if (loc.getCountry() != null && loc.getCountry().equals(getCountry())) {
			compare = false;
		}

		return compare;
	}
}
