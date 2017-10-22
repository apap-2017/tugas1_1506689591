package com.example.service;

import com.example.dao.KeluargaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.KeluargaModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService
{
    @Autowired
    private KeluargaMapper keluargaMapper;

    @Override
    public KeluargaModel selectKeluarga(String nomor_kk) {
            log.info("select keluarga with nik {}", nomor_kk);
            return keluargaMapper.selectKeluarga(nomor_kk);
    }

    @Override
    public void tambahKeluarga(KeluargaModel keluarga, long id, String nomor_kk, String id_kelurahan) {
            log.info("insert keluarga baru");
            keluargaMapper.tambahKeluarga(keluarga, id, nomor_kk, id_kelurahan);
    }

    @Override
    public String selectKodeKecamatan(String id_keluarga) {
            log.info("select kode kecamatan");
            return keluargaMapper.selectKodeKecamatan(id_keluarga);
    }

    @Override
    public String selectKodeKec(String nama_kelurahan, String nama_kecamatan, String nama_kota) {
        log.info("select kode kecamatan with nama_kelurahan {}, nama_kecamatan {}, dan nama_kota {}", nama_kelurahan, nama_kecamatan, nama_kota);
        return keluargaMapper.selectKodeKec(nama_kelurahan, nama_kecamatan, nama_kota);
    }

    @Override
    public String selectIdKelurahan(String nama_kelurahan, String nama_kecamatan, String nama_kota){
        log.info("select id kelurahan with nama_kelurahan {}, nama_kecamatan {}, dan nama_kota {}", nama_kelurahan, nama_kecamatan, nama_kota);
        return keluargaMapper.selectIdKelurahan(nama_kelurahan, nama_kecamatan, nama_kota);
    }

    @Override
    public long hitungTotalKeluarga() {
            log.info("hitung total keluarga");
            return keluargaMapper.hitungTotalKeluarga();
    }

    @Override
    public int cekNkk(String nomor_kk) {
        log.info("cek existing nomor_kk {}", nomor_kk);
        return keluargaMapper.cekNkk(nomor_kk);
    }

    @Override
    public void updateKeluarga(KeluargaModel keluarga) {
        log.info("Update keluarga");
        keluargaMapper.updateKeluarga(keluarga);
    }

    @Override
    public void updateIsTidakBerlaku(String id) {
        log.info("Update is tidak berlaku keluarga");
        keluargaMapper.updateIsTidakBerlaku(id);
    }

}
