package com.example.service;

import com.example.dao.KecamatanMapper;
import com.example.model.KecamatanModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService{

    @Autowired
    private KecamatanMapper kecamatanMapper;

    @Override
    public KecamatanModel selectKecamatan(String nik) {
        log.info("select kecamatan with nik {}", nik);
        return kecamatanMapper.selectKecamatan(nik);
    }

    @Override
    public List<KecamatanModel> getDaftarKecamatanDiKota(String id_kota){
        log.info("Get daftar kecamatan");
        return kecamatanMapper.getDaftarKecamatanDiKota(id_kota);
    }

    @Override
    public KecamatanModel cariKecamatan(String id) {
        log.info("Select kecamatan berdasarkan id_kecamatan");
        return kecamatanMapper.cariKecamatan(id);
    }
}
