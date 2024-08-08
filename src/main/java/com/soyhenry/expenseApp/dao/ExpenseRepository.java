package com.soyhenry.expenseApp.dao;


import com.soyhenry.expenseApp.entities.Expense;
import com.soyhenry.expenseApp.exceptions.DAOException;

import java.util.List;

public interface ExpenseRepository {

    Integer create(Expense expense) throws DAOException;
    List<Expense> getAllExpenses() throws DAOException;
    Expense getExpenseById(Long id) throws DAOException;
    int update(Long id, Expense expense) throws DAOException;
    int delete(Long id) throws DAOException;


}
