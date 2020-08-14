package com.roman.zadanie.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.roman.zadanie.db.entity.CategoryPrice;
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.db.entity.Income;

import java.util.List;



@Dao
public interface IncomeDao {

    @Query("SELECT * FROM income_table")
    LiveData<List<Income>> loadAllIncomes();

    @Update
    void update(Income income);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIncome(Income income);

    @Query("DELETE FROM income_table")
    void deleteAllIncomes();

    @Query("SELECT SUM(price) FROM income_table ")
    LiveData<Double> getIncomeSum();

    @Query("SELECT category, SUM(price) as price, type FROM income_table GROUP BY category")
    List<CategoryPrice> getPriceIncomeSumByCategory();

}
