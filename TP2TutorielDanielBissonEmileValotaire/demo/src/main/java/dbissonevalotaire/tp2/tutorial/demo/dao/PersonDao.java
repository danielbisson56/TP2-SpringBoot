package dbissonevalotaire.tp2.tutorial.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import dbissonevalotaire.tp2.tutorial.demo.model.Person;

public interface PersonDao {

	//Insérer une personne qui a un id.(POST)
	int insertPerson(UUID id, Person person);
	//Insérer une personne qui n'a pas d'id. Il sera auto généré.(POST)
	int insertPerson(Person person);
	//Pour ressortir le contenu de la base de donnée.(GET)
	List<Person> selectAllPeople();
	//Pour enlever une personne (Delete)
	int deletePersonById(UUID id);
	//Pour faire l'update sur une personne.(UPDATE)
	int updatePersonById(UUID id, Person person);
	
	Optional<Person> selectPersonById(UUID id);
}
