package br.com.paulomoreira.starB2W.gateway.dto;

import java.util.List;

import lombok.Data;

@Data
public class SwapiResultResponse {
	
	private String name;
	
	private String climate;
	
	private String terrain;
	
	private List<String> films;

}
