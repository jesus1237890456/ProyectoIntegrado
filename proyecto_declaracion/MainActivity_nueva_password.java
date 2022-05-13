package com.example.proyecto_declaracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity_nueva_password extends AppCompatActivity {
    private Statement St1;
    private ResultSet rs;
    private TextInputEditText pass;
    private TextInputEditText passrepit;
    private Button btn_actualizar;
    private Connection conexion;
    private String recu_correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nueva_passwprd);
        pass=(TextInputEditText) findViewById(R.id.contraseñanueva);
        passrepit=(TextInputEditText) findViewById(R.id.contraseñarepit);
        btn_actualizar = (Button) findViewById(R.id.actualizar);
        Bundle datos = this.getIntent().getExtras();
        recu_correo = getIntent().getStringExtra(MainActivity_codigo.ENVIAR_correo);
        MainActivity main = new MainActivity();

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passw = pass.getText().toString();
                String passwre = passrepit.getText().toString();
                try{
                   if(passw.equals(passwre)){
                       conexion = main.connect();
                            String consulta2;
                            consulta2 = "update cliente set contraseña='"+pass.getText().toString()+"' where email like '"+recu_correo+"'";
                            St1 = conexion.createStatement();
                            St1.executeUpdate(consulta2);
                       Intent siguiente = new Intent(MainActivity_nueva_password.this, MainActivity.class);
                       startActivity(siguiente);
                   }else{
                       Toast toast = Toast.makeText(getApplicationContext(),
                               "Las contraseñas no coinciden. "
                               , Toast.LENGTH_SHORT);
                       toast.show();
                   }
                }catch (Exception e ) {
                    e.printStackTrace();
                }
            }
        });

    }
}