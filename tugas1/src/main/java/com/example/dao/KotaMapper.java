package com.example.dao;

import com.example.model.KotaModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface KotaMapper {
    @Select("select ko.id, ko.kode_kota, ko.nama_kota " +
            "from kota ko, kecamatan kec, kelurahan kel, penduduk p, keluarga k " +
            "where ko.id = kec.id_kota and kec.id = kel.id_kecamatan and " +
            "kel.id = k.id_kelurahan and p.id_keluarga = k.id and p.nik = #{nik}")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="kode_kota", column="kode_kota"),
            @Result(property="nama_kota", column="nama_kota"),
    })
    KotaModel selectKota(@Param("nik") String nik);

    //Untuk select Semua Kota
    @Select("select * from kota")
    List<KotaModel> getDaftarKota();

    //Cari kota berdasarkan id kota
    @Select("SELECT * FROM kota WHERE id = #{id}")
    KotaModel cariKota(@Param("id") String id);
}
