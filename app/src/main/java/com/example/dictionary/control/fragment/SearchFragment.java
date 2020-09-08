package com.example.dictionary.control.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repository.DictionaryDBRepository;
import com.example.dictionary.repository.IRepository;

import java.util.List;

public class SearchFragment extends Fragment {
    private EditText mPersianWord;
    private EditText mEnglishWord;
    private Button mSearchButton;
    private CheckBox mLangCheck;
    private RecyclerView mRecyclerView;

    private List<DictionaryWord> mSearchWordList;
    private IRepository<DictionaryWord> mWordRepository;
    private WordAdapter mWordAdapter;


    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        if (getActivity() != null)
            mWordRepository = DictionaryDBRepository.getInstance(getActivity());
        mSearchWordList = mWordRepository.getList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        setListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSub();
    }

    private void updateSub() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        String subtitle = "Number Of Words: " + mWordRepository.getList().size();
        if (activity != null && activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void findViews(View view) {
        mEnglishWord = view.findViewById(R.id.search_word_en);
        mLangCheck = view.findViewById(R.id.check_search);
        mPersianWord = view.findViewById(R.id.search_word_fa);
        mSearchButton = view.findViewById(R.id.SearchFragment_search_words_button);
        mRecyclerView = view.findViewById(R.id.search_word_list);
    }

    private void updateUI() {

        if (mWordAdapter == null) {
            mWordAdapter = new WordAdapter(mSearchWordList);
            mRecyclerView.setAdapter(mWordAdapter);
        } else {
            mWordAdapter.setWords(mSearchWordList);
            mWordAdapter.notifyDataSetChanged();
        }
        updateSub();
    }

    private void setListeners() {
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchWordList.clear();
                List<DictionaryWord> allWords = mWordRepository.getList();
                if (mLangCheck.isChecked()) {
                    if (!mPersianWord.getText().toString().equals(""))
                        for (int i = 0; i < allWords.size(); i++) {
                            if (allWords.get(i).getTranslationInPersian().toLowerCase().
                                    contains(mPersianWord.getText().toString().toLowerCase()))
                                mSearchWordList.add(allWords.get(i));
                        }
                } else if (!mEnglishWord.getText().toString().equals(""))
                    for (int i = 0; i < allWords.size(); i++) {
                        if (allWords.get(i).getTranslationInEnglish().toLowerCase().
                                contains(mEnglishWord.getText().toString().toLowerCase()))
                            mSearchWordList.add(allWords.get(i));
                    }
                mWordAdapter.notifyDataSetChanged();
            }
        });
    }

    public class wordHolder extends RecyclerView.ViewHolder {
        private DictionaryWord mWord;
        private TextView mPersianWord;
        private TextView mEnglishWord;
        private ImageButton mShareButton;

        public wordHolder(@NonNull View itemView) {
            super(itemView);
            mPersianWord = itemView.findViewById(R.id.word_persian);
            mEnglishWord = itemView.findViewById(R.id.word_english);
            mShareButton = itemView.findViewById(R.id.share_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        public void bindWord(DictionaryWord word) {
            mWord = word;
            mEnglishWord.setText(word.getTranslationInEnglish());
            mPersianWord.setText(word.getTranslationInPersian());
        }
    }

    private class WordAdapter extends RecyclerView.Adapter<wordHolder> {
        public List<DictionaryWord> getWords() {
            return mSearchWordList;
        }

        public void setWords(List<DictionaryWord> wordList) {
            mSearchWordList = wordList;
        }

        public WordAdapter(List<DictionaryWord> wordList) {
            mSearchWordList = wordList;
        }

        @NonNull
        @Override
        public wordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.word_row, parent, false);
            wordHolder wordHolder = new wordHolder(view);
            return wordHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull wordHolder holder, int position) {
            DictionaryWord word = mSearchWordList.get(position);
            holder.bindWord(word);
        }

        @Override
        public int getItemCount() {
            return mSearchWordList.size();
        }
    }
}