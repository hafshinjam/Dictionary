package com.example.dictionary.control.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dictionary.R;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repository.DictionaryDBRepository;
import com.example.dictionary.repository.IRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends Fragment {
    private IRepository<DictionaryWord> mWordRepository;
    private List<DictionaryWord> mWordList;
    private WordAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddButton;
    private FloatingActionButton mSearchButton;

    public WordListFragment() {
        // Required empty public constructor
    }

    public static WordListFragment newInstance() {
        return new WordListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            mWordRepository = DictionaryDBRepository.getInstance(getActivity());
            mWordList = mWordRepository.getList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_list, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new WordAdapter(mWordList);
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.word_list);
        mAddButton = view.findViewById(R.id.add_word_button);
        mSearchButton = view.findViewById(R.id.search_word_button);
    }

    public class wordHolder extends RecyclerView.ViewHolder {
        private DictionaryWord mWord;
        private TextView mPersianWord;
        private TextView mEnglishWord;

        public wordHolder(@NonNull View itemView) {
            super(itemView);
            mPersianWord = itemView.findViewById(R.id.word_persian);
            mEnglishWord = itemView.findViewById(R.id.word_english);
            itemView.setOnClickListener(new View.OnClickListener() {
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
            return mWordList;
        }

        public void setWords(List<DictionaryWord> wordList) {
            mWordList = wordList;
        }

        public WordAdapter(List<DictionaryWord> wordList) {
            mWordList = wordList;
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
            DictionaryWord word = mWordList.get(position);
            holder.bindWord(word);
        }

        @Override
        public int getItemCount() {
            return mWordList.size();
        }
    }
}