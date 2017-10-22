package com.example.dao;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface KeluargaMapper {

    //Untuk mencari keluarga tertentu berdasarkan nomor_kk
    @Select("select * "+
            "from keluarga k, kelurahan kel, kecamatan kec, kota ko " +
            "where k.id_kelurahan = kel.id and kel.id_kecamatan = kec.id and " +
            "kec.id_kota = ko.id and k.nomor_kk = #{nomor_kk}")
    @Results(value = {
            @Result(property="anggotaKeluarga", column="nomor_kk",
                    javaType = List.class,
                    many=@Many(select="selectAnggotaKeluarga"))
    })
    KeluargaModel selectKeluarga(@Param("nomor_kk") String nomor_kk);

    //Mengambil semua anggota keluarga di suatu keluarga berdasarkan nomor_kk
    @Select("select * " +
            "from keluarga join penduduk " +
            "on keluarga.id = penduduk.id_keluarga " +
            "where keluarga.nomor_kk = #{nomor_kk}")
    List<PendudukModel> selectAnggotaKeluarga(@Param("nomor_kk") String nomor_kk);

    //Menambah keluarga baru
    @Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) " +
            "VALUES (#{id}, #{nomor_kk}, #{keluarga.alamat}, #{keluarga.rt}, " +
            "#{keluarga.rw}, #{id_kelurahan}, 0)")
    void tambahKeluarga(@Param("keluarga") KeluargaModel keluarga,
                        @Param("id") long id, @Param("nomor_kk") String nomor_kk,
                        @Param("id_kelurahan") String id_kelurahan);

    //Select kode kecamatan untuk mengenerate nik dengan parameter id_keluarga
    @Select("select kode_kecamatan from keluarga k, kelurahan kel, kecamatan kec " +
            "where k.id_kelurahan = kel.id and kel.id_kecamatan = kec.id " +
            "and k.id = #{id_keluarga}")
    String selectKodeKecamatan(@Param("id_keluarga") String id_keluarga);

    //Select kode kecamatan untuk generate nomor_kk dengan parameter nama kelurahan, nama kecamatan, nama kota
    @Select("SELECT kec.kode_kecamatan FROM kelurahan kel, kecamatan kec, kota ko " +
            "WHERE kel.id_kecamatan = kec.id AND kec.id_kota = ko.id AND " +
            "kel.nama_kelurahan = #{nama_kelurahan} AND kec.nama_kecamatan = #{nama_kecamatan} " +
            "AND ko.nama_kota LIKE #{nama_kota}")
    String selectKodeKec(@Param("nama_kelurahan") String nama_kelurahan,
                         @Param("nama_kecamatan") String nama_kecamatan,
                         @Param("nama_kota") String nama_kota);

    //Cari id_kelurahan
    @Select("SELECT kel.id FROM kelurahan kel, kecamatan kec, kota ko " +
            "WHERE kel.id_kecamatan = kec.id AND kec.id_kota = ko.id AND " +
            "kel.nama_kelurahan = #{nama_kelurahan} AND kec.nama_kecamatan = #{nama_kecamatan} " +
            "AND ko.nama_kota LIKE #{nama_kota}")
    String selectIdKelurahan(@Param("nama_kelurahan") String nama_kelurahan,
                       @Param("nama_kecamatan") String nama_kecamatan,
                       @Param("nama_kota") String nama_kota);

    //Hitung total keluarga
    @Select("SELECT COUNT(*) FROM keluarga")
    long hitungTotalKeluarga();

    //Cek nomor_kk sudah ada apa belum
    @Select ("SELECT COUNT(*) FROM keluarga WHERE nomor_kk LIKE #{nomor_kk}")
    int cekNkk(@Param("nomor_kk") String nomor_kk);

    //Update keluarga
    @Update("UPDATE keluarga SET alamat = #{keluarga.alamat}, rt = #{keluarga.rt}, rw = #{keluarga.rw}, " +
            "id_kelurahan = #{keluarga.id_kelurahan}, is_tidak_berlaku = #{keluarga.is_tidak_berlaku} " +
            "WHERE id = #{keluarga.id}")
    void updateKeluarga(@Param("keluarga") KeluargaModel keluarga);

    //Update is_tidak_berlaku suatu kk
    @Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id}")
    void updateIsTidakBerlaku(@Param("id") String id);

}
