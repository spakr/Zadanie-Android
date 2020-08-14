package com.roman.zadanie.ui.charges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.roman.zadanie.MainActivity;
import com.roman.zadanie.databinding.FragmentChargesBinding;
import com.roman.zadanie.db.MyDatabase;
import com.roman.zadanie.db.entity.Charge;
import com.roman.zadanie.db.entity.Income;
import com.roman.zadanie.dialog.AddDialogListener;
import com.roman.zadanie.dialog.UpdateDialog;
import com.roman.zadanie.ui.incomes.IncomesAdapter;

import java.util.List;

public class ChargesFragment extends Fragment {


    private ChargesViewModel viewModel;

    private FragmentChargesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        MyDatabase db = ((MainActivity)getActivity()).database;


        viewModel = new ViewModelProvider(this, new ChargesViewModel.FactoryChargesViewModel(db)).get(ChargesViewModel.class);


        binding = FragmentChargesBinding.inflate(inflater,container,false);


        binding.chargesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        final ChargesAdapter adapter = new ChargesAdapter();


        final AddDialogListener updateDialogListener = new AddDialogListener() {

            @Override
            public void onPositiveClick(String[] arr) {
                Charge chargeToUpdate = new Charge(
                        arr[0],
                        Double.valueOf(arr[1]),
                        arr[2]
                );


                chargeToUpdate.setId(Integer.valueOf(arr[3]));

                viewModel.updateCharge(
                        chargeToUpdate
                );
                Toast.makeText(requireContext(), "Charge was updated", Toast.LENGTH_LONG).show();
            }
        };


        adapter.setOnIncomeClickListener(new ChargesAdapter.ChargeOnItemClickListener() {
            @Override
            public void onItemClick(Charge charge) {
                //Toast.makeText(requireContext(), String.valueOf(charge.getId()), Toast.LENGTH_SHORT).show();

                new UpdateDialog(updateDialogListener, "Update Charges",
                        charge.getName(),
                        charge.getPrice(),
                        charge.getCategory(),
                        charge.getId())

                        .show(getActivity()
                        .getSupportFragmentManager(), "update");
            }
        });


        binding.chargesRecyclerView.setAdapter(adapter);


        viewModel.getAllCharges().observe(getViewLifecycleOwner(), new Observer<List<Charge>>() {
            @Override
            public void onChanged(List<Charge> charges) {

                adapter.submitList(charges);
            }
        });


        return binding.getRoot();
    }
}