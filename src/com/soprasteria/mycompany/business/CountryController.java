package com.soprasteria.mycompany.business;

import java.sql.SQLException;
import java.util.List;

import com.soprasteria.mycompany.entity.Country;

public interface CountryController {

	/**
	 * GET COUNTRY METHOD
	 * 
	 * @param countryID
	 * @return
	 */
	public Country getCountry(String countryID);

	/**
	 * INSERT COUNTRY METHOD
	 * 
	 * @param country
	 * @return
	 */
	public String createCountry(Country country);

	/**
	 * UPDATE COUNTRY METHOD
	 * 
	 * @param country
	 * @return
	 */
	public String updateCountry(Country country);

	/**
	 * DELETE COUNTRY METHOD
	 * 
	 * @param country
	 * @return
	 */
	public String deleteCountry(Country country);

	/**
	 * FIND COUNTRY METHOD
	 * 
	 * @param countryId
	 * @param countryName
	 * @return
	 * @throws SQLException 
	 */
	public List<Country> findCountry(String countryId, String countryName) throws SQLException;
}
