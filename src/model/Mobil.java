package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

/**
 *
 * @author ThinkPad
 */
public class Mobil {

  private int idMobil;
  private String merk;
  private int tahun;
  private String noPolisi;
  private String status; // 'tersedia' atau 'disewa'
  private int hargaSewa;
  private int dendaPerHari;

  // Constructor
  public Mobil() {
  }

  public Mobil(int idMobil, String merk, int tahun, String noPolisi, String status, int hargaSewa,
      int dendaPerHari) {
    this.idMobil = idMobil;
    this.merk = merk;
    this.tahun = tahun;
    this.noPolisi = noPolisi;
    this.status = status;
    this.hargaSewa = hargaSewa;
    this.dendaPerHari = dendaPerHari;
  }

  // Getters and Setters
  public int getIdMobil() {
    return idMobil;
  }

  public void setIdMobil(int idMobil) {
    this.idMobil = idMobil;
  }

  public String getMerk() {
    return merk;
  }

  public void setMerk(String merk) {
    this.merk = merk;
  }

  public int getTahun() {
    return tahun;
  }

  public void setTahun(int tahun) {
    this.tahun = tahun;
  }

  public String getNoPolisi() {
    return noPolisi;
  }

  public void setNoPolisi(String noPolisi) {
    this.noPolisi = noPolisi;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getHargaSewa() {
    return hargaSewa;
  }

  public void setHargaSewa(int hargaSewa) {
    this.hargaSewa = hargaSewa;
  }

  public int getDendaPerHari() {
    return dendaPerHari;
  }

  public void setDendaPerHari(int dendaPerHari) {
    this.dendaPerHari = dendaPerHari;
  }

  @Override
  public String toString() {
    return merk + " " + "(" + noPolisi + ")";
  }

  // Database methods
  public List<Mobil> getAllMobil() {
    List<Mobil> mobilList = new ArrayList<>();
    String query = "SELECT * FROM mobil";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);

      while (rs.next()) {
        Mobil mobil = new Mobil();
        mobil.setIdMobil(rs.getInt("id_mobil"));
        mobil.setMerk(rs.getString("merk"));
        mobil.setTahun(rs.getInt("tahun"));
        mobil.setNoPolisi(rs.getString("no_polisi"));
        mobil.setStatus(rs.getString("status"));
        mobil.setHargaSewa(rs.getInt("harga_sewa"));
        mobil.setDendaPerHari(rs.getInt("denda_per_hari"));

        mobilList.add(mobil);
      }
    } catch (SQLException e) {
      System.err.println("Error getting mobil list: " + e.getMessage());
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return mobilList;
  }

  public List<Mobil> getAvailableMobil() {
    List<Mobil> availableMobilList = new ArrayList<>();
    String query = "SELECT * FROM mobil WHERE status = 'tersedia'";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);

      while (rs.next()) {
        Mobil mobil = new Mobil();
        mobil.setIdMobil(rs.getInt("id_mobil"));
        mobil.setMerk(rs.getString("merk"));
        mobil.setTahun(rs.getInt("tahun"));
        mobil.setNoPolisi(rs.getString("no_polisi"));
        mobil.setStatus(rs.getString("status"));
        mobil.setHargaSewa(rs.getInt("harga_sewa"));
        mobil.setDendaPerHari(rs.getInt("denda_per_hari"));

        availableMobilList.add(mobil);
      }
    } catch (SQLException e) {
      System.err.println("Error getting available mobil: " + e.getMessage());
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return availableMobilList;
  }

  public Mobil getMobilById(int id) {
    String query = "SELECT * FROM mobil WHERE id_mobil = ?";
    Mobil mobil = null;

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setInt(1, id);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        mobil = new Mobil();
        mobil.setIdMobil(rs.getInt("id_mobil"));
        mobil.setMerk(rs.getString("merk"));
        mobil.setTahun(rs.getInt("tahun"));
        mobil.setNoPolisi(rs.getString("no_polisi"));
        mobil.setStatus(rs.getString("status"));
        mobil.setHargaSewa(rs.getInt("harga_sewa"));
        mobil.setDendaPerHari(rs.getInt("denda_per_hari"));
      }
    } catch (SQLException e) {
      System.err.println("Error getting mobil by ID: " + e.getMessage());
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
        }
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return mobil;
  }

  public boolean addMobil() {
    String query = "INSERT INTO mobil (merk, tahun, no_polisi, status, harga_sewa, denda_per_hari) "
        + "VALUES (?, ?, ?, ?, ?, ?)";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      pstmt.setString(1, merk);
      pstmt.setInt(2, tahun);
      pstmt.setString(3, noPolisi);
      pstmt.setString(4, status);
      pstmt.setInt(5, hargaSewa);
      pstmt.setInt(6, dendaPerHari);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          idMobil = rs.getInt(1);
        }
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Error adding mobil: " + e.getMessage());
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstmt != null) {
          pstmt.close();
        }
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean updateMobil() {
    String query = "UPDATE mobil SET merk = ?, tahun = ?, no_polisi = ?, "
        + "status = ?, harga_sewa = ?, denda_per_hari = ? WHERE id_mobil = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, merk);
      pstmt.setInt(2, tahun);
      pstmt.setString(3, noPolisi);
      pstmt.setString(4, status);
      pstmt.setInt(5, hargaSewa);
      pstmt.setInt(6, dendaPerHari);
      pstmt.setInt(7, idMobil);

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error updating mobil: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean deleteMobil() {
    String query = "DELETE FROM mobil WHERE id_mobil = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setInt(1, idMobil);

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error deleting mobil: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean updateStatus(String newStatus) {
    String query = "UPDATE mobil SET status = ? WHERE id_mobil = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, newStatus);
      pstmt.setInt(2, idMobil);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        this.status = newStatus;
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Error updating mobil status: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null) {
          pstmt.close();
        }
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }
}
