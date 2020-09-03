package com.example.dictionary.model;

import java.util.UUID;

public class DictionaryWord {
    private UUID wordID;
    private String translationInPersian;
    private String translationInEnglish;

    public DictionaryWord(String translationInPersian, String translationInEnglish) {
        wordID =UUID.randomUUID();
        this.translationInPersian = translationInPersian;
        this.translationInEnglish = translationInEnglish;
    }

    public UUID getWordID() {
        return wordID;
    }

    public DictionaryWord(String wordID, String translationInPersian, String translationInEnglish) {
        this.wordID =UUID.fromString(wordID);
        this.translationInPersian = translationInPersian;
        this.translationInEnglish = translationInEnglish;
    }

    public String getTranslationInPersian() {
        return translationInPersian;
    }

    public void setTranslationInPersian(String translationInPersian) {
        this.translationInPersian = translationInPersian;
    }

    public String getTranslationInEnglish() {
        return translationInEnglish;
    }

    public void setTranslationInEnglish(String translationInEnglish) {
        this.translationInEnglish = translationInEnglish;
    }
}
