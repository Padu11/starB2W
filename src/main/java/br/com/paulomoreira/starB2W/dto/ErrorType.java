package br.com.paulomoreira.starB2W.dto;

import lombok.Data;

@Data
public class ErrorType {
	

	private String message;
	
	public ErrorType(String message) {
		this.message = message;
	}

	
}
