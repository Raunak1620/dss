package com.raunak.dss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class patient_history extends AppCompatActivity {


//    TextView updatedTV,head;
    private ListView nListView;
    private ArrayAdapter nAdapter;
    public List<Map<String, Object>> suggest_medicine;

    // initializing th variable for firebase firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);

        nListView = findViewById(R.id.listoutput);
        final ArrayList<String> users = new ArrayList<>();

        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add( "Patient Name : " + (String) document.get("patient_name")+"\n\n" + "Patient Disease : " + document.get("patient_disease")
                                        + "\n\n" +"Patient address : "+ document.get("patient_address")+ "\n\n" + "Suggested Medicine : " + document.get("suggested_medicine"));
                                count++;
                            }
                            nAdapter = new ArrayAdapter(patient_history.this,R.layout.list_item, users);
                            nListView.setAdapter(nAdapter);
                            if (count == 0){
                                Toast.makeText(patient_history.this, "No Data Found" , Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(patient_history.this, "No Data Found" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

