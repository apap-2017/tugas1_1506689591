package com.example.dao;

import com.example.model.KecamatanModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface KecamatanMapper {

    @Select("select kec.id, kec.id_kota, kec.kode_kecamatan, kec.nama_kecamatan " +
            "from kecamatan kec, kelurahan kel, penduduk p, keluarga k " +
            "where kec.id = kel.id_kecamatan and kel.id = k.id_kelurahan and p.id_keluarga = k.id and p.nik = #{nik}")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="id_kota", column="id_kota"),
            @Result(property="kode_kecamatan", column="kode_kecamatan"),
            @Result(property="nama_kecamatan", column="nama_kecamatan"),
    })
    KecamatanModel selectKecamatan(@Param("nik") String nik);

    //Untuk select Kecamatan di suatu kota
    @Select("select * from kecamatan kec, kota ko " +
            "WHERE kec.id_kota = ko.id AND ko.id = #{id}")
    List<KecamatanModel> getDaftarKecamatanDiKota(@Param("id") String id);

    //Cari kecamatan berdasarkan id kecamatan
    @Select("SELECT * FROM kecamatan WHERE id = #{id}")
    KecamatanModel cariKecamatan(@Param("id") String id);

}
