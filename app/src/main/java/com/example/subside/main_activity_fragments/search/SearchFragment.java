package com.example.subside.main_activity_fragments.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private final List<UserProfile> list=new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    boolean needshimmer;

    private SearchView searchView;
    Button major_fbtn;
    Button cohort_fbtn;
    TextView noData;
    String searchText="";

    String[] fMajors={"All","Accounting","Computer Science","English Education","Math Education", "Industrial Engineering",
            "Information System", "Management", "Mechanical Engineering", "Visual Communication Design"};
    String[] fCohort={"All","2019","2020","2021","2022"};
    String majorSelected="All";
    String cohortSelected="All";

    SwipeRefreshLayout dataRefresh;
    internetConnection connection;
    int fMajorspos = 0;
    int fCohortpos = 0;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        if(savedInstanceState!=null){
            needshimmer=savedInstanceState.getBoolean("needshimmer",false);
        }
        else{
            needshimmer=true;
        }

        connection = new internetConnection(this.getContext());
        recyclerView = view.findViewById(R.id.recyclerview);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 0));

        dataRefresh();

        searchView= view.findViewById(R.id.mSearchView);
        noData = view.findViewById(R.id.nodata_txt);
        major_fbtn = view.findViewById(R.id.major_filterbtn);
        cohort_fbtn = view.findViewById(R.id.cohort_filterbtn);

        searchUser();

        major_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                majorDialog();
            }
        });

        cohort_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cohortDialog();
            }
        });

        if (MainActivity.fromHometoSearch) {
            // Set focus on searchview and show keyboard
            searchView.requestFocus();
//            ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, InputMethodManager.RESULT_SHOWN);
            MainActivity.fromHometoSearch = false;
        }

        dataRefresh = view.findViewById(R.id.refreshSearchData);
        dataRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataRefresh();
                dataRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private void dataRefresh(){
        list.clear();
        needshimmer=true;
        shimmerAnimation();
        DatabaseHelper.getAll().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot users: snapshot.getChildren()){
                    UserProfile datas = users.getValue(UserProfile.class);
                    list.add(datas);
                }
                setAdapter();
                searchUser();
                filterUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setAdapter(){
        myAdapter = new MyAdapter(list,this.getContext());
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void majorDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this.getContext());
        builder.setTitle("Filter by Major");
        builder.setSingleChoiceItems(fMajors, fMajorspos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                majorSelected=fMajors[i];
                fMajorspos =i;}
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filterUser();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();

    }

    private void shimmerAnimation(){
        if(needshimmer||!connection.isConnectingToInternet()){
            recyclerView.setVisibility(View.GONE);
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();

            Handler handler2 = new Handler();
            if(connection.isConnectingToInternet()){
                handler2.postDelayed(()->{
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                },2000);}
            else {
                handler2.postDelayed(()->{
                    if(connection.getCloseDialog()){
                        dataRefresh();
                        dataRefresh.setRefreshing(false);
                    };
                },10000);

            }
        }
        else{
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmer();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void cohortDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this.getContext());
        builder.setTitle("Filter by Cohort");
        builder.setSingleChoiceItems(fCohort,fCohortpos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cohortSelected=fCohort[i];
                fCohortpos=i;}
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filterUser();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();

    }

    private void searchUser(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText.toLowerCase();
                if(!searchText.isEmpty()){
                filterUser();}
                return false;
            }
        });
    }

    private void filterUser(){
        ArrayList<UserProfile> itemFilter = new ArrayList<>();
        for (UserProfile model : list){
            if(searchText.isEmpty()) {
                if (!majorSelected.equals("All") && !cohortSelected.equals("All")) {
                    if (model.getMajor().contains(majorSelected) && model.getCohort().contains(cohortSelected)) {
                        itemFilter.add(model);
                    }
                } else if (!majorSelected.equals("All") && cohortSelected.equals("All")) {
                    if (model.getMajor().contains(majorSelected)) {
                        itemFilter.add(model);
                    }
                } else if (majorSelected.equals("All") && !cohortSelected.equals("All")) {
                    if (model.getCohort().contains(cohortSelected)) {
                        itemFilter.add(model);
                    }
                } else if (majorSelected.equals("All") && cohortSelected.equals("All")) {
                    itemFilter.add(model);
                }

            }

            else if(!searchText.isEmpty()){
                String sname= model.getName().toLowerCase();
                if(sname.contains(searchText)){
                    if (!majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (model.getMajor().contains(majorSelected) && model.getCohort().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (!majorSelected.equals("All") && cohortSelected.equals("All")) {
                        if (model.getMajor().contains(majorSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (model.getCohort().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if(majorSelected.equals("All") && cohortSelected.equals("All")){
                        itemFilter.add(model);
                    }
                }
            }
        }
        myAdapter.setFilter(itemFilter);
        if (itemFilter.isEmpty()) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        searchView.setQuery("",false);
        searchView.clearFocus();
        searchText="";
        outState.putBoolean("needshimmer",false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
