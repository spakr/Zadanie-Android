package com.roman.zadanie.ui.main;

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
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.db.entity.Income;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeViewModel extends ViewModel {

    private MyDatabase db;


    private MutableLiveData<List<CategoryPrice>> categoryPriceList;

    public HomeViewModel(MyDatabase database){
        this.db = database;
    }


    LiveData<List<CategoryPrice>> categoryPriceListLiveData() throws ExecutionException, InterruptedException {
        if (categoryPriceList == null) {
            categoryPriceList = new MutableLiveData<List<CategoryPrice>>();
            getCategoryPricelist();
        }
        return categoryPriceList;
    }


    public void insertCharge(Charge charge) {
        new InsertChargeAsyncTask(db.chargeDao()).execute(charge);
        try {

            getCategoryPricelist();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void insertIncome(Income income) {
        new InsertIncomeAsyncTask(db.incomeDao()).execute(income);
        try {
            getCategoryPricelist();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void deleteAllRecords(){
        new DeleteAllAsyncTask(db.chargeDao(),db.incomeDao()).execute();
        try {
            getCategoryPricelist();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void getCategoryPricelist() throws ExecutionException, InterruptedException {
        List<CategoryPrice> list = new GetIncomeListAsyncTask(db.chargeDao(),db.incomeDao()).execute().get();
        categoryPriceList.setValue(list);
    }

    private static class GetIncomeListAsyncTask extends AsyncTask<IncomeDao, ChargeDao, List<CategoryPrice>> {

        private ChargeDao chargeDao;
        private IncomeDao incomeDao;

        public GetIncomeListAsyncTask(ChargeDao chargeDao, IncomeDao incomeDao) {
            this.chargeDao = chargeDao;
            this.incomeDao = incomeDao;
        }

        @Override
        protected List<CategoryPrice> doInBackground(IncomeDao... incomeDaos) {



            List<CategoryPrice> newList = new ArrayList<CategoryPrice>(chargeDao.getPriceChargeSumByCategory());
            newList.addAll(incomeDao.getPriceIncomeSumByCategory());
            return newList;
        }
    }

    private static class InsertChargeAsyncTask extends AsyncTask<Charge, Void, Void> {

        private ChargeDao chargeDao;

        private InsertChargeAsyncTask(ChargeDao chargeDao) {
            this.chargeDao = chargeDao;
        }

        @Override
        protected Void doInBackground(Charge... charges) {
            chargeDao.insertCharge(charges[0]);
            return null;
        }
    }

    private static class InsertIncomeAsyncTask extends AsyncTask<Income, Void, Void> {

        private IncomeDao incomeDao;

        private InsertIncomeAsyncTask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.insertIncome(incomes[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<IncomeDao, ChargeDao, Void> {

        private ChargeDao chargeDao;
        private IncomeDao incomeDao;

        public DeleteAllAsyncTask(ChargeDao chargeDao, IncomeDao incomeDao) {
            this.chargeDao = chargeDao;
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(IncomeDao... incomeDaos) {
            chargeDao.deleteAllCharges();
            incomeDao.deleteAllIncomes();
            return null;
        }
    }

    static class FactoryHomeViewModel implements ViewModelProvider.Factory {
        private MyDatabase mDb;

        public FactoryHomeViewModel(MyDatabase db) {
            mDb = db;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HomeViewModel(mDb);
        }
    }

}