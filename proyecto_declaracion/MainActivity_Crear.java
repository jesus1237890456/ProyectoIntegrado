package com.example.proyecto_declaracion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity_Crear extends AppCompatActivity {
    Button btn_crear;
    private TextInputEditText usuario;
    private TextInputEditText contraseña;
    private TextInputEditText email;
    private Connection conexion;
    private Statement St1;
    private Statement St2;
    private ResultSet rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuario);
        usuario=(TextInputEditText) findViewById(R.id.usuario_crear);
        contraseña=(TextInputEditText) findViewById(R.id.contraseña_crear);
        email=(TextInputEditText) findViewById(R.id.mail);
        btn_crear = (Button) findViewById(R.id.crear_sesion);

        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main = new MainActivity();
                try{
                    conexion = main.connect();
                    String consulta;
                    consulta = "SELECT contraseña, email FROM cliente WHERE email LIKE '" + email.getText().toString() + "' and contraseña LIKE '" + contraseña.getText().toString() + "'";

                    St1 = conexion.createStatement();
                    rs = St1.executeQuery(consulta);
                    if(!rs.next()) {
                        do {
                            String consulta2;
                            consulta2 = "INSERT INTO cliente (usuario, contraseña, email) values('"+usuario.getText().toString()+"','"+contraseña.getText().toString()+"','"+email.getText().toString()+"')";
                            St2 = conexion.createStatement();
                            St2.executeUpdate(consulta2);

                        } while(rs.next());
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Contraseña o email ya existentes. "
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }catch (Exception e ) {
                    e.printStackTrace();
                }
                finish();
            }

        });

    }

}