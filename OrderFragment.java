package com.example.roman.roman_pset5;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener {
    public ListView listView;
    public TextView confirm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // set view of Dialog
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        // initiate views
        listView = root.findViewById(R.id.listview);
        confirm = root.findViewById(R.id.confirm);

        //set listeners for the buttons
        Button cancel = (Button) root.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnDialogClicklistener());

        Button order = (Button) root.findViewById(R.id.order);
        order.setOnClickListener(new OnDialogClicklistener());

        //open the database to get total of order
        RestoDatabase db = RestoDatabase.getInstance(getActivity());
        Double total = db.getTotal();

        //put total in textview
        String text = "Total price of order: " + String.valueOf(total);
        confirm.setText(text);


        // return inflated layout
        return root;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // open databse to get cursor
        RestoDatabase db = RestoDatabase.getInstance(getActivity());
        Cursor c = db.selectAll();

        // set a custom adapter and bind it to the listView
        RestoAdapter adapter = new RestoAdapter(getActivity(), c);
        listView.setAdapter(adapter);
    }

    // Empty onClick because android studio wanted it.
    @Override
    public void onClick(View view) {

    }

    // private onclick listener
    private class OnDialogClicklistener implements  View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // if cancelbutton pressed
                case R.id.cancel:
                    dismiss();
                    break;

                // if orderbutton pressed
                case R.id.order:
                    RestoDatabase db = RestoDatabase.getInstance(getActivity());
                    db.clear();
                    dismiss();
                    Log.d("CREATION", "onClick: ordering");
            }
        }
    }
}
