package com.example.controller;

import com.example.model.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@Controller
public class KeluargaController {

    @Autowired
    PendudukService pendudukDAO;

    @Autowired
    KeluargaService keluargaDAO;

    @RequestMapping("/keluarga")
    public String viewKeluarga(Model model,
                               @RequestParam(value = "nkk", required = false) String nomor_kk)
    {
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nomor_kk);
        if(keluarga != null) {
            model.addAttribute("keluarga",keluarga);
            model.addAttribute("nomor_kk",nomor_kk);
            return "view-keluarga";
        } else {
            model.addAttribute("nomor_kk", nomor_kk);
            return "not-found-keluarga";
        }
    }

    @GetMapping("/keluarga/tambah")
    public String tambahKeluarga(Model model, KeluargaModel keluarga) {
        model.addAttribute("keluarga", keluarga);
        return "form-tambah-keluarga";
    }

    @PostMapping("/keluarga/tambah")
    public String tambahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga) {
        long id_keluarga = keluargaDAO.hitungTotalKeluarga() + 1;

        String kode_kecamatan = keluargaDAO.selectKodeKec(keluarga.getNama_kelurahan(),
                keluarga.getNama_kecamatan(), "%" + keluarga.getNama_kota()).substring(0,6);
        String id_kelurahan = keluargaDAO.selectIdKelurahan(keluarga.getNama_kelurahan(),
                keluarga.getNama_kecamatan(), "%" + keluarga.getNama_kota());

        //Untuk akses tanggal, bulan dan tahun
        Calendar cal = Calendar.getInstance();
        Date date_today = new Date();
        cal.setTime(date_today);
        int date = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String tanggal = "";
        String bulan = "";
        String tahun = "";
        if (date < 10) {
            tanggal = "0" + date;
        } else {
            tanggal = "" + date;
        }
        if (month < 10) {
            bulan = "0" + month;
        } else {
            bulan = "" + month;
        }
        tahun = ("" + year).substring(2,4);

        //Generate nomor_kk
        String nkkTemp = kode_kecamatan + tanggal + bulan + tahun;
        int sameNkk = keluargaDAO.cekNkk(nkkTemp + "%");//cek ada nkk yg sama atau engga
        int digitTerakhirNkk = sameNkk + 1;
        String nomor_kk = "";
        if (digitTerakhirNkk < 10) {
            nomor_kk = nkkTemp + "000" + digitTerakhirNkk;
        } else if (digitTerakhirNkk < 100) {
            nomor_kk = nkkTemp + "00" + digitTerakhirNkk;
        } else if (digitTerakhirNkk < 1000) {
            nomor_kk = nkkTemp + "0" + digitTerakhirNkk;
        } else {
            nomor_kk = nkkTemp + digitTerakhirNkk;
        }
        keluargaDAO.tambahKeluarga(keluarga, id_keluarga, nomor_kk, id_kelurahan);
        model.addAttribute("nomor_kk", nomor_kk);
        return "sukses-tambah-keluarga";
    }

    @GetMapping("/keluarga/ubah/{nomor_kk}")
    public String updateKeluarga(Model model, @PathVariable(value = "nomor_kk") String nomor_kk)
    {
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nomor_kk);
        if(keluarga != null) {
            model.addAttribute("nomor_kk", nomor_kk);
            model.addAttribute("keluarga", keluarga);
            return "form-update-keluarga";
        } else {
            return "not-found-keluarga";
        }

    }

    @PostMapping(value = "/keluarga/ubah/{nomor_kk}")
    public String updateKeluargaSubmit(Model model, @ModelAttribute KeluargaModel keluarga )
    {
        keluargaDAO.updateKeluarga(keluarga);
        String nomor_kk = keluarga.getNomor_kk();
        model.addAttribute("nomor_kk", nomor_kk);
        return "sukses-update-keluarga";

    }
}
