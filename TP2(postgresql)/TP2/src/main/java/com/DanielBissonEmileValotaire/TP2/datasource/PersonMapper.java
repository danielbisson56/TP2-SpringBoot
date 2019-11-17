package com.DanielBissonEmileValotaire.TP2.datasource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.DanielBissonEmileValotaire.TP2.model.Person;

public class PersonMapper implements RowMapper<Person> {
	
   public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
      Person person = new Person(UUID.fromString(rs.getString("id")), rs.getString("name"));      
      return person;
   }
}