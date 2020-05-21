package br.com.paulomoreira.starB2W.resource.auth;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.paulomoreira.starB2W.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String username);

}
