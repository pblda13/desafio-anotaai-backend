package com.pamela.desafioanotaai.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    // Configura e fornece uma fábrica de banco de dados do MongoDB

    @Bean
    public MongoDatabaseFactory mongoConfigure(){
        return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/product-catalog");
    }

    // Configura e fornece um template do MongoDB para interagir com o banco de dados

    @Bean
    public MongoTemplate mongoTemplate(){
        return  new MongoTemplate(mongoConfigure());
    }
}
