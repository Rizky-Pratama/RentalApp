/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rentalapp;

import java.awt.CardLayout;

/**
 * Main application frame that handles panel switching and application flow
 *
 * @author ThinkPad
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Enum representing different panel types in the application Using an enum
     * improves type safety and prevents string-based errors
     */
    public enum PanelType {
        DASHBOARD("dashboard"),
        MOBIL("mobil"),
        PELANGGAN("pelanggan"),
        SOPIR("sopir"),
        PENYEWAAN("penyewaan"),
        PENGEMBALIAN("pengembalian"),
        KARYAWAN("karyawan");

        private final String id;

        PanelType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private MobilPanel mobilPanel;
    private DashboardPanel dashboardPanel;
    private PelangganPanel pelangganPanel;
    private SopirPanel sopirPanel;
    private KaryawanPanel karyawanPanel;
    private PenyewaanPanel penyewaanPanel;
    private PengembalianPanel pengembalianPanel;

    /**
     * Initializes custom components and sets up the panel navigation system
     * Called from the constructor after initComponents()
     */
    private void initCustomComponents() {
        // Cek user login
        checkLoggedInUser();

        // Initialize panels lazily to improve startup performance
        initializePanels();

        // Register panels to content area
        registerPanels();

        // Set up the sidebar navigation
        setupSidebar();

        // Show default panel (Dashboard)
        showPanel(PanelType.DASHBOARD);
    }

    /**
     * Mengecek apakah ada user yang telah login
     */
    private void checkLoggedInUser() {
        utils.UserSession session = utils.UserSession.getInstance();
        if (session.isLoggedIn()) {
            setupAccessByRole(session.getRole());
        } else {
            redirectToLogin();
        }
    }

    /**
     * Mengatur akses menu berdasarkan role user
     */
    private void setupAccessByRole(String role) {
        // Setup role-based access control for sidebar
        if (sidebarPanel1 != null) {
            sidebarPanel1.setupRoleBasedAccess(role);
        }

        if ("staff".equalsIgnoreCase(role)) {
            System.out.println("Access configured for Staff role");
        } else if ("admin".equalsIgnoreCase(role)) {
            System.out.println("Access configured for Admin role - full access granted");
        } else {
            System.out.println("Unknown role: " + role + " - defaulting to staff permissions");
            if (sidebarPanel1 != null) {
                sidebarPanel1.setupRoleBasedAccess("staff");
            }
        }
    }

    /**
     * Kembali ke form login
     */
    public void redirectToLogin() {
        Login loginFrame = new Login();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
        dispose();
    }

    /**
     * Initialize all panels used in the application
     */
    private void initializePanels() {
        dashboardPanel = new DashboardPanel();
        mobilPanel = new MobilPanel();
        pelangganPanel = new PelangganPanel();
        sopirPanel = new SopirPanel();
        karyawanPanel = new KaryawanPanel();
        penyewaanPanel = new PenyewaanPanel();
        pengembalianPanel = new PengembalianPanel();
    }

    /**
     * Register all panels to the content panel with their respective
     * identifiers
     */
    private void registerPanels() {
        contentPanel.add(dashboardPanel, PanelType.DASHBOARD.getId());
        contentPanel.add(mobilPanel, PanelType.MOBIL.getId());
        contentPanel.add(pelangganPanel, PanelType.PELANGGAN.getId());
        contentPanel.add(sopirPanel, PanelType.SOPIR.getId());
        contentPanel.add(karyawanPanel, PanelType.KARYAWAN.getId());
        contentPanel.add(penyewaanPanel, PanelType.PENYEWAAN.getId());
        contentPanel.add(pengembalianPanel, PanelType.PENGEMBALIAN.getId());
    }

    /**
     * Configure the sidebar navigation
     */
    private void setupSidebar() {
        if (sidebarPanel1 != null) {
            sidebarPanel1.setMainFrame(this);

            // Apply role-based access after sidebar is configured
            utils.UserSession session = utils.UserSession.getInstance();
            if (session.isLoggedIn()) {
                sidebarPanel1.setupRoleBasedAccess(session.getRole());
            }
        } else {
            System.err.println("Error: sidebarPanel is not initialized!");
        }
    }

    /**
     * Shows the specified panel in the content area
     *
     * @param panelType The panel to show
     */
    public void showPanel(PanelType panelType) {
        // Check if user has access to the requested panel
        if (!hasAccessToPanel(panelType)) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Anda tidak memiliki akses ke fitur ini.\nHubungi administrator untuk informasi lebih lanjut.",
                    "Akses Ditolak",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelType.getId());
    }

    /**
     * Check if current user has access to the specified panel
     * 
     * @param panelType The panel to check access for
     * @return true if user has access, false otherwise
     */
    private boolean hasAccessToPanel(PanelType panelType) {
        utils.UserSession session = utils.UserSession.getInstance();

        if (!session.isLoggedIn()) {
            return false;
        }

        String role = session.getRole();

        // Admin has access to all panels
        if ("admin".equalsIgnoreCase(role)) {
            return true;
        }

        // Staff has access to all panels except Karyawan
        if ("staff".equalsIgnoreCase(role)) {
            return panelType != PanelType.KARYAWAN;
        }

        // Unknown role - deny access to sensitive panels
        return panelType == PanelType.DASHBOARD;
    }

    /**
     * Legacy method to support existing code
     *
     * @param panelName The panel name to show
     * @deprecated Use showPanel(PanelType) instead
     */
    @Deprecated
    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }

    /**
     * Method untuk melakukan logout
     */
    public void logout() {
        // Hapus session user
        utils.UserSession session = utils.UserSession.getInstance();
        session.clearSession();

        // Redirect ke login form
        redirectToLogin();
    }

    /**
     * Refresh role-based access control
     * This method can be called when user role changes or needs to be re-evaluated
     */
    public void refreshRoleBasedAccess() {
        utils.UserSession session = utils.UserSession.getInstance();
        if (session.isLoggedIn() && sidebarPanel1 != null) {
            sidebarPanel1.setupRoleBasedAccess(session.getRole());
        }
    }

    /**
     * Creates a new MainFrame and initializes all components This is the main
     * entry point for the application UI
     */
    public MainFrame() {
        initComponents();
        initCustomComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        sidebarpanel = new javax.swing.JPanel();
        sidebarPanel1 = new rentalapp.SidebarPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contentPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout sidebarpanelLayout = new javax.swing.GroupLayout(sidebarpanel);
        sidebarpanel.setLayout(sidebarpanelLayout);
        sidebarpanelLayout.setHorizontalGroup(
                sidebarpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarpanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(sidebarPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)));
        sidebarpanelLayout.setVerticalGroup(
                sidebarpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sidebarPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(sidebarpanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 916,
                                        Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                        .addComponent(sidebarpanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private rentalapp.SidebarPanel sidebarPanel1;
    private javax.swing.JPanel sidebarpanel;
    // End of variables declaration//GEN-END:variables
}
