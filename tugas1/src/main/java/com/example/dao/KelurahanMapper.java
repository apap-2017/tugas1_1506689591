package com.example.dao;

import com.example.model.KelurahanModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface KelurahanMapper {
    @Select("select kel.id, kel.id_kecamatan, kel.kode_kelurahan, kel.nama_kelurahan, kel.kode_pos " +
            "from kelurahan kel, penduduk p, keluarga k " +
            "where kel.id = k.id_kelurahan and p.id_keluarga = k.id and p.nik = #{nik}")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="id_kecamatan", column="id_kecamatan"),
            @Result(property="kode_kelurahan", column="kode_kelurahan"),
            @Result(property="nama_kelurahan", column="nama_kelurahan"),
            @Result(property="kode_pos", column="kode_pos")
    })
    KelurahanModel selectKelurahan(@Param("nik") String nik);

    //Untuk select Semua Kelurahan
    @Select("select * FROM kelurahan kel, kecamatan kec, kota ko " +
            "WHERE kel.id_kecamatan = kec.id AND kec.id_kota = ko.id " +
            "AND kec.id = #{id_kecamatan} AND ko.id = #{id_kota}")
    List<KelurahanModel> getDaftarKelurahanDiKecamatan(@Param("id_kota") String id_kota, @Param("id_kecamatan") String id_kecamatan);

    //Cari kelurahan berdasarkan id kelurahan
    @Select("SELECT * FROM kelurahan WHERE id = #{id}")
    KelurahanModel cariKelurahan(@Param("id") String id);
}
