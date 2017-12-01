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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener {
    public ListView listView;
    public TextView confirm;
    public TextView orderView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // set view of Dialog
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        // initiate views
        listView = root.findViewById(R.id.listview);
        confirm = root.findViewById(R.id.confirm);
        orderView = root.findViewById(R.id.ordertextView);

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

        if (c.getCount() == 0) {
            orderView.setText("Your order is empty");
        }

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
                    final String time = "Your order will take";
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    RestoDatabase db = RestoDatabase.getInstance(getActivity());
                    db.clear();
                    Log.d("CREATION", "onClick: ordering");

                    // url for Volly request
                    String url = "https://resto.mprog.nl/order";

                    // list to store items in
                    final List<String> itemlist = new ArrayList<String>();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int timeint = 0;
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                timeint = jsonObject.getInt("preparation_time");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            confirm.setText("Your order will take " + timeint + " minutes. Enjoy your food");

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    queue.add(stringRequest);
            }
            }
        }
    }

