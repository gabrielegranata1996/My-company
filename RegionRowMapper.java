package com.soprasteria.mycompany.business.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.soprasteria.mycompany.entity.Region;

@SuppressWarnings("rawtypes")
public class RegionRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Region region= new Region();
		
		region.setRegionId(rs.getInt("region_id"));
		region.setRegionName(rs.getString("region_name"));
		
		return region;
	}

}
