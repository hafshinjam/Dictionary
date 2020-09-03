package com.example.dictionary.control.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dictionary.R;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repository.DictionaryDBRepository;
import com.example.dictionary.repository.IRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TranslateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslateFragment extends Fragment {

    private EditText mFaWord;
    private EditText mEnWord;
    private SwitchCompat mLanguageSwitch;
    private Button mLookUp;

    private boolean mLanguagePersian = true;
    private List<DictionaryWord> mWordList;
    private IRepository<DictionaryWord> mDictionaryRepository;

    public TranslateFragment() {
        // Required empty public constructor
    }


    public static TranslateFragment newInstance() {
        TranslateFragment fragment = new TranslateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        mDictionaryRepository = DictionaryDBRepository.getInstance(getActivity());
        findViews(view);
        setClickListeners();
        return view;
    }

    private void setClickListeners() {
        mLookUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWordList = mDictionaryRepository.getList();
                if (mLanguagePersian) {
                    for (int i = 0; i < mWordList.size(); i++) {
                        if (mWordList.get(i).getTranslationInPersian().contains(mFaWord.getText())) {
                            mEnWord.setText(mWordList.get(i).getTranslationInEnglish());
                            break;
                        }
                        if (!mWordList.get(i).getTranslationInPersian().contains(mFaWord.getText()) &&
                                i == mWordList.size() - 1)
                            Toast.makeText(getActivity(), "the word was not found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    for (int i = 0; i < mWordList.size(); i++) {
                        if (mWordList.get(i).getTranslationInEnglish().contains(mEnWord.getText())) {
                            mFaWord.setText(mWordList.get(i).getTranslationInPersian());
                            break;
                        }
                        if (!mWordList.get(i).getTranslationInEnglish().contains(mEnWord.getText()) &&
                                i == mWordList.size() - 1)
                            Toast.makeText(getActivity(), "the word was not found", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mLanguageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mFaWord.setFocusableInTouchMode(true);
                    mEnWord.setFocusableInTouchMode(false);
                    mLanguagePersian = true;
                } else {
                    mFaWord.setFocusableInTouchMode(false);
                    mEnWord.setFocusableInTouchMode(true);
                    mLanguagePersian = false;
                }

            }
        });
    }

    private void findViews(View view) {

        mLanguageSwitch = view.findViewById(R.id.language_switch);
        mLanguageSwitch.setTextOff(getString(R.string.english));
        mLanguageSwitch.setTextOn(getString(R.string.persian));
        mLanguageSwitch.setChecked(true);
        mEnWord = view.findViewById(R.id.en_word);
        mEnWord.setFocusableInTouchMode(false);
        mFaWord = view.findViewById(R.id.fa_word);
        mLookUp = view.findViewById(R.id.look_up_button);
    }
}