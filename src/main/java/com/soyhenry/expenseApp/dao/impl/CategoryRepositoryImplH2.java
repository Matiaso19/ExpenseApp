package com.soyhenry.expenseApp.dao.impl;


import com.soyhenry.expenseApp.dao.CategoryRepository;
import com.soyhenry.expenseApp.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.soyhenry.expenseApp.dto.CategoryRequestDto;


import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CategoryRepositoryImplH2 implements CategoryRepository {




    private static final String GET_CATEGORY_BY_NAME = "SELECT id, name FROM category WHERE name = ? ";


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRepositoryImplH2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    @Override
    public CategoryRequestDto getCategoryByName(String name) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(
                    GET_CATEGORY_BY_NAME,
                    new Object[]{name},
                    new int[]{java.sql.Types.VARCHAR},
                    new CategoryDtoRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {

            return null;
        } catch (Exception e) {
            throw new DAOException("Error al obtener la categoría por nombre: " + name);
        }


    }


    private static class CategoryDtoRowMapper implements RowMapper<CategoryRequestDto>{

        @Override
        public CategoryRequestDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CategoryRequestDto(
                    rs.getString("name"),
                    rs.getLong("id")
            );
        }
    }
}
