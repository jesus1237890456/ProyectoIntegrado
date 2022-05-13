package com.example.proyecto_declaracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity_codigo extends AppCompatActivity {
private String recu_codigo;
private TextInputEditText codigo;
private Button aceptar;
private String recu_correo;
public static final String ENVIAR_correo ="correo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_codigo);
        codigo = (TextInputEditText) findViewById(R.id.codigo);
        aceptar = (Button) findViewById(R.id.codigo_enviar);
        Bundle datos = this.getIntent().getExtras();
        recu_codigo = getIntent().getStringExtra(MainActivity_correo.ENVIAR_mensaje);
        recu_correo = getIntent().getStringExtra(MainActivity_correo.ENVIAR_correo);

    aceptar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           String code = codigo.getText().toString();
            if(code.equals(recu_codigo)){
                Intent siguiente = new Intent(MainActivity_codigo.this, MainActivity_nueva_password.class);
                siguiente.putExtra(ENVIAR_correo, recu_correo);
                startActivity(siguiente);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Codigo incorrecto pruebe otra vez. "
                        , Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    });
    }
}