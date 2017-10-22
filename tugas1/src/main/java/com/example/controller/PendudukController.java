package com.example.controller;

import com.example.model.*;
import com.example.service.*;
import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class PendudukController {

    @Autowired
    PendudukService pendudukDAO;

    @Autowired
    KeluargaService keluargaDAO;

    @Autowired
    KelurahanService kelurahanDAO;

    @Autowired
    KecamatanService kecamatanDAO;
//
    @Autowired
    KotaService kotaDAO;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/penduduk")
    public String viewPenduduk(Model model,
            @RequestParam(value = "nik", required = false) String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
        if(penduduk != null) {
            model.addAttribute("penduduk", penduduk);
            return "view-penduduk";
        } else {
            model.addAttribute("nik", nik);
            return "not-found-penduduk";
        }
    }

    @GetMapping("/penduduk/tambah")
    public String tambahPenduduk(Model model, PendudukModel penduduk) {
        model.addAttribute("penduduk", penduduk);
        return "form-tambah-penduduk";
    }

    @PostMapping("/penduduk/tambah")
    public String tambahPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk) {
        long id_penduduk = pendudukDAO.hitungTotalPenduduk() + 1;
        String id_kel = penduduk.getId_keluarga();
        String kode_kecamatan = keluargaDAO.selectKodeKecamatan(id_kel).substring(0,6);

        //Untuk akses tanggal, bulan dan tahun
        Calendar cal = Calendar.getInstance();
        Date tanggal_lahir = penduduk.getTanggal_lahir();
        cal.setTime(tanggal_lahir);
        int date = cal.get(Calendar.DATE);
        if(penduduk.getJenis_kelamin() == 1) {
            date = date + 40;
        }
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        //Setting tanggal, bulan, dan tahun
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

        //Generate nik
        String nikTemp = kode_kecamatan + tanggal + bulan + tahun;
        int sameNik = pendudukDAO.cekNik(nikTemp + "%");//cek ada nik yg sama atau engga
        int digitTerakhirNik = sameNik + 1;
        String nik = "";
        if (digitTerakhirNik < 10) {
            nik = nikTemp + "000" + digitTerakhirNik;
        } else if (digitTerakhirNik < 100) {
            nik = nikTemp + "00" + digitTerakhirNik;
        } else if (digitTerakhirNik < 1000) {
            nik = nikTemp + "0" + digitTerakhirNik;
        } else {
            nik = nikTemp + digitTerakhirNik;
        }

        pendudukDAO.tambahPenduduk(penduduk, nik, id_penduduk);
        model.addAttribute("nik", nik);
        return "sukses-tambah-penduduk";
    }

    @GetMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk(Model model, @PathVariable(value = "nik") String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
        if(penduduk != null) {
            model.addAttribute("penduduk", penduduk);
            return "form-update-penduduk";
        } else {
            return "not-found-penduduk";
        }

    }

    @PostMapping(value = "/penduduk/ubah/{nik}")
    public String updatePendudukSubmit(Model model, @ModelAttribute PendudukModel penduduk)
    {
        pendudukDAO.updatePenduduk(penduduk);
        String nik = penduduk.getNik();
        model.addAttribute("nik", nik);
        return "sukses-update-penduduk";

    }

    @GetMapping(value = "/penduduk/mati")
    public String nonAktifkanPenduduk(Model model, @RequestParam(value = "nik", required = false) String nik) {
        pendudukDAO.ubahIsWafat(nik);
        String nomor_kk = pendudukDAO.cariNomorKKPenduduk(nik);
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nomor_kk);
        int jumlah_hidup = 0;
        for(int i = 0; i < keluarga.getAnggotaKeluarga().size(); i++) {
            int is_wafat = pendudukDAO.isWafatPenduduk(keluarga.getAnggotaKeluarga().get(i).getNik());
            if(is_wafat == 0) {
                jumlah_hidup += 1;
            }
        }

        if (jumlah_hidup == 0) {
            keluargaDAO.updateIsTidakBerlaku(keluarga.getId());
        }
        model.addAttribute("nik", nik);
        return "sukses-nonaktifkan-penduduk";
    }

    @RequestMapping("/penduduk/cari")
    public String cariPenduduk (Model model,
                                @RequestParam(value = "kt", required = false) String kt,
                                @RequestParam(value = "kc", required = false) String kc,
                                @RequestParam(value = "kl", required = false) String kl) {
        if(kt == null && kc == null && kl == null) {
            List<KotaModel> daftarKota = kotaDAO.getDaftarKota();
            model.addAttribute("daftar_kota", daftarKota);
            return "cari-penduduk-kota";
        } else if (kt != null && kc == null && kl == null){
            List<KecamatanModel> daftarKecamatan = kecamatanDAO.getDaftarKecamatanDiKota(kt);
            KotaModel kota = kotaDAO.cariKota(kt);
            model.addAttribute("kota", kota);
            model.addAttribute("daftar_kecamatan", daftarKecamatan);
            return "cari-penduduk-kecamatan";
        } else if (kt != null && kc != null && kl == null) {
            List<KelurahanModel> daftarKelurahan = kelurahanDAO.getDaftarKelurahanDiKecamatan(kt, kc);
            KotaModel kota = kotaDAO.cariKota(kt);
            KecamatanModel kecamatan = kecamatanDAO.cariKecamatan(kc);
            model.addAttribute("kota", kota);
            model.addAttribute("kecamatan", kecamatan);
            model.addAttribute("daftar_kelurahan", daftarKelurahan);
            return "cari-penduduk-kelurahan";
        } else {
            KotaModel kota = kotaDAO.cariKota(kt);
            KecamatanModel kecamatan = kecamatanDAO.cariKecamatan(kc);
            KelurahanModel kelurahan = kelurahanDAO.cariKelurahan(kl);
            model.addAttribute("kota", kota);
            model.addAttribute("kecamatan", kecamatan);
            model.addAttribute("kelurahan", kelurahan);
            List<PendudukModel> daftar_penduduk = pendudukDAO.listPendudukDiKelurahan(kl, kc, kt);
            model.addAttribute("daftar_penduduk", daftar_penduduk);
            return "cari-penduduk";

        }




    }
}
