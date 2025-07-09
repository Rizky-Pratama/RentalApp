package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

public class Pengembalian {

    private int idPengembalian;
    private int idSewa;
    private Date tglKembali;
    private int keterlambatan;
    private int denda;
    private int totalBayar;
    private String catatan;

    // Constructor
    public Pengembalian() {
    }

    public Pengembalian(int idPengembalian, int idSewa, Date tglKembali, int keterlambatan,
            int denda, int totalBayar, String catatan) {
        this.idPengembalian = idPengembalian;
        this.idSewa = idSewa;
        this.tglKembali = tglKembali;
        this.keterlambatan = keterlambatan;
        this.denda = denda;
        this.totalBayar = totalBayar;
        this.catatan = catatan;
    }

    // Getters and Setters
    public int getIdPengembalian() {
        return idPengembalian;
    }

    public void setIdPengembalian(int idPengembalian) {
        this.idPengembalian = idPengembalian;
    }

    public int getIdSewa() {
        return idSewa;
    }

    public void setIdSewa(int idSewa) {
        this.idSewa = idSewa;
    }

    public Date getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(Date tglKembali) {
        this.tglKembali = tglKembali;
    }

    public int getKeterlambatan() {
        return keterlambatan;
    }

    public void setKeterlambatan(int keterlambatan) {
        this.keterlambatan = keterlambatan;
    }

    public int getDenda() {
        return denda;
    }

    public void setDenda(int denda) {
        this.denda = denda;
    }

    public int getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    // Database methods
    public List<Pengembalian> getAllPengembalian() {
        List<Pengembalian> pengembalianList = new ArrayList<>();
        String query = "SELECT * FROM pengembalian";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Pengembalian pengembalian = new Pengembalian();
                pengembalian.setIdPengembalian(rs.getInt("id_pengembalian"));
                pengembalian.setIdSewa(rs.getInt("id_sewa"));
                pengembalian.setTglKembali(rs.getDate("tgl_kembali"));
                pengembalian.setKeterlambatan(rs.getInt("keterlambatan"));
                pengembalian.setDenda(rs.getInt("denda"));
                pengembalian.setTotalBayar(rs.getInt("total_bayar"));
                pengembalian.setCatatan(rs.getString("catatan"));

                pengembalianList.add(pengembalian);
            }
        } catch (SQLException e) {
            System.err.println("Error getting pengembalian list: " + e.getMessage());
        }

        return pengembalianList;
    }

    public Pengembalian getPengembalianById(int idPengembalian) {
        Pengembalian pengembalian = null;
        String query = "SELECT * FROM pengembalian WHERE id_pengembalian = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPengembalian);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pengembalian = new Pengembalian();
                pengembalian.setIdPengembalian(rs.getInt("id_pengembalian"));
                pengembalian.setIdSewa(rs.getInt("id_sewa"));
                pengembalian.setTglKembali(rs.getDate("tgl_kembali"));
                pengembalian.setKeterlambatan(rs.getInt("keterlambatan"));
                pengembalian.setDenda(rs.getInt("denda"));
                pengembalian.setTotalBayar(rs.getInt("total_bayar"));
                pengembalian.setCatatan(rs.getString("catatan"));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error getting pengembalian by ID: " + e.getMessage());
        }

        return pengembalian;
    }

    public Pengembalian getPengembalianByIdSewa(int idSewa) {
        Pengembalian pengembalian = null;
        String query = "SELECT * FROM pengembalian WHERE id_sewa = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idSewa);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pengembalian = new Pengembalian();
                pengembalian.setIdPengembalian(rs.getInt("id_pengembalian"));
                pengembalian.setIdSewa(rs.getInt("id_sewa"));
                pengembalian.setTglKembali(rs.getDate("tgl_kembali"));
                pengembalian.setKeterlambatan(rs.getInt("keterlambatan"));
                pengembalian.setDenda(rs.getInt("denda"));
                pengembalian.setTotalBayar(rs.getInt("total_bayar"));
                pengembalian.setCatatan(rs.getString("catatan"));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error getting pengembalian by ID sewa: " + e.getMessage());
        }

        return pengembalian;
    }

    public boolean addPengembalian() {
        String query = "INSERT INTO pengembalian (id_sewa, tgl_kembali, keterlambatan, denda, total_bayar, catatan) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, idSewa);
            pstmt.setDate(2, tglKembali);
            pstmt.setInt(3, keterlambatan);
            pstmt.setInt(4, denda);
            pstmt.setInt(5, totalBayar);
            pstmt.setString(6, catatan);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    idPengembalian = rs.getInt(1);
                }
                rs.close();

                // Update penyewaan status to 'selesai'
                Penyewaan penyewaan = new Penyewaan();
                penyewaan.setIdSewa(idSewa);
                penyewaan.updateStatus("selesai");

                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding pengembalian: " + e.getMessage());
        }

        return false;
    }

    public boolean updatePengembalian() {
        String query = "UPDATE pengembalian SET id_sewa = ?, tgl_kembali = ?, keterlambatan = ?, "
                + "denda = ?, total_bayar = ?, catatan = ? WHERE id_pengembalian = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idSewa);
            pstmt.setDate(2, tglKembali);
            pstmt.setInt(3, keterlambatan);
            pstmt.setInt(4, denda);
            pstmt.setInt(5, totalBayar);
            pstmt.setString(6, catatan);
            pstmt.setInt(7, idPengembalian);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating pengembalian: " + e.getMessage());
        }

        return false;
    }

    public boolean deletePengembalian() {
        String query = "DELETE FROM pengembalian WHERE id_pengembalian = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPengembalian);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting pengembalian: " + e.getMessage());
        }

        return false;
    }

    // Additional methods for calculating denda and total_bayar
    public int hitungDenda(Date tglRencanaKembali, Date tglKembali, int dendaPerHari) {
        if (tglKembali.after(tglRencanaKembali)) {
            long diffInMillies = tglKembali.getTime() - tglRencanaKembali.getTime();
            int diffInDays = (int) Math.ceil(diffInMillies / (1000.0 * 60 * 60 * 24));
            this.keterlambatan = diffInDays;
            this.denda = dendaPerHari * diffInDays;
            return this.denda;
        }
        this.keterlambatan = 0;
        this.denda = 0;
        return 0;
    }
    
    // Calculate total payment (rental cost + fines)
    public void hitungTotalBayar(int biayaSewa) {
        this.totalBayar = biayaSewa + this.denda;
    }
}
