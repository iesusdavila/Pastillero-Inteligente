package com.example.proyectogrupo9;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int contUsuarios, numHombres, numMujeres, numOtros, num18y25, num26y35, num36y45, num46y55, num56y65, numMayorDe65, edadMedia,
            promCaja1, promCaja2, promCaja3, promCaja4, promCaja5, caja1Media, caja2Media, caja3Media, caja4Media, caja5Media;
    double varianza, varianzaCaja1, varianzaCaja2, varianzaCaja3, varianzaCaja4, varianzaCaja5;

    public FourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourthFragment newInstance(String param1, String param2) {
        FourthFragment fragment = new FourthFragment();
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
        View main = inflater.inflate(R.layout.fragment_fourth, container, false);

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Usuarios");

        TextView teVHombresMedia = (TextView) main.findViewById(R.id.teVHombresMedia), teVMujeresMedia = (TextView) main.findViewById(R.id.teVMujeresMedia),
                teVOtrosMedia = (TextView) main.findViewById(R.id.teVOtrosMedia);

        TextView teV18y25Media = (TextView) main.findViewById(R.id.teV18y25Media), teV26y35Media = (TextView) main.findViewById(R.id.teV26y35Media),
                teV36y45Media = (TextView) main.findViewById(R.id.teV36y45Media), teV46y55Media = (TextView) main.findViewById(R.id.teV46y55Media),
                teV56y65Media = (TextView) main.findViewById(R.id.teV56y65Media), teVMayorDe65Media = (TextView) main.findViewById(R.id.teVMayorDe65Media);

        TextView teVDato1Media = (TextView) main.findViewById(R.id.teVDato1Media), teVDato1Varianza = (TextView) main.findViewById(R.id.teVDato1Varianza);

        TextView teVCaja1Promedio = (TextView) main.findViewById(R.id.teVCaja1Promedio), teVCaja2Promedio = (TextView) main.findViewById(R.id.teVCaja2Promedio),
                teVCaja3Promedio = (TextView) main.findViewById(R.id.teVCaja3Promedio),teVCaja4Promedio = (TextView) main.findViewById(R.id.teVCaja4Promedio),
                teVCaja5Promedio = (TextView) main.findViewById(R.id.teVCaja5Promedio);

        TextView teVDato2Media = (TextView) main.findViewById(R.id.teVDato2Media), teVDato3Media = (TextView) main.findViewById(R.id.teVDato3Media),
                teVDato4Media = (TextView) main.findViewById(R.id.teVDato4Media),teVDato5Media = (TextView) main.findViewById(R.id.teVDato5Media),
                teVDato6Media = (TextView) main.findViewById(R.id.teVDato6Media);

        TextView teVDato2Varianza = (TextView) main.findViewById(R.id.teVDato2Varianza), teVDato3Varianza = (TextView) main.findViewById(R.id.teVDato3Varianza),
                teVDato4Varianza = (TextView) main.findViewById(R.id.teVDato4Varianza),teVDato5Varianza = (TextView) main.findViewById(R.id.teVDato5Varianza),
                teVDato6Varianza = (TextView) main.findViewById(R.id.teVDato6Varianza);

        ArrayList<Integer> edades = new ArrayList<>();
        ArrayList<Integer> caja1CantPast = new ArrayList<>();
        ArrayList<Integer> caja2CantPast = new ArrayList<>();
        ArrayList<Integer> caja3CantPast = new ArrayList<>();
        ArrayList<Integer> caja4CantPast = new ArrayList<>();
        ArrayList<Integer> caja5CantPast = new ArrayList<>();

        contUsuarios = 0;
        numHombres = 0;
        numMujeres = 0;
        numOtros = 0;
        num18y25 = 0;
        num26y35 = 0;
        num36y45 = 0;
        num46y55 = 0;
        num56y65 = 0;
        numMayorDe65 = 0;
        edadMedia = 0;
        caja1Media = 0;
        caja2Media = 0;
        caja3Media = 0;
        caja4Media = 0;
        caja5Media = 0;
        varianza = 0.0;
        promCaja1 = 0;
        promCaja2 = 0;
        promCaja3 = 0;
        promCaja4 = 0;
        promCaja5 = 0;

        myref.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot usuarios: dataSnapshot.getChildren()){
                    for(DataSnapshot data: usuarios.getChildren()){
                        if(data.getKey().contains("genero")){
                            if(data.getValue().toString().contains("Masculino")){
                                numHombres++;
                            }
                            if(data.getValue().toString().contains("Femenino")){
                                numMujeres++;
                            }
                            if(data.getValue().toString().contains("Otro")){
                                numOtros++;
                            }
                        }
                        System.out.println("AQUI INICIA UN NUEVO USUARIO!!!!!!!!!!!!");
                        if(data.getKey().contains("pastillero")){
                            System.out.println(data);
                            for(DataSnapshot dataPastillero: data.getChildren()){
                                System.out.println(dataPastillero);
                                if(dataPastillero.getKey().contains("nomPrimCaja") && !dataPastillero.getValue().toString().contains("Vacío")){
                                    promCaja1++;
                                }
                                if(dataPastillero.getKey().contains("nomSecCaja") && !dataPastillero.getValue().toString().contains("Vacío")){
                                    promCaja2++;
                                }
                                if(dataPastillero.getKey().contains("nomTerCaja") && !dataPastillero.getValue().toString().contains("Vacío")){
                                    promCaja3++;
                                }
                                if(dataPastillero.getKey().contains("nomCuaCaja") && !dataPastillero.getValue().toString().contains("Vacío")){
                                    promCaja4++;
                                }
                                if(dataPastillero.getKey().contains("nomQuinCaja") && !dataPastillero.getValue().toString().contains("Vacío")){
                                    promCaja5++;
                                }
                                //------------------------------------------------------------------------------------------------------------
                                if(dataPastillero.getKey().contains("cantPrimCaja") && !dataPastillero.getValue().toString().contains("-")){
                                    caja1Media+=Integer.parseInt(dataPastillero.getValue().toString());
                                    caja1CantPast.add(Integer.parseInt(dataPastillero.getValue().toString()));
                                }
                                if(dataPastillero.getKey().contains("cantSecCaja") && !dataPastillero.getValue().toString().contains("-")){
                                    caja2Media+=Integer.parseInt(dataPastillero.getValue().toString());
                                    caja2CantPast.add(Integer.parseInt(dataPastillero.getValue().toString()));
                                }
                                if(dataPastillero.getKey().contains("cantTerCaja") && !dataPastillero.getValue().toString().contains("-")){
                                    caja3Media+=Integer.parseInt(dataPastillero.getValue().toString());
                                    caja3CantPast.add(Integer.parseInt(dataPastillero.getValue().toString()));
                                }
                                if(dataPastillero.getKey().contains("cantCuaCaja") && !dataPastillero.getValue().toString().contains("-")){
                                    caja4Media+=Integer.parseInt(dataPastillero.getValue().toString());
                                    caja4CantPast.add(Integer.parseInt(dataPastillero.getValue().toString()));
                                }
                                if(dataPastillero.getKey().contains("cantQuinCaja") && !dataPastillero.getValue().toString().contains("-")){
                                    caja5Media+=Integer.parseInt(dataPastillero.getValue().toString());
                                    caja5CantPast.add(Integer.parseInt(dataPastillero.getValue().toString()));
                                }
                            }
                        }
                        if(data.getKey().contains("nacimiento")){
                            int dia = Integer.parseInt(data.getValue().toString().split("/")[0]);
                            int mes = Integer.parseInt(data.getValue().toString().split("/")[1]);
                            int anio = Integer.parseInt(data.getValue().toString().split("/")[2]);
                            Period edad = Period.between(LocalDate.of(anio, mes, dia), LocalDate.now());
                            int aniosEdad = edad.getYears();

                            edades.add(aniosEdad);

                            edadMedia+=aniosEdad;

                            if(aniosEdad>=18 && aniosEdad<=25){
                                num18y25++;
                            }
                            if(aniosEdad>25 && aniosEdad<=35){
                                num26y35++;
                            }
                            if(aniosEdad>35 && aniosEdad<=45){
                                num36y45++;
                            }
                            if(aniosEdad>45 && aniosEdad<=55){
                                num46y55++;
                            }
                            if(aniosEdad>55 && aniosEdad<=65){
                                num56y65++;
                            }
                            if(aniosEdad>65){
                                numMayorDe65++;
                            }
                        }
                    }
                    contUsuarios++;
                }

                varianza = pasoVarianza(edades, varianza, edadMedia, contUsuarios);
                varianzaCaja1 = pasoVarianza(caja1CantPast, varianzaCaja1, caja1Media, contUsuarios);
                varianzaCaja2 = pasoVarianza(caja2CantPast, varianzaCaja2, caja2Media, contUsuarios);
                varianzaCaja3 = pasoVarianza(caja3CantPast, varianzaCaja3, caja3Media, contUsuarios);
                varianzaCaja4 = pasoVarianza(caja4CantPast, varianzaCaja4, caja4Media, contUsuarios);
                varianzaCaja5 = pasoVarianza(caja5CantPast, varianzaCaja5, caja5Media, contUsuarios);

                teVHombresMedia.setText(String.format("%.2f",numHombres*Math.pow(contUsuarios,-1)*100)+"%");
                teVMujeresMedia.setText(String.format("%.2f",numMujeres*Math.pow(contUsuarios,-1)*100)+"%");
                teVOtrosMedia.setText(String.format("%.2f",numOtros*Math.pow(contUsuarios,-1)*100)+"%");
                //, , , , ,
                teV18y25Media.setText(String.format("%.2f",num18y25*Math.pow(contUsuarios,-1)*100)+"%");
                teV26y35Media.setText(String.format("%.2f",num26y35*Math.pow(contUsuarios,-1)*100)+"%");
                teV36y45Media.setText(String.format("%.2f",num36y45*Math.pow(contUsuarios,-1)*100)+"%");
                teV46y55Media.setText(String.format("%.2f",num46y55*Math.pow(contUsuarios,-1)*100)+"%");
                teV56y65Media.setText(String.format("%.2f",num56y65*Math.pow(contUsuarios,-1)*100)+"%");
                teVMayorDe65Media.setText(String.format("%.2f",numMayorDe65*Math.pow(contUsuarios,-1)*100)+"%");
                //, , , , ,
                teVDato1Media.setText(String.format("%.2f",edadMedia*Math.pow(contUsuarios,-1)));
                teVDato1Varianza.setText(String.format("%.2f",Math.pow(varianza*Math.pow(contUsuarios-1,-1),0.5)));
                teVDato2Media.setText(String.format("%.2f",caja1Media*Math.pow(contUsuarios,-1)));
                teVDato2Varianza.setText(String.format("%.2f",Math.pow(varianzaCaja1*Math.pow(contUsuarios-1,-1),0.5)));
                teVDato3Media.setText(String.format("%.2f",caja2Media*Math.pow(contUsuarios,-1)));
                teVDato3Varianza.setText(String.format("%.2f",Math.pow(varianzaCaja2*Math.pow(contUsuarios-1,-1),0.5)));
                teVDato4Media.setText(String.format("%.2f",caja3Media*Math.pow(contUsuarios,-1)));
                teVDato4Varianza.setText(String.format("%.2f",Math.pow(varianzaCaja3*Math.pow(contUsuarios-1,-1),0.5)));
                teVDato5Media.setText(String.format("%.2f",caja4Media*Math.pow(contUsuarios,-1)));
                teVDato5Varianza.setText(String.format("%.2f",Math.pow(varianzaCaja4*Math.pow(contUsuarios-1,-1),0.5)));
                teVDato6Media.setText(String.format("%.2f",caja5Media*Math.pow(contUsuarios,-1)));
                teVDato6Varianza.setText(String.format("%.2f",Math.pow(varianzaCaja5*Math.pow(contUsuarios-1,-1),0.5)));
                //, , , , ,
                teVCaja1Promedio.setText(String.format("%.2f",promCaja1*Math.pow(contUsuarios,-1)*100)+"%");
                teVCaja2Promedio.setText(String.format("%.2f",promCaja2*Math.pow(contUsuarios,-1)*100)+"%");
                teVCaja3Promedio.setText(String.format("%.2f",promCaja3*Math.pow(contUsuarios,-1)*100)+"%");
                teVCaja4Promedio.setText(String.format("%.2f",promCaja4*Math.pow(contUsuarios,-1)*100)+"%");
                teVCaja5Promedio.setText(String.format("%.2f",promCaja5*Math.pow(contUsuarios,-1)*100)+"%");
            }
        });
        return main;
    }

    public Double pasoVarianza(ArrayList<Integer> lista, double prevarianza, int media, int muestra){
        for(int i = 0 ; i < lista.size(); i++){
            double rango;
            rango = Math.pow(lista.get(i) - media*Math.pow(muestra,-1), 2);
            prevarianza = prevarianza + rango;
        }
        return prevarianza;
    }
}