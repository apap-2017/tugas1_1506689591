package com.example.dao;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PendudukMapper
{
    //Untuk mencari data seorang penduduk
    @Select("select * from penduduk p, kota ko, kecamatan kec, kelurahan kel, keluarga k " +
            "where ko.id = kec.id_kota and kec.id = kel.id_kecamatan and " +
            "kel.id = k.id_kelurahan and p.id_keluarga = k.id and p.nik = #{nik}")
    PendudukModel selectPenduduk(@Param("nik") String nik);

    //Menambah penduduk
    @Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, " +
            "is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, " +
            "golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{penduduk.nama}, #{penduduk.tempat_lahir}, " +
            "#{penduduk.tanggal_lahir}, #{penduduk.jenis_kelamin}, #{penduduk.is_wni}, #{penduduk.id_keluarga}, " +
            "#{penduduk.agama}, #{penduduk.pekerjaan}, #{penduduk.status_perkawinan}, #{penduduk.status_dalam_keluarga}, " +
            "#{penduduk.golongan_darah}, #{penduduk.is_wafat})")
    void tambahPenduduk(@Param("penduduk") PendudukModel penduduk, @Param("nik") String nik, @Param("id") long id);

    //Menghitung jumlah penduduk dengan 12 digit pertama nik yg sama
    @Select ("SELECT COUNT(*) FROM penduduk WHERE nik LIKE #{nik}")
    int cekNik(@Param("nik") String nik);

    //Menghitung jumlah total penduduk untuk generate id
    @Select("SELECT COUNT(*) FROM penduduk")
    long hitungTotalPenduduk();

    //Update data penduduk
    @Update("UPDATE penduduk SET nama = #{penduduk.nama}, tempat_lahir = " +
            "#{penduduk.tempat_lahir}, tanggal_lahir = #{penduduk.tanggal_lahir}, " +
            "jenis_kelamin = #{penduduk.jenis_kelamin}, golongan_darah = " +
            "#{penduduk.golongan_darah}, agama = #{penduduk.agama}, status_perkawinan = " +
            "#{penduduk.status_perkawinan}, pekerjaan = #{penduduk.pekerjaan}, " +
            "is_wni = #{penduduk.is_wni}, is_wafat = #{penduduk.is_wafat}, " +
            "id_keluarga = #{penduduk.id_keluarga}, status_dalam_keluarga = " +
            "#{penduduk.status_dalam_keluarga} WHERE id = #{penduduk.id}")
    void updatePenduduk(@Param("penduduk") PendudukModel penduduk);

    //Update status kematian penduduk
    @Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
    void ubahIsWafat(@Param("nik") String nik);

    @Select("SELECT nomor_kk FROM penduduk p, keluarga k " +
            " WHERE p.id_keluarga = k.id and p.nik = #{nik}")
    String cariNomorKKPenduduk(@Param("nik") String nik);

    //Untuk nyari is wafat penduduk yg berguna untuk mengecek masih ada keluarga yg idup atau tdk
    @Select("SELECT is_wafat FROM penduduk WHERE nik = #{nik}")
    int isWafatPenduduk(@Param("nik") String nik);

    //Select penduduk di suatu kelurahan
    @Select("SELECT * FROM penduduk p, keluarga k, kelurahan kel, kecamatan kec, kota ko " +
            "WHERE p.id_keluarga = k.id AND k.id_kelurahan = kel.id AND kel.id_kecamatan = kec.id " +
            "AND kec.id_kota = ko.id AND kel.id = #{id_kelurahan} AND kec.id = #{id_kecamatan} " +
            "AND ko.id = #{id_kota}")
    List<PendudukModel> listPendudukDiKelurahan(@Param("id_kelurahan") String id_kelurahan,
                                                @Param("id_kecamatan") String id_kecamatan,
                                                @Param("id_kota") String id_kota);
}
