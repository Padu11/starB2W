package br.com.paulomoreira.starB2W.util;

import org.springframework.stereotype.Component;

@Component
public final class Constants {
	
	public static final String PLANET_NOT_CREATED = 
			"It was not possible to create the new planet.";
	
	public static final String PLANET_NOT_FOUND = "Planet not found.";

	public static final String FIND_ALL_NOT_POSSIBLE = "The list could not be fetched.";

	public static final String NOT_POSSIBLE_DELETE_PLANET = 
			"It was not possible to delete the planet.";

	public static final String ERROR = "We were unable to process your request. Try again later.";

	public static final String PLANET_DELETED = "Planet Deleted.";

	public static final String WHITE_SPACE = " ";

	public static final String FIELD = "Field ";

	public static final String PLANET_EXIST = "This planet has already been created.";

	public static final Boolean FALSE = false;
	
	public static final Boolean TRUE = true;

	public static final Long ONE = 1L;

	public static final Integer ZERO = 0;

	public static final String MOVIE_APPEARANCES_NOT_FOUND = 
			"It was not possible to identify the number of appearances.";

	public static final String ERROR_FIND = "It was not possible to perform the search with described sort and page parameters.";

	public static final String MESSAGE = "MESSAGE:";

	public static final String LIST_PLANETS_EMPTY = "There are no registered planets.";


	
}
