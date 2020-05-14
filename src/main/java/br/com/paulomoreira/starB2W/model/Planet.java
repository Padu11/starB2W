package br.com.paulomoreira.starB2W.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
@Entity
public class Planet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name = "name")
	@NotNull(message = "The name is required.")
	private String name;
	
	@Column(name = "climate")
	@NotNull(message = "The climate is required.")
	private String climate;
	
	@Column(name = "ground")
	@NotNull(message = "The ground is required.")
	private String ground;
	

}
