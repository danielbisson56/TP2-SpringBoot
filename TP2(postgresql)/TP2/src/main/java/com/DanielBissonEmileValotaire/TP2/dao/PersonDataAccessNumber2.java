package com.DanielBissonEmileValotaire.TP2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.DanielBissonEmileValotaire.TP2.datasource.PersonMapper;
import com.DanielBissonEmileValotaire.TP2.model.Person;

@Repository("implementationVoulue2")
public class PersonDataAccessNumber2 implements PersonDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PersonDataAccessNumber2(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int insertPerson(UUID id, Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertPerson(Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Person> selectAllPeople() throws SQLException{
		final String sql = "SELECT id, name FROM person;";
		List<Person> people = jdbcTemplate.query(
				sql, 
				(resultSet) -> {
					UUID id = UUID.fromString(resultSet.getString("id"));
					String name = resultSet.getString("name");
					return new Person(id,name);
					
				});
		return people;
	}

	@Override
	public int deletePersonById(UUID id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

}
