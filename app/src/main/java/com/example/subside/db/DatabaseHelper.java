package com.example.subside.db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {
    private DatabaseReference databaseReference;
    private static final String DB_PATH = "user-profile";

    public DatabaseHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(DB_PATH);
    }

    public Task<Void> add(String uid, UserProfile userProfile) {
        Map<String, UserProfile> user = new HashMap<>();
        user.put(uid, userProfile);
        return databaseReference.setValue(user);
    }

    public Task<Void> update(String uid, HashMap<String, Object> newValues) {
        return databaseReference.child(uid).updateChildren(newValues);
    }

    public Task<Void> remove(String uid) {
        return databaseReference.child(uid).removeValue();
    }

    public Query get() {
        return databaseReference.orderByKey();
    }

    public DatabaseReference getOne(String uid) {
        return databaseReference.child(uid);
    }

}
