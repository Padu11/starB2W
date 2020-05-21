package br.com.paulomoreira.starB2W.exception;

import lombok.Data;

@Data
public class ErrorType {
	

	private String message;
	
	public ErrorType(String message) {
		this.message = message;
	}

	
}
