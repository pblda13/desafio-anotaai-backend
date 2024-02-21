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


    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    private String category;

    public Product(ProductDTO data) {
        this.title = data.title();
        this.description = data.description();
        this.ownerId = data.ownerId();
        this.price = data.price();
        this.category = data.categoryId();
    }

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
}
