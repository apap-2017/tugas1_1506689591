package com.example.service;

import com.example.model.PendudukModel;

import java.util.List;

public interface PendudukService {
    PendudukModel selectPenduduk(String nik);
    void tambahPenduduk(PendudukModel penduduk, String nik, long id);
    int cekNik(String nik);
    long hitungTotalPenduduk();
    void updatePenduduk(PendudukModel penduduk);
    void ubahIsWafat(String nik);
    String cariNomorKKPenduduk(String nik);
    int isWafatPenduduk(String nik);
    List<PendudukModel> listPendudukDiKelurahan(String id_kelurahan, String id_kecamatan, String id_kota);
}
