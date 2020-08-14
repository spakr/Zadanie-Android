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
public interface ChargeDao {

    @Query("SELECT * FROM charge_table")
    LiveData<List<Charge>> loadAllCharges();

    @Update
    void update(Charge charge);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharge(Charge charge);

    @Query("DELETE FROM charge_table")
    void deleteAllCharges();

    @Query("SELECT SUM(price) FROM charge_table ")
    LiveData<Double> getChargeSum();

    @Query("SELECT category, SUM(price) as price, type FROM charge_table GROUP BY category")
    List<CategoryPrice> getPriceChargeSumByCategory();

}
