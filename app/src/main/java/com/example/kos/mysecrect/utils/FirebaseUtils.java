package com.example.kos.mysecrect.utils;

import android.util.Log;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.kos.mysecrect.utils.AppConstants.ENCRYPTED_KEY;

public class FirebaseUtils {
    private static FirebaseFirestore db;


    public static void getInstance(){
        db = FirebaseFirestore.getInstance();
    }


    public static void addNewField(UserD data){
        getInstance();
        DocumentReference col = db.collection("DataPWd").document(data.getId());
        col.set(data);

    }

    public static void updateData(UserD user){
        getInstance();
       DocumentReference doc =  db.collection("DataPWd").document(user.getId());
       doc.set(user);

    }

    public static void deleteData(UserD user){
        getInstance();
        user.setListData(new ArrayList<>());
        db.collection("DataPWd").document(user.getId()).set(user);

    }
}
