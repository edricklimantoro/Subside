package com.example.subside.db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DatabaseHelper {
    private static DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    private static final String PROFILE_PATH = "user-profile";

    public DatabaseHelper() {
    }

    public static Task<Void> add(String uid, UserProfile userProfile) {
        return mRef.child(PROFILE_PATH).child(uid).setValue(userProfile);
    }

    public static Task<Void> update(String uid, HashMap<String, Object> newValues) {
        return mRef.child(PROFILE_PATH).child(uid).updateChildren(newValues);
    }

    public static Task<Void> removeOne(String uid) {
        return mRef.child(PROFILE_PATH).child(uid).removeValue();
    }

    public static Query getAll() {
        return mRef.child(PROFILE_PATH).orderByKey();
    }

    public static DatabaseReference getOne(String uid) {
        return mRef.child(PROFILE_PATH).child(uid);
    }

}
