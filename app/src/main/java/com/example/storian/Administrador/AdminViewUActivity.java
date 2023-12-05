package com.example.storian.Administrador;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storian.R;
import com.example.storian.adapter.UsuarioAdapter;
import com.example.storian.model.usuarios;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminViewUActivity extends AppCompatActivity {
    RecyclerView mRecycler;
    UsuarioAdapter mAdapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_uactivity);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recycleviewSingle2);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("Usuarios");
        FirestoreRecyclerOptions<usuarios> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<usuarios>().setQuery(query, usuarios.class).build();

        mAdapter = new UsuarioAdapter(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}