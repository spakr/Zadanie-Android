package com.roman.zadanie.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.roman.zadanie.MainActivity;
import com.roman.zadanie.databinding.FragmentHomeBinding;
import com.roman.zadanie.db.MyDatabase;
import com.roman.zadanie.db.entity.CategoryPrice;
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.db.entity.Income;
import com.roman.zadanie.dialog.AddDialog;
import com.roman.zadanie.dialog.AddDialogListener;
import com.roman.zadanie.ui.charges.ChargesAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MyDatabase db = ((MainActivity)getActivity()).database;

        viewModel = new ViewModelProvider(this, new HomeViewModel.FactoryHomeViewModel(db)).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final HomeAdapter adapter = new HomeAdapter();

        binding.homeRecyclerView.setAdapter(adapter);

        try {
            viewModel.categoryPriceListLiveData().observe(getViewLifecycleOwner(), new Observer<List<CategoryPrice>>() {
                @Override
                public void onChanged(List<CategoryPrice> categoryPrices) {
                    adapter.submitList(categoryPrices);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final AddDialogListener listenerCharge = new AddDialogListener() {
            @Override
            public void onPositiveClick(String[] arr) {
                Charge chargeToDb = new Charge(arr[0], Double.valueOf(arr[1]), arr[2]);
                viewModel.insertCharge(chargeToDb);
                Toast.makeText(requireContext(), "New charge "+arr[0]+" was added", Toast.LENGTH_SHORT).show();
            }
        };

        binding.chargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDialog(listenerCharge, "Add charge").show(getActivity().getSupportFragmentManager(), "test");
            }
        });



        final AddDialogListener listenerIncome = new AddDialogListener() {
            @Override
            public void onPositiveClick(String[] arr) {
                Toast.makeText(requireContext(), arr[0] + " " + arr[1] + " " + arr[2], Toast.LENGTH_SHORT).show();
                Income incomeToDb = new Income(arr[0], Double.valueOf(arr[1]), arr[2]);
                viewModel.insertIncome(incomeToDb);
                Toast.makeText(requireContext(), "New income "+arr[0]+" was added", Toast.LENGTH_SHORT).show();
            }
        };

        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDialog(listenerIncome, "Add income").show(getActivity().getSupportFragmentManager(), "test");
            }
        });


        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteAllRecords();
                Toast.makeText(requireContext(), "All records were deleted", Toast.LENGTH_SHORT).show();
            }
        });



        return binding.getRoot();
    }
}