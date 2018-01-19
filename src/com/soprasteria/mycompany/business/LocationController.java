package com.soprasteria.mycompany.business;

import java.sql.SQLException;
import java.util.List;

import com.soprasteria.mycompany.entity.Location;

public interface LocationController {

	/**
	 * GET LOCATION METHOD
	 * 
	 * @param location_id
	 * @return
	 */
	public Location getLocation(int location_id);

	/**
	 * INSERT LOCATION METHOD
	 * 
	 * @param loc
	 * @return
	 */
	public String createLocation(Location loc);

	/**
	 * UPDATE LOCATION METHOD
	 * 
	 * @param loc
	 * @return
	 */
	public String updateLocation(Location loc);

	/**
	 * DELETE LOCATION METHOD
	 * 
	 * @param loc
	 * @return
	 */
	public String deleteLocation(Location loc);

	/**
	 * FIND LOCATION METHOD
	 * 
	 * @param locationId
	 * @param address
	 * @param postalCode
	 * @return
	 * @throws SQLException 
	 */
	public List<Location> findLocation(int locationId, String address, String postalCode) throws SQLException;
}
