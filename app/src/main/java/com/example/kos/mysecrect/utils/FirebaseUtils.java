package com.example.kos.mysecrect.utils;

import android.util.Log;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.kos.mysecrect.utils.AppConstants.ENCRYPTED_KEY;

public class FirebaseUtils {
    private static FirebaseFirestore db;


    public static void getInstance(){
        db = FirebaseFirestore.getInstance();
    }


    public static List<DataPWD> getDataFromFirebase(){
      CollectionReference col = FirebaseFirestore.getInstance().collection("DataPWd");

       List<DataPWD> myArray = new ArrayList<>();
            col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                        DataPWD data = document.toObject(DataPWD.class);
                        myArray.add(data);
                    }
                }

            });



return myArray;
    }




    public static void addNewField(DataPWD Ndata){
        getInstance();
//        Map< String, Object > newData = new HashMap< >();
//        newData.put(FIELD_NAME,Ndata.getFieldName());
//        newData.put(ENCRYPTED_KEY,Ndata.getEncrytKey());
//        db.collection("DataPWd").document(Ndata.getFieldName()).set(newData)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("Noti","Add new data success");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("Noti","Add new data failed");
//            }
//        });
        CollectionReference col = db.collection("DataPWd");
        col.add(Ndata);

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
