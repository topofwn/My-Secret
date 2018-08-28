package com.example.kos.mysecrect.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.kos.mysecrect.utils.AppConstants.ENCRYPTED_KEY;
import static com.example.kos.mysecrect.utils.AppConstants.FIELD_NAME;

public class FirebaseUtils {
    private static FirebaseFirestore db;
    private  static List<DataPWD>  initData;

    public static void getInstance(){
        db = FirebaseFirestore.getInstance();
    }


    public static void getDataFromFirebase(String deviceId){
        getInstance();
  //      initData = new ArrayList<>();
        db.collection("DataPWd")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              Log.d("NOti","My id: "+document.getId()+" and my data: "+document.getData());

                            }

                        } else {
                            Log.d("Noti", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }




    public static void addNewField(DataPWD Ndata){
        getInstance();
        Map< String, Object > newData = new HashMap< >();
        newData.put(FIELD_NAME,Ndata.getFieldName());
        newData.put(ENCRYPTED_KEY,Ndata.getEncrytKey());
        db.collection("DataPWd").document(Ndata.getFieldName()).set(newData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Noti","Add new data success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Noti","Add new data failed");
            }
        });

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
