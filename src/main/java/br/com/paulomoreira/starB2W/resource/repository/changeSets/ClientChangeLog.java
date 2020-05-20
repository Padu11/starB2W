package br.com.paulomoreira.starB2W.resource.repository.changeSets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

@ChangeLog
public class ClientChangeLog {

	private static final String COLLECTION_CLIMATE = "climate";
	private static final String COLLECTION_TERRAIN= "terrain";

	private List<Document> createMongoDocumentList(List<String> strs) {
		List<Document> documents = new ArrayList<>();

		for (String str : strs) {
			Document document = new Document();
			document.append("name", str);

			documents.add(document);
		}

		return documents;
	}

	@ChangeSet(order = "001", id = "cs-1", author = "mongock")
	public void changeSet1(MongoTemplate template) {
		List<String> climates = Arrays.asList("arid", "temperate", "tropical", "frozen", "murky", "windy", "hot",
				"artificial temperate", "frigid", "humid", "moist", "polluted", "superheated", "subartic", "unknown",
				"artic", "rocky");
		template.createCollection(COLLECTION_CLIMATE);
		template.getCollection(COLLECTION_CLIMATE).insertMany(createMongoDocumentList(climates));
	}
	
	@ChangeSet(order = "002", id = "cs-2", author = "mongock")
	public void changeSet2(MongoTemplate template) {
		List<String> terrains = Arrays.asList
				("acid pools","airless asteroid","ash","barren","bogs","canyons",
						"caves","cities","cityscape","cliffs","desert","fields",
						"forests","fungus forests","gas giant","glaciers","grass",
						"grasslands","grassy hills","hills","ice canyons",
						"ice caves","islands","jungle","jungles",
						"lakes","mesas","mountain ranges","mountains",
						"ocean","oceans","plains","plains",
						"plateaus","rainforests","reefs","rivers","rock",
						"rock arches","rocky canyons","rocky deserts",
						"rocky islands","savannahs","savannas","scrublands",
						"seas","swamp","swamps","toxic cloudsea","tundra",
						"unknown","urban","valleys","verdant",
						"vines","volcanoes");
		template.createCollection(COLLECTION_TERRAIN);
		template.getCollection(COLLECTION_TERRAIN).insertMany(createMongoDocumentList(terrains));
	}
	

}