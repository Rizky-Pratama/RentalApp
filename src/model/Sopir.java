package model;

import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sopir {
  private int idSopir;
  private String nama;
  private String noSim;
  private int tarifPerHari;
  private String status; // 'tersedia' atau 'dalam tugas'

  // Constructor
  public Sopir() {
  }

  public Sopir(int idSopir, String nama, String noSim, int tarifPerHari, String status) {
    this.idSopir = idSopir;
    this.nama = nama;
    this.noSim = noSim;
    this.tarifPerHari = tarifPerHari;
    this.status = status;
  }

  // Getters and Setters
  public int getIdSopir() {
    return idSopir;
  }

  public void setIdSopir(int idSopir) {
    this.idSopir = idSopir;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getNoSim() {
    return noSim;
  }

  public void setNoSim(String noSim) {
    this.noSim = noSim;
  }

  public int getTarifPerHari() {
    return tarifPerHari;
  }

  public void setTarifPerHari(int tarifPerHari) {
    this.tarifPerHari = tarifPerHari;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return nama + " (SIM: " + noSim + ")";
  }

  // Database methods
  public List<Sopir> getAllSopir() {
    List<Sopir> sopirList = new ArrayList<>();
    String query = "SELECT * FROM sopir";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        Sopir sopir = new Sopir();
        sopir.setIdSopir(rs.getInt("id_sopir"));
        sopir.setNama(rs.getString("nama"));
        sopir.setNoSim(rs.getString("no_sim"));
        sopir.setTarifPerHari(rs.getInt("tarif_per_hari"));
        sopir.setStatus(rs.getString("status"));

        sopirList.add(sopir);
      }
    } catch (SQLException e) {
      System.err.println("Error getting sopir list: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return sopirList;
  }

  public List<Sopir> getAvailableSopir() {
    List<Sopir> availableSopirList = new ArrayList<>();
    String query = "SELECT * FROM sopir WHERE status = 'tersedia'";

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query);
      while (rs.next()) {
        Sopir sopir = new Sopir();
        sopir.setIdSopir(rs.getInt("id_sopir"));
        sopir.setNama(rs.getString("nama"));
        sopir.setNoSim(rs.getString("no_sim"));
        sopir.setTarifPerHari(rs.getInt("tarif_per_hari"));
        sopir.setStatus(rs.getString("status"));

        availableSopirList.add(sopir);
      }
    } catch (SQLException e) {
      System.err.println("Error getting available sopir: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (stmt != null)
          stmt.close();
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return availableSopirList;
  }

  public Sopir getSopirById(int idSopir) {
    Sopir sopir = null;
    String query = "SELECT * FROM sopir WHERE id_sopir = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setInt(1, idSopir);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        sopir = new Sopir();
        sopir.setIdSopir(rs.getInt("id_sopir"));
        sopir.setNama(rs.getString("nama"));
        sopir.setNoSim(rs.getString("no_sim"));
        sopir.setTarifPerHari(rs.getInt("tarif_per_hari"));
        sopir.setStatus(rs.getString("status"));
      }
    } catch (SQLException e) {
      System.err.println("Error getting sopir by ID: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (pstmt != null)
          pstmt.close();
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return sopir;
  }

  public boolean addSopir() {
    String query = "INSERT INTO sopir (nama, no_sim, tarif_per_hari, status) VALUES (?, ?, ?, ?)";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, nama);
      pstmt.setString(2, noSim);
      pstmt.setInt(3, tarifPerHari);
      pstmt.setString(4, status);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          idSopir = rs.getInt(1);
        }
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Error adding sopir: " + e.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (pstmt != null)
          pstmt.close();
        // Don't close the connection here, as it's a shared singleton
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean updateSopir() {
    String query = "UPDATE sopir SET nama = ?, no_sim = ?, tarif_per_hari = ?, status = ? WHERE id_sopir = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, nama);
      pstmt.setString(2, noSim);
      pstmt.setInt(3, tarifPerHari);
      pstmt.setString(4, status);
      pstmt.setInt(5, idSopir);

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error updating sopir: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null)
          pstmt.close();
        // Tutup koneksi menggunakan metode closeConnection yang baru
        DatabaseConnection.closeConnection(conn);
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean deleteSopir() {
    String query = "DELETE FROM sopir WHERE id_sopir = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setInt(1, idSopir);

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error deleting sopir: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null)
          pstmt.close();
        // Tutup koneksi menggunakan metode closeConnection yang baru
        DatabaseConnection.closeConnection(conn);
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean updateStatus(String newStatus) {
    String query = "UPDATE sopir SET status = ? WHERE id_sopir = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = DatabaseConnection.getConnection();
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, newStatus);
      pstmt.setInt(2, idSopir);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        this.status = newStatus;
        return true;
      }
    } catch (SQLException e) {
      System.err.println("Error updating sopir status: " + e.getMessage());
    } finally {
      try {
        if (pstmt != null)
          pstmt.close();
        // Tutup koneksi menggunakan metode closeConnection yang baru
        DatabaseConnection.closeConnection(conn);
      } catch (SQLException e) {
        System.err.println("Error closing resources: " + e.getMessage());
      }
    }

    return false;
  }
}
