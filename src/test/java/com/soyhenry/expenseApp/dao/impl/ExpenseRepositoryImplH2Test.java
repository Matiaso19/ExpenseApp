package com.soyhenry.expenseApp.dao.impl;

import com.soyhenry.expenseApp.entities.Category;
import com.soyhenry.expenseApp.entities.Expense;
import com.soyhenry.expenseApp.exceptions.DAOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExpenseRepositoryImplH2Test {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ExpenseRepositoryImplH2 expenseRepositoryImplH2;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void createExpenseWithValidExpense() throws SQLException, DAOException, ParseException {
//
//        //given
//        Expense expense = new Expense();
//        expense.setId(1L);
//        expense.setAmount(100.00);
//        Date date = new Date(124, 5, 6);
//        expense.setDate(date);
//        expense.setCategoryName("comida");
//
//        Category category = new Category();
//        category.setId(1);
//        category.setName("comida");
//
//        expense.setCategoryId(category.getId());
//
//
//        when(jdbcTemplate.update(anyString(), anyString())).thenReturn(1);
//        when(jdbcTemplate.queryForObject(
//                anyString(),
//                any(Object[].class),
//                any(int[].class),
//                any(RowMapper.class)
//        )).thenReturn(category);
//
//        // When
//        int rowsAffected = expenseRepositoryImplH2.create(expense);
//
//        // Then
//        verify(jdbcTemplate).update(eq("INSERT INTO Category (name) VALUES(?)"), eq("comida"));
//        verify(jdbcTemplate).queryForObject(
//                eq("SELECT * FROM Category WHERE name = ? LIMIT 1"),
//                any(Object[].class),
//                any(int[].class),
//                any(RowMapper.class)
//        );
//
//
//        verify(jdbcTemplate).update(
//                eq("INSERT INTO expense (amount,date,categoryName,categoryid) VALUES(?,?,?,?)"),
//                eq(100.00), eq(date), eq("comida"), eq(1));
//        assertEquals(1, rowsAffected);
//    }
    @Test
    void getAllExpenses_ShouldReturnListOfExpenses_WhenDatabaseHasData() throws DAOException, SQLException {
        // Given
        Date date = new Date(2024, 5, 6);
        List<Expense> expectedExpenses = List.of(
                new Expense(200.00,date, "comida", 1),
                new Expense(300.00,date, "comida", 1)

        );

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedExpenses);

        // When
        List<Expense> actualExpenses = expenseRepositoryImplH2.getAllExpenses();

        // Then
        verify(jdbcTemplate).query(eq("SELECT * FROM expense"), any(RowMapper.class));
        assertEquals(expectedExpenses.size(), actualExpenses.size());
        assertEquals(expectedExpenses, actualExpenses);
    }
    @Test
    void getExpenseById_ShouldReturnExpense_WhenIdIsValid() throws DAOException, SQLException, ParseException {
        // Given
        Long id = 1L;
        Integer idCategory = 1;
        Date date = new Date(2024, 5, 6);

        Expense expectedExpense = new Expense(200.00, date, "Food", idCategory);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedExpense);

        // When
        Expense actualExpense = expenseRepositoryImplH2.getExpenseById(id);

        // Then
        verify(jdbcTemplate).queryForObject(eq("SELECT * FROM expense WHERE id = ?"), any(Object[].class), any(RowMapper.class));
        assertEquals(expectedExpense, actualExpense);
    }

//    @Test
//    void update_ShouldUpdateExpense_WhenValidExpenseAndId() throws DAOException, SQLException {
//        // Given
//        Long id = 1L;
//        Date date = new Date(2024, 5, 6);
//        Expense expense = new Expense();
//        expense.setAmount(300.00);
//        expense.setDate(date);
//        expense.setCategoryName("travel");
//        expense.setId(id);
//        expense.setCategoryId(1);
//
//        when(jdbcTemplate.update(anyString(), anyDouble(), any(), anyString(), anyInt())).thenReturn(1);
//
//        // When
//        int rowsAffected = expenseRepositoryImplH2.update(id, expense);
//        System.out.println(id);
//        System.out.println(expense);
//        System.out.println(rowsAffected);
//        // Then
//        verify(jdbcTemplate).update(eq("UPDATE expense SET amount = ?, date = ?, categoryName = ? WHERE id = ?"),
//                eq(300.00), eq(date), eq("travel"), eq(id));
//        assertEquals(1, rowsAffected);
//    }
    @Test
    void delete_ShouldDeleteExpense_WhenIdIsValid() throws DAOException, SQLException {
        // Given
        Long id = 1L;

        when(jdbcTemplate.update(anyString(), anyLong())).thenReturn(1);

        // When
        int rowsAffected = expenseRepositoryImplH2.delete(id);

        // Then
        verify(jdbcTemplate).update(eq("DELETE FROM expense WHERE id = ?"), eq(id));
        assertEquals(1, rowsAffected);
    }
}