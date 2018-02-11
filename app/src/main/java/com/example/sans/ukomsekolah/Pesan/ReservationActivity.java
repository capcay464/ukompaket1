package com.example.sans.ukomsekolah.Pesan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sans.ukomsekolah.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReservationActivity extends AppCompatActivity {

    private TextView kode,kereta,tanggal,harga,tujuan,nama;
    private DatabaseReference mDatabase,mRute,mTrans;
    private Button tambah;
    private String mDes_key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservation");
        mTrans = FirebaseDatabase.getInstance().getReference().child("Transportasi");
        mRute = FirebaseDatabase.getInstance().getReference().child("Rute");

        mDes_key = getIntent().getExtras().getString("id");

        kode = (TextView) findViewById(R.id.kode);
        nama = (TextView) findViewById(R.id.kode_nama);
        kereta = (TextView) findViewById(R.id.kode_kereta);
        harga = (TextView) findViewById(R.id.kode_harga);
        tujuan = (TextView) findViewById(R.id.kode_tujuan);
        tanggal = (TextView) findViewById(R.id.kode_tanggal);


        tambah = (Button) findViewById(R.id.tambah_button);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        mDatabase.child(mDes_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String nama_val = (String) dataSnapshot.child("nama").getValue();
                String kode_val = (String) dataSnapshot.child("key").getValue();
                String kereta_Val = (String) dataSnapshot.child("kode_kereta").getValue();
                String tujuan_Val = (String) dataSnapshot.child("tujuan").getValue();

                String tanggal_Val = (String) dataSnapshot.child("tanggal_keberangkatan").getValue();

                mRute.child(tujuan_Val).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String harga_Val = (String) dataSnapshot.child("harga").getValue();

                        harga.setText(harga_Val);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                kode.setText(kode_val);
                nama.setText(nama_val);
                kereta.setText(kereta_Val);
                tujuan.setText(tujuan_Val);

                tanggal.setText(tanggal_Val);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
