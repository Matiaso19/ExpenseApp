package com.soyhenry.expenseApp.service.impl;

import com.soyhenry.expenseApp.dao.ExpenseRepository;
import com.soyhenry.expenseApp.dto.ExpenseRequestDto;
import com.soyhenry.expenseApp.dto.ExpenseResponseDto;
import com.soyhenry.expenseApp.entities.Expense;
import com.soyhenry.expenseApp.exceptions.DAOException;
import com.soyhenry.expenseApp.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {


    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public String createExpense(ExpenseRequestDto expenseRequestDto) throws DAOException {
        String response = "Se registro el gasto con exito";

        Expense expense = mapDtoToExpense(expenseRequestDto);
        Integer responseInserted = expenseRepository.create(expense);
        if(responseInserted.equals(0)){
            System.out.println("No se inserto ningun registro");
        }
        return response;
    }

    @Override
    public List<ExpenseResponseDto> getAllExpenses() throws DAOException {
        List<Expense> expenses = expenseRepository.getAllExpenses();
        return expenses.stream()
                .map(this::mapExpenseToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseResponseDto getExpenseById(Long id) throws DAOException {
        Expense expense = expenseRepository.getExpenseById(id);
        return mapExpenseToResponseDto(expense);
    }

    @Override
    public void deleteExpense(Long id) throws DAOException {
        int rowsAffected = expenseRepository.delete(id);
        if(rowsAffected == 0){
            throw new DAOException("El ID " + id + " no fue encontrado");
        }
    }

    @Override
    public String updateExpense(Long id, ExpenseRequestDto expenseRequestDto) throws DAOException {
        //ExpenseRequestDto expenseRequestDto1 = getExpenseById(id);
        String response = "Se registro el cambio con exito";
        Expense existingExpense = mapDtoToExpense(expenseRequestDto);

        existingExpense.setAmount(expenseRequestDto.getAmount());
        existingExpense.setDate(expenseRequestDto.getDate());
        existingExpense.setCategoryName(expenseRequestDto.getCategoryRequestDto().getName());



        int affectedRows = expenseRepository.update(id, existingExpense);
        if(affectedRows ==  0){
            throw new DAOException("El gasto con ID " + existingExpense.getId() + " no pudo ser actualizado");
        } else {
            return response;
        }
    }

    private ExpenseResponseDto mapExpenseToResponseDto(Expense expense){
        ExpenseResponseDto dto = new ExpenseResponseDto();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setCategoryId(expense.getCategoryId());
        dto.setCategoryName(expense.getCategoryName());
        return dto;
    }

    private Expense mapDtoToExpense(ExpenseRequestDto expenseRequestDto){
            Expense expense = new Expense();
            expense.setAmount(expenseRequestDto.getAmount());
            expense.setDate(expenseRequestDto.getDate());
            expense.setCategoryName(expenseRequestDto.getCategoryRequestDto().getName());
            return expense;


    }

}
