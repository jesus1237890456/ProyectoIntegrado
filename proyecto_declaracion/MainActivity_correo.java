package com.example.proyecto_declaracion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MainActivity_correo extends AppCompatActivity {
    private TextInputEditText usuario;
    private TextInputEditText email;
    private Button enviar;
    private Statement St;
    public static final String ENVIAR_mensaje ="mensaje";
    public static final String ENVIAR_correo ="correo";
    private ResultSet rs;
    private Connection conexion = null;
    private String cadena="";
    Session sesion;
    String correoemisor;
    String contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_correo);
        usuario = (TextInputEditText) findViewById(R.id.usuario_correo);
        email = (TextInputEditText) findViewById(R.id.email_correo);
        enviar = (Button) findViewById(R.id.enviar);
        MainActivity main = new MainActivity();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    conexion = main.connect();
                    String consulta;
                    consulta = "select usuario, email from cliente  where usuario like '" + usuario.getText().toString() + "' and email like '" + email.getText().toString() + "'";
                    St = conexion.createStatement();
                    rs = St.executeQuery(consulta);
                    if (rs.next()) {
                        do {
                            Random random = new Random();
                            String setOfCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-/@";
                            for (int x = 0; x < 6; x++) {
                                int indiceAleatorio = random.nextInt(setOfCharacters.length() - 1);
                                char caracterAleatorio = setOfCharacters.charAt(indiceAleatorio);
                                cadena += caracterAleatorio;
                            }
                            Properties propiedad = new Properties();
                            propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
                            propiedad.setProperty("mail.smtp.starttls.enable", "true");
                            propiedad.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
                            propiedad.setProperty("mail.smtp.port", "587");
                            propiedad.setProperty("mail.smtp.auth", "true");
                            propiedad.put("mail.smtp.ssl.trust", "smtp.gmail.com");

                            correoemisor = "jesuscuetogonzalez14@gmail.com";
                            contraseña = "pokemons12";

                            try {
                                    sesion = Session.getDefaultInstance(propiedad, new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(correoemisor, contraseña);
                                    }
                                });

                            if(sesion != null) {
                                MimeMessage mail = new MimeMessage(sesion);
                                mail.setFrom(new InternetAddress(correoemisor));
                                mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getText().toString()));
                                mail.setContent(cadena,"text/html; charse=utf-8");
                                Transport.send(mail);
                            }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent siguiente = new Intent(MainActivity_correo.this, MainActivity_codigo.class);
                            siguiente.putExtra(ENVIAR_mensaje, cadena);
                            siguiente.putExtra(ENVIAR_correo, email.getText().toString());
                            startActivity(siguiente);

                        } while (rs.next());

                } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "usuario o email no existentes. "
                                , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    }