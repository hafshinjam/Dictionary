package com.example.dictionary.control.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dictionary.R;
import com.example.dictionary.control.fragment.TranslateFragment;
import com.example.dictionary.control.fragment.WordListFragment;
import com.example.dictionary.repository.DictionaryDBRepository;
import com.example.dictionary.repository.IRepository;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class DictionaryMainActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager = getSupportFragmentManager();

    private TabLayout mLanguageTab;
    private ViewPager2 mTranslatePager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_main);
       findViews();
        initUI();
    }

    private void initUI() {
        FragmentStateAdapter adapter = new DictionaryPagerAdapter(this);
        mTranslatePager.setAdapter(adapter);
        new TabLayoutMediator(mLanguageTab, mTranslatePager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Translate Word");
                        break;
                    case 1:
                        tab.setText("Word List");
                        break;
                }
            }
        }).attach();
    }

    private void findViews() {
        mLanguageTab = findViewById(R.id.language_tab);
        mTranslatePager = findViewById(R.id.translate_pager);
    }

    public class DictionaryPagerAdapter extends FragmentStateAdapter {

        private List<Fragment> mDictionaryFragments;

        public List<Fragment> getTaskListFragments() {
            return mDictionaryFragments;
        }

        public void setTaskListFragments(List<Fragment> taskListFragments) {
            mDictionaryFragments = taskListFragments;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        public DictionaryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return TranslateFragment.newInstance();
                default:
                    return WordListFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}