package com.example.proyecto_declaracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity_datos extends AppCompatActivity {
    private Statement St;
    private ResultSet rs;
    private ResultSet rs2;
    private Connection conexion = null;
    private Button insert;
    private Button edit;
    private Button declaracion;
    public static final String ENVIAR_user ="nombre";
    public static final String ENVIAR_password ="contraseña";
    public String recu_usuario;
    public String recu_contraseña;
    int salario_bruto ;
    int social;
    int reten;
    String total2;
    double suma_tramo;
    int liquidable;
    double tramo4;
    double tramo3;
    double tramo2;
    double tramo1;
    public static final String ENVIAR_declar ="declaracion";

    MainActivity main = new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_datos);
        insert = (Button) findViewById(R.id.insertar_datos);
        edit = (Button) findViewById(R.id.editar);
        declaracion = (Button) findViewById(R.id.declaracion);
        Bundle datos = this.getIntent().getExtras();
        recu_usuario = getIntent().getStringExtra(MainActivity.ENVIAR_usuario);
        recu_contraseña = getIntent().getStringExtra(MainActivity.ENVIAR_contraseña);
        main.connect();
        try {
            conexion = main.connect();
            String consulta;
            consulta = "select salario_bruto, seguridad_social, retenciones from declaracion_renta join cliente on cliente.id_cliente = declaracion_renta.id_clientes where usuario like '"+recu_usuario+"' and contraseña like '"+recu_contraseña+"'";
            St = conexion.createStatement();
            rs = St.executeQuery(consulta);
            if(rs.next()) {
                do {
                    insert.setVisibility(View.INVISIBLE);

                } while(rs.next());
            } else {
                declaracion.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    insert.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent siguiente = new Intent(MainActivity_datos.this, MainActivity_insert_edit.class);
            siguiente.putExtra(ENVIAR_user, recu_usuario);
            siguiente.putExtra(ENVIAR_password, recu_contraseña);
            startActivity(siguiente);
        }
    });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(MainActivity_datos.this, MainActivity_insert_edit.class);
                siguiente.putExtra(ENVIAR_user, recu_usuario);
                siguiente.putExtra(ENVIAR_password, recu_contraseña);
                startActivity(siguiente);
            }
        });

        declaracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    conexion = main.connect();
                    String consulta;
                    consulta = "select salario_bruto, seguridad_social, retenciones from declaracion_renta join cliente on cliente.id_cliente = declaracion_renta.id_clientes where usuario like '"+recu_usuario+"' and contraseña like '"+recu_contraseña+"'";
                    St = conexion.createStatement();
                    rs = St.executeQuery(consulta);
                    if(rs.next()) {
                        do {
                            salario_bruto = rs.getInt("salario_bruto");
                            social = rs.getInt("seguridad_social");
                            reten = rs.getInt("retenciones");

                        } while(rs.next());
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                    liquidable = salario_bruto - 5550 - social;
                if(liquidable >= 0 && liquidable <=12450 ){
                    tramo1= 12450*0.19;
                    double total = tramo1 - reten;
                    total2= String.valueOf(total);
                }
                if(liquidable >=12451 && liquidable <= 20200){
                    tramo1= 12450*0.19;
                    tramo2 = (liquidable-12450)*0.24;
                    suma_tramo= tramo1 + tramo2;
                    double total = suma_tramo - reten;
                    total2= String.valueOf(total);
                }
                if(liquidable >=20201 && liquidable <= 35200){
                    tramo1= 12450*0.19;
                    tramo2 = 7750*0.24;
                    tramo3 = (liquidable-20200)*0.30;
                    suma_tramo= tramo1 + tramo2 +tramo3;
                    double total = suma_tramo - reten;
                    total2= String.valueOf(total);
                }
                if(liquidable >=35201 && liquidable <= 60000){
                    tramo1= 12450*0.19;
                    tramo2 = 7750*0.24;
                    tramo3 = 15000*0.30;
                    tramo4 = (liquidable-35200*0.37);
                    suma_tramo= tramo1 + tramo2 +tramo3 + tramo4;
                    double total = suma_tramo - reten;
                    total2= String.valueOf(total);
                }
                Intent siguiente = new Intent(MainActivity_datos.this, MainActivity_declaracion.class);
                siguiente.putExtra(ENVIAR_declar, total2);
                startActivity(siguiente);
            }
        });
    }


    }

