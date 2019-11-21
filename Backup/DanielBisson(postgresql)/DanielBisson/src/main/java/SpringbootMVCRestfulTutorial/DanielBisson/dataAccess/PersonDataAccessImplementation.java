package SpringbootMVCRestfulTutorial.DanielBisson.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import SpringbootMVCRestfulTutorial.DanielBisson.model.Person;

@Repository("implementationVoulu")
public class PersonDataAccessImplementation implements PersonDataAccessInterface {

	private static List<Person> DB = new ArrayList<>();
	
	//Ajouter une personne qui a son id.
	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	//Ajouter une personne qui n'a pas d'id. Il sera auto-généré.
	@Override
	public int insertPerson(Person person) {
		UUID id = UUID.randomUUID();
		return insertPerson(id,person);
	}

	@Override
	public List<Person> selectAllPeople() {		
		return DB;
	}
	
	@Override
	public int deletePersonById(UUID id) {
		Optional <Person> personMaybe = selectPersonById(id);
		if(!personMaybe.isPresent()) {
			return 0;
		}
		DB.remove(personMaybe.get());
		return 0;
	}

	@Override
	public int updatePersonById(UUID id, Person update) {
		return selectPersonById(id)
				.map(person -> {
					int indexOfPersonToUpdate = DB.indexOf(person);
					if(indexOfPersonToUpdate >= 0) {
						DB.set(indexOfPersonToUpdate, new Person(id,update.getName()));
						return 1;
					}
					return 0;
				})
				.orElse(0);
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		//retourner la première personne trouvée avec l'id fourni.
		return DB.stream().filter(person-> person.getId().equals(id)).findFirst();
	}
}
