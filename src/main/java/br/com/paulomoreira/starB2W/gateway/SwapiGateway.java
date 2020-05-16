package br.com.paulomoreira.starB2W.gateway;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.paulomoreira.starB2W.gateway.dto.SwapiResponse;

@FeignClient(value = "swapi-gateway", url = "https://swapi.dev/api/")
public interface SwapiGateway {
	
	
	@GetMapping(path= "planets/", produces = APPLICATION_JSON_VALUE)
	public SwapiResponse findPlanetByName(@RequestParam(value = "search") String planet);

}
