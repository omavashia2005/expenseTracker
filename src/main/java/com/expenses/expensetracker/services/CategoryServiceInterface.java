package com.expenses.expensetracker.services;

import com.expenses.expensetracker.Exceptions.EtBadCategoryRequestException;
import com.expenses.expensetracker.Exceptions.EtCategoryNotFoundException;
import com.expenses.expensetracker.domain.Category;

import java.util.List;

public interface CategoryServiceInterface
{
    List<Category> fetchAllCategories(Integer userID);

    Category fetchCategoryByID(Integer userID, Integer categoryID) throws EtCategoryNotFoundException;

    Category addCategory(Integer userID, String title, String description) throws EtBadCategoryRequestException;

    void updateCategory(Integer userID, Integer categoryID, Category category) throws EtBadCategoryRequestException;

    void removeCategoryWithAllTransactions(Integer userID, Integer categoryID) throws EtCategoryNotFoundException;
}
