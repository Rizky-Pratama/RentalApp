package controller;

import java.sql.Date;
import model.Mobil;
import model.Pengembalian;
import model.Penyewaan;

public class PengembalianController {

    private Pengembalian pengembalianModel;

    public PengembalianController() {
        this.pengembalianModel = new Pengembalian();
    }

    public Pengembalian getPengembalianById(int idPengembalian) {
        return pengembalianModel.getPengembalianById(idPengembalian);
    }

    public Pengembalian getPengembalianByIdSewa(int idSewa) {
        return pengembalianModel.getPengembalianByIdSewa(idSewa);
    }

    public boolean prosesPengembalian(int idSewa, Date tglKembali, String catatan) {
        // Get penyewaan information
        Penyewaan penyewaan = new Penyewaan().getPenyewaanById(idSewa);
        if (penyewaan == null) {
            return false;
        }

        // Check if mobil exists and get denda_per_hari
        Mobil mobil = new Mobil().getMobilById(penyewaan.getIdMobil());
        if (mobil == null) {
            return false;
        }

        // Calculate keterlambatan
        Date tglRencanaKembali = penyewaan.getTglKembaliRencana();
        int dendaPerHari = mobil.getDendaPerHari();

        // Calculate denda untuk keterlambatan
        int dendaTotal = pengembalianModel.hitungDenda(tglRencanaKembali, tglKembali, dendaPerHari);
        int keterlambatan = pengembalianModel.getKeterlambatan();

        // Calculate total bayar (total harga + denda)
        int totalBayar = penyewaan.getTotalHarga() + dendaTotal;

        // Set the pengembalian properties
        pengembalianModel.setIdSewa(idSewa);
        pengembalianModel.setTglKembali(tglKembali);
        pengembalianModel.setKeterlambatan(keterlambatan);
        pengembalianModel.setDenda(dendaTotal);
        pengembalianModel.setTotalBayar(totalBayar);
        pengembalianModel.setCatatan(catatan);

        // Save pengembalian to database and update penyewaan status
        boolean result = pengembalianModel.addPengembalian();

        if (result) {
            // Update penyewaan status to 'selesai'
            PenyewaanController penyewaanController = new PenyewaanController();
            penyewaanController.updateStatus(idSewa, "selesai");
        }

        return result;
    }

    public boolean updatePengembalian(int idPengembalian, int idSewa, Date tglKembali,
            int keterlambatan, int denda, int totalBayar, String catatan) {
        pengembalianModel.setIdPengembalian(idPengembalian);
        pengembalianModel.setIdSewa(idSewa);
        pengembalianModel.setTglKembali(tglKembali);
        pengembalianModel.setKeterlambatan(keterlambatan);
        pengembalianModel.setDenda(denda);
        pengembalianModel.setTotalBayar(totalBayar);
        pengembalianModel.setCatatan(catatan);

        return pengembalianModel.updatePengembalian();
    }

    public boolean deletePengembalian(int idPengembalian) {
        pengembalianModel.setIdPengembalian(idPengembalian);
        return pengembalianModel.deletePengembalian();
    }
}
