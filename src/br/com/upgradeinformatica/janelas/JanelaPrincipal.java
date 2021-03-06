package br.com.upgradeinformatica.janelas;

import java.sql.*;
import br.com.upgradeinformatica.DAL.ModuloConexao;
import java.awt.HeadlessException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Alexandra Miguel Raibolt da Silva
 */
public class JanelaPrincipal extends javax.swing.JFrame {
    Connection conexao = null;

    /**
     * Creates new form JanelaPrincipal
     */
    public JanelaPrincipal() {
        initComponents();

        conexao = ModuloConexao.conector();
        /* System.out.println(conexao); */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelInfo = new javax.swing.JPanel();
        jLabelImagem = new javax.swing.JLabel();
        jLabelAlterarUsuario = new javax.swing.JLabel();
        jLabelAlterarData = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDesktopPane = new javax.swing.JDesktopPane();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuArquivo = new javax.swing.JMenu();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenuGerenciamento = new javax.swing.JMenu();
        jMenuItemCliente = new javax.swing.JMenuItem();
        jMenuItemOS = new javax.swing.JMenuItem();
        jMenuRelatorio = new javax.swing.JMenu();
        jMenuItemClientes = new javax.swing.JMenuItem();
        jMenuItemServicos = new javax.swing.JMenuItem();
        jMenuAjuda = new javax.swing.JMenu();
        jMenuItemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Upgrade Informática - Principal");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanelInfo.setBackground(new java.awt.Color(255, 255, 255));

        jLabelImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/logo.png"))); // NOI18N

        jLabelAlterarUsuario.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabelAlterarUsuario.setForeground(new java.awt.Color(51, 51, 51));
        jLabelAlterarUsuario.setText("Usuário");

        jLabelAlterarData.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabelAlterarData.setForeground(new java.awt.Color(51, 51, 51));
        jLabelAlterarData.setText("Data");

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Acesso com:");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Acesso em:");

        jDesktopPane.setBackground(new java.awt.Color(0, 153, 204));
        jDesktopPane.setPreferredSize(new java.awt.Dimension(595, 559));

        javax.swing.GroupLayout jDesktopPaneLayout = new javax.swing.GroupLayout(jDesktopPane);
        jDesktopPane.setLayout(jDesktopPaneLayout);
        jDesktopPaneLayout.setHorizontalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1046, Short.MAX_VALUE)
        );
        jDesktopPaneLayout.setVerticalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelInfoLayout = new javax.swing.GroupLayout(jPanelInfo);
        jPanelInfo.setLayout(jPanelInfoLayout);
        jPanelInfoLayout.setHorizontalGroup(
            jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelImagem)
                    .addGroup(jPanelInfoLayout.createSequentialGroup()
                        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAlterarData)
                            .addComponent(jLabelAlterarUsuario))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1046, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        jPanelInfoLayout.setVerticalGroup(
            jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInfoLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelAlterarUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAlterarData)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                .addComponent(jLabelImagem)
                .addGap(56, 56, 56))
            .addComponent(jDesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );

        jMenuBar.setBackground(new java.awt.Color(255, 255, 255));

        jMenuArquivo.setText("Arquivo");

        jMenuItemSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemSair.setText("Sair");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSairActionPerformed(evt);
            }
        });
        jMenuArquivo.add(jMenuItemSair);

        jMenuBar.add(jMenuArquivo);

        jMenuGerenciamento.setText("Gerenciamento");
        jMenuGerenciamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGerenciamentoActionPerformed(evt);
            }
        });

        jMenuItemCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemCliente.setText("Cliente");
        jMenuItemCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemClienteActionPerformed(evt);
            }
        });
        jMenuGerenciamento.add(jMenuItemCliente);

        jMenuItemOS.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemOS.setText("Ordem de Serviço");
        jMenuItemOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOSActionPerformed(evt);
            }
        });
        jMenuGerenciamento.add(jMenuItemOS);

        jMenuBar.add(jMenuGerenciamento);

        jMenuRelatorio.setText("Relatório");

        jMenuItemClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemClientes.setText("Clientes");
        jMenuItemClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemClientesActionPerformed(evt);
            }
        });
        jMenuRelatorio.add(jMenuItemClientes);

        jMenuItemServicos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemServicos.setText("Serviços");
        jMenuItemServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemServicosActionPerformed(evt);
            }
        });
        jMenuRelatorio.add(jMenuItemServicos);

        jMenuBar.add(jMenuRelatorio);

        jMenuAjuda.setText("Ajuda");

        jMenuItemSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        jMenuItemSobre.setText("Sobre");
        jMenuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSobreActionPerformed(evt);
            }
        });
        jMenuAjuda.add(jMenuItemSobre);

        jMenuBar.add(jMenuAjuda);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 1267, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1116, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemClienteActionPerformed
        /* Abre dentro do Desktop Pane */
        JanelaCliente janelaCliente = new JanelaCliente();
        janelaCliente.setVisible(true);

        jDesktopPane.add(janelaCliente);
    }//GEN-LAST:event_jMenuItemClienteActionPerformed

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        /* Caixa de diálogo */
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);

        /* Fecha Programa */
        if(sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void jMenuItemOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOSActionPerformed
        /* Janela Ordem de Serviço */
        JanelaOS janelaOS = new JanelaOS();
        janelaOS.setVisible(true);

        jDesktopPane.add(janelaOS);
    }//GEN-LAST:event_jMenuItemOSActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        /* Substituição da Label Data para a data atual do sistema */
        Date data = new Date();
        DateFormat formata = DateFormat.getDateInstance(DateFormat.SHORT);
        jLabelAlterarData.setText(formata.format(data));
    }//GEN-LAST:event_formWindowActivated

    private void jMenuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSobreActionPerformed
        /* Janela Sobre */
        JanelaSobre janelaSobre = new JanelaSobre();
        janelaSobre.setVisible(true);
    }//GEN-LAST:event_jMenuItemSobreActionPerformed

    private void jMenuGerenciamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGerenciamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuGerenciamentoActionPerformed

    private void jMenuItemClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemClientesActionPerformed
        /* Gerando o relatório de clientes */
        int confirmar = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatório?", "Atenção", JOptionPane.YES_NO_OPTION);

        /* Imprime o relatório */
        if(confirmar == JOptionPane.YES_OPTION) {
            /* Framework JasperReport */
            try {
                /* Classe JasperPrint - Prepara a impressão do relatório */
                JasperPrint print = JasperFillManager.fillReport("C:/Users/Alexandra/Desktop/UpgradeInformatica/src/br/com/upgradeinformatica/relatorios/Clientes.jasper", null, conexao);

                /* Class JasperViewer - Exibe o relatório */
                JasperViewer.viewReport(print, false);

            } catch (HeadlessException e){
                JOptionPane.showMessageDialog(null, e);
            } catch (JRException ex) {
                Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItemClientesActionPerformed

    private void jMenuItemServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemServicosActionPerformed
        /* Gerando o relatório de serviços */
        int confirmar = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatório?", "Atenção", JOptionPane.YES_NO_OPTION);

        /* Imprime o relatório */
        if(confirmar == JOptionPane.YES_OPTION) {
            /* Framework JasperReport */
            try {
                /* Classe JasperPrint - Prepara a impressão do relatório */
                JasperPrint print = JasperFillManager.fillReport("C:/Users/Alexandra/Desktop/UpgradeInformatica/src/br/com/upgradeinformatica/relatorios/Servicos.jasper", null, conexao);

                /* Class JasperViewer - Exibe o relatório */
                JasperViewer.viewReport(print, false);

            } catch (HeadlessException e){
                JOptionPane.showMessageDialog(null, e);
            } catch (JRException ex) {
                Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItemServicosActionPerformed

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
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelAlterarData;
    public static javax.swing.JLabel jLabelAlterarUsuario;
    private javax.swing.JLabel jLabelImagem;
    private javax.swing.JMenu jMenuAjuda;
    private javax.swing.JMenu jMenuArquivo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuGerenciamento;
    private javax.swing.JMenuItem jMenuItemCliente;
    private javax.swing.JMenuItem jMenuItemClientes;
    private javax.swing.JMenuItem jMenuItemOS;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuItemServicos;
    private javax.swing.JMenuItem jMenuItemSobre;
    private javax.swing.JMenu jMenuRelatorio;
    private javax.swing.JPanel jPanelInfo;
    // End of variables declaration//GEN-END:variables
}
