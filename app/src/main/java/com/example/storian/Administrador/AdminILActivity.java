package com.example.storian.Administrador;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.storian.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdminILActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button btn_add;
    EditText name, reseña;
    ImageView imageView;

    private FirebaseFirestore mfirestore;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_iactivity);

        name = findViewById(R.id.nombreLogro);
        reseña = findViewById(R.id.DescipcionLogro);
        btn_add = findViewById(R.id.btn_addLogro);
        imageView = findViewById(R.id.imageLugar);
        mfirestore = FirebaseFirestore.getInstance();
        String id = getIntent().getStringExtra("id_lugar");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        if (id == null || id.equals("")) {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameL = name.getText().toString().trim();
                    String reseñaL = reseña.getText().toString().trim();

                    if (imageUri== null && nameL.isEmpty() && reseñaL.isEmpty()) {
                        Toast.makeText(AdminILActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    } else {
                        postLugar(nameL, reseñaL);
                    }
                }
            });
        } else {
            btn_add.setText("Update");
            getLugar(id);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameL = name.getText().toString().trim();
                    String reseñaL = reseña.getText().toString().trim();

                    if ( nameL.isEmpty() && reseñaL.isEmpty()) {
                        Toast.makeText(AdminILActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    } else {
                        updateLugar(nameL, reseñaL, id);
                    }
                }
            });
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void postLugar(String nameL, String reseñaL) {
        Map<String, Object> map = new HashMap<>();
        map.put("NombreLugar", nameL);
        map.put("Reseña", reseñaL);

        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("imagenes_lugares/" + UUID.randomUUID().toString()); // Ajusta la ruta según tus necesidades
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String urlImagen = uri.toString();
                            map.put("url_imagen_lugar", urlImagen);
                            saveLugarData(map);
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AdminILActivity.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                    });
        } else {
            saveLugarData(map);
        }
    }

    private void saveLugarData(Map<String, Object> map) {
        mfirestore.collection("LugaresH").add(map).addOnSuccessListener(documentReference -> {
            Toast.makeText(AdminILActivity.this, "Ingresado exitosamente!", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(AdminILActivity.this, AdminViewLActivity.class);
            startActivity(intent);
        }).addOnFailureListener(e -> {
            Toast.makeText(AdminILActivity.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateLugar(String nameL, String reseñaL, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("NombreLugar", nameL);
        map.put("Reseña", reseñaL);

        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("imagenes_lugares/" + UUID.randomUUID().toString());
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String urlImagen = uri.toString();
                            map.put("url_imagen_lugar", urlImagen);
                            updateLugarData(map, id);
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AdminILActivity.this, "Error al subir la nueva imagen", Toast.LENGTH_SHORT).show();
                    });
        } else {
            updateLugarData(map, id);
        }
    }

    private void updateLugarData(Map<String, Object> map, String id) {
        mfirestore.collection("LugaresH").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminILActivity.this, "Actualizado exitosamente!", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(AdminILActivity.this, AdminViewLActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminILActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getLugar(String id){
        mfirestore.collection("LugaresH").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nameLugar = documentSnapshot.getString("NombreLugar");
                String ReseñaLugar = documentSnapshot.getString("Reseña");

                name.setText(nameLugar);
                reseña.setText(ReseñaLugar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminILActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();

            }
        });
    }


}


