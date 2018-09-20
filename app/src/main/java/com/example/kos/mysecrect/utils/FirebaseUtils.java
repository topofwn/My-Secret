package com.example.kos.mysecrect.utils;

import com.example.kos.mysecrect.data.model.UserD;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FirebaseUtils {
    private static FirebaseFirestore db;


    public static void getInstance(){
        db = FirebaseFirestore.getInstance();
    }


    public static void addNewField(UserD data){
        getInstance();
        DocumentReference col = db.collection("DATA").document(data.getId());
        col.set(data);

    }

    public static void updateData(UserD user){
        getInstance();
       DocumentReference doc =  db.collection("DATA").document(user.getId());
       doc.set(user);

    }

    public static void deleteData(UserD user){
        getInstance();
        user.setListData(new ArrayList<>());
        db.collection("DATA").document(user.getId()).set(user);

    }


}
