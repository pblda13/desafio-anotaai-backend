package com.pamela.desafioanotaai.service;

import com.pamela.desafioanotaai.domain.category.Category;
import com.pamela.desafioanotaai.domain.category.CategoryDTO;
import com.pamela.desafioanotaai.repositories.CategoryRepository;
import com.pamela.desafioanotaai.service.aws.AwsSnsService;
import com.pamela.desafioanotaai.service.aws.messageDTO;
import org.springframework.stereotype.Service;
import com.pamela.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    // Repositório para interagir com o banco de dados de categorias

    private CategoryRepository repository;

    // Serviço para lidar com comunicações SNS (Simple Notification Service) da AWS

    private final AwsSnsService snsService;

    public CategoryService(CategoryRepository repository, AwsSnsService snsService) {
        this.repository = repository;
        this.snsService = snsService;
    }

    // Método para inserir uma nova categoria

    public Category insert(CategoryDTO categoryData) {
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        this.snsService.publish(new messageDTO(newCategory.toString()));
        return newCategory;
    }

    // Método para obter todas as categorias

    public List<Category> getAll(){
        return this.repository.findAll();
    }

    // Método para obter uma categoria por ID

    public Optional<Category> getById(String id){
        return this.repository.findById(id);
    }

    // Método para atualizar uma categoria existente

    public Category update(String id, CategoryDTO categoryData){
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());

        this.repository.save(category);

        this.snsService.publish(new messageDTO(category.toString()));

        return category;
    }

    // Método para deletar uma categoria existente

    public void delete(String id) {

        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.repository.delete(category);
        this.snsService.publish(new messageDTO(category.deleteToString()));
    }
}
