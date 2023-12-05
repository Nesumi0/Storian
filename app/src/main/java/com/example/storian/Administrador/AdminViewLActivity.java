package com.example.storian.Administrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storian.R;
import com.example.storian.adapter.LugarAdapter;
import com.example.storian.model.LugaresH;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminViewLActivity extends AppCompatActivity {
    Button btn_addL;
    RecyclerView mRecycler;
    LugarAdapter mAdapter;
    FirebaseFirestore mFirestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_lactivity);
        btn_addL = findViewById(R.id.btn_addLogro);
        mRecycler = findViewById(R.id.recycleviewSingle2);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("LugaresH").orderBy("NombreLugar", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<LugaresH> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<LugaresH>().setQuery(query, LugaresH.class).build();

        mAdapter = new LugarAdapter(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btn_addL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminViewLActivity.this, AdminILActivity.class);
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





















