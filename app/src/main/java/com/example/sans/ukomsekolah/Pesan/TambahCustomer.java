package com.example.sans.ukomsekolah.Pesan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sans.ukomsekolah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahCustomer extends AppCompatActivity {

    private EditText nama,alamat,telepon;
    private Spinner gender;
    private DatabaseReference mDatabase;
    private Button tambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Customer");
        nama = (EditText) findViewById(R.id.nama_cus);
        alamat = (EditText) findViewById(R.id.alamat_cus);
        telepon = (EditText) findViewById(R.id.telepon_cus);
        gender = (Spinner) findViewById(R.id.gender);
        tambah = (Button) findViewById(R.id.tambah_button);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();

            }
        });
    }

    private void addData(){
        final String nama_val = nama.getText().toString().trim();
        final String alamat_val = alamat.getText().toString().trim();
        final String telepon_val = telepon.getText().toString().trim();
        final String gender_val = gender.getSelectedItem().toString().trim();




                if (!TextUtils.isEmpty(nama_val) && !TextUtils.isEmpty(telepon_val) && !TextUtils.isEmpty(alamat_val)){
                    DatabaseReference newDes = mDatabase.push();
                    newDes.child("key").setValue(newDes.getKey());
                    newDes.child("nama").setValue(nama_val);
                    newDes.child("alamat").setValue(alamat_val);
                    newDes.child("telepon").setValue(telepon_val);
                    newDes.child("gender").setValue(gender_val);

                    SharedPreferences sharedPref = getSharedPreferences("Key_trans",0);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("id_custom", newDes.getKey());
                    editor.putString("nama", nama_val);
                    editor.apply();
                    editor.commit();
                    startActivity(new Intent(TambahCustomer.this, CekRuteActivity.class));
                    finish();
            }

                // Code that is executed when clicking YES


    }
}