package com.example.proyectogrupo9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditarPastillero extends AppCompatActivity {

    EditText editTeNomPastillero;
    EditText editTeNomPrimCaja, editTeNomSecCaja, editTeNomTerCaja, editTeNomCuarCaja, editTeNomQuintaCaja;
    EditText editTeCantPrimCaja, editTeCantSecCaja, editTeCantTerCaja, editTeCantCuarCaja, editTeCantQuintaCaja;
    EditText editTeTiempoPrimCaja, editTeTiempoSecCaja, editTeTiempoTerCaja, editTeTiempoCuarCaja, editTeTiempoQuintaCaja;

    FirebaseAuth firebaseAuth;

    DatabaseReference dataBase;

    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_pastillero);

        editTeNomPastillero = findViewById(R.id.editTeNomPastillero);

        editTeNomPrimCaja = findViewById(R.id.editTeNomPrimCaja);
        editTeNomSecCaja = findViewById(R.id.editTeNomSecCaja);
        editTeNomTerCaja = findViewById(R.id.editTeNomTerCaja);
        editTeNomCuarCaja = findViewById(R.id.editTeNomCuarCaja);
        editTeNomQuintaCaja = findViewById(R.id.editTeNomQuintaCaja);

        editTeCantPrimCaja = findViewById(R.id.editTeCantPrimCaja);
        editTeCantSecCaja = findViewById(R.id.editTeCantSecCaja);
        editTeCantTerCaja = findViewById(R.id.editTeCantTerCaja);
        editTeCantCuarCaja = findViewById(R.id.editTeCantCuarCaja);
        editTeCantQuintaCaja = findViewById(R.id.editTeCantQuintaCaja);

        editTeTiempoPrimCaja = findViewById(R.id.editTeTiempoPrimCaja);
        editTeTiempoSecCaja = findViewById(R.id.editTeTiempoSecCaja);
        editTeTiempoTerCaja = findViewById(R.id.editTeTiempoTerCaja);
        editTeTiempoCuarCaja = findViewById(R.id.editTeTiempoCuarCaja);
        editTeTiempoQuintaCaja = findViewById(R.id.editTeTiempoQuintaCaja);

        firebaseAuth = FirebaseAuth.getInstance();

        dataBase = FirebaseDatabase.getInstance().getReference();

        id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dataBase.child("Usuarios").child(id_user).child("pastillero").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataPastillero: dataSnapshot.getChildren()){
                    switch (dataPastillero.getKey()){
                        case "nomPastillero":
                            editTeNomPastillero.setHint(dataPastillero.getValue().toString());
                            break;
                        case "nomPrimCaja":
                            editTeNomPrimCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "nomSecCaja":
                            editTeNomSecCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "nomTerCaja":
                            editTeNomTerCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "nomCuaCaja":
                            editTeNomCuarCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "nomQuinCaja":
                            editTeNomQuintaCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "cantPrimCaja":
                            editTeCantPrimCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "cantSecCaja":
                            editTeCantSecCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "cantTerCaja":
                            editTeCantTerCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "cantCuaCaja":
                            editTeCantCuarCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "cantQuinCaja":
                            editTeCantQuintaCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "tiempoPrimCaja":
                            editTeTiempoPrimCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "tiempoSecCaja":
                            editTeTiempoSecCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "tiempoTerCaja":
                            editTeTiempoTerCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "tiempoCuaCaja":
                            editTeTiempoCuarCaja.setHint(dataPastillero.getValue().toString());
                            break;
                        case "tiempoQuinCaja":
                            editTeTiempoQuintaCaja.setHint(dataPastillero.getValue().toString());
                            break;
                    }
                }

            }
        });
    }

    public void confirmar(View view){
        if(pruebaDeErrores()){
            Map<String, Object> map = new HashMap<>();

            map.put("nomPastillero", llenadoDelMap(editTeNomPastillero));

            map.put("nomPrimCaja", llenadoDelMap(editTeNomPrimCaja));
            map.put("nomSecCaja", llenadoDelMap(editTeNomSecCaja));
            map.put("nomTerCaja", llenadoDelMap(editTeNomTerCaja));
            map.put("nomCuaCaja", llenadoDelMap(editTeNomCuarCaja));
            map.put("nomQuinCaja", llenadoDelMap(editTeNomQuintaCaja));

            map.put("cantPrimCaja", llenadoDelMap(editTeCantPrimCaja));
            map.put("cantSecCaja", llenadoDelMap(editTeCantSecCaja));
            map.put("cantTerCaja", llenadoDelMap(editTeCantTerCaja));
            map.put("cantCuaCaja", llenadoDelMap(editTeCantCuarCaja));
            map.put("cantQuinCaja", llenadoDelMap(editTeCantQuintaCaja));

            map.put("tiempoPrimCaja", llenadoDelMap(editTeTiempoPrimCaja));
            map.put("tiempoSecCaja", llenadoDelMap(editTeTiempoSecCaja));
            map.put("tiempoTerCaja", llenadoDelMap(editTeTiempoTerCaja));
            map.put("tiempoCuaCaja", llenadoDelMap(editTeTiempoCuarCaja));
            map.put("tiempoQuinCaja", llenadoDelMap(editTeTiempoQuintaCaja));

            dataBase.child("Usuarios").child(id_user).child("pastillero").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if(task2.isSuccessful()){
                        Toast.makeText(EditarPastillero.this, "Usuario actualizado con exito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditarPastillero.this, PaginaDeInicio.class);
                        startActivity(intent);
                        finish();
                    }else{
                        //Toast.makeText(CreacionNuevoPastillero.this, "Verifique los datos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public String llenadoDelMap(EditText editTeSelec){
        if(editTeSelec.getText().toString().isEmpty()){
            return editTeSelec.getHint().toString();
        }else{
            return editTeSelec.getText().toString();
        }
    }

    public Boolean pruebaDeErrores(){
        ArrayList<EditText> listaCajas = new ArrayList<>();
        listaCajas.add(editTeNomPrimCaja);
        listaCajas.add(editTeNomSecCaja);
        listaCajas.add(editTeNomTerCaja);
        listaCajas.add(editTeNomCuarCaja);
        listaCajas.add(editTeNomQuintaCaja);

        ArrayList<EditText> listaCantPastillas = new ArrayList<>();
        listaCantPastillas.add(editTeCantPrimCaja);
        listaCantPastillas.add(editTeCantSecCaja);
        listaCantPastillas.add(editTeCantTerCaja);
        listaCantPastillas.add(editTeCantCuarCaja);
        listaCantPastillas.add(editTeCantQuintaCaja);

        ArrayList<EditText> listaTiempoPastillas = new ArrayList<>();
        listaTiempoPastillas.add(editTeTiempoPrimCaja);
        listaTiempoPastillas.add(editTeTiempoSecCaja);
        listaTiempoPastillas.add(editTeTiempoTerCaja);
        listaTiempoPastillas.add(editTeTiempoCuarCaja);
        listaTiempoPastillas.add(editTeTiempoQuintaCaja);

        for(int pos=0;pos<listaCajas.size();pos++){
            if(!listaCajas.get(pos).getText().toString().isEmpty()){

                if(Integer.parseInt(listaCantPastillas.get(pos).getText().toString()) > 50){
                    listaCantPastillas.get(pos).setError("Ingrese una cantidad menor o igual de 50");
                    listaCantPastillas.get(pos).requestFocus();
                    return false;
                }

                if(Integer.parseInt(listaTiempoPastillas.get(pos).getText().toString()) > 48){
                    listaTiempoPastillas.get(pos).setError("No es recomendable tomar pastillas en un intervalo mayor a 48 horas");
                    listaTiempoPastillas.get(pos).requestFocus();
                    return false;
                }

                if(listaCantPastillas.get(pos).getText().toString().isEmpty()){
                    listaCantPastillas.get(pos).setError("Ingrese una cantidad menor de 50");
                    listaCantPastillas.get(pos).requestFocus();
                    return false;
                }
                if(listaTiempoPastillas.get(pos).getText().toString().isEmpty()){
                    listaTiempoPastillas.get(pos).setError("Ingrese el tiempo (en horas)");
                    listaTiempoPastillas.get(pos).requestFocus();
                    return false;
                }
            }else{
                if(!listaCantPastillas.get(pos).getText().toString().isEmpty() && !listaTiempoPastillas.get(pos).getText().toString().isEmpty()){
                    listaCajas.get(pos).setError("No puede ingresar solo la cantidad y tiempo sin el nombre");
                    listaCajas.get(pos).requestFocus();
                    return false;
                }
                if(!listaCantPastillas.get(pos).getText().toString().isEmpty()){
                    listaCajas.get(pos).setError("No puede ingresar solo la cantidad sin el tiempo y nombre");
                    listaCajas.get(pos).requestFocus();
                    listaTiempoPastillas.get(pos).setError("No puede ingresar solo la cantidad sin el tiempo y nombre");
                    listaTiempoPastillas.get(pos).requestFocus();
                    return false;
                }
                if(!listaTiempoPastillas.get(pos).getText().toString().isEmpty()){
                    listaCajas.get(pos).setError("No puede ingresar solo el tiempo sin la cantidad y nombre");
                    listaCajas.get(pos).requestFocus();
                    listaCantPastillas.get(pos).setError("No puede ingresar solo el tiempo sin la cantidad y nombre");
                    listaCantPastillas.get(pos).requestFocus();
                    return false;
                }
            }
        }
        return true;
    }
}