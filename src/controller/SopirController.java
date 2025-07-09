package controller;


import java.util.List;
import model.Sopir;

public class SopirController {

  private Sopir sopirModel;

  public SopirController() {
    this.sopirModel = new Sopir();
  }

  public List<Sopir> getAllSopir() {
    return sopirModel.getAllSopir();
  }

  public List<Sopir> getAvailableSopir() {
    return sopirModel.getAvailableSopir();
  }

  public Sopir getSopirById(int idSopir) {
    return sopirModel.getSopirById(idSopir);
  }
  public boolean addSopir(String nama, String noSim, int tarifPerHari) {
    sopirModel.setNama(nama);
    sopirModel.setNoSim(noSim);
    sopirModel.setTarifPerHari(tarifPerHari);
    sopirModel.setStatus("tersedia");

    return sopirModel.addSopir();
  }
  public boolean updateSopir(int idSopir, String nama, String noSim, int tarifPerHari, String status) {
    sopirModel.setIdSopir(idSopir);
    sopirModel.setNama(nama);
    sopirModel.setNoSim(noSim);
    sopirModel.setTarifPerHari(tarifPerHari);
    sopirModel.setStatus(status);

    return sopirModel.updateSopir();
  }

  public boolean deleteSopir(int idSopir) {
    sopirModel.setIdSopir(idSopir);
    return sopirModel.deleteSopir();
  }

  public boolean updateStatus(int idSopir, String status) {
    sopirModel.setIdSopir(idSopir);
    return sopirModel.updateStatus(status);
  }
}
