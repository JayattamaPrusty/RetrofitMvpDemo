package com.jayaa.example.retrofitmvpdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jayaa.example.retrofitmvpdemo.R;
import com.jayaa.example.retrofitmvpdemo.model.NewsModel;
import com.jayaa.example.retrofitmvpdemo.utils.RecyclerItemClickListener;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context mcontext;
    private ArrayList<NewsModel.Row> dataList;
    private RecyclerItemClickListener recyclerItemClickListener;

    public HomeAdapter(Context mcontext, ArrayList<NewsModel.Row> dataList, RecyclerItemClickListener recyclerItemClickListener) {
        this.mcontext = mcontext;
        this.dataList = dataList;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {



        holder.txtNoticeTitle.setText(dataList.get(position).getTitle());
        holder.txtNoticeBrief.setText(dataList.get(position).getDescription());

        //Glide.with(mcontext).load(dataList.get(position).getImageHref()).into(holder.iv_newsitem);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.nph);
        requestOptions.error(R.drawable.nph);
        //requestOptions.override(200,200);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.fitCenter();

        Glide.with(mcontext)
                .setDefaultRequestOptions(requestOptions)
                .load(dataList.get(position).getImageHref())
                .into(holder.iv_newsitem);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {


        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNoticeTitle, txtNoticeBrief;
        ImageView iv_newsitem;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtNoticeTitle = itemView.findViewById(R.id.txt_news_title);
            txtNoticeBrief = itemView.findViewById(R.id.txt_news_brief);
            iv_newsitem = itemView.findViewById(R.id.iv_newsitem);
        }
    }
}
