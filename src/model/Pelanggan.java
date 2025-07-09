package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

public class Pelanggan {

    private int idPelanggan;
    private String nama;
    private String alamat;
    private String noTelepon;
    private String email;

    // Constructor
    public Pelanggan() {
    }

    public Pelanggan(int idPelanggan, String nama, String alamat, String noTelepon, String email) {
        this.idPelanggan = idPelanggan;
        this.nama = nama;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.email = email;
    }

    // Getters and Setters
    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nama + " (" + noTelepon + ")";
    }

    public List<Pelanggan> getAllPelanggan() {
        List<Pelanggan> pelangganList = new ArrayList<>();
        String query = "SELECT * FROM pelanggan";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Pelanggan pelanggan = new Pelanggan();
                pelanggan.setIdPelanggan(rs.getInt("id_pelanggan"));
                pelanggan.setNama(rs.getString("nama"));
                pelanggan.setAlamat(rs.getString("alamat"));
                pelanggan.setNoTelepon(rs.getString("no_telepon"));
                pelanggan.setEmail(rs.getString("email"));

                pelangganList.add(pelanggan);
            }
        } catch (SQLException e) {
            System.err.println("Error getting pelanggan list: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                // Tutup koneksi menggunakan metode closeConnection yang baru
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return pelangganList;
    }

    public Pelanggan getPelangganById(int idPelanggan) {
        Pelanggan pelanggan = null;
        String query = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, idPelanggan);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pelanggan = new Pelanggan();
                pelanggan.setIdPelanggan(rs.getInt("id_pelanggan"));
                pelanggan.setNama(rs.getString("nama"));
                pelanggan.setAlamat(rs.getString("alamat"));
                pelanggan.setNoTelepon(rs.getString("no_telepon"));
                pelanggan.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting pelanggan by ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                // Tutup koneksi menggunakan metode closeConnection
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return pelanggan;
    }

    public boolean addPelanggan() {
        String query = "INSERT INTO pelanggan (nama, alamat, no_telepon, email) VALUES (?, ?, ?, ?)";

        // try (Connection conn = DatabaseConnection.getConnection();
        // PreparedStatement pstmt = conn.prepareStatement(query,
        // Statement.RETURN_GENERATED_KEYS)) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, nama);
            pstmt.setString(2, alamat);
            pstmt.setString(3, noTelepon);
            pstmt.setString(4, email);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    idPelanggan = rs.getInt(1);
                }
                rs.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding pelanggan: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                // Tutup koneksi menggunakan metode closeConnection
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }

    public boolean updatePelanggan() {
        String query = "UPDATE pelanggan SET nama = ?, alamat = ?, no_telepon = ?, email = ? WHERE id_pelanggan = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, nama);
            pstmt.setString(2, alamat);
            pstmt.setString(3, noTelepon);
            pstmt.setString(4, email);
            pstmt.setInt(5, idPelanggan);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating pelanggan: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                // Tutup koneksi menggunakan metode closeConnection
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }

    public boolean deletePelanggan() {
        String query = "DELETE FROM pelanggan WHERE id_pelanggan = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, idPelanggan);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting pelanggan: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                // Tutup koneksi menggunakan metode closeConnection
                DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }
}
