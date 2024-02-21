package com.pamela.desafioanotaai.domain.product;

import com.pamela.desafioanotaai.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    // Identificador único do produto

    @Id
    private String id;

    // Título do produto

    private String title;

    // Descrição do produto

    private String description;

    // Identificador do proprietário do produto

    private String ownerId;

    // Preço do produto

    private Integer price;

    // Categoria do produto

    private String category;

    // Construtor para criar uma instância de produto a partir de um DTO

    public Product(ProductDTO data) {
        this.title = data.title();
        this.description = data.description();
        this.ownerId = data.ownerId();
        this.price = data.price();
        this.category = data.categoryId();
    }

    // Método para gerar uma representação em string do produto

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("ownerID", this.ownerId);
        json.put("id", this.id);
        json.put("categoryId", this.category);
        json.put("price", this.price);
        json.put("type", "product");

        return json.toString();
    }

    // Método para gerar uma representação em string da exclusão do produto

    public String deleteToString(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("ownerId", this.ownerId);
        json.put("type", "delete-produto");

        return json.toString();
    }
}
