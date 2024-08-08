package com.soyhenry.expenseApp.service;

import com.soyhenry.expenseApp.dto.ExpenseRequestDto;
import com.soyhenry.expenseApp.dto.ExpenseResponseDto;
import com.soyhenry.expenseApp.exceptions.DAOException;

import java.util.List;

public interface ExpenseService {

    String createExpense(ExpenseRequestDto expenseRequestDto) throws DAOException;
    List<ExpenseResponseDto> getAllExpenses() throws DAOException;
    ExpenseResponseDto getExpenseById(Long id) throws DAOException;
    void deleteExpense(Long id) throws DAOException;
    String updateExpense(Long id, ExpenseRequestDto expenseRequestDto) throws DAOException;

}
