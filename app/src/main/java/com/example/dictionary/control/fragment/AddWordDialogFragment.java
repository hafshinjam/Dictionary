package com.example.dictionary.control.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.dictionary.R;
import com.example.dictionary.model.DictionaryWord;

import java.util.UUID;


public class AddWordDialogFragment extends DialogFragment {
    public static final String EXTRA_ADD_WORD = "extraWordCreate";
    private DictionaryWord mWord;

    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mEnglishWord;
    private EditText mPersianWord;

    public AddWordDialogFragment() {
        // Required empty public constructor
    }


    public static AddWordDialogFragment newInstance() {
        return  new AddWordDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_word_dialog, container, false);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_add_word_dialog, null);
        initViews(view);
        setListeners();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    private void setListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mWord = new DictionaryWord(UUID.randomUUID().toString(),
                       mPersianWord.getText().toString(),mEnglishWord.getText().toString());
                Fragment fragment = getTargetFragment();
                Intent intent = new Intent();
                intent.putExtra(EXTRA_ADD_WORD, mWord);
                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                fragment.onResume();
                dismiss();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initViews(View view) {
        mEnglishWord = view.findViewById(R.id.word_in_english);
        mPersianWord = view.findViewById(R.id.word_in_persian);
        mSaveButton = view.findViewById(R.id.save_button);
        mCancelButton = view.findViewById(R.id.cancel_button);
    }
}