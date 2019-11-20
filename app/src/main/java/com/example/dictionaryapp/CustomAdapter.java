package com.example.dictionaryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<AnhvietModel> data ;
    private Context mContext;


    public CustomAdapter(Context mContext,List<AnhvietModel> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        /*LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);*/
      // LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new RecyclerViewHolder(view);
        } else  if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return  null;
    }
    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewHolder) {
            populateItemRows((RecyclerViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }
    @Override
    public int getItemCount() {
       // return data.size();
       return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void filterList(ArrayList<AnhvietModel> newvocabulary) {
        data = newvocabulary;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName,txtUsername2;
        LinearLayout line;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtUserName =  itemView.findViewById(R.id.tv_Name);
            txtUsername2 =  itemView.findViewById(R.id.tv_Name2);
            line =  itemView.findViewById(R.id.line);
        }
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(final RecyclerViewHolder viewHolder, int position) {
        AnhvietModel item = data.get(position);
        viewHolder.txtUserName.setText(item.getName1());
        viewHolder.txtUsername2.setText(item.getName2());
       // viewHolder.txtUserName.setText(data.get(position));
        viewHolder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = viewHolder.txtUserName.getText().toString();
                Intent intent = new Intent(mContext,WebviewMain.class);
                Bundle bundle = new Bundle();
                bundle.putString("chuoi",s );
                intent.putExtra("package",bundle);
                mContext.startActivity(intent);
            }
        });
    }
    public interface ILoadmore {
        void onLoadMore();
    }

    public interface OnItemClickedListener {
        void onItemClick(String username);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}

