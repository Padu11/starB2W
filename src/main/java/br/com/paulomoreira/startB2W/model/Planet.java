package br.com.paulomoreira.startB2W.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class Planet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String uuid = UUID.randomUUID().toString();
	
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
