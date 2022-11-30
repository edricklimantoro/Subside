package com.example.subside.main_activity_fragments.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.example.subside.R;
import com.example.subside.databinding.FragmentSearchBinding;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private final List<UserProfile> list=new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    private SearchView searchView;
    Button major_fbtn;
    Button cohort_fbtn;
    TextView noData;

    String[] fMajors={"All","Accounting","Computer Science","English Education","Math Education", "Industrial Engineering",
            "Information System", "Management", "Mechanical Engineering", "Visual Communication Design"};
    String[] fCohort={"All","2019","2020","2021","2022"};
    String majorSelected="All";
    String cohortSelected="All";
    int fMajorspos = 0;
    int fCohortpos = 0;

    private FragmentSearchBinding binding;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 0));

        DatabaseHelper.getAll().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot users: snapshot.getChildren()){
                    UserProfile datas = users.getValue(UserProfile.class);
                    list.add(datas);
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView= view.findViewById(R.id.searchView);
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

        return view;
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
                searchUser();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();

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
                searchUser();
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
                newText = newText.toLowerCase();
                if(!newText.isEmpty()){
                    ArrayList<UserProfile> itemFilter = new ArrayList<>();
                    for (UserProfile model: list){
                        String sname= model.getName().toLowerCase();
                        if(sname.contains(newText)){
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
                    myAdapter.setFilter(itemFilter);

                    if(itemFilter.isEmpty()){
                        noData.setVisibility(View.VISIBLE);
                    }
                    else{
                        noData.setVisibility(View.GONE);
                    }}
                return false;
            }
        });
    }

    private void filterUser(){
        ArrayList<UserProfile> itemFilter = new ArrayList<>();
        for (UserProfile model : list){
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
            myAdapter.setFilter(itemFilter);
            if(itemFilter.isEmpty()){
                noData.setVisibility(View.VISIBLE);
            }
            else{
                noData.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
