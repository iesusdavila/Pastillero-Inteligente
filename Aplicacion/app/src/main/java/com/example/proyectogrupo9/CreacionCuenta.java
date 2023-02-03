package com.example.proyectogrupo9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreacionCuenta extends AppCompatActivity {

    private Spinner spinner1;

    private int dia, mes, anio;

    EditText etCorreo, etContrasena, etNombres, etTelefono, etNacimiento;

    AwesomeValidation awesomeValidation;

    FirebaseAuth firebaseAuth;

    FirebaseUser user;

    DatabaseReference dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_cuenta);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        String [] opciones = {"Masculino","Femenino","Otro"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_items_configuraciones, opciones);
        spinner1.setAdapter(adapter);

        etCorreo = (EditText) findViewById(R.id.editTeNomSecCaja);
        etContrasena = (EditText) findViewById(R.id.editTeContrasena);
        etNombres = (EditText) findViewById(R.id.editTeNombres);
        etTelefono = (EditText) findViewById(R.id.editTeTelefono);
        etNacimiento = (EditText) findViewById(R.id.editTeNacimiento);

        etNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == etNacimiento){
                    final Calendar c = Calendar.getInstance();
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH);
                    anio = c.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(CreacionCuenta.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            etNacimiento.setText(day+"/"+(month+1)+"/"+year);
                        }
                    },dia,mes,anio);

                    datePickerDialog.show();
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        dataBase = FirebaseDatabase.getInstance().getReference();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.editTeNomSecCaja, Patterns.EMAIL_ADDRESS,R.string.correo_invalido);
        awesomeValidation.addValidation(this,R.id.editTeContrasena, ".{6,}",R.string.contrasena_invalida);
    }

    public void goToMainActivity (View v){
        String mail = etCorreo.getText().toString();
        String pass = etContrasena.getText().toString();
        String tel = etTelefono.getText().toString();
        String nacim = etNacimiento.getText().toString();
        String nombre = etNombres.getText().toString();
        String genero = spinner1.getSelectedItem().toString();

        Boolean verificacionDatos = pruebaErrores(tel, nacim, nombre, genero);

        if(awesomeValidation.validate() && verificacionDatos){
            firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                String id = firebaseAuth.getCurrentUser().getUid();

                                Map<String, Object> map = new HashMap<>();
                                map.put("nombre", nombre);
                                map.put("correo", mail);
                                map.put("nacimiento", nacim);
                                map.put("telefono", tel);
                                map.put("genero", genero);
                                map.put("contrasena", pass);

                                user = firebaseAuth.getCurrentUser();

                                dataBase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if(task2.isSuccessful()){

                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task3) {
                                                    if(task3.isSuccessful()){
                                                        Toast.makeText(CreacionCuenta.this, "Usuario creado con exito", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(CreacionCuenta.this, CreacionNuevoPastillero.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }else{
                                                        Toast.makeText(CreacionCuenta.this, "Problema en el envio de verificacion de correo", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(CreacionCuenta.this, "Verifique los datos", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);
                            }
                        }
                    }
            );
        }else{
            Toast.makeText(this, "Complete todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean pruebaErrores(String tel, String nacim, String nombre, String genero){

        if(!tel.isEmpty() && !nacim.isEmpty() && !nombre.isEmpty() && !genero.isEmpty()){

            String [] fechaNacimiento = nacim.split("/");

            int verifNombre = 0;

            int anio2 = Integer.parseInt(fechaNacimiento[2]);

            if(anio2 > 2004){
                Toast.makeText(CreacionCuenta.this, "No se permite el registro de menores de edad", Toast.LENGTH_LONG).show();
                return false;
            }

            if((!tel.startsWith("0")) || (tel.charAt(1)!='9')){
                etTelefono.setError("Ingrese un numero de telefono valido");
                etTelefono.requestFocus();
                return false;
            }

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

            return true;

        }

        if(nacim.isEmpty()){
            Toast.makeText(CreacionCuenta.this, "Ingrese la fecha de nacimiento", Toast.LENGTH_LONG).show();
        }

        if(nombre.isEmpty()){
            etNombres.setError("Ingrese su nombre");
            etNombres.requestFocus();
        }

        if(tel.isEmpty()){
            etTelefono.setError("Ingrese el numero de telefono");
            etTelefono.requestFocus();
        }

        return false;
    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(CreacionCuenta.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(CreacionCuenta.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(CreacionCuenta.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(CreacionCuenta.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                etCorreo.setError("La dirección de correo electrónico está mal formateada.");
                etCorreo.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(CreacionCuenta.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                etContrasena.setError("la contraseña es incorrecta ");
                etContrasena.requestFocus();
                etContrasena.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(CreacionCuenta.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(CreacionCuenta.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(CreacionCuenta.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(CreacionCuenta.this, "El correo ya esta registrado", Toast.LENGTH_LONG).show();
                etCorreo.setError("El correo ya esta registrado");
                etCorreo.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(CreacionCuenta.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(CreacionCuenta.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(CreacionCuenta.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(CreacionCuenta.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(CreacionCuenta.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(CreacionCuenta.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(CreacionCuenta.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                etContrasena.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                etContrasena.requestFocus();
                break;

        }

    }
}