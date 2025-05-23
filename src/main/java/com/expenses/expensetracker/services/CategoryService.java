package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.Repositories.CategoryRepository;
import com.expenses.expensetracker.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService implements CategoryServiceInterface
{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(Integer userID) {
        return categoryRepository.findAll(userID);
    }

    @Override
    public Category fetchCategoryByID(Integer userID, Integer categoryID) throws EtResourceNotFoundException {
        return categoryRepository.findByID(userID, categoryID);

    }

    @Override
    public Category addCategory(Integer userID, String title, String description) throws EtBadRequestException {
        int categoryID = categoryRepository.create(userID, title, description);
        return categoryRepository.findByID(userID, categoryID);
    }

    @Override
    public void updateCategory(Integer userID, Integer categoryID, Category category) throws EtBadRequestException {
        categoryRepository.update(userID, categoryID, category);
    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userID, Integer categoryID) throws EtResourceNotFoundException {

    }
}
