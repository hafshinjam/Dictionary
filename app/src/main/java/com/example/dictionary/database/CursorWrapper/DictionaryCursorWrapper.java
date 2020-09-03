package com.example.dictionary.database.CursorWrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.dictionary.database.DictionaryDBSchema.dictionaryTable.*;
import com.example.dictionary.model.DictionaryWord;

public class DictionaryCursorWrapper extends CursorWrapper {
    public DictionaryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public DictionaryWord getWord() {
        String stringUUID = getString(getColumnIndex(COLS.UUID));
        String translationPersian = getString(getColumnIndex(COLS.TRANSLATIONPERSIAN));
        String translationEnglish = getString(getColumnIndex(COLS.TRANSLATIONENGLISH));
        return new DictionaryWord(stringUUID,translationPersian,translationEnglish);
    }
}
