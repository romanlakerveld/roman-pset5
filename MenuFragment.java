package com.example.roman.roman_pset5;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends ListFragment {
    public Map<String, Double> priceMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize Hashmap for prices
        priceMap = new HashMap<>();

        // get chosen category
        Bundle arguments = this.getArguments();
        String category = arguments.getString("category");

        // initialize queue for Volley requests
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // url for Volly request
        String url = "https://resto.mprog.nl/menu?category=" + category;

        // list to store items in
        final List<String> itemlist = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // get menu items from JSON
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String itemname = jsonArray.getJSONObject(i).getString("name");
                        itemlist.add(itemname);
                        // fill priceMap
                        priceMap.put(itemname, jsonArray.getJSONObject(i).getDouble("price"));

                    }
                    // initialize adapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemlist);
                    MenuFragment.this.setListAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // call addItem in database for added item
        String item = (String) l.getItemAtPosition(position);
        RestoDatabase db = RestoDatabase.getInstance(getActivity());
        db.addItem(item, priceMap.get(item));


    }
}
