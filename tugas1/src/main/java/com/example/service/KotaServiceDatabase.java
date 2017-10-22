package com.example.service;

import com.example.dao.KotaMapper;
import com.example.model.KotaModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService{
    @Autowired
    private KotaMapper kotaMapper;

    @Override
    public KotaModel selectKota(String nik) {
        log.info("select kota with nik {}", nik);
        return kotaMapper.selectKota(nik);
    }

    @Override
    public List<KotaModel> getDaftarKota() {
        log.info("Get daftar kota");
        return kotaMapper.getDaftarKota();
    }

    @Override
    public KotaModel cariKota(String id) {
        log.info("Select kota berdasarkan id_kota");
        return kotaMapper.cariKota(id);
    }
}
