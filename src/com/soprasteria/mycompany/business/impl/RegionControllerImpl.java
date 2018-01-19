package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.soprasteria.mycompany.business.RegionController;
import com.soprasteria.mycompany.business.rowMapper.RegionRowMapper;
import com.soprasteria.mycompany.entity.Region;

public class RegionControllerImpl extends EntityController implements RegionController {

	private PreparedStatement stm;
	private ResultSet rs;

	public RegionControllerImpl() {
		super();
	}

	public RegionControllerImpl(Connection conn) {
		super(conn);
	}

	protected void initValues() {
		this.tableName = "regions";
		this.primaryKeyColumn = "region_id";
	}

	/**
	 * METHOD MANAGE THE PRINTING ABOUT REGION TABLE
	 */
	@Override
	public Region getRegion(int regionId) {
		Region region = new Region();

		String sql = "select * from regions where region_id=?";

		try {
			openConnection();
			stm = conn.prepareStatement(sql);
			stm.setInt(1, regionId);
			rs = stm.executeQuery();

			if (rs.next()) {
				region.setRegionId(regionId);
				region.setRegionName(rs.getString("region_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			region = null;
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
		return region;
	}

	/**
	 * THIS METHOD CONTROLE THE NULLABLE VARIABLES
	 * 
	 * @param region
	 * @return
	 */
	public boolean validateRegion(Region region) {
		Boolean validate = true;

		// CONTROLLO CAMPO REGION_ID NULLABLE (OBBLIGATORIO)
		if (region.getRegionId() == 0) {
			System.out.print("ERRORE: Campo REGION_ID OBBLIGATORIO!!");
			validate = false;
			return validate;
		}
		return validate;
	}

	/**
	 * THIS METHOD CONTROL REGION_NAME IS EXIST
	 * 
	 * @param region
	 * @return
	 */
	public boolean controlRegionName(Region region) throws SQLException {
		Boolean risControl = true;

		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		try {
			// CONTROLLO EISTSENZA DI OGGETTI ALL'INTERNO DELLA TABELLA REGION (UNIQUE)
			String controllo = "select count (*) from regions where region_name=?";

			stm = conn.prepareStatement(controllo);

			stm.setString(1, region.getRegionName()); // SET REGION_NAME (CONDIZONE NEL WHERE)

			rs = stm.executeQuery();
			if (rs.next()) {
				int cont = rs.getInt(1);
				if (cont > 0) { // SE IL CONTATORE E' MAGGIORE DI 0 SIGNIFICA CHE C'E' GIA' UNA REGION PRESENTE
					// ALL'INTERNO DELLA TABELLA
					System.out.print("ERRORE: Region_name GIA\' PRESENTE!!");
					risControl = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			risControl = false;
		}
		return risControl;
	}

	/**
	 * THIS METHOD MANAGE THE INSERT ONE REGION INTO REGIONS TABLE
	 */
	@Override
	public String createRegion(Region region) {
		String ris = null;

		validateRegion(region); // CONSIDERO I VARI CAMPI DA VALIDARE

		try {
			openConnection();
			if (controlRegionName(region)) { // VIENE ESEGUITO L'UPDATE SE E SOLO SE VIENE SODDISFATTA LA
				// CONDIZONE DEL METODO controlRegionName
				// INSERT REGION
				String sql = "insert into regions (region_id,region_name) values (?,?)";
				stm = conn.prepareStatement(sql);

				stm.setInt(1, region.getRegionId()); // POS 1, REGION_ID

				// CONTROLLO DEL CAMPO REGION_NAME==NULL
				if (region.getRegionName() == null) {
					stm.setNull(2, Types.VARCHAR);
				} else {
					stm.setString(2, region.getRegionName()); // SE POPOLATO INSERISCO REGION_NAME, POS 2
				}

				stm.executeUpdate();
				ris = "\n" + "REGION INSERITO";
			} else {
				System.out.print("Errore di inserimento!!");
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
	 * THIS METHOD MANAGE THE UPDATE INTO REGIONS TABLE
	 */
	@Override
	public String updateRegion(Region region) {
		String ris = null;

		validateRegion(region); // CONTROLLO PER VALIDAZIONE DEGLI ATTRIBUTI DELLA TABELLA

		try {
			openConnection();

			boolean regionExists = this.entityExists(region.getRegionId());

			if (regionExists && controlRegionName(region)) { // VIENE ESEGUITA L'UPDATE SE E SOLO SE VIENE SODDISFATTA
																// LA
				// CONDIZIONE DAL METODO controlRegionName
				String sql = "update regions set region_name=? where region_id=?";
				stm = conn.prepareStatement(sql);
				stm.setString(1, region.getRegionName()); // SET REGION_NAME
				stm.setInt(2, region.getRegionId()); // SET REGION_ID (CONDIZIONE DEL WHERE)

				stm.executeQuery();
				ris = "REGION AGGIORNATA";
			} else {
				System.out.print("\n" + "Errore nell'update!!!");
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
	 * THIS METHOD MANAGE THE DELETE ONE REGION FROM REGIONS TABLE
	 */
	@Override
	public String deleteRegion(Region region) {
		String ris = null;

		try {
			openConnection();
			boolean regionExists = this.entityExists(region.getRegionId());

			CountryControllerImpl countryControl = new CountryControllerImpl(this.conn);
			boolean checkCountryForeignKey = countryControl.checkForeignKey("region_id", region.getRegionId());

			if (regionExists && checkCountryForeignKey) {
				String sql = "delete from region where region_id=?";
				stm = conn.prepareStatement(sql);
				stm.setInt(1, region.getRegionId());
				stm.executeUpdate();

				ris = "\n" + "REGION ELIMINATA!!";
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
	 * METHOD MANAGE THE FIND INTO REGION TABLE
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Region> findRegion(String regionName) throws SQLException {

		openDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);

		if (regionName == null) {
			System.out.print("ERRORE: Campo nullo!!");
		}

		List<Region> region = (List<Region>) jdbcTemplate.query("select * from regions where 1=1 and region_name=?",
				new Object[] { regionName }, new RegionRowMapper());

		return region;
	}

}
