package br.com.paulomoreira.starB2W.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.paulomoreira.starB2W.dto.ErrorType;
import br.com.paulomoreira.starB2W.dto.ResponseDTO;
import br.com.paulomoreira.starB2W.util.Constants;

public class ServerTreatment {
	
	
	public static ResponseEntity<ResponseDTO> response() {
		ResponseDTO bodyError = ResponseDTO.responseGenerator(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				Arrays.asList(new ErrorType(Constants.ERROR)));
		
		ResponseEntity<ResponseDTO> error = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyError);
	
		return error;
	
	}

}