package com.example.storian.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storian.R;
import com.example.storian.adapter.LogrosAdapter;
import com.example.storian.model.Logros;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminViewLoActivity extends AppCompatActivity {
    Button btn_addLogro;
    RecyclerView mRecycler;
    LogrosAdapter mAdapter;

    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_lo_activity);
        btn_addLogro = findViewById(R.id.btn_agregarLo2);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recicleViewSingle4);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("Logros").orderBy("NombreLogro", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Logros> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Logros>().setQuery(query, Logros.class).build();

        mAdapter = new LogrosAdapter(firestoreRecyclerOptions,this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);



        btn_addLogro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminViewLoActivity.this, AdminILogrosActivity.class);
                startActivity(intent);
            }
        });
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