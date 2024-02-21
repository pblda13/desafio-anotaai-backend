package com.pamela.desafioanotaai.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    // Identificador único da categoria

    @Id
    private String id;

    // Título da categoria

    private String title;

    // Descrição da categoria

    private String description;

    // Identificador do proprietário da categoria

    private String ownerId;

    // Construtor para criar uma instância de categoria a partir de um DTO

    public  Category(CategoryDTO categoryDTO ){
        this.title = categoryDTO.title();
        this.description = categoryDTO.description();
        this.ownerId = categoryDTO.ownerId();
    }

    // Método para gerar uma representação em string da categoria

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("ownerID", this.ownerId);
        json.put("id", this.id);
        json.put("type", "category");

        return json.toString();
    }

    // Método para gerar uma representação em string da exclusão da categoria

    public String deleteToString(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("ownerId", this.ownerId);
        json.put("type", "delete-categoria");

        return json.toString();
    }
}
