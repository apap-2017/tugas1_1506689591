package com.example.service;

import com.example.dao.KelurahanMapper;
import com.example.model.KelurahanModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService{

    @Autowired
    private KelurahanMapper kelurahanMapper;

    @Override
    public KelurahanModel selectKelurahan(String nik) {
        log.info("select kelurahan with nik {}", nik);
        return kelurahanMapper.selectKelurahan(nik);
    }

    @Override
    public List<KelurahanModel> getDaftarKelurahanDiKecamatan(String id_kota, String id_kecamatan){
        log.info("Get daftar kelurahan");
        return kelurahanMapper.getDaftarKelurahanDiKecamatan(id_kota, id_kecamatan);
    }

    @Override
    public KelurahanModel cariKelurahan(String id) {
        log.info("Select kecamatan berdasarkan id_kecamatan");
        return kelurahanMapper.cariKelurahan(id);
    }

}
