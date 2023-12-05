package com.example.storian.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storian.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminILogrosActivity extends AppCompatActivity {
    Button btn_addl;
    EditText name,description;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ilogros);

        btn_addl = findViewById(R.id.btn_addLogro);
        name = findViewById(R.id.nombreLogro);
        description = findViewById(R.id.DescipcionLogro);
        mFirestore = FirebaseFirestore.getInstance();
        String id = getIntent().getStringExtra("id_logro");

        if (id ==null || id ==""){
            btn_addl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameL = name.getText().toString().trim();
                    String descL = description.getText().toString().trim();

                    if (nameL.isEmpty() && descL.isEmpty()){
                        Toast.makeText(AdminILogrosActivity.this, "Por favor rellene los datos", Toast.LENGTH_SHORT).show();
                    }else {
                        postLogro( nameL, descL);
                    }
                }
            });
        }else{
            btn_addl.setText("Update");
            getLogro(id);
            btn_addl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameL = name.getText().toString().trim();
                    String descL = description.getText().toString().trim();

                    if (nameL.isEmpty() && descL.isEmpty()){
                        Toast.makeText(AdminILogrosActivity.this, "Por favor rellene los datos", Toast.LENGTH_SHORT).show();
                    }else {
                        updateLogro( nameL, descL,id);
                    }

                }
            });
        }
    }
    private void updateLogro(String nameL, String descL, String id) {
        Map<String, Object> map= new HashMap<>();
        map.put("NombreLogro",nameL);
        map.put("DescripcionLogro", descL);

        mFirestore.collection("Logros").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminILogrosActivity.this, "Logro actualizado exitosamente!", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(AdminILogrosActivity.this, AdminViewLoActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminILogrosActivity.this, "Error al actualizar logro", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void postLogro(String nameL, String descL) {
        Map<String, Object> map= new HashMap<>();
        map.put("NombreLogro",nameL);
        map.put("DescripcionLogro", descL);

        mFirestore.collection("Logros").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AdminILogrosActivity.this, "Logro Agregado exitosamente!", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(AdminILogrosActivity.this, AdminViewLoActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminILogrosActivity.this, "Error al ingresar logro", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getLogro(String id){
        mFirestore.collection("Logros").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nameLogro = documentSnapshot.getString("NombreLogro");
                String descLogro = documentSnapshot.getString("DescripcionLogro");
                name.setText(nameLogro);
                description.setText(descLogro);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminILogrosActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });
    }
}