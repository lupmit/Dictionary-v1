package com.example.dictionaryapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.AnhvietModel;
import com.example.dictionaryapp.CustomAdapter;
import com.example.dictionaryapp.DatabaseAccess;
import com.example.dictionaryapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.dictionaryapp.R.layout.fragment_home;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ArrayList<AnhvietModel> vocabulary;
    ArrayList<AnhvietModel> vocabulary2 ;


    DatabaseAccess dbAccess;
    AutoCompleteTextView autoCompleteTextView;
    FloatingActionButton floatingActionButton;
    private  static final int REQUEST_CODE =1000;
    boolean isLoading = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(fragment_home, container, false);
       /* final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

       ImageView imageView = root.findViewById(R.id.img_voice);
       imageView.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);

       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               speak();
           }
       });

        floatingActionButton = root.findViewById(R.id.fab);
        recyclerView = root.findViewById(R.id.recycler_View);
        vocabulary = new ArrayList<>();
        dbAccess = DatabaseAccess.getInstance(getActivity());
        dbAccess.open();
        vocabulary = dbAccess.getWords(0, 50);




        customAdapter = new CustomAdapter(getContext(),vocabulary);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);

        initScrollListener();



        autoCompleteTextView = root.findViewById(R.id.atv_Search);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return root;
    }
    private void speak(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Bạn hãy nói gì đó để tìm kiếm");
        try{
            startActivityForResult(intent,REQUEST_CODE);

        }catch (Exception e){
            Toast.makeText(getActivity(),"" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE : {
                if(resultCode == getActivity().RESULT_OK && null !=data){
                    ArrayList<String> s = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    autoCompleteTextView.setText(s.get(0));
                }
                break;
            }
        }
    }

    private void filter(String text){
        ArrayList<AnhvietModel> newvocabulary = new ArrayList<>();
        for(AnhvietModel s : vocabulary){
           if(s.getName1().toLowerCase().contains(text.toLowerCase())){
               newvocabulary.add(s);
           }
        }
        customAdapter.filterList(newvocabulary);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == vocabulary.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });

    }
    private void loadMore() {
        vocabulary.add(null);
        customAdapter.notifyItemInserted(vocabulary.size() - 1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                vocabulary.remove(vocabulary.size() - 1);
                int scrollPosition = vocabulary.size();
                customAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 20;

                vocabulary2 = dbAccess.getWords(currentSize, nextLimit);

                for (int i = 1; i < 20; i++) {
                    vocabulary.add(vocabulary2.get(i));
                }
                customAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }
}