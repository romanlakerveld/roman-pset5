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
        String name = cursor.getString(cursor.getColumnIndex("name"));
        Log.d("CREATION", "bindView: " + name);
        int amount = cursor.getInt(cursor.getColumnIndex("amount"));
        TextView itemName = view.findViewById(R.id.itemName);
        TextView amountName = view.findViewById(R.id.itemAmount);
        itemName.setText(name);
        amountName.setText(String.valueOf(amount));
    }
}
