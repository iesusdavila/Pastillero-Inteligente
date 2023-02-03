package com.example.proyectogrupo9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VerificarCuenta extends AppCompatActivity {

    EditText editTeCorreo;

    String correo = "";

    FirebaseAuth mAuth;

    ProgressDialog mDialog;

    DatabaseReference dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verificacion_cuenta);

        editTeCorreo = (EditText) findViewById(R.id.editTeCorreo);

        mAuth = FirebaseAuth.getInstance();

        dataBase = FirebaseDatabase.getInstance().getReference();

        mDialog = new ProgressDialog(this);
    }

    public void restablecerContrasena (View v){
        correo = editTeCorreo.getText().toString();

        if(!correo.isEmpty()){
            mAuth.setLanguageCode("es");
            mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        mDialog.setMessage("Espere por favor....");
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.show();
                        Toast.makeText(VerificarCuenta.this, "Correo enviado para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VerificarCuenta.this, ConfirmacionNuevaContra.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(VerificarCuenta.this, "Email inexistente en la base de datos", Toast.LENGTH_SHORT).show();
                        editTeCorreo.setError("Ingrese un email registrado");
                        editTeCorreo.requestFocus();
                    }
                    mDialog.dismiss();
                }
            });
        }else{
            Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
            editTeCorreo.setError("Ingrese un email valido");
            editTeCorreo.requestFocus();
        }
    }
}