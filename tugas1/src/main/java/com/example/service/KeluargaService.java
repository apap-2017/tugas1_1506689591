package com.example.service;

import com.example.model.KeluargaModel;

public interface KeluargaService {
    KeluargaModel selectKeluarga(String nomor_kk);
    void tambahKeluarga(KeluargaModel keluarga, long id, String nomor_kk, String id_kelurahan);
    String selectKodeKecamatan(String id_keluarga);
    String selectKodeKec(String nama_kelurahan, String nama_kecamatan, String nama_kota);
    String selectIdKelurahan(String nama_kelurahan, String nama_kecamatan, String nama_kota);
    long hitungTotalKeluarga();
    int cekNkk(String nomor_kk);
    void updateKeluarga(KeluargaModel keluarga);
    void updateIsTidakBerlaku(String id);
}
