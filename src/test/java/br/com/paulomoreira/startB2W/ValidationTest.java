package br.com.paulomoreira.startB2W;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.paulomoreira.starB2W.StarB2WApplication;
import br.com.paulomoreira.starB2W.model.Climate;
import br.com.paulomoreira.starB2W.resource.repository.ClimateRepository;
import br.com.paulomoreira.starB2W.resource.repository.PlanetRepository;
import br.com.paulomoreira.starB2W.util.Validation;

@SpringBootTest(classes = StarB2WApplication.class)
public class ValidationTest {

	@Autowired
	Validation validation;

	@MockBean
	PlanetRepository planetRepository;

	@MockBean
	ClimateRepository climateRepository;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.validation);
	}

	@Test
	public void returnSucess_climate_ifClimateValid() {

		List<String> climatesString = Arrays.asList("tropical", "arid");
		Climate climateArid = new Climate();
		climateArid.setId("1");
		climateArid.setName("arid");
		Climate climateTropical = new Climate();
		climateTropical.setName("tropical");
		climateTropical.setId("2");

		List<Climate> climatesDataBase = Arrays.asList(climateArid, climateTropical);

		when(climateRepository.findByNameIn(climatesString)).thenReturn(climatesDataBase);

		Boolean response = validation.climate(climatesString);

		assertEquals(true, response);

	}

	@Test
	public void returnSucess_climate_ifclimateInvalid() {

		List<String> climatesString = Arrays.asList("tropicals", "arid");
		Climate climateArid = new Climate();
		climateArid.setId("1");
		climateArid.setName("arid");
		Climate climateTropical = new Climate();
		climateTropical.setName("tropical");
		climateTropical.setId("2");

		List<Climate> climatesDataBase = Arrays.asList(climateArid);

		when(climateRepository.findByNameIn(climatesString)).thenReturn(climatesDataBase);

		Boolean response = validation.climate(climatesString);

		assertEquals(false, response);

	}

}
