package com.soyhenry.expenseApp.controller;


import com.soyhenry.expenseApp.dto.ExpenseRequestDto;
import com.soyhenry.expenseApp.dto.ExpenseResponseDto;
import com.soyhenry.expenseApp.exceptions.DAOException;
import com.soyhenry.expenseApp.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @PostMapping()
    public ResponseEntity<String> createExpenseHandler(@RequestBody ExpenseRequestDto expenseRequestDto) throws DAOException {
       String response = expenseService.createExpense(expenseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpensesHandler() throws DAOException {
        List<ExpenseResponseDto> expenses = expenseService.getAllExpenses();
        return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseByIdHandler(@PathVariable Long id) {
        try {
            ExpenseResponseDto expenseDto = expenseService.getExpenseById(id);
            return ResponseEntity.ok(expenseDto);
        } catch (DAOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gasto con ID " + id + " no encontrado: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpenseHandler(@PathVariable Long id) throws DAOException{
        try{
        expenseService.deleteExpense(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Gasto con ID " + id + " eliminado");
    }catch (DAOException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El ID que quiere eliminar no fue encontrado: " + e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExpenseHandler(@PathVariable Long id, @RequestBody ExpenseRequestDto expenseRequestDto ){
        try{
            String response = expenseService.updateExpense(id, expenseRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DAOException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gasto con ID " + id + " no encontrado: " + e.getMessage());
        }
    }
}
