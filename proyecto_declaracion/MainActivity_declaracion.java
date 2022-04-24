package com.example.proyecto_declaracion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity_declaracion extends AppCompatActivity {
private TextView declarac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_declaracion);
        declarac = (TextView) findViewById(R.id.declara);
        MainActivity main = new MainActivity();
        Bundle datos = this.getIntent().getExtras();
        String declara = getIntent().getStringExtra(MainActivity_datos.ENVIAR_declar);
        float declaracion = Float.valueOf(declara);
        if(declaracion >= 0 ){
            declarac.setText("Sale a pagar "+declara);
        }else{
            declarac.setText("Sale a devolver "+declara);
        }
    }
}