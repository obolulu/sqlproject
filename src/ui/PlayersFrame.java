/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;


import dao.ExpansionDAO;
import dao.PlayerDAO;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.PlayerExpansionDAO;
import model.Expansion;
import model.Player;

/**
 *
 * @author esena
 */
public class PlayersFrame extends javax.swing.JFrame {

    /**
     * Creates new form PlayersFrame
     */
    
    private PlayerDAO playerDAO;
    private PlayerExpansionDAO playerExpansionDAO;

    private ExpansionDAO expansionDAO;
    public PlayersFrame() {
        initComponents();
        playerDAO = new PlayerDAO();
        playerExpansionDAO = new PlayerExpansionDAO();
        expansionDAO = new ExpansionDAO();
        loadPlayersTable();
        jTablePlayers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting() && jTablePlayers.getSelectedRow() != -1) {
                            displaySelectedPlayer();
                        } else if (jTablePlayers.getSelectedRow() == -1) {
                            clearForm();
                            jButtonUpdate.setEnabled(false);
                            jButtonDelete.setEnabled(false);
                        }
                    }
                });

                // Initially disable update and delete buttons
                jButtonUpdate.setEnabled(false);
                jButtonDelete.setEnabled(false);
            }
    
        private void clearForm() {
        jTextFieldPlayerId.setText("");
        jTextFieldUsername.setText("");
        jTextFieldRealName.setText("");
        jTextFieldEmail.setText("");
        jTablePlayers.clearSelection();
        jButtonUpdate.setEnabled(false);
        jButtonDelete.setEnabled(false);
    }
        
        private void loadPlayersTable() {
        DefaultTableModel model = (DefaultTableModel) jTablePlayers.getModel();
        model.setRowCount(0);
           
        
        //dynamically creates the rows
        //no need to modify if they are entered later on in UI instead of
        // from here
        if (model.getColumnCount() == 0) {
            model.setColumnIdentifiers(new Object[]{"ID", "Username", "Real Name", "Email"});
        }

        List<Player> players = playerDAO.getAllPlayers();
        for (Player player : players) {
            model.addRow(new Object[]{
                player.getPlayerId(),
                player.getUsername(),
                player.getRealName(),
                player.getEmail()
            });
        }
    }

    private void loadPlayerExpansions(){
        int selectedRow = jTablePlayers.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        int playerId = (Integer) jTablePlayers.getValueAt(selectedRow, 0);
        
        // Load owned expansions
        List<Expansion> ownedExpansions = playerExpansionDAO.getExpansionsOwnedByPlayer(playerId);
        
        // Create new DefaultListModel for owned expansions
        DefaultListModel<String> ownedModel = new DefaultListModel<>();
        for (Expansion exp : ownedExpansions) {
            ownedModel.addElement(exp.getExpansionId() + " " + exp.getName());
        }
        jListOwnedExpansion.setModel(ownedModel);

        // Load unowned expansions
        List<Expansion> unownedExpansions = new ArrayList<>();
        List<Expansion> allExpansions = expansionDAO.getAllExpansions();
        for (Expansion exp : allExpansions) {
            if (!ownedExpansions.contains(exp)) {
                unownedExpansions.add(exp);
            }
        }
        
        // Create new DefaultListModel for unowned expansions
        DefaultListModel<String> unownedModel = new DefaultListModel<>();
        for (Expansion exp : unownedExpansions) {
            unownedModel.addElement(exp.getExpansionId() + " " + exp.getName());
        }
        jListUnownedExpansions.setModel(unownedModel);
    }


    private void displaySelectedPlayer() {
        int selectedRow = jTablePlayers.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        int playerId = (Integer) jTablePlayers.getValueAt(selectedRow, 0);

        Player player = playerDAO.getPlayerById(playerId);

        if (player != null) {
            jTextFieldPlayerId.setText(String.valueOf(player.getPlayerId()));
            jTextFieldUsername.setText(player.getUsername());
            jTextFieldRealName.setText(player.getRealName());
            jTextFieldEmail.setText(player.getEmail());
            jButtonUpdate.setEnabled(true);
            jButtonDelete.setEnabled(true);
            loadPlayerExpansions();
        } else {
            JOptionPane.showMessageDialog(this, "Player not found ", "Error", JOptionPane.ERROR_MESSAGE);
            clearForm();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PlayerDetails = new javax.swing.JPanel();
        ID = new javax.swing.JLabel();
        jTextFieldPlayerId = new javax.swing.JTextField();
        Username = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        RealName = new javax.swing.JLabel();
        jTextFieldRealName = new javax.swing.JTextField();
        Email = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jButtonAdd = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonClearForm = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPanePlayers = new javax.swing.JScrollPane();
        jTablePlayers = new javax.swing.JTable();
        jButtonAddExpansion = new javax.swing.JButton();
        jButtonRemoveExpansion = new javax.swing.JButton();
        jScrollPaneUnownedExpansions = new javax.swing.JScrollPane();
        jListUnownedExpansions = new javax.swing.JList<>();
        jScrollPaneOwnedExpansions = new javax.swing.JScrollPane();
        jListOwnedExpansion = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PlayerDetails.setBorder(new javax.swing.border.MatteBorder(null));

        ID.setText("ID");

        jTextFieldPlayerId.setEditable(false);
        jTextFieldPlayerId.setText("TextFieldPlayerId");
        jTextFieldPlayerId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPlayerIdActionPerformed(evt);
            }
        });

        Username.setText("Username");

        jTextFieldUsername.setText("TextFieldUsername");
        jTextFieldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUsernameActionPerformed(evt);
            }
        });

        RealName.setText("RealName");

        jTextFieldRealName.setText("TextFieldRealName");
        jTextFieldRealName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRealNameActionPerformed(evt);
            }
        });

        Email.setText("Email");

        jTextFieldEmail.setText("EmailTextField");
        jTextFieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmailActionPerformed(evt);
            }
        });

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonClearForm.setText("Clear Form");

        jTablePlayers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPanePlayers.setViewportView(jTablePlayers);

        jButtonAddExpansion.setText("add");
        jButtonAddExpansion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddExpansionActionPerformed(evt);
            }
        });

        jButtonRemoveExpansion.setText("remove");
        jButtonRemoveExpansion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveExpansionActionPerformed(evt);
            }
        });

        jListUnownedExpansions.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPaneUnownedExpansions.setViewportView(jListUnownedExpansions);

        jListOwnedExpansion.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPaneOwnedExpansions.setViewportView(jListOwnedExpansion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPanePlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(399, 399, 399))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jButtonAddExpansion)
                        .addGap(239, 239, 239)
                        .addComponent(jButtonRemoveExpansion))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPaneOwnedExpansions, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPaneUnownedExpansions, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPanePlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddExpansion)
                    .addComponent(jButtonRemoveExpansion))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPaneUnownedExpansions, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPaneOwnedExpansions)))
                .addGap(148, 148, 148))
        );

        javax.swing.GroupLayout PlayerDetailsLayout = new javax.swing.GroupLayout(PlayerDetails);
        PlayerDetails.setLayout(PlayerDetailsLayout);
        PlayerDetailsLayout.setHorizontalGroup(
            PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlayerDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PlayerDetailsLayout.createSequentialGroup()
                        .addComponent(jButtonUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClearForm))
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PlayerDetailsLayout.createSequentialGroup()
                        .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPlayerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PlayerDetailsLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(ID)))
                        .addGap(53, 53, 53)
                        .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PlayerDetailsLayout.createSequentialGroup()
                                .addComponent(Email)
                                .addGap(33, 33, 33))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PlayerDetailsLayout.createSequentialGroup()
                        .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PlayerDetailsLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(Username)))
                        .addGap(31, 31, 31)
                        .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldRealName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlayerDetailsLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(RealName)
                                .addGap(35, 35, 35)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PlayerDetailsLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        PlayerDetailsLayout.setVerticalGroup(
            PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlayerDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PlayerDetailsLayout.createSequentialGroup()
                        .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Email)
                            .addComponent(ID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPlayerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PlayerDetailsLayout.createSequentialGroup()
                        .addComponent(RealName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldRealName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addComponent(jButtonAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PlayerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonDelete)
                    .addComponent(jButtonClearForm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(PlayerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PlayerDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPlayerIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPlayerIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPlayerIdActionPerformed

    private void jTextFieldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUsernameActionPerformed

    private void jTextFieldRealNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRealNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRealNameActionPerformed

    private void jTextFieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmailActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        String username = jTextFieldUsername.getText();
        String realName = jTextFieldRealName.getText();
        String email = jTextFieldEmail.getText();
        
        if(username.isEmpty()){
            JOptionPane.showMessageDialog(this, "Insert Username.", "Input Error",
                    JOptionPane.WARNING_MESSAGE);
        }
        Player newPlayer = new Player(0, username, realName, email);
        int generatedID = playerDAO.addPlayer(newPlayer);
        
        // -1 is for error states from the database ----
        // if return has -1 in it, it means sql database gave an error
        if(generatedID != -1){
            JOptionPane.showMessageDialog(this, "Player added. ID: " + generatedID, "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPlayersTable();
            clearForm();
        } 
        else {
            // either username exists or databse connection had a problem
         JOptionPane.showMessageDialog(this, "Failed to add player. Username might already exist.",
                 "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        if(jTextFieldPlayerId.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please select a player to delete.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int playerId = Integer.parseInt(jTextFieldPlayerId.getText());
        boolean deleted = playerDAO.deletePlayer(playerId);
        if(deleted){
            JOptionPane.showMessageDialog(this, "Player deleted", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPlayersTable();
            clearForm();
        }
        else {
            JOptionPane.showMessageDialog(this, "Failed to update player. Check console for details.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        if(jTextFieldPlayerId.getText().isEmpty()){
         JOptionPane.showMessageDialog(this, "Please select a player to update.",
                 "Selection Error", JOptionPane.WARNING_MESSAGE);
         return;
        }
        int playerId = Integer.parseInt(jTextFieldPlayerId.getText());
        String username = jTextFieldUsername.getText();
        String realName = jTextFieldRealName.getText();
        String email = jTextFieldEmail.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Player updatedPlayer = new Player(playerId, username, realName, email);
        boolean success = playerDAO.updatePlayer(updatedPlayer);
         if (success) {
            JOptionPane.showMessageDialog(this, "Player updated", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPlayersTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update player. Check console for details.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonAddExpansionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddExpansionActionPerformed
        int selectedRow = jListUnownedExpansions.getSelectedIndex();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an expansion to add.", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int expansionId = Integer.parseInt(jListUnownedExpansions.getModel().getElementAt(selectedRow).split(" ")[0]);

        int playerId = Integer.parseInt(jTextFieldPlayerId.getText());
        boolean added = playerExpansionDAO.addPlayerExpansion(playerId, expansionId);

        if (added) {
            JOptionPane.showMessageDialog(this, "Expansion added to player.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPlayerExpansions();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add expansion. Check console for details.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAddExpansionActionPerformed

    private void jButtonRemoveExpansionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveExpansionActionPerformed
        int selectedRow = jListOwnedExpansion.getSelectedIndex();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an owned expansion to remove.", "Selection Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int playerId = Integer.parseInt(jTextFieldPlayerId.getText());
        int expansionId = Integer.parseInt(jListOwnedExpansion.getModel().getElementAt(selectedRow).split(" ")[0]);
        boolean removed = playerExpansionDAO.removePlayerExpansion(playerId, expansionId);

        if (removed) {
            JOptionPane.showMessageDialog(this, "Expansion removed from player.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPlayerExpansions();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove expansion. Check console for details.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonRemoveExpansionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlayersFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlayersFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlayersFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayersFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlayersFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Email;
    private javax.swing.JLabel ID;
    private javax.swing.JPanel PlayerDetails;
    private javax.swing.JLabel RealName;
    private javax.swing.JLabel Username;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddExpansion;
    private javax.swing.JButton jButtonClearForm;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonRemoveExpansion;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JList<String> jListOwnedExpansion;
    private javax.swing.JList<String> jListUnownedExpansions;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPaneOwnedExpansions;
    private javax.swing.JScrollPane jScrollPanePlayers;
    private javax.swing.JScrollPane jScrollPaneUnownedExpansions;
    private javax.swing.JTable jTablePlayers;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldPlayerId;
    private javax.swing.JTextField jTextFieldRealName;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables
}
