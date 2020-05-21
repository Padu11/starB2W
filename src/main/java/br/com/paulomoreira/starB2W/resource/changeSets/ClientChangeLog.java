package br.com.paulomoreira.starB2W.resource.changeSets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

import br.com.paulomoreira.starB2W.model.Role;
import br.com.paulomoreira.starB2W.model.User;

@ChangeLog
public class ClientChangeLog {
	// $2a$10$W.27opWfx9ikvmj/C8EdnOlNI44B4.ZzOZKj5.nwpUoVkgD2OmBXG
	private static final String COLLECTION_CLIMATE = "climate";
	private static final String COLLECTION_TERRAIN = "terrain";
	private static final String COLLECTION_USER = "user";

	private List<Document> createMongoDocumentList(List<String> strs) {
		List<Document> documents = new ArrayList<>();

		for (String str : strs) {
			Document document = new Document();
			document.append("name", str);

			documents.add(document);
		}

		return documents;
	}

	@SuppressWarnings("static-access")
	private Document createMongoDocumentPassword(String user, String pass) throws JsonProcessingException {

		User users = new User();
		
		users.setName("Paulo");
		users.setPass(pass);
		users.setEmail(user);
		Role role = new Role();
		role.setName("user");
		users.setProfiles(Arrays.asList(role));
		
		ObjectMapper map = new ObjectMapper();

		String json = map.writeValueAsString(users);
		
		Document document = new Document();
		
		return document.parse(json);
	}

	@ChangeSet(order = "001", id = "cs-1", author = "Paulo Moreira")
	public void changeSet1(MongoTemplate template) {
		List<String> climates = Arrays.asList("arid", "temperate", "tropical", "frozen", "murky", "windy", "hot",
				"artificial temperate", "frigid", "humid", "moist", "polluted", "superheated", "subartic", "unknown",
				"artic", "rocky");
		template.createCollection(COLLECTION_CLIMATE);
		template.getCollection(COLLECTION_CLIMATE).insertMany(createMongoDocumentList(climates));
	}

	@ChangeSet(order = "002", id = "cs-2", author = "Paulo Moreira")
	public void changeSet2(MongoTemplate template) {
		List<String> terrains = Arrays.asList("acid pools", "airless asteroid", "ash", "barren", "bogs", "canyons",
				"caves", "cities", "cityscape", "cliffs", "desert", "fields", "forests", "fungus forests", "gas giant",
				"glaciers", "grass", "grasslands", "grassy hills", "hills", "ice canyons", "ice caves", "islands",
				"jungle", "jungles", "lakes", "mesas", "mountain ranges", "mountains", "ocean", "oceans", "plains",
				"plains", "plateaus", "rainforests", "reefs", "rivers", "rock", "rock arches", "rocky canyons",
				"rocky deserts", "rocky islands", "savannahs", "savannas", "scrublands", "seas", "swamp", "swamps",
				"toxic cloudsea", "tundra", "unknown", "urban", "valleys", "verdant", "vines", "volcanoes");
		template.createCollection(COLLECTION_TERRAIN);
		template.getCollection(COLLECTION_TERRAIN).insertMany(createMongoDocumentList(terrains));
	}

	@ChangeSet(order = "003", id = "cs-3", author = "Paulo Moreira")
	public void changeSet3(MongoTemplate template) throws JsonProcessingException {

		template.createCollection(COLLECTION_USER);
		template.getCollection(COLLECTION_USER).insertOne(createMongoDocumentPassword("eng.paulomoreira@gmail.com",
				"$2a$10$Zn3vwKalxYlJBPSxZ0VFf.xMluB8FQULHiAsL1v./eIYfpFLbXNZm"));
	}

}