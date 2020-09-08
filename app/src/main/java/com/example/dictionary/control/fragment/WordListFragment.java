package com.example.dictionary.control.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionary.R;
import com.example.dictionary.control.activity.SearchActivity;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repository.DictionaryDBRepository;
import com.example.dictionary.repository.IRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.dictionary.control.fragment.AddWordDialogFragment.EXTRA_ADD_WORD;

public class WordListFragment extends Fragment {
    private final String DIALOG_ADD_WORD = "DialogCreateWord";
    private final String EDIT_DELETE_WORD_FRAGMENT = "EditDeleteWordFragment";
    private IRepository<DictionaryWord> mWordRepository;
    private List<DictionaryWord> mWordList;
    private WordAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddButton;
    private FloatingActionButton mSearchButton;
    private int ADD_WORD_REQUEST_CODE = 2;

    public WordListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null)
            if (requestCode == ADD_WORD_REQUEST_CODE && data.getExtras() != null) {
                DictionaryWord word = (DictionaryWord) data.getExtras().getSerializable(EXTRA_ADD_WORD);
                mWordRepository.insert(word);
                mWordList.add(word);
                mAdapter.notifyDataSetChanged();
            }
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
        setListeners();
        return view;
    }

    private void updateSub() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        String subtitle = "Number Of Words: " + mWordRepository.getList().size();
        if (activity != null && activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        mWordList = mWordRepository.getList();
        if (mAdapter == null) {
            mAdapter = new WordAdapter(mWordList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setWords(mWordList);
            mAdapter.notifyDataSetChanged();
        }
        updateSub();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.word_list);
        mAddButton = view.findViewById(R.id.add_word_button);
        mSearchButton = view.findViewById(R.id.search_word_button);
    }

    private void setListeners() {
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddWordDialogFragment fragment = AddWordDialogFragment.newInstance();

                fragment.setTargetFragment(WordListFragment.this, ADD_WORD_REQUEST_CODE);
                if (getFragmentManager() != null)
                    fragment.show(getFragmentManager(), DIALOG_ADD_WORD);
            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SearchActivity.newIntent(getActivity());
                startActivity(intent);
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
                    EditDeleteWordFragment fragment = EditDeleteWordFragment.newInstance(mWord);
                    fragment.setTargetFragment(WordListFragment.this, 4);
                    if (getFragmentManager() != null) {
                        fragment.show(getFragmentManager(), EDIT_DELETE_WORD_FRAGMENT);
                    }
                }
            });
            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = ShareCompat.IntentBuilder.from(getActivity()).
                            setType("text/plain").
                            setSubject(getString(R.string.share_word)).
                            setText(mWord.getWordToShareString()).getIntent();
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivity(shareIntent);
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