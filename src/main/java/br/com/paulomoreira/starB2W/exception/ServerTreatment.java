package br.com.paulomoreira.starB2W.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.paulomoreira.starB2W.util.Constants;

public class ServerTreatment {
	
	
	public static ResponseEntity<?> response() {
		String bodyError = Constants.ERROR;
		
		ResponseEntity<?> error = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyError);
	
		return error;
	
	}

}