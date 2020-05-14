package br.com.paulomoreira.startB2W;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.paulomoreira.startB2W.model.Planet;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, Long> {

	Optional<Planet> findAllByName(String name);

	Optional<Planet> deleteByUuid();

}
