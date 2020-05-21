package br.com.paulomoreira.startB2W;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.paulomoreira.starB2W.StarB2WApplication;
import br.com.paulomoreira.starB2W.model.Planet;
import br.com.paulomoreira.starB2W.resource.planet.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.util.Converter;
import br.com.paulomoreira.starB2W.util.Validation;

@SpringBootTest(classes = StarB2WApplication.class)
public class ConverterTest {
	
	
	@Autowired
	Converter converter;
	
	@MockBean
	Validation validation;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.converter);
	}
	
	
	@Test
	public void returNull_toPlanetResponse_ifPlanetNull() {
		
		Planet planet = null;
		
		Optional<PlanetResponse> response = converter.toPlanetResponse(planet);
		
		assertEquals(null, response);	
	}
	
	@Test
	public void returnLowerCase_listToLowerCase_isWorking() {
		
		List<String> expects = Arrays.asList("testeum", "teste_dois");
		List<String> upperCase = Arrays.asList("TESTEUM", "TESTE_DOIS");
		upperCase = converter.listToLowerCase(upperCase);
		
		assertEquals(expects, upperCase);
	}
	
	@Test
	public void returnList_climateToList_ifIsWorking() {
		
		String climate = "arid, tropical";
		List<String> expect = Arrays.asList("arid","tropical");
		
		List<String> response = converter.climateToList(climate);
		
		assertEquals(expect, response);
		
	}
	
	@Test
	public void returnList_terrainToList_ifIsWorking() {
		
		String climate = "tundra, ice caves";
		List<String> expect = Arrays.asList("tundra","ice caves");
		
		List<String> response = converter.climateToList(climate);
		
		assertEquals(expect, response);
		
	}
	

}
