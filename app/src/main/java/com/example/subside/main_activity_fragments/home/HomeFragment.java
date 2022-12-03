package com.example.subside.main_activity_fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subside.MainActivity;
import com.example.subside.R;
import com.example.subside.db.DatabaseHelper;
import com.example.subside.db.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    RecyclerView leaderBoardRV;
    ArrayList<UserProfile> leaderBoard = new ArrayList<>();
    LeaderBoardRVAdapter leaderBoardAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        leaderBoardRV = view.findViewById(R.id.leaderbrd_recyclerView);
        leaderBoardRV.setHasFixedSize(true);
        leaderBoardRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        leaderBoardRV.addItemDecoration(new DividerItemDecoration(view.getContext(), 0));
        // leaderboard query
        DatabaseHelper.getSortedByFunFact().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = 0;
                long length = snapshot.getChildrenCount();
                for(DataSnapshot child : snapshot.getChildren()){
                    count++;
                    if (count > length - 3) {
                        UserProfile user = child.getValue(UserProfile.class);
                        leaderBoard.add(user);
                    }
                }
                Collections.reverse(leaderBoard);
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }

    private void setAdapter() {
        leaderBoardAdapter = new LeaderBoardRVAdapter(leaderBoard,this.getContext());
        leaderBoardRV.setAdapter(leaderBoardAdapter);
        leaderBoardAdapter.notifyDataSetChanged();
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