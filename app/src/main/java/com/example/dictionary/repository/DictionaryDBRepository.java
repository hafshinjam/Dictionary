package com.example.dictionary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dictionary.database.CursorWrapper.DictionaryCursorWrapper;
import com.example.dictionary.database.DictionaryBaseHelper;
import com.example.dictionary.database.DictionaryDBSchema;
import com.example.dictionary.database.DictionaryDBSchema.dictionaryTable.*;
import com.example.dictionary.model.DictionaryWord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.dictionary.database.DictionaryDBSchema.dictionaryTable.NAME;

public class DictionaryDBRepository implements IRepository<DictionaryWord> {
    //TODO:memory leak - should be handled
    private static Context mContext;
    private static DictionaryDBRepository sDictionaryDBRepository;

    private SQLiteDatabase mDatabase;

    public static DictionaryDBRepository getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sDictionaryDBRepository == null)
            sDictionaryDBRepository = new DictionaryDBRepository();

        return sDictionaryDBRepository;
    }

    public DictionaryDBRepository() {
        DictionaryBaseHelper dictionaryBaseHelper = new DictionaryBaseHelper(mContext);
            mDatabase = dictionaryBaseHelper.getWritableDatabase();
    }

    @Override
    public List<DictionaryWord> getList() {
        List<DictionaryWord> dictionaryWordList = new ArrayList<>();

        DictionaryCursorWrapper cursorWrapper = QueryWords(null, null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                dictionaryWordList.add(cursorWrapper.getWord());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return dictionaryWordList;
    }

    @Override
    public DictionaryWord get(UUID uuid) {
        String selection = DictionaryDBSchema.dictionaryTable.COLS.UUID + "=?";
        String[] selectionArgs = new String[]{uuid.toString()};
        DictionaryCursorWrapper cursor = QueryWords(selection, selectionArgs);
        if (cursor == null || cursor.getCount() == 0)
            return null;

        try {
            cursor.moveToFirst();
            return cursor.getWord();
        } finally {
            cursor.close();
        }
    }

    public DictionaryCursorWrapper QueryWords(String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return new DictionaryCursorWrapper(cursor);
    }

    @Override
    public void update(DictionaryWord dictionaryWord) {
        String whereClause = COLS.UUID + "=?";
        String[] whereArgs = new String[]{dictionaryWord.getWordID().toString()};
        mDatabase.update(NAME, getWordContentValues(dictionaryWord), whereClause, whereArgs);
    }

    @Override
    public void delete(DictionaryWord dictionaryWord) {
        String whereClause = COLS.UUID + "=?";
        String[] whereArgs = new String[]{dictionaryWord.getWordID().toString()};
        mDatabase.delete(NAME, whereClause, whereArgs);
    }

    @Override
    public void insert(DictionaryWord dictionaryWord) {
        mDatabase.insert(NAME, null, getWordContentValues(dictionaryWord));
    }

    @Override
    public void insertList(List<DictionaryWord> list) {

    }

    @Override
    public int getPosition(UUID uuid) {
        List<DictionaryWord> list = getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getWordID().equals(uuid))
                return i;
        }
        return -1;
    }

    public ContentValues getWordContentValues(DictionaryWord dictionaryWord) {
        ContentValues values = new ContentValues();
        values.put(COLS.UUID, dictionaryWord.getWordID().toString());
        values.put(COLS.TRANSLATIONPERSIAN, dictionaryWord.getTranslationInPersian());
        values.put(COLS.TRANSLATIONENGLISH, dictionaryWord.getTranslationInEnglish());
        return values;
    }
}
