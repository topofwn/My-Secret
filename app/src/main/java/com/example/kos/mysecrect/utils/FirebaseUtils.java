package com.example.kos.mysecrect.utils;

import android.util.Log;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public static void updateData(String field, String newKey){
        getInstance();
       DocumentReference doc =  db.collection("DataPWd").document(field);
       doc.update(ENCRYPTED_KEY,newKey).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               Log.d("Noti","Updated successfully");
           }
       });
    }

    public static void deleteData(String field){
        getInstance();
        db.collection("DataPWd").document(field).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Noti","Deleted Successfully");
            }
        });
    }
}
