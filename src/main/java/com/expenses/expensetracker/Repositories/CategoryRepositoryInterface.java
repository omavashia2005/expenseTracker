package com.expenses.expensetracker.Repositories;

import com.expenses.expensetracker.Exceptions.EtBadRequestException;
import com.expenses.expensetracker.Exceptions.EtResourceNotFoundException;
import com.expenses.expensetracker.domain.Category;

import java.util.List;

public interface CategoryRepositoryInterface
{
    List<Category> findAll(Integer userID) throws EtResourceNotFoundException;
    Category findByID(Integer userID, Integer categoryID) throws EtResourceNotFoundException;
    Integer create(Integer userID, String title, String description) throws EtBadRequestException;
    void update(Integer userID, Integer categoryID, Category category) throws EtBadRequestException;
    void renoveByID(Integer userID, Integer categoryID);

}
