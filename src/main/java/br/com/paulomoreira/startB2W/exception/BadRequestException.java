package br.com.paulomoreira.startB2W.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 7002051671535440028L;

	public BadRequestException() {
	}

	public BadRequestException(String message) {
		super(message);
	}

}