package com.example.proyecto_declaracion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.*;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    SharedPreferences.Editor editor;
    public EditText usuario;
    public TextInputEditText contraseña;
    private CheckBox chekRecordardatos;
    private Connection conexion = null;
    private Statement St;
    private ResultSet rs;
    private Button btncrear;
    private Button btnolvide;
    public static final String ENVIAR_usuario ="nombre";
    public static final String ENVIAR_contraseña = "contraseña";
    private SharedPreferences prefe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_Proyecto_declaracion);
        setContentView(R.layout.activity_main);
        usuario = (EditText) findViewById(R.id.usuario);
        contraseña = (TextInputEditText) findViewById(R.id.contraseña);
        btnLogin = (Button) findViewById(R.id.logear);
        btncrear = (Button) findViewById(R.id.create);
        btnolvide = (Button) findViewById(R.id.olvide);
        chekRecordardatos = (CheckBox) findViewById(R.id.recordar);
        SharedPreferences preferencias = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        usuario.setText(preferencias.getString("usuario", ""));
        if(preferencias.getString("usuario", "").isEmpty()){
            chekRecordardatos.setChecked(false);
        }else{
            chekRecordardatos.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(usuario.getText().toString() == null || contraseña.getText().toString() == null){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Introduzca todos los datos correspondientes. "
                        , Toast.LENGTH_SHORT);
                toast.show();
            }else{
                connect();
                try {
                    String consulta;
                    consulta = "SELECT usuario , contraseña FROM cliente WHERE usuario LIKE '" + usuario.getText().toString() + "' and contraseña LIKE '" + contraseña.getText().toString() + "'";
                    St = conexion.createStatement();
                    rs = St.executeQuery(consulta);
                    if(rs.next()) {
                        do {
                            Intent siguiente = new Intent(MainActivity.this, MainActivity_datos.class);
                            String user = usuario.getText().toString();
                            String pass = contraseña.getText().toString();
                            siguiente.putExtra(ENVIAR_usuario,user);
                            siguiente.putExtra(ENVIAR_contraseña,pass);
                            startActivity(siguiente);
                        } while(rs.next());
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "No existe este usuario, o no coincide el usuario y/o contraseña."
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if (chekRecordardatos.isChecked()) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Ha decidido recordar datos"
                            , Toast.LENGTH_SHORT);
                    toast.show();
                    prefe = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                    editor = prefe.edit();
                    editor.putString("usuario", usuario.getText().toString());
                    editor.commit();
                }else{
                    if(preferencias.getString("usuario", "") != null) {
                        preferencias.edit().clear().commit();
                    }else{
                    }
                    }

            }
            }
        });

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(view.getContext(), MainActivity_Crear.class);
                startActivity(siguiente);

            }
        });

        btnolvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(view.getContext(), MainActivity_correo.class);
                startActivity(siguiente);
            }
        });


    }

    public Connection connect() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //conexion = DriverManager.getConnection (url,user, password);
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.151;databaseName=declaracion;user=jesus;password=pokemons12;");


            System.out.println("Conectado a la base de datos con éxito !!!");
        } catch (Exception e) {
            System.out.println("NO CONECTA!");
            e.printStackTrace();
        }
        return conexion;
    }
}


