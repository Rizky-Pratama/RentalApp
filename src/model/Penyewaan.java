package model;

import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Penyewaan {
  private int idSewa;
  private int idPelanggan;
  private int idMobil;
  private Integer idSopir; // Nullable
  private int idKaryawan;
  private Date tglSewa;
  private Date tglKembaliRencana;
  private int totalHarga;
  private String status; // 'disewa' atau 'selesai'

  // Constructor
  public Penyewaan() {
  }

  public Penyewaan(int idSewa, int idPelanggan, int idMobil, Integer idSopir, int idKaryawan,
      Date tglSewa, Date tglKembaliRencana, int totalHarga, String status) {
    this.idSewa = idSewa;
    this.idPelanggan = idPelanggan;
    this.idMobil = idMobil;
    this.idSopir = idSopir;
    this.idKaryawan = idKaryawan;
    this.tglSewa = tglSewa;
    this.tglKembaliRencana = tglKembaliRencana;
    this.totalHarga = totalHarga;
    this.status = status;
  }

  // Getters and Setters
  public int getIdSewa() {
    return idSewa;
  }

  public void setIdSewa(int idSewa) {
    this.idSewa = idSewa;
  }

  public int getIdPelanggan() {
    return idPelanggan;
  }

  public void setIdPelanggan(int idPelanggan) {
    this.idPelanggan = idPelanggan;
  }

  public int getIdMobil() {
    return idMobil;
  }

  public void setIdMobil(int idMobil) {
    this.idMobil = idMobil;
  }

  public Integer getIdSopir() {
    return idSopir;
  }

  public void setIdSopir(Integer idSopir) {
    this.idSopir = idSopir;
  }

  public int getIdKaryawan() {
    return idKaryawan;
  }

  public void setIdKaryawan(int idKaryawan) {
    this.idKaryawan = idKaryawan;
  }

  public Date getTglSewa() {
    return tglSewa;
  }

  public void setTglSewa(Date tglSewa) {
    this.tglSewa = tglSewa;
  }

  public Date getTglKembaliRencana() {
    return tglKembaliRencana;
  }

  public void setTglKembaliRencana(Date tglKembaliRencana) {
    this.tglKembaliRencana = tglKembaliRencana;
  }

  public int getTotalHarga() {
    return totalHarga;
  }

  public void setTotalHarga(int totalHarga) {
    this.totalHarga = totalHarga;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  // Database methods
  public List<Penyewaan> getAllPenyewaan() {
    List<Penyewaan> penyewaanList = new ArrayList<>();
    String query = "SELECT * FROM penyewaan";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);

      while (rs.next()) {
        Penyewaan penyewaan = new Penyewaan();
        penyewaan.setIdSewa(rs.getInt("id_sewa"));
        penyewaan.setIdPelanggan(rs.getInt("id_pelanggan"));
        penyewaan.setIdMobil(rs.getInt("id_mobil"));

        // Handle nullable idSopir
        int idSopir = rs.getInt("id_sopir");
        if (!rs.wasNull()) {
          penyewaan.setIdSopir(idSopir);
        } else {
          penyewaan.setIdSopir(null);
        }

        penyewaan.setIdKaryawan(rs.getInt("id_karyawan"));
        penyewaan.setTglSewa(rs.getDate("tgl_sewa"));
        penyewaan.setTglKembaliRencana(rs.getDate("tgl_kembali_rencana"));
        penyewaan.setTotalHarga(rs.getInt("total_harga"));
        penyewaan.setStatus(rs.getString("status"));

        penyewaanList.add(penyewaan);
      }
    } catch (SQLException e) {
      System.err.println("Error getting penyewaan list: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        DatabaseConnection.closeConnection(conn); // Menggunakan metode baru untuk menutup koneksi
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }
    return penyewaanList;
  }

  public List<Penyewaan> getActivePenyewaan() {
    List<Penyewaan> activePenyewaanList = new ArrayList<>();
    String query = "SELECT * FROM penyewaan WHERE status = 'disewa'";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);

      while (rs.next()) {
        Penyewaan penyewaan = new Penyewaan();
        penyewaan.setIdSewa(rs.getInt("id_sewa"));
        penyewaan.setIdPelanggan(rs.getInt("id_pelanggan"));
        penyewaan.setIdMobil(rs.getInt("id_mobil"));

        // Handle nullable idSopir
        int idSopir = rs.getInt("id_sopir");
        if (!rs.wasNull()) {
          penyewaan.setIdSopir(idSopir);
        } else {
          penyewaan.setIdSopir(null);
        }

        penyewaan.setIdKaryawan(rs.getInt("id_karyawan"));
        penyewaan.setTglSewa(rs.getDate("tgl_sewa"));
        penyewaan.setTglKembaliRencana(rs.getDate("tgl_kembali_rencana"));
        penyewaan.setTotalHarga(rs.getInt("total_harga"));
        penyewaan.setStatus(rs.getString("status"));

        activePenyewaanList.add(penyewaan);
      }
    } catch (SQLException e) {
      System.err.println("Error getting active penyewaan: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        DatabaseConnection.closeConnection(conn); // Menggunakan metode baru untuk menutup koneksi
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return activePenyewaanList;
  }

  public Penyewaan getPenyewaanById(int idSewa) {
    Penyewaan penyewaan = null;
    String query = "SELECT * FROM penyewaan WHERE id_sewa = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, idSewa);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        penyewaan = new Penyewaan();
        penyewaan.setIdSewa(rs.getInt("id_sewa"));
        penyewaan.setIdPelanggan(rs.getInt("id_pelanggan"));
        penyewaan.setIdMobil(rs.getInt("id_mobil"));

        // Handle nullable idSopir
        int idSopir = rs.getInt("id_sopir");
        if (!rs.wasNull()) {
          penyewaan.setIdSopir(idSopir);
        } else {
          penyewaan.setIdSopir(null);
        }

        penyewaan.setIdKaryawan(rs.getInt("id_karyawan"));
        penyewaan.setTglSewa(rs.getDate("tgl_sewa"));
        penyewaan.setTglKembaliRencana(rs.getDate("tgl_kembali_rencana"));
        penyewaan.setTotalHarga(rs.getInt("total_harga"));
        penyewaan.setStatus(rs.getString("status"));
      }
    } catch (SQLException e) {
      System.err.println("Error getting penyewaan by ID: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (pstmt != null)
          pstmt.close();
        DatabaseConnection.closeConnection(conn); // Menggunakan metode baru untuk menutup koneksi
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return penyewaan;
  }

  public boolean addPenyewaan() {
    String query = "INSERT INTO penyewaan (id_pelanggan, id_mobil, id_sopir, id_karyawan, tgl_sewa, " +
        "tgl_kembali_rencana, total_harga, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean success = false;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      pstmt.setInt(1, idPelanggan);
      pstmt.setInt(2, idMobil);

      // Handle nullable idSopir
      if (idSopir != null) {
        pstmt.setInt(3, idSopir);
      } else {
        pstmt.setNull(3, java.sql.Types.INTEGER);
      }

      pstmt.setInt(4, idKaryawan);
      pstmt.setDate(5, tglSewa);
      pstmt.setDate(6, tglKembaliRencana);
      pstmt.setInt(7, totalHarga);
      pstmt.setString(8, status);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          idSewa = rs.getInt(1);
        }
        success = true;
      }
    } catch (SQLException e) {
      System.err.println("Error adding penyewaan: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (pstmt != null)
          pstmt.close();
        DatabaseConnection.closeConnection(conn); // Menggunakan metode baru untuk menutup koneksi
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    // Pindahkan pembaruan status sopir ke luar dari blok try-finally
    // untuk menghindari penggunaan koneksi yang sama
    if (success && idSopir != null) {
      updateSopirStatus();
    }

    return success;
  }

  // Metode tambahan untuk memperbarui status sopir
  private void updateSopirStatus() {
    if (idSopir != null) {
      Sopir sopir = new Sopir();
      sopir.setIdSopir(idSopir);
      sopir.updateStatus("dalam tugas");
    }
  }

  public boolean updatePenyewaan() {
    String query = "UPDATE penyewaan SET id_pelanggan = ?, id_mobil = ?, id_sopir = ?, id_karyawan = ?, " +
        "tgl_sewa = ?, tgl_kembali_rencana = ?, total_harga = ?, status = ? WHERE id_sewa = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    boolean success = false;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setInt(1, idPelanggan);
      pstmt.setInt(2, idMobil);

      // Handle nullable idSopir
      if (idSopir != null) {
        pstmt.setInt(3, idSopir);
      } else {
        pstmt.setNull(3, java.sql.Types.INTEGER);
      }

      pstmt.setInt(4, idKaryawan);
      pstmt.setDate(5, tglSewa);
      pstmt.setDate(6, tglKembaliRencana);
      pstmt.setInt(7, totalHarga);
      pstmt.setString(8, status);
      pstmt.setInt(9, idSewa);

      int rowsAffected = pstmt.executeUpdate();
      success = rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error updating penyewaan: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null)
          pstmt.close();
        DatabaseConnection.closeConnection(conn); // Menggunakan metode baru untuk menutup koneksi
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return success;
  }

  public boolean deletePenyewaan() {
    // First get current penyewaan data to know which mobil and sopir to update
    Penyewaan currentData = getPenyewaanById(idSewa);
    if (currentData == null) {
      return false;
    }

    // Store the IDs for later use
    int mobilId = currentData.getIdMobil();
    Integer sopirId = currentData.getIdSopir();

    // Now delete the penyewaan
    String query = "DELETE FROM penyewaan WHERE id_sewa = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    boolean success = false;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setInt(1, idSewa);

      int rowsAffected = pstmt.executeUpdate();
      success = rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error deleting penyewaan: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null)
          pstmt.close();
        DatabaseConnection.closeConnection(conn); // Menggunakan metode baru untuk menutup koneksi
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    // If deletion was successful, update associated resources
    if (success) {
      // Update mobil status to 'tersedia'
      Mobil mobil = new Mobil();
      mobil.setIdMobil(mobilId);
      mobil.updateStatus("tersedia");

      // Update sopir status if a sopir was assigned
      if (sopirId != null) {
        Sopir sopir = new Sopir();
        sopir.setIdSopir(sopirId);
        sopir.updateStatus("tersedia");
      }
    }

    return success;
  }

  public boolean updateStatus(String newStatus) {
    String query = "UPDATE penyewaan SET status = ? WHERE id_sewa = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    boolean success = false;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, newStatus);
      pstmt.setInt(2, idSewa);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        this.status = newStatus;
        // Status updates for mobil and sopir should be handled by the controller
        success = true;
      }
    } catch (SQLException e) {
      System.err.println("Error updating penyewaan status: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null)
          pstmt.close();
        DatabaseConnection.closeConnection(conn); // Menggunakan metode baru untuk menutup koneksi
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return success;
  }

  // Additional method to get detail information
  public String getDetailInfo() {
    StringBuilder detail = new StringBuilder();
    // Tidak perlu membuat koneksi di sini karena setiap model akan membuat koneksi
    // sendiri

    try {
      // Get Pelanggan info
      Pelanggan pelanggan = new Pelanggan().getPelangganById(idPelanggan);
      if (pelanggan != null) {
        detail.append("Pelanggan: ").append(pelanggan.getNama()).append("\n");
        detail.append("Telepon: ").append(pelanggan.getNoTelepon()).append("\n");
      }

      // Get Mobil info
      Mobil mobil = new Mobil().getMobilById(idMobil);
      if (mobil != null) {
        detail.append("Mobil: ").append(mobil.getMerk()).append("\n");
        detail.append("No. Polisi: ").append(mobil.getNoPolisi()).append("\n");
      }

      // Get Sopir info if available
      if (idSopir != null) {
        Sopir sopir = new Sopir().getSopirById(idSopir);
        if (sopir != null) {
          detail.append("Sopir: ").append(sopir.getNama()).append("\n");
          detail.append("No. SIM: ").append(sopir.getNoSim()).append("\n");
        }
      } else {
        detail.append("Tanpa Sopir\n");
      }

      // Get Karyawan info
      Karyawan karyawan = new Karyawan().getKaryawanById(idKaryawan);
      if (karyawan != null) {
        detail.append("Petugas: ").append(karyawan.getNama()).append("\n");
      }

      // Tanggal dan Biaya
      detail.append("Tanggal Sewa: ").append(tglSewa).append("\n");
      detail.append("Rencana Kembali: ").append(tglKembaliRencana).append("\n");
      detail.append("Total Biaya: Rp").append(totalHarga).append("\n");
      detail.append("Status: ").append(status);
    } catch (Exception e) {
      System.err.println("Error getting detail info: " + e.getMessage());
    }
    // Tidak perlu menutup koneksi di sini karena setiap model mengelola koneksinya
    // sendiri

    return detail.toString();
  }
}
