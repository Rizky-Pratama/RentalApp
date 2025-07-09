package controller;

// BigDecimal no longer used
import java.util.List;
import model.Mobil;

public class MobilController {

    private Mobil mobilModel;

    public MobilController() {
        this.mobilModel = new Mobil();
    }

    public List<Mobil> getAllMobil() {
        return mobilModel.getAllMobil();
    }

    public List<Mobil> getAvailableMobil() {
        return mobilModel.getAvailableMobil();
    }

    public Mobil getMobilById(int idMobil) {
        return mobilModel.getMobilById(idMobil);
    }

    public boolean addMobil(String merk, String tipe, int tahun, String noPolisi,
            int hargaSewa, int dendaPerHari) {
        mobilModel.setMerk(merk);
        mobilModel.setTahun(tahun);
        mobilModel.setNoPolisi(noPolisi);
        mobilModel.setStatus("tersedia");
        mobilModel.setHargaSewa(hargaSewa);
        mobilModel.setDendaPerHari(dendaPerHari);

        return mobilModel.addMobil();
    }

    public boolean updateMobil(int idMobil, String merk, int tahun,
            String noPolisi, String status, int hargaSewa,
            int dendaPerHari) {
        mobilModel.setIdMobil(idMobil);
        mobilModel.setMerk(merk);
        mobilModel.setTahun(tahun);
        mobilModel.setNoPolisi(noPolisi);
        mobilModel.setStatus(status);
        mobilModel.setHargaSewa(hargaSewa);
        mobilModel.setDendaPerHari(dendaPerHari);

        return mobilModel.updateMobil();
    }

    public boolean deleteMobil(int idMobil) {
        mobilModel.setIdMobil(idMobil);
        return mobilModel.deleteMobil();
    }

    public boolean updateStatus(int idMobil, String newStatus) {
        mobilModel.setIdMobil(idMobil);
        return mobilModel.updateStatus(newStatus);
    }
}
