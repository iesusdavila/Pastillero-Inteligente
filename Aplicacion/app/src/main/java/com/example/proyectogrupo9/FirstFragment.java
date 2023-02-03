package com.example.proyectogrupo9;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }
    /*public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View main = inflater.inflate(R.layout.fragment_first, container, false);

        String id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ImageButton imgConfPastillero = (ImageButton) main.findViewById(R.id.imgConfPastillero);
        TextView teVConf = (TextView) main.findViewById(R.id.teVConf);

        imgConfPastillero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditarPastillero.class);
                startActivity(intent);
            }
        });

        teVConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditarPastillero.class);
                startActivity(intent);
            }
        });

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Usuarios").child(id_user);

        TextView teVPastilleroUsuario = main.findViewById(R.id.teVPastilleroUsuario);
        TextView teVNomPastillero = main.findViewById(R.id.teVNomPastillero);
        TextView teVFechaHora = main.findViewById(R.id.teVFechaHora);
        TextView teVNomCaja1 = main.findViewById(R.id.teVNomCaja1), teVNomCaja2 = main.findViewById(R.id.teVNomCaja2), teVNomCaja3 = main.findViewById(R.id.teVNomCaja3),
                teVNomCaja4 = main.findViewById(R.id.teVNomCaja4), teVNomCaja5 = main.findViewById(R.id.teVNomCaja5);
        TextView teVCantPastillas1 = main.findViewById(R.id.teVCantPastillas1), teVCantPastillas2 = main.findViewById(R.id.teVCantPastillas2),
                teVCantPastillas3 = main.findViewById(R.id.teVCantPastillas3), teVCantPastillas4 = main.findViewById(R.id.teVCantPastillas4),
                teVCantPastillas5 = main.findViewById(R.id.teVCantPastillas5);
        TextView teVTiempo1 = main.findViewById(R.id.teVTiempo1), teVTiempo2 = main.findViewById(R.id.teVTiempo2), teVTiempo3 = main.findViewById(R.id.teVTiempo3),
                teVTiempo4 = main.findViewById(R.id.teVTiempo4), teVTiempo5 = main.findViewById(R.id.teVTiempo5);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nombreUsuario = snapshot.child("nombre").getValue().toString();

                String nombrePastillero = snapshot.child("pastillero").child("nomPastillero").getValue().toString();
                String nomCaja1 = snapshot.child("pastillero").child("nomPrimCaja").getValue().toString(), nomCaja2 = snapshot.child("pastillero").child("nomSecCaja").getValue().toString(),
                        nomCaja3 = snapshot.child("pastillero").child("nomTerCaja").getValue().toString(), nomCaja4 = snapshot.child("pastillero").child("nomCuaCaja").getValue().toString(),
                        nomCaja5 = snapshot.child("pastillero").child("nomQuinCaja").getValue().toString();
                String cantidad1 = snapshot.child("pastillero").child("cantPrimCaja").getValue().toString(), cantidad2 = snapshot.child("pastillero").child("cantSecCaja").getValue().toString(),
                        cantidad3 = snapshot.child("pastillero").child("cantTerCaja").getValue().toString(), cantidad4 = snapshot.child("pastillero").child("cantCuaCaja").getValue().toString(),
                        cantidad5 = snapshot.child("pastillero").child("cantQuinCaja").getValue().toString();
                String tiempo1 = snapshot.child("pastillero").child("tiempoPrimCaja").getValue().toString(), tiempo2 = snapshot.child("pastillero").child("tiempoSecCaja").getValue().toString(),
                        tiempo3 = snapshot.child("pastillero").child("tiempoTerCaja").getValue().toString(), tiempo4 = snapshot.child("pastillero").child("tiempoCuaCaja").getValue().toString(),
                        tiempo5 = snapshot.child("pastillero").child("tiempoQuinCaja").getValue().toString();

                teVPastilleroUsuario.setText("Nombre: "+nombreUsuario);
                teVNomPastillero.setText(nombrePastillero);
                teVNomCaja1.setText(nomCaja1);
                teVNomCaja2.setText(nomCaja2);
                teVNomCaja3.setText(nomCaja3);
                teVNomCaja4.setText(nomCaja4);
                teVNomCaja5.setText(nomCaja5);
                teVCantPastillas1.setText(cantidad1);
                teVCantPastillas2.setText(cantidad2);
                teVCantPastillas3.setText(cantidad3);
                teVCantPastillas4.setText(cantidad4);
                teVCantPastillas5.setText(cantidad5);
                teVTiempo1.setText(tiempo1);
                teVTiempo2.setText(tiempo2);
                teVTiempo3.setText(tiempo3);
                teVTiempo4.setText(tiempo4);
                teVTiempo5.setText(tiempo5);

                Timer timer = new Timer();
                TimerTask tarea = new TimerTask() {
                    @Override
                    public void run() {
                        Time time = new Time(Time.getCurrentTimezone());
                        time.setToNow();
                        String fechaHora = "Fecha: " + time.monthDay+"/"+(time.month+1)+"/"+time.year+"     Hora: "+time.hour + ":" + time.minute + ":" + time.second;
                        teVFechaHora.setText(fechaHora);
                    }
                };

                timer.schedule(tarea, 0, 1000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        return main;
    }


}