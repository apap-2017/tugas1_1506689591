package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
    private String id;
    private String nomor_kk;
    private String alamat;
    private String rt;
    private String rw;
    private String id_kelurahan;
    private String is_tidak_berlaku;

    private String nama_kelurahan;
    private String nama_kecamatan;
    private String nama_kota;

    List<PendudukModel> anggotaKeluarga;
}
