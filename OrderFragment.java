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
        Log.d("CREATION", "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        listView = root.findViewById(R.id.listview);
        confirm = root.findViewById(R.id.confirm);

        Button cancel = (Button) root.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnDialogClicklistener());

        Button order = (Button) root.findViewById(R.id.order);
        order.setOnClickListener(new OnDialogClicklistener());
        RestoDatabase db = RestoDatabase.getInstance(getActivity());
        Double total = db.getTotal();
        String text = "Total price of order: " + String.valueOf(total);
        confirm.setText(text);


        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d("CREATION", "onViewStateRestored: ");
        super.onViewStateRestored(savedInstanceState);
        RestoDatabase db = RestoDatabase.getInstance(getActivity());
        Cursor c = db.selectAll();
        RestoAdapter adapter = new RestoAdapter(getActivity(), c);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    private class OnDialogClicklistener implements  View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cancel:
                    dismiss();
                    break;
                case R.id.order:
                    RestoDatabase db = RestoDatabase.getInstance(getActivity());
                    db.clear();
                    dismiss();
                    Log.d("CREATION", "onClick: ordering");
            }
        }
    }
}
