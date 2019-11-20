package com.example.dictionaryapp.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.dictionaryapp.DatabaseAccess;
import com.example.dictionaryapp.PraticePageAdapter;
import com.example.dictionaryapp.R;
import com.example.dictionaryapp.Vocabulary;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    ViewPager vpVocabulary;
    PraticePageAdapter mAdapter;
    ArrayList<Vocabulary> mList;
    int mCurrentPage = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
      /*  final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        vpVocabulary = root.findViewById(R.id.vp_Vocabulary);
        mList = new ArrayList<>();
        mList.add(new Vocabulary("School","/sku:l/",R.drawable.ic_keyboard_voice_black_24dp,
                "100","scul"));
        mList.add(new Vocabulary("Hello","/he'lou/",R.drawable.ic_keyboard_voice_black_24dp,
                "80","hallo"));
        mList.add(new Vocabulary("You","/ju:/",R.drawable.ic_keyboard_voice_black_24dp,
                "90","yu"));
        mList.add(new Vocabulary("Radio","/'reidiou/",R.drawable.ic_keyboard_voice_black_24dp,
                "50","reidio"));

        mAdapter = new PraticePageAdapter(getContext(),R.layout.paper_item,mList);
        vpVocabulary.setAdapter(mAdapter);
        vpVocabulary.setCurrentItem(mCurrentPage);
        vpVocabulary.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return root;
    }
}