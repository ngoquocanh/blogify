package com.keybds.blogify.service;

import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Page<Category> getAllCategories(Pageable pageable);
    void deleteCategories(List<Long> categoryIds) throws MvcException;
}
