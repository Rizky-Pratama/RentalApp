package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

public class Karyawan {

    private int idKaryawan;
    private String nama;
    private String username;
    private String password;
    private String role; // 'admin' atau 'staff'

    // Constructor
    public Karyawan() {
    }

    public Karyawan(int idKaryawan, String nama, String username, String password, String role) {
        this.idKaryawan = idKaryawan;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(int idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return nama + " (" + role + ")";
    }

    public List<Karyawan> getAllKaryawan() {
        List<Karyawan> karyawanList = new ArrayList<>();
        String query = "SELECT * FROM karyawan";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Karyawan karyawan = new Karyawan();
                karyawan.setIdKaryawan(rs.getInt("id_karyawan"));
                karyawan.setNama(rs.getString("nama"));
                karyawan.setUsername(rs.getString("username"));
                karyawan.setPassword(rs.getString("password"));
                karyawan.setRole(rs.getString("role"));

                karyawanList.add(karyawan);
            }
        } catch (SQLException e) {
            System.err.println("Error getting karyawan list: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                // Jangan tutup koneksi karena itu adalah koneksi singleton
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return karyawanList;
    }

    public Karyawan getKaryawanById(int idKaryawan) {
        Karyawan karyawan = null;
        String query = "SELECT * FROM karyawan WHERE id_karyawan = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idKaryawan);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                karyawan = new Karyawan();
                karyawan.setIdKaryawan(rs.getInt("id_karyawan"));
                karyawan.setNama(rs.getString("nama"));
                karyawan.setUsername(rs.getString("username"));
                karyawan.setPassword(rs.getString("password"));
                karyawan.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting karyawan by ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                // Jangan tutup koneksi karena itu adalah koneksi singleton
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return karyawan;
    }

    public Karyawan login(String username, String password) {
        Karyawan karyawan = null;
        String query = "SELECT * FROM karyawan WHERE username = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                karyawan = new Karyawan();
                karyawan.setIdKaryawan(rs.getInt("id_karyawan"));
                karyawan.setNama(rs.getString("nama"));
                karyawan.setUsername(rs.getString("username"));
                karyawan.setPassword(rs.getString("password"));
                karyawan.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                // Jangan tutup koneksi karena itu adalah koneksi singleton
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return karyawan;
    }

    public boolean addKaryawan() {
        String query = "INSERT INTO karyawan (nama, username, password, role) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, nama);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, role);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    idKaryawan = rs.getInt(1);
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding karyawan: " + e.getMessage());
        } finally {
            // Tutup resources tapi jangan close connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }

    public boolean updateKaryawan() {
        String query = "UPDATE karyawan SET nama = ?, username = ?, role = ? WHERE id_karyawan = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, nama);
            pstmt.setString(2, username);
            pstmt.setString(3, role);
            pstmt.setInt(4, idKaryawan);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating karyawan: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                // Jangan tutup koneksi karena itu adalah koneksi singleton
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }

    public boolean updatePassword(String newPassword) {
        String query = "UPDATE karyawan SET password = ? WHERE id_karyawan = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, newPassword);
            pstmt.setInt(2, idKaryawan);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                this.password = newPassword;
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                // Jangan tutup koneksi karena itu adalah koneksi singleton
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }

    public boolean deleteKaryawan() {
        String query = "DELETE FROM karyawan WHERE id_karyawan = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, idKaryawan);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting karyawan: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                // Jangan tutup koneksi karena itu adalah koneksi singleton
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return false;
    }
}
