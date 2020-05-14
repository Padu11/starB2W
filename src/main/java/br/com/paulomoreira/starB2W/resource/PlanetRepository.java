package br.com.paulomoreira.starB2W.resource;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.paulomoreira.starB2W.model.Planet;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, Long> {

	Optional<Planet> deleteById();

	Optional<Planet> findByName(String name);

	Optional<Planet> findById(String id);

}
