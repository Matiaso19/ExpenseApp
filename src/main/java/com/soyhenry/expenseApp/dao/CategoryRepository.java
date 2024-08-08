package com.soyhenry.expenseApp.dao;




import com.soyhenry.expenseApp.exceptions.DAOException;
import com.soyhenry.expenseApp.dto.CategoryRequestDto;

public interface CategoryRepository {

    CategoryRequestDto getCategoryByName(String name) throws DAOException;


}
