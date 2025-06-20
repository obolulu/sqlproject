/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import dao.GameDAO;
import dao.PlayerDAO;
import dto.GameSummaryDTO;
import model.Player;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author esena
 */
public class GameFrame extends javax.swing.JFrame {

    /**
     * Creates new form GameFrame
     */
    private GameDAO gameDAO;
    private PlayerDAO playerDAO;
    public GameFrame() {
        initComponents();
        gameDAO = new GameDAO();
        playerDAO = new PlayerDAO();
        //populateWinnerComboBox();

        jTableGames.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && jTableGames.getSelectedRow() != -1) {
                    displaySelectedGame();
                } else if (jTableGames.getSelectedRow() == -1) {
                    clearGameForm();
                   //setGameCrudButtonsEnabled(false);
                }
            }
        });

        //setGameCrudButtonsEnabled(false);

        jTableGames.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTablePlayers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableSupplies.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        loadGamesTable();

    }
    private void clearGameForm() {
        ((DefaultTableModel) jTablePlayers.getModel()).setRowCount(0);
        ((DefaultTableModel) jTableSupplies.getModel()).setRowCount(0);

        jTableGames.clearSelection();
    }

    private void loadGamesTable(){
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Game ID", "Winner Player ID"} // Ensure column names are set
        );
        //DefaultTableModel model = (DefaultTableModel) jTableGames.getModel();
        model.setRowCount(0); // Clear existing rows

        gameDAO.getAllGames().forEach(game -> {
            Object[] row = new Object[]{
                game.getGameId(),
                    game.getWinnerPlayerId() != null ? game.getWinnerPlayerId() : "No winner"
            };
            model.addRow(row);
        });
        System.out.println(model.getRowCount() + " games loaded.");
        jTableGames.setModel(model);
    }
    private void displaySelectedGame() {
        int selectedRow = jTableGames.getSelectedRow();
        if (selectedRow != -1) {
            int gameId = (int) jTableGames.getValueAt(selectedRow, 0);
            GameSummaryDTO gameSummary = gameDAO.getGameSummary(gameId);
            if (gameSummary == null) {
                return;
            }

            var players = gameSummary.getParticipants();
            DefaultTableModel playersModel = new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"Player ID", "Username", "Real Name", "Turn Order", "Final Score"}
            );
            players.forEach(player -> {
                Object[] row = new Object[]{
                        player.getPlayerId(),
                        player.getUsername(),
                        player.getRealName(),
                        player.getTurnOrder()
                };
                playersModel.addRow(row);
            });
            jTablePlayers.setModel(playersModel);

            var supplies = gameSummary.getKingdomCards();
            DefaultTableModel suppliesModel = new DefaultTableModel(
                    new Object[][]{},
                    new String[]{"Card ID", "Card Name", "Starting Quantity"}
            );
            supplies.forEach(supply -> {
                Object[] row = new Object[]{
                        supply.getCardId(),
                        supply.getCardName(),
                        supply.getStartingQuantity()
                };
                suppliesModel.addRow(row);
            });
            jTableSupplies.setModel(suppliesModel);
        }
    }

    void setWinner(Player player) {
        int selectedRow = jTableGames.getSelectedRow();
        if (selectedRow != -1) {
            int gameId = (int) jTableGames.getValueAt(selectedRow, 0);
            gameDAO.setWinner(gameId, player.getPlayerId());
            loadGamesTable();
            clearGameForm();
        } else {
            System.out.println("No game selected.");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableGames = new javax.swing.JTable();
        jButtonDetails = new javax.swing.JButton();
        jButtonNewGame = new javax.swing.JButton();
        jPanelDetails = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePlayers = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableSupplies = new javax.swing.JTable();
        jButtonSetWinner = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTableGames.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTableGames.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableGames);

        jButtonDetails.setText("View Details");
        jButtonDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetailsActionPerformed(evt);
            }
        });

        jButtonNewGame.setText("New Game");
        jButtonNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewGameActionPerformed(evt);
            }
        });

        jPanelDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Game Details"));

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
        jScrollPane2.setViewportView(jTablePlayers);

        jTableSupplies.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTableSupplies);

        jButtonSetWinner.setText("setWinner");
        jButtonSetWinner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetWinnerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDetailsLayout = new javax.swing.GroupLayout(jPanelDetails);
        jPanelDetails.setLayout(jPanelDetailsLayout);
        jPanelDetailsLayout.setHorizontalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jButtonSetWinner)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDetailsLayout.setVerticalGroup(
            jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetailsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButtonSetWinner)
                .addGap(18, 18, 18)
                .addGroup(jPanelDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonDetails)
                        .addGap(70, 70, 70)
                        .addComponent(jButtonNewGame)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNewGame)
                    .addComponent(jButtonDetails))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetailsActionPerformed
        int selectedRow = jTableGames.getSelectedRow();
        if (selectedRow != -1) {
            displaySelectedGame();
        } else {
            System.out.println("No game selected.");
        }
        //setGameCrudButtonsEnabled(true);
    }//GEN-LAST:event_jButtonDetailsActionPerformed

    private void jButtonNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewGameActionPerformed
        NewGameDialog newGameDialog = new NewGameDialog(this, true);
        newGameDialog.setVisible(true);

        if (newGameDialog.isGameCreated()) {
            loadGamesTable();
            clearGameForm();
        }    }//GEN-LAST:event_jButtonNewGameActionPerformed

    private void jButtonSetWinnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetWinnerActionPerformed
        setWinner(playerDAO.getPlayerById(
                (int) jTablePlayers.getValueAt(jTablePlayers.getSelectedRow(), 0)
        ));
    }//GEN-LAST:event_jButtonSetWinnerActionPerformed

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
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDetails;
    private javax.swing.JButton jButtonNewGame;
    private javax.swing.JButton jButtonSetWinner;
    private javax.swing.JPanel jPanelDetails;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableGames;
    private javax.swing.JTable jTablePlayers;
    private javax.swing.JTable jTableSupplies;
    // End of variables declaration//GEN-END:variables
}
