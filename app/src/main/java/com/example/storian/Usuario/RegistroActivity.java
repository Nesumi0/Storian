package com.example.storian.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storian.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegistroActivity extends AppCompatActivity {
    Button btn_register;
    EditText nombre, apellido, correoElectronico, FechaNac, ciudad, edad;

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nombre= findViewById(R.id.nomb);
        apellido= findViewById(R.id.Apellido);
        edad=findViewById(R.id.Edad);
        correoElectronico=findViewById(R.id.CorreoElectronico);
        FechaNac=findViewById(R.id.FechaNac);
        ciudad=findViewById(R.id.Ciudad);
        btn_register=findViewById(R.id.btn_registro);

        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nameUser = nombre.getText().toString().trim();
                String apellidoUser = apellido.getText().toString().trim();
                String edadUser = edad.getText().toString().trim();
                String emailUser = correoElectronico.getText().toString().trim();
                String FechaNacUser = FechaNac.getText().toString().trim();
                String CiudadU = ciudad.getText().toString().trim();

                if(nameUser.isEmpty() && apellidoUser.isEmpty() && edadUser.isEmpty() && emailUser.isEmpty() && FechaNacUser.isEmpty() && CiudadU.isEmpty() ){
                    Toast.makeText(RegistroActivity.this, " Complete los datos solicitados ", Toast.LENGTH_SHORT).show();

                }else{
                    registerUser(nameUser,apellidoUser,edadUser,emailUser,FechaNacUser,CiudadU);

                }

            }
        });

    }

    private void registerUser(String nameUser, String apellidoUser, String edadUser, String emailUser, String fechaNacUser, String ciudadU) {

        mAuth.createUserWithEmailAndPassword(emailUser,nameUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id= mAuth.getCurrentUser().getUid();

                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("nombre", nameUser);
                map.put("apellido", apellidoUser);
                map.put("edad", edadUser);
                map.put("email", emailUser);
                map.put("fechanacimiento", fechaNacUser);
                map.put("ciudad", ciudadU);

                mFirestore.collection("Usuarios").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(RegistroActivity.this, LogeoActivity.class));
                        Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistroActivity.this, "Error al registrar.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}


