package com.example.storian.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storian.Administrador.AdminActivity;
import com.example.storian.Administrador.AdminViewLActivity;
import com.example.storian.Inicio;
import com.example.storian.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogeoActivity extends AppCompatActivity {
    Button btn_login ,btn_register;
    EditText emailU, password;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logeo);

        btn_login = (Button) findViewById(R.id.btn_login);
        password = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        emailU = (EditText) findViewById(R.id.email);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailUser = emailU.getText().toString().trim();
                String passwordUser = password.getText().toString().trim();

                if (emailUser.isEmpty() && passwordUser.isEmpty()) {
                    Toast.makeText(LogeoActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();

                } else {
                    loginUser(emailUser, passwordUser);
                }

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogeoActivity.this, "Bienvenido al registro", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogeoActivity.this, RegistroActivity.class));

            }
        });
    }

    private void loginUser(String emailUser, String passwordUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (emailUser.equals("admin@gmail.com")) {
                        startActivity(new Intent(LogeoActivity.this, AdminActivity.class));
                        Toast.makeText(LogeoActivity.this, "Bienvenido a la vista admin!", Toast.LENGTH_SHORT).show();
                    } else if (emailUser.equals("editor@gmail.com")) {
                        startActivity(new Intent(LogeoActivity.this, AdminViewLActivity.class));
                        Toast.makeText(LogeoActivity.this, "Bienvenido a la vista de editor!", Toast.LENGTH_SHORT).show();
                    } else if (emailUser.equals("autor@gmail.com")) {
                        startActivity(new Intent(LogeoActivity.this, AdminViewLActivity.class));
                        Toast.makeText(LogeoActivity.this, "Bienvenido a la vista de autor!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LogeoActivity.this, Inicio.class));
                        Toast.makeText(LogeoActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(LogeoActivity.this, "No se encuentra este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LogeoActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

}