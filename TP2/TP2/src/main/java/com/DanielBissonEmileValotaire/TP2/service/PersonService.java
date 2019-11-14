package com.DanielBissonEmileValotaire.TP2.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.DanielBissonEmileValotaire.TP2.dao.PersonDao;
import com.DanielBissonEmileValotaire.TP2.model.Person;

@Service
public class PersonService {
	
	private final PersonDao personDao;
	
	@Autowired
	public PersonService(@Qualifier("implementationVoulue2") PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public int addPerson(Person person) {
		return personDao.insertPerson(person);
	}
	
	public List<Person> getAllPeople() throws SQLException{
		return personDao.selectAllPeople();
	}
	
	public Optional<Person> getPersonById(UUID id){
		return personDao.selectPersonById(id);
	}
	
	public int deletePerson(UUID id) {
		return personDao.deletePersonById(id);
	}
	
	public int updatePerson(UUID id, Person newPerson) {
		return personDao.updatePersonById(id, newPerson);
	}
}
