package com.raunak.dss;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class prescribe_medicine extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText pat_name_edt,pat_age_edt,pat_address_edt,pat_phone_edt;
    private RadioGroup rad_btn;
    private Spinner disease_btn, stage_btn;
    private Button submit_btn;
    private String patient_name, patient_gender, patient_disease, patient_address, disease_stage,
            patient_age, patient_phone;
    private String suggested_medicine;

    public static final String EXTRA_DISEASE = "disease";
    public static final String EXTRA_STAGE = "stage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescribe_medicine);

        pat_name_edt = findViewById(R.id.pat_name);
        pat_age_edt = findViewById(R.id.pat_age);
        pat_address_edt = findViewById(R.id.pat_address);
        pat_phone_edt = findViewById(R.id.pat_phone);

        disease_btn = findViewById(R.id.symptoms);
        stage_btn = findViewById(R.id.stage_score);

        rad_btn = findViewById(R.id.pat_gen);
        submit_btn = findViewById(R.id.pat_submit);

        final String[] stage = { "High", "Medium", "Low"};
        Spinner spin2 = findViewById(R.id.stage_score);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, R.layout.spinner_padding, stage);
        adapter2.setDropDownViewResource(R.layout.spinner_padding);
        spin2.setAdapter(adapter2);

        //storing value got from radio button
        rad_btn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton radioButton = findViewById(checkedId);
                patient_gender = radioButton.getText().toString();
            }
        }
        );

        //event after clicking on submit button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint( "ResourceType" )
            @Override
            public void onClick(View view) {
                 patient_name = pat_name_edt.getText().toString();
                 patient_age = pat_age_edt.getText().toString();
                 patient_address = pat_address_edt.getText().toString();
                 patient_phone = pat_phone_edt.getText().toString();
                 patient_disease = disease_btn.getSelectedItem().toString();
                 disease_stage = stage_btn.getSelectedItem().toString();

                if (TextUtils.isEmpty(patient_name)){
                    pat_name_edt.setError("Please Enter Patient Name");
                }else if (TextUtils.isEmpty(patient_age)){
                    pat_age_edt.setError("Please Enter Patient Age");
                }else if (TextUtils.isEmpty(patient_phone)){
                    pat_phone_edt.setError("Please Enter Patient Phone Number");
                }else if (TextUtils.isEmpty(patient_address)){
                    pat_address_edt.setError("Please Select Stage");
                }else if(rad_btn.getCheckedRadioButtonId()<=0 ){
                    Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference documentReference = db.collection("medicine").document(patient_disease);
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value,@Nullable FirebaseFirestoreException error) {
                            // inside the on event method.
                            String suggested_medicine = "";
                            if (error != null) {
                                Toast.makeText(prescribe_medicine.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (value != null && value.exists()) {
                                suggested_medicine = value.get(disease_stage).toString();
                                addDatatoFirestore(patient_name, patient_gender, patient_disease, patient_address,disease_stage,patient_age, patient_phone,suggested_medicine);
                            }
                        }
                    });
                }
            }
        });
        db.collection("medicine").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }
                Spinner spin = findViewById(R.id.symptoms);
                ArrayAdapter<String> adapter = new ArrayAdapter(prescribe_medicine.this, R.layout.spinner_padding, ids);
                spin.setAdapter(adapter);
            }
        });

    }

    public void addDatatoFirestore(String patient_name,String patient_gender,final String patient_disease,String patient_address,
                                    final String disease_stage,String patient_age,String patient_phone, String suggested_medicine) {
        CollectionReference dbuser = db.collection("user");

        patient_data pat_data = new patient_data(patient_name,patient_gender, patient_disease,
                patient_address, disease_stage,patient_age, patient_phone,suggested_medicine);

        dbuser.add(pat_data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
//               Toast.makeText(prescribe_medicine.this, "Your Data has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(prescribe_medicine.this, patient_suggested_med.class);
               intent.putExtra(EXTRA_DISEASE, patient_disease);
               intent.putExtra(EXTRA_STAGE, disease_stage);
               startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(prescribe_medicine.this, "Fail to add Data \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

