package com.example.exam.service;

import com.example.exam.model.entity.Category;
import com.example.exam.model.entity.CategoryNameEnum;

public interface CategoryService {
    void initCategories();

    Category findByCategoryNameEnum(CategoryNameEnum categoryNameEnum);
}
