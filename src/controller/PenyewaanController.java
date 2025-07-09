package controller;

import java.sql.Date;
import java.util.List;
import model.Penyewaan;
import model.Mobil;
import model.Sopir;

public class PenyewaanController {

  private Penyewaan penyewaanModel;

  public PenyewaanController() {
    this.penyewaanModel = new Penyewaan();
  }

  public List<Penyewaan> getAllPenyewaan() {
    return penyewaanModel.getAllPenyewaan();
  }

  public List<Penyewaan> getActivePenyewaan() {
    return penyewaanModel.getActivePenyewaan();
  }

  public Penyewaan getPenyewaanById(int idSewa) {
    return penyewaanModel.getPenyewaanById(idSewa);
  }

  public boolean addPenyewaan(int idPelanggan, int idMobil, Integer idSopir, int idKaryawan,
      Date tglSewa, Date tglKembaliRencana, int totalHarga) {
    penyewaanModel.setIdPelanggan(idPelanggan);
    penyewaanModel.setIdMobil(idMobil);
    penyewaanModel.setIdSopir(idSopir);
    penyewaanModel.setIdKaryawan(idKaryawan);
    penyewaanModel.setTglSewa(tglSewa);
    penyewaanModel.setTglKembaliRencana(tglKembaliRencana);
    penyewaanModel.setTotalHarga(totalHarga);
    penyewaanModel.setStatus("disewa");

    boolean result = penyewaanModel.addPenyewaan();

    if (result) {
      // Update mobil status
      Mobil mobil = new Mobil();
      mobil.setIdMobil(idMobil);
      mobil.updateStatus("disewa");

      // Update sopir status if there is a sopir
      if (idSopir != null) {
        Sopir sopir = new Sopir();
        sopir.setIdSopir(idSopir);
        sopir.updateStatus("dalam tugas");
      }
    }

    return result;
  }

  public boolean updatePenyewaan(int idSewa, int idPelanggan, int idMobil, Integer idSopir,
      int idKaryawan, Date tglSewa, Date tglKembaliRencana,
      int totalHarga, String status) {
    penyewaanModel.setIdSewa(idSewa);
    penyewaanModel.setIdPelanggan(idPelanggan);
    penyewaanModel.setIdMobil(idMobil);
    penyewaanModel.setIdSopir(idSopir);
    penyewaanModel.setIdKaryawan(idKaryawan);
    penyewaanModel.setTglSewa(tglSewa);
    penyewaanModel.setTglKembaliRencana(tglKembaliRencana);
    penyewaanModel.setTotalHarga(totalHarga);
    penyewaanModel.setStatus(status);

    return penyewaanModel.updatePenyewaan();
  }

  public boolean deletePenyewaan(int idSewa) {
    penyewaanModel.setIdSewa(idSewa);
    return penyewaanModel.deletePenyewaan();
  }

  public boolean updateStatus(int idSewa, String newStatus) {
    // Get current penyewaan to get the associated mobil and sopir
    Penyewaan penyewaan = penyewaanModel.getPenyewaanById(idSewa);

    penyewaanModel.setIdSewa(idSewa);
    boolean result = penyewaanModel.updateStatus(newStatus);

    if (result && "selesai".equals(newStatus)) {
      // Update mobil status
      Mobil mobil = new Mobil();
      mobil.setIdMobil(penyewaan.getIdMobil());
      mobil.updateStatus("tersedia");

      // Update sopir status if there was a sopir
      if (penyewaan.getIdSopir() != null) {
        Sopir sopir = new Sopir();
        sopir.setIdSopir(penyewaan.getIdSopir());
        sopir.updateStatus("tersedia");
      }
    }

    return result;
  }

  // Helper method to calculate duration in days between two dates
  public int calculateDuration(Date startDate, Date endDate) {
    if (startDate == null || endDate == null)
      return 0;

    long difference = endDate.getTime() - startDate.getTime();
    return (int) (difference / (1000 * 60 * 60 * 24)) + 1; // Add 1 to include both start and end dates
  }

  // Helper method to calculate total rental cost
  public int calculateRentalCost(int idMobil, Integer idSopir, int durationDays) {
    int totalCost = 0;

    // Get mobil harga_sewa
    Mobil mobil = new Mobil().getMobilById(idMobil);
    if (mobil != null) {
      int mobilCost = mobil.getHargaSewa()*durationDays;
      totalCost = totalCost + mobilCost;
    }

    // Get sopir tarif if applicable
    if (idSopir != null) {
      Sopir sopir = new Sopir().getSopirById(idSopir);
      if (sopir != null) {
        int sopirCost = sopir.getTarifPerHari() * durationDays;
        totalCost = totalCost + sopirCost;
      }
    }

    return totalCost;
  }
}
