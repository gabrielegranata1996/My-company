package com.soprasteria.mycompany.business;

import java.sql.SQLException;
import java.util.List;

import com.soprasteria.mycompany.entity.Region;

public interface RegionController {

	/**
	 * GET REGION METHOD
	 * 
	 * @param regionId
	 * @return
	 */
	public Region getRegion(int regionId);

	/**
	 * INSERT REGION METHOD
	 * 
	 * @param region
	 * @return
	 */
	public String createRegion(Region region);

	/**
	 * UPDATE REGION METHOD
	 * 
	 * @param region
	 * @return
	 */
	public String updateRegion(Region region);

	/**
	 * DELETE REGION METHOD
	 * 
	 * @param region
	 * @return
	 */
	public String deleteRegion(Region region);

	/**
	 * FIND REGION METHOD
	 * 
	 * @param regionName
	 * @return
	 * @throws SQLException 
	 */
	public List<Region> findRegion(String regionName) throws SQLException;
}
