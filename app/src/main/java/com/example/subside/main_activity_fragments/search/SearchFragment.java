package com.example.subside.main_activity_fragments.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.subside.R;
import com.example.subside.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterRecyclerView recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Button major_fbtn;
    Button cohort_fbtn;
    TextView noData;

    String[] fMajors={"All","Computer Science", "Industrial Engineering"};
    String[] fCohort={"All","2019","2020","2021","2022"};
    String majorSelected="All";
    String cohortSelected="All";
    int fMajorspos = 0;
    int fCohortpos = 0;

    ArrayList<ItemModel> data;
    private SearchView searchView;

    private FragmentSearchBinding binding;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerViewLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 0));

        data = new ArrayList<>();
        for (int i = 0; i< MyItem.Name.length; i++){
            data.add(new ItemModel(MyItem.Name[i],
                    MyItem.Major[i],
                    MyItem.iconList[i],
                    MyItem.Id[i],
                    MyItem.Ig[i],
                    MyItem.Email[i],
                    MyItem.Phone[i],
                    MyItem.Linkedin[i]
            ));
        }

        recyclerViewAdapter=new AdapterRecyclerView(data);
        recyclerView.setAdapter(recyclerViewAdapter);

        searchView= view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<ItemModel> itemFilter = new ArrayList<>();
                for (ItemModel model : data){
                    String sname= model.getName().toLowerCase();
                    if (!majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (sname.contains(newText) && model.getMajor().contains(majorSelected) && model.getMajor().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (!majorSelected.equals("All") && cohortSelected.equals("All")) {
                        if (sname.contains(newText) && model.getMajor().contains(majorSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (sname.contains(newText) && model.getMajor().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if(majorSelected.equals("All") && cohortSelected.equals("All")){
                        if (sname.contains(newText)) {
                            itemFilter.add(model);
                        }
                    }
                }
                recyclerViewAdapter.setFilter(itemFilter);
                if(itemFilter.isEmpty()){
                    noData.setVisibility(View.VISIBLE);
                }
                else{
                    noData.setVisibility(View.GONE);
                }
                return false;
            }
        });
        noData = view.findViewById(R.id.nodata_txt);
        major_fbtn = view.findViewById(R.id.major_filterbtn);
        cohort_fbtn = view.findViewById(R.id.cohort_filterbtn);
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
                ArrayList<ItemModel> itemFilter = new ArrayList<>();
                for (ItemModel model : data){
                    if (!majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (model.getMajor().contains(majorSelected) && model.getMajor().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (!majorSelected.equals("All") && cohortSelected.equals("All")) {
                        if (model.getMajor().contains(majorSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (model.getMajor().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if(majorSelected.equals("All") && cohortSelected.equals("All")){
                        itemFilter.add(model);
                    }
                    recyclerViewAdapter.setFilter(itemFilter);
                    if(itemFilter.isEmpty()){
                        noData.setVisibility(View.VISIBLE);
                    }
                    else{
                        noData.setVisibility(View.GONE);
                    }
                }
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
                ArrayList<ItemModel> itemFilter = new ArrayList<>();
                for (ItemModel model : data){
                    if (!majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (model.getMajor().contains(majorSelected) && model.getMajor().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (!majorSelected.equals("All") && cohortSelected.equals("All")) {
                        if (model.getMajor().contains(majorSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if (majorSelected.equals("All") && !cohortSelected.equals("All")) {
                        if (model.getMajor().contains(cohortSelected)) {
                            itemFilter.add(model);
                        }
                    }

                    else if(majorSelected.equals("All") && cohortSelected.equals("All")){
                        itemFilter.add(model);
                    }
                    recyclerViewAdapter.setFilter(itemFilter);
                    if(itemFilter.isEmpty()){
                        noData.setVisibility(View.VISIBLE);
                    }
                    else{
                        noData.setVisibility(View.GONE);
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
