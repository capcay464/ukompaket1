package com.example.sans.ukomsekolah.Transportiasi;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sans.ukomsekolah.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahTransportasi extends AppCompatActivity {

    private EditText kode,deskripsi,kursi;
    private Spinner kelas;
    private DatabaseReference mDatabase;
    private Button tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transportasi);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Transportasi");

        kode = (EditText) findViewById(R.id.kode);
        deskripsi = (EditText) findViewById(R.id.deskripsi);
        kursi = (EditText) findViewById(R.id.kursi);
        kelas = (Spinner) findViewById(R.id.kelas);

        tambah = (Button) findViewById(R.id.tambah_button);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddData();
            }
        });


    }

    private void AddData(){
        final String kode_val = kode.getText().toString().trim();
        final String deskipsi_val = deskripsi.getText().toString().trim();
        final String kursi_val = kursi.getText().toString().trim();
        final String kelas_val = kelas.getSelectedItem().toString().trim();

        if (!TextUtils.isEmpty(kode_val) && !TextUtils.isEmpty(deskipsi_val) && !TextUtils.isEmpty(kursi_val)){
            DatabaseReference newDes = mDatabase.push();
            newDes.child("key").setValue(newDes.getKey());
            newDes.child("kode").setValue(kode_val);
            newDes.child("deskripsi").setValue(deskipsi_val);
            newDes.child("kursi").setValue(kursi_val);
            newDes.child("kelas").setValue(kelas_val);

            finish();
        }
    }
}
