package com.example.dictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dictionary.database.DictionaryDBSchema.dictionaryTable.*;

import java.util.UUID;

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
        ContentValues contentValues = new ContentValues();
        String tempUUID = UUID.randomUUID().toString();
        addInitialEntities(db, contentValues, tempUUID, 1, "Hello", "سلام");
        contentValues.clear();
        tempUUID = UUID.randomUUID().toString();
        addInitialEntities(db, contentValues, tempUUID, 2, "World", "دنیا");
        contentValues.clear();
        tempUUID = UUID.randomUUID().toString();
        addInitialEntities(db, contentValues, tempUUID, 3, "Again", "دوباره");
    }

    private void addInitialEntities(SQLiteDatabase db, ContentValues contentValues, String tempUUID, int i, String English, String Persian) {
        contentValues.put(COLS.ID, i);
        contentValues.put(COLS.UUID, tempUUID);
        contentValues.put(COLS.TRANSLATIONENGLISH, English);
        contentValues.put(COLS.TRANSLATIONPERSIAN, Persian);
        db.insert(NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
