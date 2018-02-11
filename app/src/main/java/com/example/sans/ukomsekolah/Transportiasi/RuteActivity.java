package com.example.sans.ukomsekolah.Transportiasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sans.ukomsekolah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RuteActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    private DatabaseReference mSpin;
    private EditText berangkat,sampai,harga;
    private Button add;
    private Spinner spiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rute);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rute");


        berangkat = (EditText) findViewById(R.id.tambah_kota_b);
        sampai = (EditText) findViewById(R.id.tambah_kota_s);
        harga = (EditText) findViewById(R.id.tambah_harga);

        add = (Button) findViewById(R.id.tambah_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();;
            }
        });


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("Tujuan").getValue(String.class);
                    areas.add(areaName);
                }

                Spinner spiner = (Spinner) findViewById(R.id.spiner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(RuteActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spiner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addData(){
        final String berangkat_val = berangkat.getText().toString().trim();
        final String sampai_val = sampai.getText().toString().trim();
        final String harga_val = harga.getText().toString().trim();


        if (!TextUtils.isEmpty(berangkat_val) && !TextUtils.isEmpty(sampai_val) && !TextUtils.isEmpty(harga_val)){

            DatabaseReference newDes = mDatabase.push();
            newDes.child("key").setValue(newDes.getKey());
            newDes.child("Tujuan").setValue(berangkat_val + " " + "-" + sampai_val);
            newDes.child("Harga").setValue(sampai_val);

            berangkat.setText("");
            sampai.setText("");
            harga.setText("");




        }

    }

}
