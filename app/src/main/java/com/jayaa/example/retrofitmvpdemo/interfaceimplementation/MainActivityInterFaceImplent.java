package com.jayaa.example.retrofitmvpdemo.interfaceimplementation;

import com.jayaa.example.retrofitmvpdemo.model.NewsModel;

import java.util.ArrayList;
import java.util.Iterator;

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

            Iterator<NewsModel.Row> iter = newslist.iterator();

            while (iter.hasNext()) {


                NewsModel.Row row=iter.next();

                if(row.getTitle()==null && row.getDescription()==null && row.getImageHref() == null){

                    iter.remove();
                    newslist.remove(row);
                }


            }

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
