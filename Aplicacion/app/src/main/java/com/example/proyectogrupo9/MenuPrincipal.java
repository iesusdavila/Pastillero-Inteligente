package com.example.proyectogrupo9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
    }

    public void cerrarSesion (View v){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Cierre de Sesion Exitoso", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuPrincipal.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}