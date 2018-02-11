package com.example.sans.ukomsekolah.Pesan;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sans.ukomsekolah.R;
import com.example.sans.ukomsekolah.Transportiasi.RuteActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CekRuteActivity extends AppCompatActivity {

    private DatabaseReference mDatabase , mTrans , mRute;
    private TextView kode,kelas,kursi,harga,tvDateResult;
    private Button tambah,tanggal_btn;
    private Spinner spiner;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String mDes_key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_rute);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservation");
        mTrans = FirebaseDatabase.getInstance().getReference().child("Transportasi");
        mRute = FirebaseDatabase.getInstance().getReference().child("Rute");

        kode = (TextView) findViewById(R.id.rute_kode);
        kelas = (TextView) findViewById(R.id.rute_kelas);
        kursi = (TextView) findViewById(R.id.rute_kursi);
        harga = (TextView) findViewById(R.id.rute_harga);
        tvDateResult = (TextView) findViewById(R.id.tanggal_text);


        spiner = (Spinner) findViewById(R.id.spiner);



        tanggal_btn = (Button) findViewById(R.id.tanggal_button);
        tanggal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        SharedPreferences sharedPref = getSharedPreferences("Key_trans",0);
        mTrans.child(sharedPref.getString("id_trans", " ")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kode_1 = (String) dataSnapshot.child("kode").getValue();
                String kelas_1 = (String) dataSnapshot.child("kelas").getValue();
                String kursi_1 = (String) dataSnapshot.child("kursi").getValue();

                kode.setText(kode_1);
                kelas.setText(kelas_1);
                kursi.setText(kursi_1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRute.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();
                String harga_2 = (String) dataSnapshot.child("harga").getValue();

                harga.setText(harga_2);

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("Tujuan").getValue(String.class);
                    areas.add(areaName);
                }

                Spinner spiner = (Spinner) findViewById(R.id.spiner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(CekRuteActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spiner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tambah = (Button) findViewById(R.id.tambah_button);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(CekRuteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

//                java.util.Calendar newDate = java.util.Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//
//                tvDateResult.setText(dateFormatter.format(newDate.getTime()));


                String date = String.valueOf(year) +"-"+String.valueOf(monthOfYear)
                        +"-"+String.valueOf(dayOfMonth);
                tvDateResult.setText(date);


            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }



    private void addData(){
        final String kode_val = kode.getText().toString().trim();
        final String kursi_val = kursi.getText().toString().trim();
        final String kelas_val = kelas.getText().toString().trim();
        final String harga_val = harga.getText().toString().trim();
        final String tanggal_val = tvDateResult.getText().toString().trim();
        final String tujuan_val = spiner.getSelectedItem().toString().trim();



            SharedPreferences sharedPref = getSharedPreferences("Key_trans",0);
            DatabaseReference newDes = mDatabase.push();
            newDes.child("key").setValue(newDes.getKey());
            final String des_key = newDes.getKey();
            newDes.child("nama").setValue(sharedPref.getString("id_custom", " "));
            newDes.child("kode_kereta").setValue(sharedPref.getString("id_trans", " "));
            newDes.child("nama").setValue(sharedPref.getString("nama", " "));
            newDes.child("kode").setValue(kode_val);
            newDes.child("kursi").setValue(kursi_val);
            newDes.child("kelas").setValue(kelas_val);
           // newDes.child("harga").setValue(harga_val);
            newDes.child("tanggal_keberangkatan").setValue(tanggal_val);
           // newDes.child("harga").setValue(harga_val);
            newDes.child("tujuan").setValue(tujuan_val);

             Intent desSingleIntent = new Intent(CekRuteActivity.this, ReservationActivity.class);
             desSingleIntent.putExtra("id",des_key );
             startActivity(desSingleIntent);



            finish();


    }
}
