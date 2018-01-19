
package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.soprasteria.mycompany.business.CountryController;
import com.soprasteria.mycompany.business.rowMapper.CountryRowMapper;
import com.soprasteria.mycompany.entity.Country;
import com.soprasteria.mycompany.entity.Region;

public class CountryControllerImpl extends EntityController implements CountryController {

	private PreparedStatement stm;
	private ResultSet rs;

	public CountryControllerImpl() {
		super();
	}

	public CountryControllerImpl(Connection conn) {
		super(conn);
	}

	// SETTO I VALORI DELLA TABELLA E DELLA COLONNA
	protected void initValues() {
		this.tableName = "countries";
		this.primaryKeyColumn = "country_id";
	}

	/**
	 * METHOD MANAGE THE PRINTING ABOUT COUNTRY TABLE
	 * 
	 */
	@Override
	public Country getCountry(String countryId) {
		Country country = new Country();

		String sql = "select * from countries where country_id=?";
		try {
			openConnection();
			stm = conn.prepareStatement(sql);
			stm.setString(1, countryId);
			rs = stm.executeQuery();

			if (rs.next()) {
				country.setCountryId(countryId);
				country.setCountryName(rs.getString("country_name"));

				// GESTIONE CHIAVI ESTERNE
				int regionId = rs.getInt("region_id"); // creo un variabile che conterrà il valore recuperato dal DB
				RegionControllerImpl regCon = new RegionControllerImpl(); // istanzio l'oggetto di tipo
																			// ControllerImpl
				Region region = regCon.getRegion(regionId); // nell'oggetto LOCATION inserisco il valore recuperato dal
															// DB

				country.setRegion(region);
			}
		} catch (Exception e) {
			e.printStackTrace();
			country = null;
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
				closeConnection();
			} catch (Exception e) {

			}
		}
		return country;
	}

	/**
	 * THIS METHOD CONTROL THE NULLABLE VARIABLES
	 * 
	 * @param country
	 * @return
	 */
	public boolean validateCountry(Country country) {
		Boolean validate = true;

		// CONTROLLO IL VALORE DEL COUNTRY ID_ (NOT NULL)
		if (country.getCountryId() == null) {
			System.out.print("ERRORE: Campo Country_ID OBBLIGATORIO!!");
			validate = false;
			return validate;
		}
		return validate;
	}

	/**
	 * METHOD CONTROL THE COUNTRY_NAME is UNIQUE
	 * 
	 * @param country
	 * @return
	 */
	public boolean controlCountryName(Country country) throws SQLException {
		Boolean control = true;

		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		try {
			// CONTROLLO ESISTENZA DI OGGETTI ALL'INTERNO DELLA TABELLA (UNIQUE)
			String controllo = "select count (*) from countries where country_name=?";

			stm = conn.prepareStatement(controllo);
			stm.setString(1, country.getCountryName()); // SETTO L'UGUAGLIANZA CHE PASSO ALL'INTERNO DELL'OGGETTO
														// COUNTRY
			rs = stm.executeQuery();

			if (rs.next()) {
				int cont = rs.getInt(1);
				if (cont > 0) { // SE IL CONTATORE E' MAGGIORE DI 0 SIGNIFICA CHE E' GIA' PRESENTE UNA COUNTRY
								// ALL'INTERNO DELLA TABELLA
					System.out.print("\n" + "ERRORE: COUNTRY GIA' PRESENTE!!");
					control = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			control = false;
		}
		return control;
	}

	/**
	 * THIS METHOD MANAGE THE INSERT INTO COUNTRY TABLE
	 */
	@Override
	public String createCountry(Country country) {
		String ris = null;

		validateCountry(country); // RICHIAMO IL METODO VALIDATE PER CAMPI NULLABLE

		try {
			openConnection();
			if (controlCountryName(country)) { // LA QUERY VIENE ESEGUITA SE SODDISFATTA LA CONDIZIONE DEL
				// METODO controlCountryName
				String sql = "insert into countries (country_id,country_name,region_id)" + "values (?,?,?)";
				stm = conn.prepareStatement(sql);

				stm.setString(1, country.getCountryId()); // POS 1, COUNTRY_ID

				// CONTROLLO DEL CAMPO COUNTRY_NAME==null
				if (country.getCountryName() == null) {
					stm.setNull(2, Types.VARCHAR);
				} else {
					stm.setString(2, country.getCountryName()); // SE POPOLATO INSERISCO COUNTRY_NAME, POS 2
				}

				// CONTROLLO DEL CAMPO REGION_ID==null
				if (country.getRegion() == null) {
					stm.setNull(3, Types.INTEGER);
				} else {
					stm.setInt(3, country.getRegion().getRegionId()); // SE POPOLATO INSERISCO REGION_ID, POS 3
				}
				stm.executeUpdate();
				ris = "\n" + "COUNTRY INSERITO!!";
			} else {
				System.out.print("\n" + "ERRORE: COUNTRY_NAME GIA\' VALIDATO!!");
				ris = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			ris = null;
		}

		return ris;
	}

	/**
	 * THIS METHOD MANAGE THE UPDATE INTO COUNTRY TABLE
	 */
	@Override
	public String updateCountry(Country country) {
		String ris = null;

		validateCountry(country);

		try {
			openConnection();
			boolean countryExists = entityExists(country.getCountryId());
			if (countryExists && controlCountryName(country)) { // LA QUERY DI UPDATE VIENE ESEGUITA SE LA CONDIZIONE
																// DEL
				// METODO controlCountryName VIENE SODDISFATTA

				String sql = "update countries " + "set country_name=?, region_id=? where country_id=?";
				stm = conn.prepareStatement(sql);

				stm.setString(1, country.getCountryName()); // SET COUNTRY_NAME
				stm.setInt(2, country.getRegion().getRegionId()); // SET REGION_ID (CHIAVE ESTERNA)
				stm.setString(3, country.getCountryId()); // SET COUNTRY_ID (CONDIZIONE DI WHERE)

				stm.executeUpdate();
				ris = "COUNTRY AGGIORNATO";

			} else {
				System.out.print("\n" + "ERRORE: COUNTRY_NAME GIA\' VALIDATO!!");
				ris = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ris = null;
		} finally {
			try {
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
				closeConnection();
			} catch (Exception e2) {

			}
		}
		return ris;
	}

	/**
	 * THIS METHOD MANAGE THE DELETE INTO COUNTRY TABLE
	 */
	public String deleteCountry(Country country) {
		String ris = null;

		try {
			openConnection();
			boolean countryExists = entityExists(country.getCountryId());
			LocationControllerImpl locController = new LocationControllerImpl(this.conn);
			boolean checkLocationForeignKey = locController.checkForeignKey("country_id", country.getCountryId());

			if (countryExists && checkLocationForeignKey) {
				String sql = "delete from countries where country_id=?";
				stm = conn.prepareStatement(sql);
				stm.setString(1, country.getCountryId());
				stm.executeUpdate();

				ris = "\n \n" + "EMPLOYEE ELIMINATO!!";
			} else {
				System.out.print("\n \n" + "IMPOSSIBILE EFFETTUARE LA DELETE!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			ris = null;
		} finally {
			try {
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
				closeConnection();
			} catch (Exception e2) {
			}
		}

		return ris;
	}

	/**
	 * THIS METHODMANAGE THE FIND INTO COUNTRY TABLE
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Country> findCountry(String countryId, String countryName) throws SQLException {

		openDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		if (countryId == null && countryName == null) {
			System.out.print("ERRORE: Campi nulli!!");
		}

		List<Country> country = (List<Country>) jdbcTemplate.query(
				"select * from countries where country_id=? and country_name=?",
				new Object[] { countryId, countryName }, new CountryRowMapper());
		return country;

	}
}
