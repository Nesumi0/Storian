package com.example.storian.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storian.R;
import com.example.storian.model.usuarios;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsuarioAdapter extends FirestoreRecyclerAdapter<usuarios, UsuarioAdapter.viewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    public UsuarioAdapter(@NonNull FirestoreRecyclerOptions<usuarios> options, Activity activity ) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull usuarios model) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.name.setText(model.getNombre());
        holder.apellido.setText(model.getApellido());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion(id);
            }
        });
    }

    private void mostrarDialogoConfirmacion(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Eliminar Usuario");
        builder.setMessage("Estás seguro de que deseas eliminar este lugar?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser(id);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteUser(String id) {
        mFirestore.collection("Usuarios").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Usuario eliminado correctamente!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "No se pudo eliminar al usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_usuario_single, parent, false);
        return new viewHolder (v);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name, apellido;
        ImageView btn_delete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nombreSingleLogro);
            apellido = itemView.findViewById(R.id.DescripcionSingleLogro);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
