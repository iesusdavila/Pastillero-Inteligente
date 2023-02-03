package com.example.proyectogrupo9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NuevaContrasena extends AppCompatActivity {

    EditText editTeContraNueva, editTeContraNuevaRep;

    FirebaseAuth firebaseAuth;

    FirebaseUser user;

    DatabaseReference dataBase;

    String id_user, contrasenaUsuario;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_contrasena);

        editTeContraNueva = (EditText) findViewById(R.id.editTeContraNueva);
        editTeContraNuevaRep = (EditText) findViewById(R.id.editTeContraNuevaRep);

        firebaseAuth = FirebaseAuth.getInstance();

        dataBase = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dataBase.child("Usuarios").child(id_user).child("contrasena").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    contrasenaUsuario = task.getResult().getValue().toString();
                    System.out.println(task.getResult().getValue().toString());
                }
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.editTeContraNueva, ".{6,}",R.string.contrasena_invalida);
        awesomeValidation.addValidation(this,R.id.editTeContraNuevaRep, ".{6,}",R.string.contrasena_invalida);
    }

    public void goToMainActivity (View v){
        String contraNueva = editTeContraNueva.getText().toString();
        String contraNuevaRep = editTeContraNuevaRep.getText().toString();

        if(contraNueva.equals(contraNuevaRep) && awesomeValidation.validate()){
            Map<String, Object> map = new HashMap<>();

            map.put("contrasena",contraNueva);

            user.updatePassword(contraNueva).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()){
                        Toast.makeText(NuevaContrasena.this, "Usuario actualizado en la base de datos", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NuevaContrasena.this, PaginaDeInicio.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });

            dataBase.child("Usuarios").child(id_user).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if(task2.isSuccessful()){
                        Toast.makeText(NuevaContrasena.this, "Usuario actualizado con exito", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}