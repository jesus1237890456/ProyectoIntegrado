package com.example.proyecto_declaracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity_insert_edit extends AppCompatActivity {
    private Statement St;
    private ResultSet rs;
    private ResultSet rs2;
    private Connection conexion = null;
    private EditText salario;
    private EditText seguridad;
    private EditText retenciones;
    private Button guardar;
    private int id_cliente;
    private Button cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_insert_edit);
        salario = (EditText) findViewById(R.id.salario);
        seguridad = (EditText) findViewById(R.id.seguridad);
        retenciones = (EditText) findViewById(R.id.retenciones);
        guardar = (Button) findViewById(R.id.guardar_datos);
        cancelar = (Button) findViewById(R.id.cancelar);
        Bundle datos = this.getIntent().getExtras();
        String usuario = getIntent().getStringExtra(MainActivity.ENVIAR_usuario);
        String contraseña = getIntent().getStringExtra(MainActivity.ENVIAR_contraseña);
        MainActivity main = new MainActivity();
        main.connect();
        try {
            conexion = main.connect();
            String consulta;
            consulta = "select salario_bruto, seguridad_social, retenciones from declaracion_renta join cliente on cliente.id_cliente = declaracion_renta.id_clientes where usuario like '"+usuario+"' and contraseña like '"+contraseña+"'";
            St = conexion.createStatement();
            rs = St.executeQuery(consulta);
            if(rs.next()) {
                do {
                    String salario_bruto = rs.getString ("salario_bruto");
                    String social = rs.getString ("seguridad_social");
                    String reten = rs.getString ("retenciones");
                    salario.setText(salario_bruto);
                    seguridad.setText(social);
                    retenciones.setText(reten);

                } while(rs.next());
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            String consulta;
            consulta = "select id_cliente from cliente where usuario like '" + usuario + "' and contraseña like '" + contraseña + "'";
            St = conexion.createStatement();
            rs2 = St.executeQuery(consulta);
            while (rs2.next()) {
                id_cliente = rs2.getInt("id_cliente");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(rs.next()) {
                        do { String consulta2;
                            consulta2= "update declaracion_renta set salario_bruto='"+salario.getText().toString()+"',seguridad_social= '"+seguridad.getText().toString()+"',retenciones='"+retenciones.getText().toString()+"')";
                            St = conexion.createStatement();
                            St.executeUpdate(consulta2);
                        } while(rs.next());
                    } else {
                        String consulta2;
                        consulta2= "INSERT INTO declaracion_renta (id_clientes, salario_bruto, seguridad_social, retenciones) values("+id_cliente+",'"+salario.getText().toString()+"','"+seguridad.getText().toString()+"','"+retenciones.getText().toString()+"')";
                        St = conexion.createStatement();
                        St.executeUpdate(consulta2);
                    }

                    } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent siguiente = new Intent(MainActivity_insert_edit.this, MainActivity.class);
                startActivity(siguiente);
        }

});
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}