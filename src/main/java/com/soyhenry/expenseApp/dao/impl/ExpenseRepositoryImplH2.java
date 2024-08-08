package com.soyhenry.expenseApp.dao.impl;



import com.soyhenry.expenseApp.dao.ExpenseRepository;

import com.soyhenry.expenseApp.entities.Category;
import com.soyhenry.expenseApp.entities.Expense;
import com.soyhenry.expenseApp.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



@Repository
public class ExpenseRepositoryImplH2 implements ExpenseRepository {

    private static final String INSERT_INTO_EXPENSE = "INSERT INTO expense (amount,date,categoryName,categoryid) VALUES(?,?,?,?)";
    private static final String GET_ALL_EXPENSES = "SELECT * FROM expense";
    private static final String GET_EXPENSE_BY_ID = "SELECT * FROM expense WHERE id = ?";
    private static final String UPDATE_EXPENSE = "UPDATE expense SET amount = ?, date = ?, categoryName = ? WHERE id = ?"    ;
    private static final String DELETE_EXPENSE = "DELETE FROM expense WHERE id = ?";
    private static final String INSERT_INTO_CATEGORY_EXPENSE = "INSERT INTO Category (name) VALUES(?)";
    private static final String SELECT_FROM_EXPENSE_CATEGORY_BY_NAME = "SELECT * FROM Category WHERE name = ? LIMIT 1";




    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseRepositoryImplH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Integer create(Expense expense) throws DAOException {
        jdbcTemplate.update(INSERT_INTO_CATEGORY_EXPENSE, expense.getCategoryName().toLowerCase());

        Object[] params = {expense.getCategoryName()};
        int[] types = {1};

        Category category = jdbcTemplate.queryForObject(SELECT_FROM_EXPENSE_CATEGORY_BY_NAME,
                params,
                types,
                new CategoryRowMapper());

        return jdbcTemplate.update(INSERT_INTO_EXPENSE,
                expense.getAmount(),
                expense.getDate(),
                category.getName(),
                category.getId());
    }

    static class CategoryRowMapper implements RowMapper<Category>{

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            return category;
        }
    }


    public List<Expense> getAllExpenses()throws DAOException{
        try{
            return jdbcTemplate.query(GET_ALL_EXPENSES, new ExpenseRowMapper());
        } catch (Exception e){
            throw new DAOException("Error al obtener las expensas", (SQLException) e);
        }
    }

    @Override
    public Expense getExpenseById(Long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(GET_EXPENSE_BY_ID, new Object[]{id}, new ExpenseRowMapper());
        }catch (EmptyResultDataAccessException e) {
            throw new DAOException("No se encontró el gasto con ID: " + id);
        }catch (Exception e) {
            throw new DAOException("Error al obtener el gasto con ID: " + id);
        }
    }

    @Override
    public int update(Long id, Expense expense) throws DAOException {
        return jdbcTemplate.update(UPDATE_EXPENSE,
                expense.getAmount(),
                expense.getDate(),
                expense.getCategoryName(),
                id);
    }

    @Override
    public int delete(Long id) throws DAOException {
        try {
            return jdbcTemplate.update(DELETE_EXPENSE, id);


        } catch (Exception e){
            throw new DAOException("No se pudo eliminar el gasto con ID " + id);
        }
    }

    static class ExpenseRowMapper implements RowMapper<Expense>{

        @Override
        public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
            Expense expense = new Expense();
            expense.setId(rs.getLong("id"));
            expense.setAmount(rs.getDouble("amount"));
            expense.setDate(rs.getDate("date"));
            expense.setCategoryName(rs.getString("categoryName"));
            expense.setCategoryId(rs.getInt("categoryId"));
            return expense;
        }
    }


}
