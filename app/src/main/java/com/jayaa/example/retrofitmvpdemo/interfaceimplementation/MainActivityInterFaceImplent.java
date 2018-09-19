package com.jayaa.example.retrofitmvpdemo.interfaceimplementation;

import com.jayaa.example.retrofitmvpdemo.model.NewsModel;

import java.util.ArrayList;

public class MainActivityInterFaceImplent implements MainActivityInterfaceAll.PresentorRequest,MainActivityInterfaceAll.GetNewList.OnFinishedListener {


    private MainActivityInterfaceAll.MainActivityPresentorDisplay mainActivityPresentorDisplay;
    private MainActivityInterfaceAll.GetNewList getNewList;

    public MainActivityInterFaceImplent(MainActivityInterfaceAll.MainActivityPresentorDisplay mainActivityPresentorDisplay, MainActivityInterfaceAll.GetNewList getNewList) {
        this.mainActivityPresentorDisplay = mainActivityPresentorDisplay;
        this.getNewList = getNewList;
    }


    @Override
    public void onDestroy() {
        mainActivityPresentorDisplay = null;
    }

    @Override
    public void onRefreshButtonClick() {

        if(mainActivityPresentorDisplay != null){
            mainActivityPresentorDisplay.showProgress();
        }
        getNewList.getNewsArrayList(this);

    }

    @Override
    public void requestDataFromServer() {
        getNewList.getNewsArrayList(this);
    }

    @Override
    public void onFinished(ArrayList<NewsModel.Row> newslist) {
        if(mainActivityPresentorDisplay != null){
            mainActivityPresentorDisplay.setDataToRecyclerView(newslist);
            mainActivityPresentorDisplay.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mainActivityPresentorDisplay != null){
            mainActivityPresentorDisplay.onResponseFailure(t);
            mainActivityPresentorDisplay.hideProgress();
        }
    }
}
