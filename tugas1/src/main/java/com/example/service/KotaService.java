package com.example.service;

import com.example.model.KotaModel;

import java.util.List;

public interface KotaService {
    KotaModel selectKota(String nik);
    List<KotaModel> getDaftarKota();
    KotaModel cariKota(String id);
}
