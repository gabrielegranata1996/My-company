package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.soprasteria.mycompany.business.LocationController;
import com.soprasteria.mycompany.business.rowMapper.LocationRowMapper;
import com.soprasteria.mycompany.entity.Country;
import com.soprasteria.mycompany.entity.Location;

public class LocationControllerImpl extends EntityController implements LocationController {

	private PreparedStatement stm;
	private ResultSet rs;

	public LocationControllerImpl() {
		super();
	}

	public LocationControllerImpl(Connection conn) {
		super(conn);
	}

	// SETTO I VALORI DELLA TABELLA E DELLA COLONNA
	protected void initValues() {
		this.tableName = "locations";
		this.primaryKeyColumn = "location_id";
	}

	/**
	 * METHOD MANAGE THE PRINTING ABOUT LOCATION TABLE
	 */
	@Override
	public Location getLocation(int location_id) {
		Location loc = new Location();

		String sql = "select * from locations where location_id=?";
		try {
			openConnection();
			stm = conn.prepareStatement(sql);
			stm.setInt(1, location_id);
			rs = stm.executeQuery();

			if (rs.next()) {
				loc.setLocationId(location_id);
				loc.setStreetAdress(rs.getString("street_address"));
				loc.setPostalCode(rs.getString("postal_code"));
				loc.setCity(rs.getString("city"));
				loc.setStateProvince(rs.getString("state_province"));

				// GESTIONE CHIAVI ESTERNE
				String countryId = rs.getString("country_id"); // creo un variabile che conterrà il valore recuperato
																// dal DB
				CountryControllerImpl couController = new CountryControllerImpl(); // istanzio l'oggetto di tipo
																					// ControllerImpl
				Country con = couController.getCountry(countryId); // nell'oggetto DEPARTEMENT inserisco il valore
																	// recuperato dal DB
				loc.setCountry(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
			loc = null;
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
		return loc;
	}

	/**
	 * THIS METHOD CONTROL THE NULLABLE VARIABLES
	 * 
	 * @param loc
	 * @return
	 */
	public boolean validateLocation(Location loc, String op) {
		Boolean validate = true;

		if (op.equals("update")) { // BLOCCO IF RISERVATO SOLO ALLA CHIAMATA DELLA validateLocation DA PARTE
									// DELL'updateLocation DOVE SETTO IL CAMPO NULLABLE LOCATION_ID
			// CONTROLLO CAMPO LOCATION_ID NULLABLE (OBBLIGATORIO)
			if (loc.getLocationId() == 0) {
				System.out.print("ERRORE: Campo Location_ID OBBLIGATORIO!!");
				validate = false;
				return validate;
			}
		}

		// CONTROLLO CAMPO CITY NULLABLE (OBBLIGATORIO)
		if (loc.getCity() == null) {
			System.out.print("ERRORE: Campo City OBBLIGATORIO!!");
			validate = false;
			return validate;
		}
		return validate;
	}

	/**
	 * THIS METHOD CONTROL THE UNIQUE VALUE IN LOCATION TABLE
	 * 
	 * @param loc
	 * @return
	 */
	public boolean controlUniqueLocation(Location loc) throws SQLException {
		Boolean controlUnique = true;

		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		try {
			// CONTROLLO SUI VALORI UNIQUE DELLA TABELLA LOCATION
			String controllo = "select count (*) from locations where street_address=? and postal_code=? and city=?";
			stm = conn.prepareStatement(controllo);
			stm.setString(1, loc.getStreetAdress()); // SET STREET_ADDRESS (CONDIZIONE NEL WHERE)
			stm.setString(2, loc.getPostalCode()); // SET POSTAL_CODE (CONDIZIONE NEL WHERE)
			stm.setString(3, loc.getCity()); // SET CITY (CONDIZIONE NEL WHERE)

			rs = stm.executeQuery();

			if (rs.next()) {
				int cont = rs.getInt(1);
				if (cont > 0) { // CONTROLLO SUI CAMPI UNICI; SE TROVIAMO DEI VALORI SIMILI NELLA TABELLA
					System.out.print("\n" + "ERRORE: Questa via e/o postal_code e/o city sono GIA\' ESISTENTI!!");
					controlUnique = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			controlUnique = false;
		}
		return controlUnique;
	}

	/**
	 * THIS METHOD MANAGE THE INSERT ABOUT LOCATION TABLE
	 */
	@Override
	public String createLocation(Location loc) {
		String ris = null, op = "";

		validateLocation(loc, op);

		try {
			openConnection();
			if (controlUniqueLocation(loc)) { // VIENE ESEGUITA LA INSERT SE E SOLO SE VIENE SODDISFATTA
				// LA CONDIZIONE NEL METODO controlUniqueLocation
				String sql = "insert into locations (location_id,street_address,postal_code,city,state_province,country_id)"
						+ " values (locations_seq.nextval,?,?,?,?,?)";
				stm = conn.prepareStatement(sql);

				// CONTROLLO DEL CAMPO STREET_ADDRESS==NULL
				if (loc.getStreetAdress() == null) {
					stm.setNull(1, Types.VARCHAR);
				} else {
					stm.setString(1, loc.getStreetAdress()); // SE POPOLATO INSERT STREET_ADDRESS, POS 1
				}

				// CONTROLLO DEL CAMPO POSTAL_CODE==NULL
				if (loc.getPostalCode() == null) {
					stm.setNull(2, Types.VARCHAR);
				} else {
					stm.setString(2, loc.getPostalCode()); // SE POPOLATO INSERT POSTAL_CODE, POS 2
				}

				stm.setString(3, loc.getCity()); // INSERT POS 3, CITY

				// CONTROLLO DEL CAMPO STATE_PROVINCE==NULL
				if (loc.getStateProvince() == null) {
					stm.setNull(4, Types.VARCHAR);
				} else {
					stm.setString(4, loc.getStateProvince()); // SE POPOLATO INSERT STATE_PROVINCE, POS 4
				}

				// CONTROLLO DEL CAMPO COUNTRY_ID
				if (loc.getCountry() == null) {
					stm.setNull(5, Types.VARCHAR);
				} else {
					stm.setString(5, loc.getCountry().getCountryId()); // SE POPOLATO INSERT COUNTRY_ID, POS 5
				}

				stm.executeUpdate();
				ris = "\n" + "LOCATION INSERITA!!!";
			} else {
				System.out.println("ERRORE!!");
				ris = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ris = null;
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
	 * THIS METHOD MANAGE TH EUPDATE ABOUT LOCATION TABLE
	 */
	@Override
	public String updateLocation(Location loc) {
		String ris = null, op = "update";

		validateLocation(loc, op);

		try {
			openConnection();
			boolean locationExists = entityExists(loc.getLocationId());

			if (locationExists && controlUniqueLocation(loc)) { // VIENE ESEGUITA L'UPDATE SE E SOLO SE VIENE
																// SODDISFATTA LA
				// CONDIZIONE DEL METODO controlUniqueLocation
				String sql = "update locations "
						+ "set street_address=?, postal_code=?, city=?, state_province=?, country_id=?"
						+ "where location_id=?";
				stm = conn.prepareStatement(sql);
				stm.setString(1, loc.getStreetAdress()); // SET STREET_ADDRESS
				stm.setString(2, loc.getPostalCode()); // SET POSTAL_CODE
				stm.setString(3, loc.getCity()); // SET CITY
				stm.setString(4, loc.getStateProvince()); // SET STATE_PROVINCE
				stm.setString(5, loc.getCountry().getCountryId()); // SET COUNTRY_ID (CHIAVE ESTERNA)

				stm.setInt(6, loc.getLocationId()); // SET LOCATION_ID (CONDIZONE NEL WHERE)

				rs = stm.executeQuery();
				ris = "LOCATION AGGIORNATA";

			} else {
				System.out.print("ERRORE!!");
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
	 * METHOD MANAGE THE DELETE INTO LOCATION TABLE
	 */
	@Override
	public String deleteLocation(Location loc) {
		String ris = null;

		try {
			openConnection();
			boolean locationExists = entityExists(loc.getLocationId());
			DepartementControllerImpl depControl = new DepartementControllerImpl(this.conn);
			boolean checkDepartmentForeignKey = depControl.checkForeignKey("location_id", loc.getLocationId());

			if (locationExists && checkDepartmentForeignKey) {
				String sql = "delete from locations where location_id=?";
				stm = conn.prepareStatement(sql);
				stm.setInt(1, loc.getLocationId());
				stm.executeUpdate();

				ris = "\n" + "LOCATION ELIMINATA!!";
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
	 * METHOD MANAGE YHE FIND INTO LOCATION TABLE
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Location> findLocation(int locationId, String address, String postalCode) throws SQLException {

		openDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		if (locationId == 0 && address == null && postalCode == null) {
			System.out.print("ERRORE: Campo nullo!!!");
		}

		List<Location> loc = (List<Location>) jdbcTemplate.query(
				"select * from locations where 1=1 and location_id=? and street_address=? and postal_code=?",
				new Object[] { locationId, address, postalCode }, new LocationRowMapper());

		return loc;
	}

}
