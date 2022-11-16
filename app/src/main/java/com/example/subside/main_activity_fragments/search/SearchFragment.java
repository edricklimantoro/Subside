package com.example.subside.main_activity_fragments.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                    String name= model.getName().toLowerCase();
                    if (name.contains(newText)){
                        itemFilter.add(model);
                    }
                }
                recyclerViewAdapter.setFilter(itemFilter);

                return false;
            }
        });


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
