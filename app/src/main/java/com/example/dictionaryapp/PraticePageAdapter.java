package com.example.dictionaryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class PraticePageAdapter  extends PagerAdapter {
    private Context mContext;
    private int mLayoutId;
    private ArrayList<Vocabulary> mList;
    public PraticePageAdapter(Context context, int layout,ArrayList<Vocabulary> list){
        mContext = context;
        mLayoutId= layout;
        mList = list;
    }
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
      //  Vocabulary vocabulary = new Vocabulary();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(mLayoutId,container,false);
        TextView tvword = layout.findViewById(R.id.tv_Word);
        TextView tv_pronunciation = layout.findViewById(R.id.tv_Pronunciation);
        ImageView imageView = layout.findViewById(R.id.imv);
        TextView score = layout.findViewById(R.id.tv_Score);
        TextView spoken = layout.findViewById(R.id.tv_SpokenWord);

        Vocabulary vocabulary1 = mList.get(position);

        tvword.setText(vocabulary1.getWord());
        tv_pronunciation.setText(vocabulary1.getPronunciation());
        imageView.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);
        score.setText(vocabulary1.getScore());
        spoken.setText(vocabulary1.getSpokenword());
        container.addView(layout);
        return layout;
    }
}
