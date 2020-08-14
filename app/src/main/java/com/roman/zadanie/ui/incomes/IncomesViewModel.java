package com.roman.zadanie.ui.incomes;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.roman.zadanie.db.Dao.ChargeDao;
import com.roman.zadanie.db.Dao.IncomeDao;
import com.roman.zadanie.db.MyDatabase;
import com.roman.zadanie.db.entity.CategoryPrice;
import com.roman.zadanie.db.entity.Income;
import com.roman.zadanie.ui.main.HomeViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IncomesViewModel extends ViewModel {
    private MyDatabase db;

    public IncomesViewModel(MyDatabase database){
        this.db = database;
    }

    LiveData<List<Income>> getAllIncomes() {
        return db.incomeDao().loadAllIncomes();
    }

    public void updateIncome(Income income){
        new UpdateSpecificIncome(db.incomeDao()).execute(income);
    }

    private static class UpdateSpecificIncome extends AsyncTask<Income, Void, Void> {

        private IncomeDao incomeDao;

        public UpdateSpecificIncome(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }


        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.update(
                    incomes[0]
            );
            return null;
        }
    }


    static class FactoryIncomesViewModel implements ViewModelProvider.Factory {
        private MyDatabase mDb;

        public FactoryIncomesViewModel(MyDatabase db) {
            mDb = db;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new IncomesViewModel(mDb);
        }
    }
}