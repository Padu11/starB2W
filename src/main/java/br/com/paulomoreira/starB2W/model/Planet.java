package br.com.paulomoreira.starB2W.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
@Entity
public class Planet {
	
	@Id
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private List<String> climates;
	
	@NotNull
	private List<String> terrains;
	
	@NotNull
	private String movieAppearances;
	
	
	

}
