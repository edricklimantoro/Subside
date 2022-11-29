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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    private final List<ItemModel> list=new ArrayList<>();

    private FragmentSearchBinding binding;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 0));

        recyclerView.setAdapter(new MyAdapter(list,this.getContext()));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot users: snapshot.child("user-profile").getChildren()){
                    String getProfPictUri = users.child("profPictUri").getValue(String.class);
                    String getName = users.child("name").getValue(String.class);
                    String getMajor = users.child("major").getValue(String.class);
                    String getFaculty = users.child("faculty").getValue(String.class);
                    String getCohort = users.child("cohort").getValue(String.class);
                    String getSid = users.child("sid").getValue(String.class);
                    String getInstagram = users.child("instagram").getValue(String.class);
                    String getEmail = users.child("email").getValue(String.class);
                    String getPhoneNum = users.child("phoneNum").getValue(String.class);
                    String getLinkedin = users.child("linkedIn").getValue(String.class);
                    String getFunfact = users.child("funFact").getValue(String.class);
                    ItemModel datas = new ItemModel(getProfPictUri,getName,getMajor,getFaculty,getCohort,getSid,getInstagram,getEmail,getPhoneNum,getLinkedin,getFunfact);
                    list.add(datas);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
