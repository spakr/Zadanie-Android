package com.roman.zadanie.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.roman.zadanie.db.Dao.ChargeDao;
import com.roman.zadanie.db.Dao.IncomeDao;
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.db.entity.Income;


@Database(entities = {Charge.class, Income.class}, version = 3,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "savings_db";
    private static MyDatabase INSTANCE;

    public static MyDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, MyDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract ChargeDao chargeDao();
    public abstract IncomeDao incomeDao();

}
