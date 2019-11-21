package SpringbootMVCRestfulTutorial.DanielBisson.model;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

public class Person {

	private final UUID id;
	@NotBlank
	private final String name;
	
	public Person(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}	
}