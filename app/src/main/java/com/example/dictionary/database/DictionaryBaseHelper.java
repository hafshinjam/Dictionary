package com.example.dictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dictionary.database.DictionaryDBSchema.dictionaryTable.*;

import static com.example.dictionary.database.DictionaryDBSchema.dictionaryTable.NAME;

public class DictionaryBaseHelper extends SQLiteOpenHelper {
    public DictionaryBaseHelper(@Nullable Context context) {
        super(context, DictionaryDBSchema.NAME, null, DictionaryDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NAME + "(" +
                COLS.ID + " integer primary key autoincrement," +
                COLS.UUID + " text," +
                COLS.TRANSLATIONENGLISH + " text," +
                COLS.TRANSLATIONPERSIAN + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
