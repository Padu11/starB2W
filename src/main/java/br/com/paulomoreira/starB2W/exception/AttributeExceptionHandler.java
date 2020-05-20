package br.com.paulomoreira.starB2W.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.HashMap;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.paulomoreira.starB2W.util.Constants;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AttributeExceptionHandler {

	@ResponseStatus(BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(AttributeException.class)
	public ResponseEntity<?> AttributeExceptionException(AttributeException ex) {
		
		HashMap<String, String> message = new HashMap<>();
		
		message.put(Constants.MESSAGE, ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(message);
	}


}