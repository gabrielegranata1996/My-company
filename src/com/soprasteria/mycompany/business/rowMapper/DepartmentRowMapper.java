package com.soprasteria.mycompany.business.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.soprasteria.mycompany.entity.Departement;
import com.soprasteria.mycompany.entity.Employee;
import com.soprasteria.mycompany.entity.Location;

@SuppressWarnings("rawtypes")
public class DepartmentRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Departement dep = new Departement();

		Employee manager = new Employee();
		Location location = new Location();

		dep.setDepartementId(rs.getInt("department_id"));
		dep.setDepartementName(rs.getString("department_name"));

		manager.setEmployeeId(rs.getInt("manager_id"));
		dep.setManager(manager);
		location.setLocationId(rs.getInt("location_id"));
		dep.setLocation(location);

		return dep;
	}

}
