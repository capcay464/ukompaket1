package com.example.sans.ukomsekolah.Model;

/**
 * Created by SANS on 2/11/2018.
 */

public class transportasi {

    private String kode;
    private String deskripsi;
    private String kursi;
    private String kelas;

    public transportasi() {

    }

    public transportasi(String kode, String deskripsi, String kursi, String kelas) {
        this.kode = kode;
        this.deskripsi = deskripsi;
        this.kursi = kursi;
        this.kelas = kelas;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKursi() {
        return kursi;
    }

    public void setKursi(String kursi) {
        this.kursi = kursi;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}
