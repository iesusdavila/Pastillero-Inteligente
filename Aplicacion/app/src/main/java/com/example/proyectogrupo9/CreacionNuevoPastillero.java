package com.example.proyectogrupo9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreacionNuevoPastillero extends AppCompatActivity {

    EditText editTeNomPastillero;
    EditText editTeNomPrimCaja, editTeNomSecCaja, editTeNomTerCaja, editTeNomCuarCaja, editTeNomQuintaCaja;
    EditText editTeCantPrimCaja, editTeCantSecCaja, editTeCantTerCaja, editTeCantCuarCaja, editTeCantQuintaCaja;
    EditText editTeTiempoPrimCaja, editTeTiempoSecCaja, editTeTiempoTerCaja, editTeTiempoCuarCaja, editTeTiempoQuintaCaja;

    FirebaseAuth firebaseAuth;

    DatabaseReference dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_nuevo_pastillero);

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
    }

    public void goToPaginaDeInicio(View v){
        String nomPastillero = editTeNomPastillero.getText().toString();

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

        String id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Usuarios").child(id_user);

        if(verfNomNoVacio(nomPastillero) && verfNomCajasNoVacias(listaCajas)){
            if(verfTiemCantNoVacias(listaCajas,listaCantPastillas,listaTiempoPastillas)){

                Map<String, Object> map = new HashMap<>();

                map.put("nomPastillero", nomPastillero);

                map.put("nomPrimCaja", llenadoDelMap(editTeNomPrimCaja));
                map.put("nomSecCaja", llenadoDelMap(editTeNomSecCaja));
                map.put("nomTerCaja", llenadoDelMap(editTeNomTerCaja));
                map.put("nomCuaCaja", llenadoDelMap(editTeNomCuarCaja));
                map.put("nomQuinCaja", llenadoDelMap(editTeNomQuintaCaja));

                map.put("cantPrimCaja", llenadoDelMapTiemyCant(editTeCantPrimCaja));
                map.put("cantSecCaja", llenadoDelMapTiemyCant(editTeCantSecCaja));
                map.put("cantTerCaja", llenadoDelMapTiemyCant(editTeCantTerCaja));
                map.put("cantCuaCaja", llenadoDelMapTiemyCant(editTeCantCuarCaja));
                map.put("cantQuinCaja", llenadoDelMapTiemyCant(editTeCantQuintaCaja));

                map.put("tiempoPrimCaja", llenadoDelMapTiemyCant(editTeTiempoPrimCaja));
                map.put("tiempoSecCaja", llenadoDelMapTiemyCant(editTeTiempoSecCaja));
                map.put("tiempoTerCaja", llenadoDelMapTiemyCant(editTeTiempoTerCaja));
                map.put("tiempoCuaCaja", llenadoDelMapTiemyCant(editTeTiempoCuarCaja));
                map.put("tiempoQuinCaja", llenadoDelMapTiemyCant(editTeTiempoQuintaCaja));

                dataBase.child("Usuarios").child(id_user).child("pastillero").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            Toast.makeText(CreacionNuevoPastillero.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreacionNuevoPastillero.this, PaginaDeInicio.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(CreacionNuevoPastillero.this, "Verifique los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    public  Boolean verfNomNoVacio(String nomPastillero){
        if(!nomPastillero.isEmpty()){
                return true;
        }
        editTeNomPastillero.setError("Ingrese un nombre");
        editTeNomPastillero.requestFocus();
        return false;
    }


    public Boolean verfNomCajasNoVacias(ArrayList<EditText> listNomCajas){
        String nomCaja1 = listNomCajas.get(0).getText().toString(), nomCaja2 = listNomCajas.get(1).getText().toString(),
                nomCaja3 = listNomCajas.get(2).getText().toString(), nomCaja4 = listNomCajas.get(3).getText().toString(),
                nomCaja5 = listNomCajas.get(4).getText().toString();
        if(!nomCaja1.isEmpty() || !nomCaja2.isEmpty() || !nomCaja3.isEmpty() || !nomCaja4.isEmpty() || !nomCaja5.isEmpty()){
            return true;
        }

        listNomCajas.get(0).setError("Minimo debe de llenar 1 caja");
        listNomCajas.get(0).requestFocus();

        listNomCajas.get(1).setError("Minimo debe de llenar 1 caja");
        listNomCajas.get(1).requestFocus();

        listNomCajas.get(2).setError("Minimo debe de llenar 1 caja");
        listNomCajas.get(2).requestFocus();

        listNomCajas.get(3).setError("Minimo debe de llenar 1 caja");
        listNomCajas.get(3).requestFocus();

        listNomCajas.get(4).setError("Minimo debe de llenar 1 caja");
        listNomCajas.get(4).requestFocus();
        return false;
    }

    public Boolean verfTiemCantNoVacias(ArrayList<EditText> listNomCajas, ArrayList<EditText> listCantCajas, ArrayList<EditText> listTiempoCajas){
        for(int pos=0;pos<listNomCajas.size();pos++){
            if(!listNomCajas.get(pos).getText().toString().isEmpty()){
                if(Integer.parseInt(listCantCajas.get(pos).getText().toString()) > 50){
                    listCantCajas.get(pos).setError("Ingrese una cantidad menor o igual de 50");
                    listCantCajas.get(pos).requestFocus();
                    return false;
                }

                if(Integer.parseInt(listTiempoCajas.get(pos).getText().toString()) > 48){
                    listTiempoCajas.get(pos).setError("No es recomendable tomar pastillas en un intervalo mayor a 48 horas");
                    listTiempoCajas.get(pos).requestFocus();
                    return false;
                }
                if(listCantCajas.get(pos).getText().toString().isEmpty()){
                    listCantCajas.get(pos).setError("Ingrese una cantidad menor de 50");
                    listCantCajas.get(pos).requestFocus();
                    return false;
                }
                if(listTiempoCajas.get(pos).getText().toString().isEmpty()){
                    listTiempoCajas.get(pos).setError("Ingrese el tiempo (en horas)");
                    listTiempoCajas.get(pos).requestFocus();
                    return false;
                }
            }
        }
        return true;
    }

    public String llenadoDelMap(EditText editTeSelec){
        if(editTeSelec.getText().toString().isEmpty()){
            return "Vacío";
        }else{
            return editTeSelec.getText().toString();
        }
    }

    public String llenadoDelMapTiemyCant(EditText editTeSelec){
        if(editTeSelec.getText().toString().isEmpty()){
            return "-";
        }else{
            return editTeSelec.getText().toString();
        }
    }
}