package br.com.paulomoreira.starB2W.gateway.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SwapiResponse {

	@JsonProperty(value = "results")
	List<SwapiResultResponse> results;
}
