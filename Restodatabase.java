package com.example.roman.roman_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Map;


/**
 * Created by roman on 22/11/2017.
 */

public class RestoDatabase extends SQLiteOpenHelper {
    private static RestoDatabase instance;

    public static RestoDatabase getInstance(Context context) {
        if(instance == null) {
            instance = new RestoDatabase(context, "resto", null, 2);
        }
        return instance;
    }
    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE resto (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price DOUBLE, amount INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS todos");
        onCreate(sqLiteDatabase);
    }

    public Cursor selectAll() {
        SQLiteDatabase writer = getWritableDatabase();
        Cursor c = writer.rawQuery("SELECT * FROM resto", null);
        return c;

    }

    public void addItem(String name, Double price) {
        Log.d("plog", "addItem: " + price);
        Log.d("CREATION", "addItem: adding item");
        SQLiteDatabase writer = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor c = writer.rawQuery("SELECT amount FROM resto WHERE name = '"+name+"'", null);
        Log.d("CREATION", String.valueOf(c.getCount()));
        if (c.getCount() > 0) {
            c.moveToFirst();
            Log.d("CREATION", "addItem: meer of gelijk aan nul");
            int amount = c.getInt(c.getColumnIndex("amount"));
            Log.d("CREATION", "addItem: " + String.valueOf(amount));
            amount += 1;
            contentValues.put("amount", amount);
            writer.update("resto", contentValues,"name = " + "'" + name + "'", null);
        }
        else if (c.getCount() == 0){
            Log.d("CREATION", "addItem: is nul, dus maak nieuw");
            contentValues.put("amount", 1);
            contentValues.put("name", name);
            contentValues.put("price", price);
            writer.insert("resto", null, contentValues);
        }
    }

    public void clear() {
        SQLiteDatabase writer = getWritableDatabase();
        writer.execSQL("DELETE FROM resto");
    }

    public Double getTotal() {
        SQLiteDatabase writer = getWritableDatabase();
        Cursor c = writer.rawQuery("SELECT SUM(price * amount) AS grand_total FROM resto ", null);
        c.moveToFirst();
        Double total = c.getDouble(c.getColumnIndex("grand_total"));
        return total;
    }

    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("todos", "_id = " + id, null);
        Log.d("TAG", "delete: ");
    }
}
