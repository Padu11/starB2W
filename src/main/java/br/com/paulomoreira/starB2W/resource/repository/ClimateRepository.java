package br.com.paulomoreira.starB2W.resource.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.paulomoreira.starB2W.model.Climate;

public interface ClimateRepository extends MongoRepository<Climate, String> {

	List<Climate> findByNameIn(List<String> climates);

}
