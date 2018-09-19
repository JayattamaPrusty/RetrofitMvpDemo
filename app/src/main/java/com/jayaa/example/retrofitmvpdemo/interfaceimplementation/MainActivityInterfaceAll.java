package com.jayaa.example.retrofitmvpdemo.interfaceimplementation;

import com.jayaa.example.retrofitmvpdemo.model.NewsModel;

import java.util.ArrayList;

public interface MainActivityInterfaceAll {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface PresentorRequest{

        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();
    }


    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    interface MainActivityPresentorDisplay{
        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(ArrayList<NewsModel.Row> newslist);

        void onResponseFailure(Throwable throwable);

    }

    interface GetNewList{

        interface OnFinishedListener {
            void onFinished(ArrayList<NewsModel.Row> newslist);
            void onFailure(Throwable t);
        }

        void getNewsArrayList(OnFinishedListener onFinishedListener);
    }
}
