package com.example.dictionary.control.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dictionary.R;
import com.example.dictionary.control.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    private final String FRAGMENT_SEARCH_TAG = "SearchFragmentTag";

    public static Intent newIntent(Context context){
        return new Intent(context,SearchActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.search_container);
        if (fragment==null){
            fragmentManager.
                    beginTransaction().
                    add(R.id.search_container,createFragment(), FRAGMENT_SEARCH_TAG).
                    commit();
        }
    }
    public Fragment createFragment(){
         return SearchFragment.newInstance();
    }
}