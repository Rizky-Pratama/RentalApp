package utils;

import model.Karyawan;

/**
 * Singleton class untuk menyimpan data sesi user yang login
 */
public class UserSession {
  private static UserSession instance;

  private int idKaryawan;
  private String nama;
  private String username;
  private String role;
  private boolean loggedIn = false;

  // Private constructor untuk mencegah instansiasi dari luar class
  private UserSession() {
  }

  /**
   * Mendapatkan instance UserSession (singleton)
   * 
   * @return instance UserSession
   */
  public static UserSession getInstance() {
    if (instance == null) {
      instance = new UserSession();
    }
    return instance;
  }

  /**
   * Menyimpan data user yang login
   * 
   * @param karyawan objek Karyawan yang berhasil login
   */
  public void setUser(Karyawan karyawan) {
    if (karyawan != null) {
      this.idKaryawan = karyawan.getIdKaryawan();
      this.nama = karyawan.getNama();
      this.username = karyawan.getUsername();
      this.role = karyawan.getRole();
      this.loggedIn = true;
    }
  }

  /**
   * Mengecek apakah user sudah login
   * 
   * @return true jika user sudah login, false jika belum
   */
  public boolean isLoggedIn() {
    return loggedIn;
  }

  /**
   * Menghapus data sesi user (logout)
   */
  public void clearSession() {
    this.idKaryawan = 0;
    this.nama = null;
    this.username = null;
    this.role = null;
    this.loggedIn = false;
  }

  // Getter methods
  public int getIdKaryawan() {
    return idKaryawan;
  }

  public String getNama() {
    return nama;
  }

  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }
}
