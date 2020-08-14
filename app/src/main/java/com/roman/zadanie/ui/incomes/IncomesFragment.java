package com.roman.zadanie.ui.incomes;

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
import com.roman.zadanie.databinding.FragmentIncomesBinding;
import com.roman.zadanie.db.MyDatabase;
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.db.entity.Income;
import com.roman.zadanie.dialog.AddDialog;
import com.roman.zadanie.dialog.AddDialogListener;
import com.roman.zadanie.dialog.UpdateDialog;
import com.roman.zadanie.ui.charges.ChargesAdapter;

import java.util.List;

public class IncomesFragment extends Fragment {

    private IncomesViewModel viewModel;
    private FragmentIncomesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MyDatabase db = ((MainActivity)getActivity()).database;

        viewModel = new ViewModelProvider(this, new IncomesViewModel.FactoryIncomesViewModel(db)).get(IncomesViewModel.class);

        binding = FragmentIncomesBinding.inflate(inflater,container,false);
        binding.incomesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final IncomesAdapter adapter = new IncomesAdapter();


        final AddDialogListener updateDialogListener = new AddDialogListener() {
            @Override
            public void onPositiveClick(String[] arr) {
                Income incomeToUpdate = new Income(
                        arr[0],
                        Double.valueOf(arr[1]),
                        arr[2]
                );

                incomeToUpdate.setId(Integer.valueOf(arr[3]));

                viewModel.updateIncome(
                        incomeToUpdate
                );
                Toast.makeText(requireContext(), "Income was updated", Toast.LENGTH_LONG).show();
            }
        };


       adapter.setOnIncomeClickListener(new IncomesAdapter.IncomeOnItemClickListener() {
           @Override
           public void onItemClick(Income income) {
               new UpdateDialog(updateDialogListener, "Update Charges",
                       income.getName(),
                       income.getPrice(),
                       income.getCategory(),
                       income.getId())

                       .show(getActivity()
                       .getSupportFragmentManager(), "update");
           }
       });

        binding.incomesRecyclerView.setAdapter(adapter);

        viewModel.getAllIncomes().observe(getViewLifecycleOwner(), new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                adapter.submitList(incomes);
            }
        });

        return binding.getRoot();
    }
}