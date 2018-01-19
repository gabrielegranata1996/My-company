package com.soprasteria.mycompany.business;

import java.sql.SQLException;
import java.util.List;

import com.soprasteria.mycompany.entity.Departement;

public interface DepartementController {

	/**
	 * GET DEPARTMENT METHOD
	 * 
	 * @param departementID
	 * @return
	 */
	public Departement getDepartement(int departementID);

	/**
	 * INSERT DEPARTMENT METHOD
	 * 
	 * @param dep
	 * @return
	 */
	public String createDepartement(Departement dep);

	/**
	 * UPDATE DEPARTMENT METHOD
	 * 
	 * @param dep
	 * @return
	 */
	public String updateDepartement(Departement dep);

	/**
	 * DELETE DEPARTMENT METHOD
	 * 
	 * @param dep
	 * @return
	 */
	public String deleteDepartement(Departement dep);

	/**
	 * FIND DEPARTMENT METHOD
	 * 
	 * @param depId
	 * @param depName
	 * @return
	 * @throws SQLException 
	 */
	public List<Departement> findDepartement(int depId, String depName) throws SQLException;
}
