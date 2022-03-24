package com.example.asm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asm.dao.BillDao;
import com.example.asm.dao.BookDao;
import com.example.asm.dao.CategoryDao;
import com.example.asm.dao.HDCTDao;
import com.example.asm.dao.UserDao;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "PNlib.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDao.table_user);
        db.execSQL(CategoryDao.table_category);
        db.execSQL(BookDao.table_book);
        db.execSQL(BillDao.table_bill);
        db.execSQL(HDCTDao.table_hdct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
