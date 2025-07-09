package controller;

import java.util.List;
import model.Karyawan;

public class KaryawanController {

  private Karyawan karyawanModel;

  public KaryawanController() {
    this.karyawanModel = new Karyawan();
  }

  public List<Karyawan> getAllKaryawan() {
    return karyawanModel.getAllKaryawan();
  }

  public Karyawan getKaryawanById(int idKaryawan) {
    return karyawanModel.getKaryawanById(idKaryawan);
  }

  public Karyawan login(String username, String password) {
    return karyawanModel.login(username, password);
  }

  public boolean addKaryawan(String nama, String username, String password, String role) {
    karyawanModel.setNama(nama);
    karyawanModel.setUsername(username);
    karyawanModel.setPassword(password);
    karyawanModel.setRole(role);

    return karyawanModel.addKaryawan();
  }

  public boolean updateKaryawan(int idKaryawan, String nama, String username, String role) {
    karyawanModel.setIdKaryawan(idKaryawan);
    karyawanModel.setNama(nama);
    karyawanModel.setUsername(username);
    karyawanModel.setRole(role);

    return karyawanModel.updateKaryawan();
  }

  public boolean updatePassword(int idKaryawan, String newPassword) {
    karyawanModel.setIdKaryawan(idKaryawan);
    return karyawanModel.updatePassword(newPassword);
  }

  public boolean deleteKaryawan(int idKaryawan) {
    karyawanModel.setIdKaryawan(idKaryawan);
    return karyawanModel.deleteKaryawan();
  }

  public int getTotalKaryawan() {
    List<Karyawan> list = karyawanModel.getAllKaryawan();
    return list != null ? list.size() : 0;
  }
}
