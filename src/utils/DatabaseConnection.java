package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas utilitas untuk mengelola koneksi database.
 * Menggunakan metode connection pool sederhana untuk menghindari error "No
 * operations allowed after connection closed"
 * ketika satu model dipanggil oleh model lain.
 */
public class DatabaseConnection {
  private static final String URL = "jdbc:mysql://localhost:3306/rental_mobil";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  /**
   * Mendapatkan koneksi baru ke database setiap kali dipanggil.
   * Ini mencegah masalah saat satu model menutup koneksi yang sedang digunakan
   * model lain.
   * 
   * @return Connection objek koneksi database baru
   */
  public static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("Error koneksi database: " + e.getMessage());
    }
    return connection;
  }

  /**
   * Menutup koneksi database dengan aman.
   * Method ini sebaiknya dipanggil di blok finally setelah setiap operasi
   * database.
   * 
   * @param connection koneksi yang akan ditutup
   */
  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.err.println("Error menutup koneksi: " + e.getMessage());
      }
    }
  }
}
