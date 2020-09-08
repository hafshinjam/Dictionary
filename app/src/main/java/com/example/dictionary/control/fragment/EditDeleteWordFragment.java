package com.example.dictionary.control.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dictionary.R;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repository.DictionaryDBRepository;
import com.example.dictionary.repository.IRepository;


public class EditDeleteWordFragment extends DialogFragment {
    private static final String WORD_TO_EDIT = "wordToEdit";
    private DictionaryWord mCurrentWord;
    private EditText mEditWordPersian;
    private EditText mEditWordEnglish;
    private Button mButtonSaveChange;
    private Button mButtonDelete;
    private IRepository<DictionaryWord> mDictionaryRepository;


    public EditDeleteWordFragment() {
        // Required empty public constructor
    }

    public static EditDeleteWordFragment newInstance(DictionaryWord word) {
        EditDeleteWordFragment fragment = new EditDeleteWordFragment();
        Bundle args = new Bundle();
        args.putSerializable(WORD_TO_EDIT, word);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentWord = (DictionaryWord) getArguments().getSerializable(WORD_TO_EDIT);
        }
        if (getActivity() != null)
            mDictionaryRepository = DictionaryDBRepository.getInstance(getActivity());
    }

    /**
     * change in case of changing it into a fragment that is not dialog
     * get data from createDialog
     */
 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_delete_word, container, false);
    }*/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_edit_delete_word, null);
        findViews(view);
        setListeners();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    private void setListeners() {
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDictionaryRepository.delete(mCurrentWord);
                getTargetFragment().onResume();
                dismiss();
            }
        });
        mButtonSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentWord.setTranslationInPersian(mEditWordPersian.getText().toString());
                mCurrentWord.setTranslationInEnglish(mEditWordEnglish.getText().toString());
                mDictionaryRepository.update(mCurrentWord);
                getTargetFragment().onResume();
                dismiss();
            }
        });
    }

    private void findViews(View view) {
        mEditWordPersian = view.findViewById(R.id.word_in_persian_edit);
        mEditWordEnglish = view.findViewById(R.id.word_in_english_edit);
        mButtonSaveChange = view.findViewById(R.id.save_change_button);
        mButtonDelete = view.findViewById(R.id.delete_word_button);
        mEditWordEnglish.setText(mCurrentWord.getTranslationInEnglish());
        mEditWordPersian.setText(mCurrentWord.getTranslationInPersian());
    }
}