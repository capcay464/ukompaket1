package com.example.sans.ukomsekolah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sans.ukomsekolah.Login.LoginActivity;
import com.example.sans.ukomsekolah.Pesan.DataTransportasi;
import com.example.sans.ukomsekolah.Pesan.TambahCustomer;
import com.example.sans.ukomsekolah.Transportiasi.RuteActivity;
import com.example.sans.ukomsekolah.Transportiasi.TambahTransportasi;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private Button tcustom,transport,rute,transportasi;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        email = (TextView) findViewById(R.id.email);
        email.setText(auth.getCurrentUser().getEmail());



        tcustom = (Button) findViewById(R.id.logout);
        tcustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        transport = (Button) findViewById(R.id.to_transport);
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahTransportasi.class));
            }
        });
        transportasi = (Button) findViewById(R.id.to_datat);
        transportasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DataTransportasi.class));
            }
        });
        rute = (Button) findViewById(R.id.to_rute);
        rute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RuteActivity.class));
            }
        });
    }
}
