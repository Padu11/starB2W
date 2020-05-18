package br.com.paulomoreira.starB2W.model;

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
	private String climate;
	
	@NotNull
	private String terrain;
	
	@NotNull
	private String movieAppearances;
	
	
	

}
