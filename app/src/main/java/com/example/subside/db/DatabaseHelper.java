package com.example.subside.db;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DatabaseHelper {
    private static DatabaseReference mRef;
    private static final String PROFILE_PATH = "user-profile";
    private static final String FUN_FACT_PATH = "unlocked-profile";

    public DatabaseHelper() {
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    public static Task<Void> add(String uid, UserProfile userProfile) {
        return mRef.child(PROFILE_PATH).push().setValue(userProfile);
    }

    public static Task<Void> update(String uid, HashMap<String, Object> newValues) {
        return mRef.child(PROFILE_PATH).orderByChild("uid").equalTo(uid).getRef().updateChildren(newValues);
    }

    public static Task<Void> removeOneUserProfile(String uid) {
        return mRef.child(PROFILE_PATH).orderByChild("uid").equalTo(uid).getRef().removeValue();
    }

    public static Query getAllUserProfiles() {
        return mRef.child(PROFILE_PATH).orderByKey();
    }

    public final Query getOneUserProfile(String uid) {
        return mRef.child(PROFILE_PATH).orderByChild("uid").equalTo(uid);
    }

}
