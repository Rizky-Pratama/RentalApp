/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rentalapp;

/**
 *
 * @author ThinkPad
 */
public class RentalApp {    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RentalApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            // Cek apakah ada user yang sudah login
            utils.UserSession session = utils.UserSession.getInstance();
            if (session.isLoggedIn()) {
                // Jika sudah login, tampilkan MainFrame
                MainFrame frame = new MainFrame();
                frame.setLocationRelativeTo(null); // Posisikan di tengah layar
                frame.setVisible(true);
            } else {
                // Jika belum login, tampilkan form Login
                Login login = new Login();
                login.setLocationRelativeTo(null); // Posisikan di tengah layar
                login.setVisible(true);
            }
        });
    }

}
