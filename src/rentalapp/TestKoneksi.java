/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentalapp;

import java.sql.Connection;
import java.sql.SQLException;
import utils.DatabaseConnection;

public class TestKoneksi {

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Koneksi ke database berhasil!");
            } else {
                System.out.println("❌ Gagal terkoneksi ke database.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Kesalahan SQL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error umum: " + e.getMessage());
        }
    }
}
