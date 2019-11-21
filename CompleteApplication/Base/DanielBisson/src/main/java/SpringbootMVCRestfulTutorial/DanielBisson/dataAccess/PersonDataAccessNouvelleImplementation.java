package SpringbootMVCRestfulTutorial.DanielBisson.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import SpringbootMVCRestfulTutorial.DanielBisson.model.Person;

@Repository("nouvelleImplementation")
public class PersonDataAccessNouvelleImplementation implements PersonDataAccessInterface {

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
	public List<Person> selectAllPeople() {
		List<Person> people = new ArrayList<Person>();
		
		people.add(new Person(UUID.randomUUID(),"Ceci est ma nouvelle implémentation et ça fonctionne!"));
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
