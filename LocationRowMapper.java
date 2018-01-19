package com.soprasteria.mycompany.business.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.soprasteria.mycompany.entity.Country;
import com.soprasteria.mycompany.entity.Location;

@SuppressWarnings("rawtypes")
public class LocationRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

		Location loc = new Location();

		Country country = new Country();

		loc.setLocationId(rs.getInt("location_id"));
		loc.setStreetAdress(rs.getString("street_address"));
		loc.setPostalCode(rs.getString("postal_code"));
		loc.setCity(rs.getString("city"));
		loc.setStateProvince(rs.getString("state_province"));

		country.setCountryId(rs.getString("country_id"));
		loc.setCountry(country);

		return loc;
	}

}
