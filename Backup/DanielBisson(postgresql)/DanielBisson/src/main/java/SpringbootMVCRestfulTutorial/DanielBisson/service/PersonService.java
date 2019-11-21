package SpringbootMVCRestfulTutorial.DanielBisson.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import SpringbootMVCRestfulTutorial.DanielBisson.dataAccess.PersonDataAccessInterface;
import SpringbootMVCRestfulTutorial.DanielBisson.model.Person;

@Service
public class PersonService {

	private final PersonDataAccessInterface personDao;

	@Autowired
	public PersonService(@Qualifier("nouvelleImplementation") PersonDataAccessInterface personDao) {
		this.personDao = personDao;
	}
	
	public int addPerson(Person person) {
		return personDao.insertPerson(person);
	}
	
	public List<Person> getAllPeople(){
		return personDao.selectAllPeople();
	}	
	
	public int deletePerson(UUID id) {
		return personDao.deletePersonById(id);
	}
	
	public int updatePerson(UUID id, Person newPerson) {
		return personDao.updatePersonById(id, newPerson);
	}
	
	public Optional<Person> getPersonById(UUID id){
		return personDao.selectPersonById(id);
	}
}
