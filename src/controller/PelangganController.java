package controller;

import java.util.List;
import model.Pelanggan;

public class PelangganController {

  private Pelanggan pelangganModel;

  public PelangganController() {
    this.pelangganModel = new Pelanggan();
  }

  public List<Pelanggan> getAllPelanggan() {
    return pelangganModel.getAllPelanggan();
  }

  public Pelanggan getPelangganById(int idPelanggan) {
    return pelangganModel.getPelangganById(idPelanggan);
  }

  public boolean addPelanggan(String nama, String alamat, String noTelepon, String email) {
    pelangganModel.setNama(nama);
    pelangganModel.setAlamat(alamat);
    pelangganModel.setNoTelepon(noTelepon);
    pelangganModel.setEmail(email);

    return pelangganModel.addPelanggan();
  }

  public boolean updatePelanggan(int idPelanggan, String nama, String alamat, String noTelepon, String email) {
    pelangganModel.setIdPelanggan(idPelanggan);
    pelangganModel.setNama(nama);
    pelangganModel.setAlamat(alamat);
    pelangganModel.setNoTelepon(noTelepon);
    pelangganModel.setEmail(email);

    return pelangganModel.updatePelanggan();
  }

  public boolean deletePelanggan(int idPelanggan) {
    pelangganModel.setIdPelanggan(idPelanggan);
    return pelangganModel.deletePelanggan();
  }
}
