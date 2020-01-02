package com.yousef.owner.restaurantappfirebase.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.yousef.owner.restaurantappfirebase.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "foodsOrder.db";
    private static final int VERSION = 1;

    public Database(Context context) {

        super(context, DB_NAME, null, VERSION);
    }

    public List<Order> getCarts() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        String[] sqlSelect = {"prodectname", "prodectid", "quantity", "price", "discount"};
        String sqlTable = "foods";

        qb.setTables(sqlTable);

        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("prodectid")),
                        cursor.getString(cursor.getColumnIndex("prodectname")),
                        cursor.getString(cursor.getColumnIndex("quantity")),
                        cursor.getString(cursor.getColumnIndex("price")),
                        cursor.getString(cursor.getColumnIndex("discount"))));
            } while (cursor.moveToNext());


        }

        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase database = getReadableDatabase();
        String query = "INSERT INTO foods(prodectid,prodectname,quantity,price,discount) VALUES('" + order.getProdectID() + "','" + order.getProdectname() + "','" + order.getQuantity() + "','" + order.getPrice() + "','" + order.getDiscount() + "');";

        database.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "DELETE FROM foods";

        database.execSQL(query);
    }
}
