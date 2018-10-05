package com.jayaa.example.retrofitmvpdemo.interfaceimplementation;

import android.content.Context;
import android.util.Log;

import com.jayaa.example.retrofitmvpdemo.model.NewsModel;
import com.jayaa.example.retrofitmvpdemo.network.RetrofitInstance;
import com.jayaa.example.retrofitmvpdemo.network.RetrofitInterfaceMethodCall;
import com.jayaa.example.retrofitmvpdemo.utils.Utility;

import java.util.ArrayList;

import com.jayaa.example.retrofitmvpdemo.databaseHelper.MyLocalDb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityGetNewsListImplementation implements MainActivityInterfaceAll.GetNewList {


    private Context mcontext;

    public MyLocalDb myLocalDb;

    public MainActivityGetNewsListImplementation(Context mcontext) {
        this.mcontext = mcontext;
        myLocalDb=new MyLocalDb(mcontext);
    }

    @Override
    public void getNewsArrayList(final OnFinishedListener onFinishedListener) {


        if(Utility.isInternetAvailable(mcontext)){

            /** Create handle for the RetrofitInstance interface*/
            RetrofitInterfaceMethodCall service = RetrofitInstance.getRetrofitInstance().create(RetrofitInterfaceMethodCall.class);

            /** Call the method with parameter in the interface to get the notice data*/
            Call<NewsModel> call = service.getNewsData();

            /**Log the URL called*/
            Log.wtf("URL Called", call.request().url() + "");

            call.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {




                    for(NewsModel.Row row:response.body().getRows()){

                        if(row.getTitle()!=null){

                            if(!myLocalDb.isRowExist(row.getTitle(),row.getDescription(),row.getImageHref())){
                                myLocalDb.insertNewsRecords(row.getTitle(),row.getTitle(),row.getDescription(),row.getImageHref());
                            }

                        }
                        myLocalDb.insertNewsRecords(row.getTitle(),row.getTitle(),row.getDescription(),row.getImageHref());

                    }
                    onFinishedListener.onFinished((ArrayList<NewsModel.Row>) response.body().getRows());

                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });

        }else {

            onFinishedListener.onFinished(myLocalDb.getNewsList(mcontext));

        }



    }


}

