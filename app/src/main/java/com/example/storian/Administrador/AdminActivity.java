package com.example.storian.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storian.R;

public class AdminActivity extends AppCompatActivity {
    Button btn_Lugar, btn_Usuario, btn_logros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btn_Lugar = findViewById(R.id.btn_lugares);
        btn_Usuario = findViewById(R.id.btn_usuarios);
        btn_logros = findViewById(R.id.btn_logros);

        btn_Lugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminViewLActivity.class);
                Toast.makeText(AdminActivity.this, "Bienvenido a interfaz Lugares", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        btn_Usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Bienvenido a intertaz usuarios!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminActivity.this, AdminViewUActivity.class);
                startActivity(intent);

            }
        });
        btn_logros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Bienvenido a interfaz logros", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminActivity.this, AdminViewLoActivity.class);
                startActivity(intent);
            }
        });

    }

}