package com.soprasteria.mycompany.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.soprasteria.mycompany.utility.ConnectionManager;
import java.sql.SQLException;

import javax.sql.DataSource;

public class EntityController {

	protected Connection conn;
	protected DataSource dataSource;
	protected boolean closeConnection;
	protected boolean closeDataSource;
	protected String tableName;
	protected String primaryKeyColumn;

	public EntityController(Connection conn) {
		super();
		this.conn = conn;
		closeConnection = false;
		initValues();
	}

	public EntityController() {
		super();
		closeConnection = true;
		initValues();
	}

	protected void initValues() {
		this.tableName = "";
		this.primaryKeyColumn = "";
	}

	/**
	 * METHOD MANAGE THE OPEN CONNECTION WITH DB
	 * 
	 * @throws SQLException
	 */
	protected void openConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			this.conn = ConnectionManager.getConnection();
		}
	}

	/**
	 * METHOD MANAGE OPEN DATA SOURCE (call getDataSource())
	 * 
	 * @throws SQLException
	 */
	protected void openDataSource() throws SQLException {
		if (dataSource == null) {
			this.dataSource = ConnectionManager.getDataSource();
		}
	}

	/**
	 * METHOD MANAGE THE CLOSE CONNECTION WITH DB
	 * 
	 * @throws SQLException
	 */
	protected void closeConnection() throws SQLException {
		if (closeConnection && conn != null && !conn.isClosed()) {
			ConnectionManager.closeConnection(conn);
		}
	}

	/**
	 * METHOD CHECK EMPLOYEE_ID EXIST
	 * 
	 * @param primaryKeyValue
	 * @return
	 * @throws SQLException
	 */
	protected boolean entityExists(Object primaryKeyValue) throws SQLException {

		PreparedStatement stm = null;
		ResultSet rs = null;
		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connessione chiusa!!");
		}

		// CONTROLLO ESISTENZA DI UN EMPLOYEE_ID ALL'INTERNO DELLA TABELLA EMPLOYEE
		String controllo = "select count (*) from " + tableName + " where " + primaryKeyColumn + "=?"; // COUNT --->
																										// CONTA LA
																										// PRESENZA DI
		// QUANTI
		// EMPLOYEE_ID SONO PRESENTI
		// ALL'INTERNO DEL DB
		try {
			stm = conn.prepareStatement(controllo);
			stm.setObject(1, primaryKeyValue); // SET EMPLOYEE_ID (CONDIZIONE LE WHERE)
			rs = stm.executeQuery();

			if (rs.next()) {
				int contEmail = rs.getInt(1);
				if (contEmail > 0) { // SE IL CONTATORE E' MAGGIORE DI 0 SIGNIFICA CHE C'E' UN'EMPLOYEE PRESENTE
					// ALL'INTERNO DELLA TABELLA
					System.out.print("\n" + tableName + " PRESENTE");
				} else {
					System.out.print("\n" + "NESSUN " + tableName + " CON QUESTO ID");
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	/**
	 * METODO CHE CONTROLLA LA POSSIBILITA' DI AVERE LA PRESENZA DI UN RIFERIMENTO
	 * IN UNA TABELLA
	 * 
	 * @param tableName
	 * @param primaryKeyColumn
	 * @param jHistory
	 * @return
	 * @throws SQLException
	 */
	protected boolean checkForeignKey(String foreignKeyColumn, Object foreignKeyValue) throws SQLException {
		PreparedStatement stm = null;
		ResultSet rs = null;

		if (conn == null || conn.isClosed()) { // CONTROLLO LA POSSIBILE CHIUSURA DELLA CONNESSIONE AL DB
			throw new SQLException("Connessione chiusa!!"); // SOLLEVO UN'ECCEZIONE
		}

		// CONTROLLO ESISTENZA DI ALMENO UN EMPLOYEE_ID ALL'INTERNO DELLA TABELLA
		// JOB_HISTORY
		String controllo = "select count (*) from " + tableName + " where " + foreignKeyColumn + "=?";
		try {
			stm = conn.prepareStatement(controllo);
			stm.setObject(1, foreignKeyValue); // SET EMPLOYEE_ID (CONDIZIONE LE WHERE)
			rs = stm.executeQuery();

			if (rs.next()) {
				int contForeign = rs.getInt(1);
				if (contForeign > 0) { // SE IL CONTATORE E' MAGGIORE DI 0 SIGNIFICA CHE C'E' UN'EMPLOYEE PRESENTE
					// ALL'INTERNO DELLA TABELLA
					System.out.print("\n \n" + "IMPOSSIBILE ELIMINARE IL RECORD: CI SONO RIFERIMENTI ALL'INTERNO DI "
							+ tableName + "");
					return false;
				} else {
					System.out.print("\n \n" + "OK :::::: NESSUN RIFERIMENTO A " + tableName);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	/**
	 * METODO CHE CONTROLLA LA POSSIBILE PRESENZA DI UN VALORE NULL IN UN CAMPO DI
	 * UNA TABELLA
	 * 
	 * @param nameColumn
	 * @return
	 * @throws SQLException
	 */
	protected boolean checkColumnValue(String nameColumn) throws SQLException {
		PreparedStatement stm = null;
		ResultSet rs = null;

		if (conn == null || conn.isClosed()) { // CONTROLLO LA POSSIBILE CHIUSURA DELLA CONNESSIONE AL DB
			throw new SQLException("Connessione chiusa!!"); // SOLLEVO UN'ECCEZIONE
		}

		String sql = "select * from " + tableName + " where " + nameColumn + "='null'";
		try {
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();

			if (rs.next()) {
				System.out.print(
						"\n" + nameColumn + "e\' impostato con valore NULL!! IMPOSSIBILE RICERCARE " + tableName);
			} else {
				System.out.print("\n" + nameColumn + "e\' popolato sulla tabella " + tableName);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}
}
