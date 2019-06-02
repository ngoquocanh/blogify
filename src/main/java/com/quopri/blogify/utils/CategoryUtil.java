package com.quopri.blogify.utils;

import com.quopri.blogify.dto.CategoryDTO;
import com.quopri.blogify.entity.Category;

public class CategoryUtil {

    public static CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setValue(category.getValue());
        return categoryDTO;
    }

    public static Category convertToEntity(CategoryDTO categoryDTO, boolean isNew) {
        Category category = new Category();
        if (!isNew) {
            category.setId(categoryDTO.getId());
        }
        category.setValue(categoryDTO.getValue());
        return category;
    }
}
