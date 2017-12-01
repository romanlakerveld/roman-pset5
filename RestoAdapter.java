package com.example.roman.roman_pset5;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.ResourceCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by roman on 23/11/2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {
    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_order, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //get name and amount from cursor
        String name = cursor.getString(cursor.getColumnIndex("name"));
        int amount = cursor.getInt(cursor.getColumnIndex("amount"));

        // initialize views
        TextView itemName = view.findViewById(R.id.itemName);
        TextView amountName = view.findViewById(R.id.itemAmount);

        // set text values
        itemName.setText(name);
        amountName.setText(String.valueOf(amount));
    }
}
