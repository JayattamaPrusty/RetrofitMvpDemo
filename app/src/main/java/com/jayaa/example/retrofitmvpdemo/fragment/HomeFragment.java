package com.jayaa.example.retrofitmvpdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jayaa.example.retrofitmvpdemo.MyApplication;
import com.jayaa.example.retrofitmvpdemo.R;
import com.jayaa.example.retrofitmvpdemo.adapter.HomeAdapter;
import com.jayaa.example.retrofitmvpdemo.interfaceimplementation.MainActivityGetNewsListImplementation;
import com.jayaa.example.retrofitmvpdemo.interfaceimplementation.MainActivityInterFaceImplent;
import com.jayaa.example.retrofitmvpdemo.interfaceimplementation.MainActivityInterfaceAll;
import com.jayaa.example.retrofitmvpdemo.model.NewsModel;
import com.jayaa.example.retrofitmvpdemo.utils.ConnectivityReceiver;
import com.jayaa.example.retrofitmvpdemo.utils.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MainActivityInterfaceAll.MainActivityPresentorDisplay,SwipeRefreshLayout.OnRefreshListener ,ConnectivityReceiver.ConnectivityReceiverListener {


    View view;
    private ProgressBar progressBar;
    @BindView(R.id.rv_notice)
    RecyclerView rv_notice;

    @Override
    public void onResume() {
        super.onResume();

        // register connection status listener
       // MyApplication.getInstance().setConnectivityListener((ConnectivityReceiver.ConnectivityReceiverListener) getActivity());
    }

    @BindView(R.id.srl_homecontainer)SwipeRefreshLayout swipeRefreshLayout;

    private MainActivityInterfaceAll.PresentorRequest presenter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //butterknie bind to view
        ButterKnife.bind(this, view);
        //initialize views
        initializeviews();

        //initialize loader
        initProgressBar();


        presenter = new MainActivityInterFaceImplent(this, new MainActivityGetNewsListImplementation(getActivity()));

        presenter.requestDataFromServer();
        return view;
    }

    private void initProgressBar() {

        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        getActivity().addContentView(relativeLayout, params);
    }

    private void initializeviews() {

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_notice.setLayoutManager(layoutManager);
    }

    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<NewsModel.Row> newslist) {


        rv_notice.setAdapter(new HomeAdapter(getActivity(),newslist,recyclerItemClickListener ));
    }

    /**
     * RecyclerItem click event listener
     * */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(NewsModel.Row notice) {

            Toast.makeText(getActivity(),
                    "List title:  " + notice.getTitle(),
                    Toast.LENGTH_LONG).show();

        }
    };


    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(getActivity(),
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(false);

        presenter.onRefreshButtonClick();

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        Toast.makeText(getActivity(), ""+isConnected,Toast.LENGTH_SHORT).show();

    }
}
