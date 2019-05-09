package com.quopri.blogify.service;

import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Page<Category> getAllCategories(Pageable pageable);
    void deleteCategories(List<Long> categoryIds) throws MvcException;
    Category getCategory(Long categoryId) throws MvcException;
    Category updateCategory(Category category) throws MvcException;
    Category createCategory(Category category) throws MvcException;
}
