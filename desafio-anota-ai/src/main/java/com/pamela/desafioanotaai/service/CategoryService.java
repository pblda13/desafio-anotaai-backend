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

    private CategoryRepository repository;
    private final AwsSnsService snsService;

    public CategoryService(CategoryRepository repository,AwsSnsService snsService) {
        this.repository = repository;
        this.snsService = snsService;
    }

    public Category insert(CategoryDTO categoryData) {
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        this.snsService.publish(new messageDTO(newCategory.toString()));
        return newCategory;

    }

    public List<Category> getAll(){
        return this.repository.findAll();
    }
    public Optional<Category> getById(String id){
        return this.repository.findById(id);
    }



    public Category update(String id, CategoryDTO categoryData){
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());


        this.repository.save(category);

        this.snsService.publish(new messageDTO(category.toString()));

        return category;
    }

    public void delete(String id) {

        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.repository.delete(category);

    }


}
