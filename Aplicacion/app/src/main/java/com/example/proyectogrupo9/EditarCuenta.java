package com.example.proyectogrupo9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class EditarCuenta extends AppCompatActivity {

    private Spinner spinner1;

    EditText etNombres, etTelefono;

    FirebaseAuth firebaseAuth;

    DatabaseReference dataBase;

    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_cuenta);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        String [] opciones = {"No cambiar","Masculino","Femenino","Otro"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_items_configuraciones, opciones);
        spinner1.setAdapter(adapter);

        etNombres = (EditText) findViewById(R.id.editTeNombres);
        etTelefono = (EditText) findViewById(R.id.editTeTelefono);

        firebaseAuth = FirebaseAuth.getInstance();

        dataBase = FirebaseDatabase.getInstance().getReference();

        id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dataBase.child("Usuarios").child(id_user).child("nombre").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    etNombres.setHint(task.getResult().getValue().toString());
                }
            }
        });

        dataBase.child("Usuarios").child(id_user).child("telefono").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    etTelefono.setHint(task.getResult().getValue().toString());
                }
            }
        });
    }

    public void confirmar(View view){
        String tel = etTelefono.getText().toString();
        String nombre = etNombres.getText().toString();
        String genero = spinner1.getSelectedItem().toString();

        Boolean verificacionDatos = pruebaErrores(tel, nombre);

        if(verificacionDatos){
            Map<String, Object> map = new HashMap<>();

            if(nombre.isEmpty()){
                map.put("nombre", etNombres.getHint());
            }else{
                map.put("nombre", nombre);
            }

            if(tel.isEmpty()){
                map.put("telefono", etTelefono.getHint());
            }else{
                map.put("telefono", tel);
            }

            if(!genero.equals("No cambiar")){
                map.put("genero", genero);
            }

            dataBase.child("Usuarios").child(id_user).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if(task2.isSuccessful()){
                        Toast.makeText(EditarCuenta.this, "Usuario actualizado con exito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditarCuenta.this, PaginaDeInicio.class);
                        startActivity(intent);
                        finish();
                    }else{
                        //Toast.makeText(CreacionNuevoPastillero.this, "Verifique los datos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "Complete todos los datos de forma correcta", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean pruebaErrores(String tel, String nombre){

        int verifNombre = 0;

        for (int indice = 0; indice < nombre.length(); indice++) {
            if (!Character.isAlphabetic((nombre.charAt(indice)))){
                if(!(nombre.charAt(indice) == ' ')){
                    verifNombre++;
                }
            }
        }

        if(verifNombre!=0){
            etNombres.setError("Ingrese un nombre correcto");
            etNombres.requestFocus();
            return false;
        }

        if(((!tel.startsWith("0")) || (tel.charAt(1)!='9')) && tel.length()!=0){
            etTelefono.setError("Ingrese un numero de telefono valido");
            etTelefono.requestFocus();
            return false;
        }

        return true;
    }
}