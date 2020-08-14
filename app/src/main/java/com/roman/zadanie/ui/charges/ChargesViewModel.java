package com.roman.zadanie.ui.charges;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.roman.zadanie.db.Dao.ChargeDao;
import com.roman.zadanie.db.Dao.IncomeDao;
import com.roman.zadanie.db.MyDatabase;
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.db.entity.Income;
import com.roman.zadanie.ui.incomes.IncomesViewModel;
import com.roman.zadanie.ui.main.HomeViewModel;

import java.util.List;


public class ChargesViewModel extends ViewModel {


    private MyDatabase db;

    public ChargesViewModel(MyDatabase database){
        this.db = database;
    }


    LiveData<List<Charge>> getAllCharges() {
        return db.chargeDao().loadAllCharges();
    }


    public void updateCharge(Charge charge){
        new UpdateSpecificCharge(db.chargeDao()).execute(charge);
    }


    private static class UpdateSpecificCharge extends AsyncTask<Charge, Void, Void> {

        private ChargeDao chargeDao;

        public UpdateSpecificCharge(ChargeDao chargeDao) {
            this.chargeDao = chargeDao;
        }

        @Override
        protected Void doInBackground(Charge... charges) {
            chargeDao.update(
                    charges[0]
            );
            return null;
        }
    }


    static class FactoryChargesViewModel implements ViewModelProvider.Factory {
        private MyDatabase mDb;

        public FactoryChargesViewModel(MyDatabase db) {
            mDb = db;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ChargesViewModel(mDb);
        }
    }

}