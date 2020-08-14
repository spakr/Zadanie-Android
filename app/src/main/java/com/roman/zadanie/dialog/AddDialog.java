package com.roman.zadanie.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.roman.zadanie.R;


public class AddDialog extends DialogFragment {

    private AddDialogListener listener;
    private String heading;


    public AddDialog(AddDialogListener listener, String heading){
        this.listener = listener;
        this.heading = heading;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        View viewToDialog = getLayoutInflater().inflate(R.layout.fragment_add_dialog,null);
        builder.setView(viewToDialog);


        final TextView heading = viewToDialog.findViewById(R.id.heading);
        heading.setText(this.heading);


        final EditText name = viewToDialog.findViewById(R.id.nameField);
        final EditText price = viewToDialog.findViewById(R.id.priceField);
        final EditText category = viewToDialog.findViewById(R.id.categoryField);


        final String[] arr = new String[3];

        viewToDialog.findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(TextUtils.isEmpty(name.getText().toString())){
                        name.setError("Empty name");
                    }else {
                        if(TextUtils.isEmpty(price.getText().toString())) {
                                price.setError("Empty price");
                        } else {
                            if(TextUtils.isEmpty(category.getText().toString())) {
                                category.setError("Empty category");
                            }else{

                                arr[0] = name.getText().toString();
                                arr[1] = price.getText().toString();
                                arr[2] = category.getText().toString();


                                listener.onPositiveClick(arr);
                                dismiss();
                            }
                        }
                    }
            }
        });

        return builder.create();
    }
}

