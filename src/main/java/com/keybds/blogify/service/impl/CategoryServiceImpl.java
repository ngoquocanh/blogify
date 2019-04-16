package com.keybds.blogify.service.impl;

import com.keybds.blogify.exceptions.MvcException;
import com.keybds.blogify.model.Category;
import com.keybds.blogify.repository.CategoryRepository;
import com.keybds.blogify.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl extends AbstractService implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        if (isPaged(pageable)) {
            return categoryRepository.findAll(pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public void deleteCategories(List<Long> categoryIds) throws MvcException {
        for (Long categoryId : categoryIds) {
            if (categoryRepository.existsById(categoryId)) {
                categoryRepository.deleteById(categoryId);
            }
        }
    }
}
