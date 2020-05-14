package br.com.paulomoreira.starB2W.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.paulomoreira.starB2W.dto.PlanetResponse;
import br.com.paulomoreira.starB2W.dto.ResponseDTO;

public class Validation {

	public static ResponseEntity<ResponseDTO> planetResponse(Optional<PlanetResponse> planetResponse,
			ResponseDTO bodyIfNull, ResponseDTO bodyIfNotNull) {

		if (planetResponse == null) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyIfNull);
		}

		return ResponseEntity.ok(bodyIfNotNull);

	}

}
