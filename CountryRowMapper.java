package com.soprasteria.mycompany.business.rowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.soprasteria.mycompany.entity.Country;
import com.soprasteria.mycompany.entity.Region;

@SuppressWarnings("rawtypes")
public class CountryRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Country country= new Country();
		Region region = new Region();

		country.setCountryId(rs.getString("country_id"));
		country.setCountryName(rs.getString("country_name"));

		region.setRegionId(rs.getInt("region_id"));
		country.setRegion(region);
		return country;
	}
}