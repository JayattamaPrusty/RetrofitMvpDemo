package com.jayaa.example.retrofitmvpdemo.utils;


import com.jayaa.example.retrofitmvpdemo.model.NewsModel;

public interface RecyclerItemClickListener {
    void onItemClick(NewsModel.Row row);
}