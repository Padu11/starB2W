package br.com.paulomoreira.starB2W.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
@Entity
public class Planet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name = "name", unique = true, nullable = false)
	@NotNull(message = "The name is required.")
	@Valid
	private String name;
	
	@Column(name = "climate", nullable = false)
	@NotNull(message = "The climate is required.")
	@Valid
	private String climate;
	
	@Column(name = "ground", nullable = false)
	@NotNull(message = "The ground is required.")
	@Valid
	private String ground;
	

}
