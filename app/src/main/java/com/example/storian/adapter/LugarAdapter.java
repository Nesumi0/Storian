package com.example.storian.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.storian.Administrador.AdminILActivity;
import com.example.storian.R;
import com.example.storian.model.LugaresH;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LugarAdapter extends FirestoreRecyclerAdapter<LugaresH, LugarAdapter.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private Activity activity;

    public LugarAdapter(@NonNull FirestoreRecyclerOptions<LugaresH> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull LugaresH model) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        String nombreLugar = model.getNombreLugar();
        String reseña = model.getReseña();
        String urlImagenLugar = model.getUrlImagenLugar();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String emailUser = currentUser.getEmail();

        if (nombreLugar != null) {
            holder.name.setText(nombreLugar);
        }

        if (reseña != null) {
            holder.reseña.setText(reseña);
        }

        if (urlImagenLugar != null && !urlImagenLugar.isEmpty()) {
            Glide.with(activity)
                    .load(urlImagenLugar)
                    .into(holder.imageLugar);

        }
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, AdminILActivity.class);
                i.putExtra("id_lugar", id);
                activity.startActivity(i);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailUser.equals("autor@gmail.com")) {
                    Toast.makeText(activity, "Opción deshabilitada", Toast.LENGTH_SHORT).show();
                } else {
                    mostrarDialogoConfirmacion(id);
                }
            }
        });
    }

    private void mostrarDialogoConfirmacion(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Eliminar Lugar");
        builder.setMessage("Estás seguro de que deseas eliminar este lugar?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteLugar(id);
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

    private void deleteLugar(String id) {
        mFirestore.collection("LugaresH").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Lugar eliminado correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "No se pudo eliminar el lugar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lugar_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, reseña;
        ImageView btn_delete, btn_edit;
        ImageView imageLugar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nombreSingleLogro);
            reseña = itemView.findViewById(R.id.DescripcionSingleLogro);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);
            btn_edit = itemView.findViewById(R.id.btn_editar);
            imageLugar = itemView.findViewById(R.id.ImageLugarSingle);
        }
    }
}







