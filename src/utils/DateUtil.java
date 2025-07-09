/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ThinkPad
 */
public class DateUtil {

    // Format tanggal ke bentuk "dd-MM-yyyy"
    public static String formatTanggal(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    } // Hitung selisih hari antara dua tanggal

    public static int hitungSelisihHari(Date mulai, Date selesai) {
        // Pastikan tanggal tidak null
        if (mulai == null || selesai == null) {
            return 0;
        }

        // Hitung selisih dalam millisecond
        long selisihMillis = selesai.getTime() - mulai.getTime();

        // Konversi ke hari (pembulatan ke atas untuk hari parsial)
        double selisihHariDouble = (double) selisihMillis / (1000 * 60 * 60 * 24);
        int selisihHari = (int) Math.ceil(selisihHariDouble);

        // Tetapkan minimum periode sewa (1 hari)
        return Math.max(selisihHari, 1);
    }
    
    // Menghitung biaya sewa berdasarkan tarif harian
    public static double hitungBiayaSewa(Date mulai, Date selesai, double tarifHarian) {
        int jumlahHari = hitungSelisihHari(mulai, selesai);
        
        // Tambahkan diskon untuk sewa jangka panjang (opsional)
        double diskon = 0;
        if (jumlahHari > 7) {
            diskon = 0.10; // 10% diskon untuk sewa > 7 hari
        } else if (jumlahHari > 30) {
            diskon = 0.25; // 25% diskon untuk sewa > 30 hari
        }
        
        return jumlahHari * tarifHarian * (1 - diskon);
    }

    // Parse string ke Date (format "yyyy-MM-dd")
    public static Date parseTanggal(String tanggalStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(tanggalStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
