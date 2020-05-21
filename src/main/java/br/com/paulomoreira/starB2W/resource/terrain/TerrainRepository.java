package br.com.paulomoreira.starB2W.resource.terrain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.paulomoreira.starB2W.model.Climate;
import br.com.paulomoreira.starB2W.model.Terrain;

public interface TerrainRepository extends MongoRepository<Terrain, String> {

	List<Climate> findByNameIn(List<String> terrains);

}
