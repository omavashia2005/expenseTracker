package com.expenses.expensetracker.Repositories;

import com.expenses.expensetracker.Exceptions.EtBadCategoryRequestException;
import com.expenses.expensetracker.Exceptions.EtCategoryNotFoundException;
import com.expenses.expensetracker.domain.Category;

import java.util.List;

public interface CategoryRepositoryInterface
{
    List<Category> findAll(Integer userID) throws EtCategoryNotFoundException;
    Category findByID(Integer userID, Integer categoryID) throws EtCategoryNotFoundException;
    Integer create(Integer userID, String title, String description) throws EtBadCategoryRequestException;
    void update(Integer userID, Integer categoryID, Category category) throws EtBadCategoryRequestException;
    void renoveByID(Integer userID, Integer categoryID);

}
