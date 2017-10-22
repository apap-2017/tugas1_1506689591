package com.example.service;

import com.example.model.KecamatanModel;

import java.util.List;

public interface KecamatanService {
    KecamatanModel selectKecamatan(String nik);
    List<KecamatanModel> getDaftarKecamatanDiKota(String id_kota);
    KecamatanModel cariKecamatan(String id);
}
