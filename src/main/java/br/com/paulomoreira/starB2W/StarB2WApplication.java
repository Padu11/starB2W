package br.com.paulomoreira.starB2W;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@SpringBootApplication
@EnableFeignClients
public class StarB2WApplication {
	
	@Value
	("${spring.data.mongodb.uri}")
	private String mongoURI;
	

	public static void main(String[] args) {
		SpringApplication.run(StarB2WApplication.class, args);
	}

    @Bean
    public Mongock mongock() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURI));
        
        return new SpringMongockBuilder
        		(mongoClient, "starB2W", "br.com.paulomoreira.starB2W.changeSets")
                .setLockQuickConfig()
                .build();
    }


}
