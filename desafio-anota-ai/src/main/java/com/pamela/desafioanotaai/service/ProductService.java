package com.pamela.desafioanotaai.service;

import com.pamela.desafioanotaai.domain.product.ProductDTO;
import com.pamela.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.pamela.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.pamela.desafioanotaai.domain.product.Product;
import com.pamela.desafioanotaai.repositories.ProductRepository;
import com.pamela.desafioanotaai.service.aws.AwsSnsService;
import com.pamela.desafioanotaai.service.aws.messageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    // Serviço para manipulação de categorias

    private final CategoryService categoryService;

    // Repositório para interagir com o banco de dados de produtos

    private final ProductRepository repository;

    // Serviço para lidar com comunicações SNS (Simple Notification Service) da AWS

    private final AwsSnsService snsService;

    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService snsService) {
        this.categoryService = categoryService;
        this.repository = productRepository;
        this.snsService = snsService;
    }

    // Método para inserir um novo produto

    public Product insert(ProductDTO productData){
        this.categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);

        this.repository.save(newProduct);

        this.snsService.publish(new messageDTO(newProduct.toString()));

        return newProduct;
    }

    // Método para obter todos os produtos

    public List<Product> getAll() {
        return this.repository.findAll();
    }

    // Método para atualizar um produto existente

    public Product update(String id, ProductDTO productData){
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId())
                    .orElseThrow(ProductNotFoundException::new);
            product.setCategory(productData.categoryId());
        }
        if(!productData.title().isEmpty()) product.setTitle(productData.title());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());

        this.repository.save(product);
        this.snsService.publish(new messageDTO(product.toString()));

        return product;
    }

    // Método para deletar um produto existente

    public void delete(String id){
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.repository.delete(product);
        this.snsService.publish(new messageDTO(product.deleteToString()));
    }
}
