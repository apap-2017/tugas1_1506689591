package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.example.dao.PendudukMapper;
import com.example.model.PendudukModel;

import java.util.List;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {

    @Autowired
    private PendudukMapper pendudukMapper;

    @Override
    public PendudukModel selectPenduduk(String nik) {
        log.info("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk(nik);
    }

    @Override
    public void tambahPenduduk(PendudukModel penduduk, String nik, long id) {
        log.info("Add penduduk");
        pendudukMapper.tambahPenduduk(penduduk, nik, id);
    }

    @Override
    public int cekNik(String nik) {
        log.info("cek nik");
        return pendudukMapper.cekNik(nik);
    }

    @Override
    public long hitungTotalPenduduk() {
        log.info("hitung total penduduk");
        return pendudukMapper.hitungTotalPenduduk();
    }

    @Override
    public void updatePenduduk(PendudukModel penduduk){
        log.info("penduduk " + penduduk.getNik() + " updated");
        pendudukMapper.updatePenduduk(penduduk);
    }

    @Override
    public void ubahIsWafat(String nik){
        log.info("Ubah status meninggan penduduk dengan nik {}", nik);
        pendudukMapper.ubahIsWafat(nik);
    }

    @Override
    public String cariNomorKKPenduduk(String nik) {
        log.info("cari nkk penduduk");
        return pendudukMapper.cariNomorKKPenduduk(nik);
    }

    @Override
    public int isWafatPenduduk(String nik) {
        log.info("Cek penduduk masih hidup atau sudah meninggal");
        return pendudukMapper.isWafatPenduduk(nik);
    }

    @Override
    public List<PendudukModel> listPendudukDiKelurahan(String id_kelurahan, String id_kecamatan, String id_kota) {
        log.info("Select semua penduduk di kelurahan tertentu");
        return pendudukMapper.listPendudukDiKelurahan(id_kelurahan, id_kecamatan, id_kota);
    }
}
