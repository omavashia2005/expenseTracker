package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.domain.Category;

import java.util.List;

public interface CategoryServiceInterface
{
    List<Category> fetchAllCategories(Integer userID);

    Category fetchCategoryByID(Integer userID, Integer categoryID) throws EtResourceNotFoundException;

    Category addCategory(Integer userID, String title, String description) throws EtBadRequestException;

    void updateCategory(Integer userID, Integer categoryID, Category category) throws EtBadRequestException;

    void removeCategoryWithAllTransactions(Integer userID, Integer categoryID) throws EtResourceNotFoundException;
}
