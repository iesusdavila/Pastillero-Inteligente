package com.example.proyectogrupo9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_second, container, false);

        //ACCESO

        Button btnLEDAcceso = (Button) main.findViewById(R.id.btnLEDAcceso);

        View viewLEDAcceso = main.findViewById(R.id.viewLEDAcceso);

        final int[] defaultColorLEDAcceso = {ContextCompat.getColor(getActivity(), R.color.second_color)};

        //BLOQUEO

        Button btnLEDBloqueo = (Button) main.findViewById(R.id.btnLEDBloqueo);

        View viewLEDBloqueo = main.findViewById(R.id.viewLEDBloqueo);

        final int[] defaultColorLEDBloqueo = {ContextCompat.getColor(getActivity(), R.color.second_color)};

        //ACTUALIZAR DATOS

        Button btnActualizarDatos = (Button) main.findViewById(R.id.btnActualizarDatos);

        //CAMBIAR CONTRASEÑA

        Button btnRecuperarContrasena = (Button) main.findViewById(R.id.btnRecuperarContrasena);

        btnLEDBloqueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmbilWarnaDialog ambilWarnaDialog2 = new AmbilWarnaDialog(getActivity(), defaultColorLEDBloqueo[0], new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color2) {
                        defaultColorLEDBloqueo[0] = color2;

                        viewLEDBloqueo.setBackgroundColor(defaultColorLEDBloqueo[0]);
                    }
                });
                ambilWarnaDialog2.show();
            }
        });

        btnLEDAcceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getActivity(), defaultColorLEDAcceso[0], new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        defaultColorLEDAcceso[0] = color;

                        viewLEDAcceso.setBackgroundColor(defaultColorLEDAcceso[0]);
                    }
                });
                ambilWarnaDialog.show();
            }
        });

        btnActualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditarCuenta.class);
                startActivity(intent);
                try {
                    SecondFragment.this.finalize();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        btnRecuperarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NuevaContrasena.class);
                startActivity(intent);
                try {
                    SecondFragment.this.finalize();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        return main;
    }
}