package br.com.paulomoreira.starB2W.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7057856486637859540L;

	private int statusCode;

	private String message;
	
	private Boolean success;

	private List<ErrorType> messages;

	private Object result;

	public ResponseDTO(int statusCode, Object resultado) {
		super();
		this.statusCode = statusCode;
		this.result = resultado;
		this.success = true;
	}

	public ResponseDTO(int statusCode, List<ErrorType> messages) {
		super();
		this.statusCode = statusCode;
		addMessages(messages);
		this.messages = messages;
		this.success = false;
	}
	

	private void addMessages(List<ErrorType> messages) {
		if (Objects.isNull(this.messages)) {
			this.messages = new ArrayList<>();
		}
		this.messages.addAll(messages);
	}
	
	public static ResponseDTO responseGenerator(int statusCode, List<ErrorType> messages) {
		return new ResponseDTO(statusCode, messages);
	}

	public static ResponseDTO responseGenerator(int statusCode, Object resultado) {
		return new ResponseDTO(statusCode, resultado);
	}



}
