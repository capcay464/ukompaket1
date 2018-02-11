package com.example.sans.ukomsekolah.Pesan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sans.ukomsekolah.Model.transportasi;
import com.example.sans.ukomsekolah.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataTransportasi extends AppCompatActivity {


    private  DatabaseReference mDatabase;
    ;
    private RecyclerView nDesList;
    private String mDes_key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transportasi);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Transportasi");


        nDesList = (RecyclerView) findViewById(R.id.transportasi_list);
        nDesList.setHasFixedSize(true);
        nDesList.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<transportasi, DesViewHolder> firebaseRecylerAdapeter = new FirebaseRecyclerAdapter<transportasi, DesViewHolder>(
                transportasi.class,
                R.layout.trans_row,
                DesViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(DesViewHolder viewHolder, transportasi model, int position) {

                final String des_key = getRef(position).getKey();

                viewHolder.setKode(model.getKode());
                viewHolder.setDeskripsi(model.getDeskripsi());
                viewHolder.setKelas(model.getKelas());
                viewHolder.setKursi(model.getKursi());

                viewHolder.dView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences sharedPref = getSharedPreferences("Key_trans",0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("id_trans", des_key);
                        Intent desSingleIntent = new Intent(DataTransportasi.this, TambahCustomer.class);
                        desSingleIntent.putExtra("id",des_key );
                        editor.apply();
                        editor.commit();
                        startActivity(desSingleIntent);
                        finish();

                    }
                });



            }
        };

        nDesList.setAdapter(firebaseRecylerAdapeter);

    }

    public static class DesViewHolder extends RecyclerView.ViewHolder{

        View dView;


        public DesViewHolder(View itemView) {
            super(itemView);

            dView = itemView;

        }

        public void setKode(String kode){

            TextView des_tujuan = (TextView) dView.findViewById(R.id.koderow);
            des_tujuan.setText(kode);
        }

        public void setDeskripsi(String deskripsi){

            TextView des_harga = (TextView) dView.findViewById(R.id.deskripsirow);
            des_harga.setText(deskripsi);
        }

        public void setKelas(String kelas){

            TextView des_deskripsi = (TextView) dView.findViewById(R.id.kelasrow);
            des_deskripsi.setText(kelas);
        }

        public void setKursi(String kursi){

            TextView des_deskripsi = (TextView) dView.findViewById(R.id.kursirow);
            des_deskripsi.setText(kursi);

        }
    }
}
