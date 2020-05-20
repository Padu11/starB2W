package br.com.paulomoreira.starB2W.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.paulomoreira.starB2W.util.Constants;

public class ServerTreatment {

	public static ResponseEntity<?> response() {

		HashMap<String, String> body = new HashMap<>();

		body.put(Constants.MESSAGE, Constants.ERROR);

		ResponseEntity<?> error = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);

		return error;

	}

}