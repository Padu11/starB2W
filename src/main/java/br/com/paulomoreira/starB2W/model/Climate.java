package br.com.paulomoreira.starB2W.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Climate {
	
	@Id
	private String id;
	
	@NotNull
	private String name;

}
