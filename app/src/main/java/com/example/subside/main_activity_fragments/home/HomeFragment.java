package com.example.subside.main_activity_fragments.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.subside.MainActivity;
import com.example.subside.R;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.example.subside.internetConnection;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView leaderBoardRV, featuredRV;
    private final ArrayList<UserProfile> userProfiles = new ArrayList<>();
    private ArrayList<UserProfile> leaderBoard = new ArrayList<>();
    private ArrayList<UserProfile> featuredProfiles = new ArrayList<>();
    private LeaderBoardRVAdapter leaderBoardAdapter;
    private FeaturedRVAdapter featuredAdapter;
    private final int LDR_BRD_LENGTH = 3, FEATURED_LENGTH = 3;
    internetConnection connection;
    ShimmerFrameLayout leaderBoardShimmer;
    ShimmerFrameLayout featuredShimmer;
    SwipeRefreshLayout homeRefresh;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        leaderBoardRV = view.findViewById(R.id.leaderbrd_recyclerView);
        leaderBoardRV.setHasFixedSize(true);
        leaderBoardRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        leaderBoardRV.addItemDecoration(new DividerItemDecoration(view.getContext(), 0));

        featuredRV = view.findViewById(R.id.featured_recyclerView);
        featuredRV.setHasFixedSize(true);
        featuredRV.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));

        connection = new internetConnection(this.getContext());
        leaderBoardShimmer = view.findViewById(R.id.leaderbrd_shimmer);
        featuredShimmer = view.findViewById(R.id.featured_shimmer);

        homeRefresh = view.findViewById(R.id.homeRefresh);

        // userProfile query
        userProfileQuery();

        homeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userProfiles.clear();
                userProfileQuery();
                homeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private void userProfileQuery(){
        shimmerAnimation();
        DatabaseHelper.getSortedByFunFact().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfiles.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    UserProfile user = child.getValue(UserProfile.class);
                    if (!user.isHideAccount()) {
                        userProfiles.add(user);
                    }
                }
                setLeaderBoard();   // add to leaderboard list
                setFeatured();      // add to featured list
                setLeaderBoardAdapter();
                setFeaturedAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void shimmerAnimation(){
        if(userProfiles.isEmpty()||!connection.isConnectingToInternet()){
            leaderBoardRV.setVisibility(View.GONE);
            featuredRV.setVisibility(View.GONE);

            leaderBoardShimmer.setVisibility(View.VISIBLE);
            featuredShimmer.setVisibility(View.VISIBLE);

            leaderBoardShimmer.startShimmer();
            featuredShimmer.startShimmer();

            Handler handler2 = new Handler();
            if(connection.isConnectingToInternet()){
                handler2.postDelayed(()->{
                    leaderBoardShimmer.stopShimmer();
                    leaderBoardShimmer.setVisibility(View.GONE);

                    featuredShimmer.stopShimmer();
                    featuredShimmer.setVisibility(View.GONE);

                    leaderBoardRV.setVisibility(View.VISIBLE);
                    featuredRV.setVisibility(View.VISIBLE);
                },2000);}
            else {
                handler2.postDelayed(()->{
                    if(connection.getCloseDialog()){
                        shimmerAnimation();
                    };
                },10000);

            }
        }
        else{
            leaderBoardShimmer.stopShimmer();
            leaderBoardShimmer.setVisibility(View.GONE);
            leaderBoardRV.setVisibility(View.VISIBLE);
            featuredShimmer.stopShimmer();
            featuredShimmer.setVisibility(View.GONE);
            featuredRV.setVisibility(View.VISIBLE);
        }
    }

    private void setLeaderBoard() {
        int length = userProfiles.size();
        ArrayList<UserProfile> currTopUsers = new ArrayList<>();
        for (int i = 1; i <= LDR_BRD_LENGTH; i++) {
            currTopUsers.add(userProfiles.get(length - i));
        }
        Log.d("SLDKFJSLDKFJS", currTopUsers.toString());
        leaderBoard = currTopUsers;
        setLeaderBoardAdapter();
    }

    private void setFeatured() {
        int length = userProfiles.size();
        ArrayList<UserProfile> currFeatured = new ArrayList<>();
        UserProfile randUser;
        for (int i = 1; i <= FEATURED_LENGTH; i++) {
            randUser = userProfiles.get((int)(Math.random() * length));
            if (randUser.isDisableFeatured() || currFeatured.contains(randUser)) {
                // don't add user, reset this iteration
                i--;
                continue;
            }
            currFeatured.add(randUser);
        }
        featuredProfiles = currFeatured;
        setFeaturedAdapter();
    }

    private void setLeaderBoardAdapter() {
        leaderBoardAdapter = new LeaderBoardRVAdapter(leaderBoard,this.getContext());
        leaderBoardRV.setAdapter(leaderBoardAdapter);
        leaderBoardAdapter.notifyDataSetChanged();
    }

    private void setFeaturedAdapter() {
        featuredAdapter = new FeaturedRVAdapter(featuredProfiles,this.getContext());
        featuredRV.setAdapter(featuredAdapter);
        featuredAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View searchbar = view.findViewById(R.id.mSearchView);

        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.setA();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}