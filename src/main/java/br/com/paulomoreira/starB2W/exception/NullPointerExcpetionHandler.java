package br.com.paulomoreira.starB2W.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.HashMap;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.paulomoreira.starB2W.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class NullPointerExcpetionHandler {

	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ResponseBody
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> methodArgumentNotValidException(NullPointerException ex) {
		log.error(ex.getMessage());

		HashMap<String, String> body = new HashMap<>();

		body.put(Constants.MESSAGE, Constants.ERROR);

		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(body);
	}

}
