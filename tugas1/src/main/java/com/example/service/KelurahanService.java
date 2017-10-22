package com.example.service;

import com.example.model.KelurahanModel;

import java.util.List;

public interface KelurahanService {
    KelurahanModel selectKelurahan(String nik);
    List<KelurahanModel> getDaftarKelurahanDiKecamatan(String id_kota, String id_kecamatan);
    KelurahanModel cariKelurahan(String id);
}
