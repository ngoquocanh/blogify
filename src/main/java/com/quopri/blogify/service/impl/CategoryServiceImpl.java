package com.quopri.blogify.service.impl;

import com.quopri.blogify.enums.StatusMessageCode;
import com.quopri.blogify.exceptions.MvcException;
import com.quopri.blogify.entity.ArticleCategory;
import com.quopri.blogify.entity.Category;
import com.quopri.blogify.repository.ArticleCategoryRepository;
import com.quopri.blogify.repository.CategoryRepository;
import com.quopri.blogify.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl extends AbstractService implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

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
                List<ArticleCategory> articleCategoryList = articleCategoryRepository.findByCategoryId(categoryId);
                if (articleCategoryList != null) {
                    articleCategoryRepository.deleteAll(articleCategoryList);
                }
                categoryRepository.deleteById(categoryId);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategory(Long categoryId) throws MvcException {
        Category category;
        if (categoryRepository.existsById(categoryId)) {
            Optional<Category> categoryExisted = categoryRepository.findById(categoryId);
            if (categoryExisted.isPresent()) {
                category = categoryExisted.get();
            } else {
                throw new MvcException(StatusMessageCode.CATEGORY_NOT_FOUND);
            }
        } else {
            throw new MvcException(StatusMessageCode.CATEGORY_NOT_FOUND);
        }
        return category;
    }

    @Override
    public Category updateCategory(Category category) throws MvcException {
        Category categoryUpdated;
        if (categoryRepository.existsById(category.getId())) {
            Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
            if (optionalCategory.isPresent()) {
                Category categoryToUpdate = new Category();
                categoryToUpdate.setId(optionalCategory.get().getId());
                categoryToUpdate.setValue(category.getValue());
                categoryUpdated = categoryRepository.save(categoryToUpdate);
            } else {
                throw new MvcException(StatusMessageCode.CATEGORY_NOT_FOUND);
            }
        } else {
            throw new MvcException(StatusMessageCode.CATEGORY_NOT_FOUND);
        }
        return categoryUpdated;
    }

    @Override
    public Category createCategory(Category category) throws MvcException {
        Category categoryToCreate = new Category();
        categoryToCreate.setValue(category.getValue());
        return categoryRepository.save(categoryToCreate);
    }
}
