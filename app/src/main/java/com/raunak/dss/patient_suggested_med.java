package com.raunak.dss;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Map;

public class patient_suggested_med extends AppCompatActivity {

    private ListView pListView;
    private ArrayAdapter pAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_suggested_med);
        pListView = findViewById(R.id.suggested_med_list);

        //variable taken from prescribe med
        Intent intent = getIntent();
        String patt_disease = intent.getStringExtra(prescribe_medicine.EXTRA_DISEASE);
        final String dis_stage = intent.getStringExtra(prescribe_medicine.EXTRA_STAGE);

        DocumentReference documentReference = db.collection("medicine").document(patt_disease);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value,@Nullable FirebaseFirestoreException error) {
                // inside the on event method.
                if (error != null) {
                    Toast.makeText(patient_suggested_med.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null && value.exists()) {
                    List<Map<String, Object>> users = (List<Map<String, Object>>) value.get(dis_stage);
                    pAdapter = new ArrayAdapter(patient_suggested_med.this, android.R.layout.simple_list_item_1, users);
                    pListView.setAdapter(pAdapter);
                }
            }
        });
    }
}
