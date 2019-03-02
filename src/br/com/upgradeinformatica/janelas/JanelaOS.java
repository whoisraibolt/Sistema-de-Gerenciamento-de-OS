package br.com.upgradeinformatica.janelas;

import java.sql.*;
import br.com.upgradeinformatica.DAL.ModuloConexao;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/* Bliblioteca para consulta avançada (rs2xml.jar) */
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Alexandra Miguel Raibolt da Silva
 */
public class JanelaOS extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    /* Armazena a opção marcada no Radio Button */
    private String tipo;

    /**
     * Creates new form JanelaOS
     */
    public JanelaOS() {
        initComponents();

        conexao = ModuloConexao.conector();
        /* System.out.println(conexao); */
    }

    /* Método para emitir ordem de serviços */
    private void emitirOS() {
        String SQL = "INSERT INTO ordemServico(\n" +
        "id_idCliente, \n" +
        "tipo, \n" +
        "situacao, \n" +
        "equipamento, \n" +
        "defeito, \n" +
        "servico,\n" +
        "tecnico, \n" +
        "valor) \n" +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            /* Consulta ao banco de dados em função do que foi digitado */
            pst = conexao.prepareStatement(SQL);

            pst.setString(1, jTextFieldID.getText());
            pst.setString(2, tipo);
            pst.setString(3, jComboBoxSituacao.getSelectedItem().toString());
            pst.setString(4, jTextFieldEquipamento.getText());
            pst.setString(5, jTextFieldDefeito.getText());
            pst.setString(6, jTextFieldServico.getText());
            pst.setString(7, jTextFieldTecnico.getText());
            pst.setString(8, jTextFieldValorTotal.getText());


            /* Validação dos campos obrigatórios */
            if(
            (jTextFieldID.getText().isEmpty()) ||
            (jTextFieldEquipamento.getText().isEmpty()) ||
            (jTextFieldDefeito.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
            } else {
                /* Realiza a atualização */
                int adicionado = pst.executeUpdate();

                if(adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço emitida com sucesso!");

                    /* Limpeza dos campos */
                    jTextFieldID.setText(null);
                    jTextFieldEquipamento.setText(null);
                    jTextFieldDefeito.setText(null);
                    jTextFieldServico.setText(null);
                    jTextFieldTecnico.setText(null);
                    jTextFieldValorTotal.setText(null);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ModuloConexao.closeConnection(conexao, pst);
        }
    }

    /* Método para consultar ordem de serviços */
    private void consultarOS() {
        String numOS = JOptionPane.showInputDialog("Digite o número da Ordem de Serviço");

        String SQL = "SELECT * FROM ordemServico WHERE idOrdemServico="+numOS;

        try {
            /* Consulta ao banco de dados em função do que foi digitado */
            pst = conexao.prepareStatement(SQL);

            /* Execução da Query SQL */
            rs = pst.executeQuery();

            /* Acessa a biblioteca rs2xml.jar */
            //jTableClientes.setModel(DbUtils.resultSetToTableModel(rs));

            if(rs.next()) {
                jTextFieldNOS.setText(rs.getString(1));
                jTextFieldID.setText(rs.getString(2));
                jTextFieldData.setText(rs.getString(3));

                /* Radio Button */
                String rbTipo = rs.getString(4);

                if(rbTipo.equals("Orçamento")) {
                    jRadioButtonOrcamento.setSelected(true);
                    tipo = "Orçamento";
                } else {
                    jRadioButtonOS.setSelected(true);
                    tipo = "Ordem de Serviço";
                }

                jComboBoxSituacao.setSelectedItem(rs.getString(5));
                jTextFieldEquipamento.setText(rs.getString(6));
                jTextFieldDefeito.setText(rs.getString(7));
                jTextFieldServico.setText(rs.getString(8));
                jTextFieldTecnico.setText(rs.getString(9));
                jTextFieldValorTotal.setText(rs.getString(10));

                /* Evita emitir o mesmo dado */
                jButtonAdicionar.setEnabled(false);

                /* Bloqueia a consulta a outro cliente */
                jTextFieldConsultarCliente.setEnabled(false);

                /* Desabilita a tabela */
                jTableClientes.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Ordem de Serviço não cadastrada!");
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "Ordem de Serviço inválida!");
            System.out.println(e);
        } catch (HeadlessException | SQLException e2) {
            JOptionPane.showMessageDialog(null, e2);
        } finally {
            ModuloConexao.closeConnection(conexao, pst);
        }
    }

    /* Método para consultar clientes */
    private void consultarCliente() {
        String SQL = "SELECT idCliente, nomeCliente, telefoneFixoCliente FROM cliente WHERE nomeCliente like ?";

        try {
            /* Consulta ao banco de dados em função do que foi digitado */
            pst = conexao.prepareStatement(SQL);

            pst.setString(1, jTextFieldConsultarCliente.getText() + "%"); /* % continuação do texto */

            /* Execução da Query SQL */
            rs = pst.executeQuery();

            /* Acessa a biblioteca rs2xml.jar */
            jTableClientes.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ModuloConexao.closeConnection(conexao, pst);
        }
    }

    /* Setar os campos do fomulário com o conteúdo da tabela */
    private void setarFormulario() {
        int setar = jTableClientes.getSelectedRow();

        jTextFieldID.setText(jTableClientes.getModel().getValueAt(setar, 0).toString());

        /* Desabilita o botão adicionar */
        /* jButtonAdicionar.setEnabled(false); */
    }

    /* Método para realizar a limpeza dos campos do formulário */
    private void limparFormulario() {
        /* Limpeza dos campos */
        jTextFieldNOS.setText(null);
        jTextFieldID.setText(null);
        jTextFieldData.setText(null);
        jComboBoxSituacao.setSelectedItem(null);
        jTextFieldEquipamento.setText(null);
        jTextFieldDefeito.setText(null);
        jTextFieldServico.setText(null);
        jTextFieldTecnico.setText(null);
        jTextFieldValorTotal.setText(null);
    }

    /* Método para alterar ordem de serviços */
    private void alterarOS() {
        String SQL = "UPDATE ordemServico SET \n" +
        "tipo=?, \n" +
        "situacao=?, \n" +
        "equipamento=?, \n" +
        "defeito=?, \n" +
        "servico=?,\n" +
        "tecnico=?, \n" +
        "valor=? \n" +
        "WHERE idOrdemServico=?";

        try {
            /* Consulta ao banco de dados em função do que foi digitado */
            pst = conexao.prepareStatement(SQL);

            pst.setString(1, tipo);
            pst.setString(2, jComboBoxSituacao.getSelectedItem().toString());
            pst.setString(3, jTextFieldEquipamento.getText());
            pst.setString(4, jTextFieldDefeito.getText());
            pst.setString(5, jTextFieldServico.getText());
            pst.setString(6, jTextFieldTecnico.getText());
            pst.setString(7, jTextFieldValorTotal.getText());
            pst.setString(8, jTextFieldNOS.getText());


            /* Validação dos campos obrigatórios */
            if(
            (jTextFieldID.getText().isEmpty()) ||
            (jTextFieldEquipamento.getText().isEmpty()) ||
            (jTextFieldDefeito.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
            } else {
                /* Realiza a atualização */
                int adicionado = pst.executeUpdate();

                if(adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço alterada com sucesso!");

                    /* Limpeza dos campos */
                    jTextFieldNOS.setText(null);
                    jTextFieldID.setText(null);
                    jTextFieldData.setText(null);
                    jTextFieldEquipamento.setText(null);
                    jTextFieldDefeito.setText(null);
                    jTextFieldServico.setText(null);
                    jTextFieldTecnico.setText(null);
                    jTextFieldValorTotal.setText(null);

                    /* Habilita o botão Adicionar */
                    jButtonAdicionar.setEnabled(true);

                    /* Habilita a consulta a outro cliente */
                    jTextFieldConsultarCliente.setEnabled(true);

                    /* Habilita a tabela */
                    jTableClientes.setVisible(true);
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ModuloConexao.closeConnection(conexao, pst);
        }
    }

    /* Método para deletar ordem de serviços */
    private void deletarOS() {
        /* Caixa de diálogo */
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar esta ordem de serviço?", "Atenção", JOptionPane.YES_NO_OPTION);

        /* Deleta o cliente */
        if(confirmar == JOptionPane.YES_OPTION) {
            String SQL = "DELETE FROM ordemServico WHERE idOrdemServico=?";

            try {
                pst = conexao.prepareStatement(SQL);

                pst.setString(1, jTextFieldNOS.getText());

                /* Realiza a atualização */
                int apagado = pst.executeUpdate();

                if(apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço deletada com sucesso!");

                    /* Limpeza dos campos */
                    jTextFieldNOS.setText(null);
                    jTextFieldID.setText(null);
                    jTextFieldData.setText(null);
                    jTextFieldEquipamento.setText(null);
                    jTextFieldDefeito.setText(null);
                    jTextFieldServico.setText(null);
                    jTextFieldTecnico.setText(null);
                    jTextFieldValorTotal.setText(null);

                    /* Habilita o botão Adicionar */
                    jButtonAdicionar.setEnabled(true);

                    /* Habilita a consulta a outro cliente */
                    jTextFieldConsultarCliente.setEnabled(true);

                    /* Habilita a tabela */
                    jTableClientes.setVisible(true);
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                ModuloConexao.closeConnection(conexao, pst);
            }
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
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanelInfo = new javax.swing.JPanel();
        jLabelNOS = new javax.swing.JLabel();
        jLabelData = new javax.swing.JLabel();
        jTextFieldNOS = new javax.swing.JTextField();
        jTextFieldData = new javax.swing.JTextField();
        jRadioButtonOrcamento = new javax.swing.JRadioButton();
        jRadioButtonOS = new javax.swing.JRadioButton();
        jPanelCliente = new javax.swing.JPanel();
        jLabelConsultarCliente = new javax.swing.JLabel();
        jLabelID = new javax.swing.JLabel();
        jTextFieldConsultarCliente = new javax.swing.JTextField();
        jTextFieldID = new javax.swing.JTextField();
        jScrollPaneClientes = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jLabelSituação = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        jLabelEquipamento = new javax.swing.JLabel();
        jLabelDefeito = new javax.swing.JLabel();
        jLabelServico = new javax.swing.JLabel();
        jLabelTecnico = new javax.swing.JLabel();
        jLabelValorTotal = new javax.swing.JLabel();
        jComboBoxSituacao = new javax.swing.JComboBox<>();
        jTextFieldEquipamento = new javax.swing.JTextField();
        jTextFieldDefeito = new javax.swing.JTextField();
        jTextFieldServico = new javax.swing.JTextField();
        jTextFieldTecnico = new javax.swing.JTextField();
        jTextFieldValorTotal = new javax.swing.JTextField();
        jButtonAdicionar = new javax.swing.JButton();
        jButtonConsultar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonDeletar = new javax.swing.JButton();
        jButtonLimparFormulario = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(595, 559));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(null);
        jPanel1.setPreferredSize(new java.awt.Dimension(595, 559));

        jPanelInfo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Info:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanelInfo.setForeground(new java.awt.Color(51, 51, 51));

        jLabelNOS.setBackground(new java.awt.Color(51, 51, 51));
        jLabelNOS.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelNOS.setForeground(new java.awt.Color(51, 51, 51));
        jLabelNOS.setText("Nº OS:");

        jLabelData.setBackground(new java.awt.Color(51, 51, 51));
        jLabelData.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelData.setForeground(new java.awt.Color(51, 51, 51));
        jLabelData.setText("Data:");

        jTextFieldNOS.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldNOS.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldNOS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldNOS.setEnabled(false);
        jTextFieldNOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNOSActionPerformed(evt);
            }
        });

        jTextFieldData.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldData.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldData.setEnabled(false);
        jTextFieldData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDataActionPerformed(evt);
            }
        });

        jRadioButtonOrcamento.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButtonOrcamento);
        jRadioButtonOrcamento.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jRadioButtonOrcamento.setForeground(new java.awt.Color(51, 51, 51));
        jRadioButtonOrcamento.setText("Orçamento");
        jRadioButtonOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonOrcamentoActionPerformed(evt);
            }
        });

        jRadioButtonOS.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButtonOS);
        jRadioButtonOS.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jRadioButtonOS.setForeground(new java.awt.Color(51, 51, 51));
        jRadioButtonOS.setText("Ordem de Serviço");
        jRadioButtonOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelInfoLayout = new javax.swing.GroupLayout(jPanelInfo);
        jPanelInfo.setLayout(jPanelInfoLayout);
        jPanelInfoLayout.setHorizontalGroup(
            jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInfoLayout.createSequentialGroup()
                        .addComponent(jRadioButtonOS)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelInfoLayout.createSequentialGroup()
                        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonOrcamento)
                            .addGroup(jPanelInfoLayout.createSequentialGroup()
                                .addComponent(jLabelNOS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNOS, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelInfoLayout.setVerticalGroup(
            jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoLayout.createSequentialGroup()
                .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNOS)
                    .addComponent(jLabelData)
                    .addComponent(jTextFieldNOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonOrcamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonOS))
        );

        jPanelCliente.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanelCliente.setForeground(new java.awt.Color(51, 51, 51));
        jPanelCliente.setPreferredSize(new java.awt.Dimension(296, 136));

        jLabelConsultarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/consultar.png"))); // NOI18N

        jLabelID.setBackground(new java.awt.Color(51, 51, 51));
        jLabelID.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelID.setForeground(new java.awt.Color(51, 51, 51));
        jLabelID.setText("*ID:");

        jTextFieldConsultarCliente.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldConsultarCliente.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldConsultarCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldConsultarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldConsultarClienteActionPerformed(evt);
            }
        });
        jTextFieldConsultarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldConsultarClienteKeyReleased(evt);
            }
        });

        jTextFieldID.setEditable(false);
        jTextFieldID.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldID.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldID.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextFieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDActionPerformed(evt);
            }
        });

        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone Fixo"
            }
        ));
        jTableClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableClientesMouseClicked(evt);
            }
        });
        jTableClientes.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTableClientesComponentShown(evt);
            }
        });
        jScrollPaneClientes.setViewportView(jTableClientes);

        javax.swing.GroupLayout jPanelClienteLayout = new javax.swing.GroupLayout(jPanelCliente);
        jPanelCliente.setLayout(jPanelClienteLayout);
        jPanelClienteLayout.setHorizontalGroup(
            jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanelClienteLayout.createSequentialGroup()
                        .addComponent(jTextFieldConsultarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelConsultarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(jLabelID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelClienteLayout.setVerticalGroup(
            jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClienteLayout.createSequentialGroup()
                .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelConsultarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldConsultarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelID)
                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabelSituação.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelSituação.setForeground(new java.awt.Color(51, 51, 51));
        jLabelSituação.setText("Situação:");

        jLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel.setForeground(new java.awt.Color(51, 51, 51));
        jLabel.setText("* Campos Obrigatórios");

        jLabelEquipamento.setBackground(new java.awt.Color(51, 51, 51));
        jLabelEquipamento.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelEquipamento.setForeground(new java.awt.Color(51, 51, 51));
        jLabelEquipamento.setText("*Equipamento:");

        jLabelDefeito.setBackground(new java.awt.Color(51, 51, 51));
        jLabelDefeito.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelDefeito.setForeground(new java.awt.Color(51, 51, 51));
        jLabelDefeito.setText("*Defeito:");

        jLabelServico.setBackground(new java.awt.Color(51, 51, 51));
        jLabelServico.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelServico.setForeground(new java.awt.Color(51, 51, 51));
        jLabelServico.setText("Serviço:");

        jLabelTecnico.setBackground(new java.awt.Color(51, 51, 51));
        jLabelTecnico.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelTecnico.setForeground(new java.awt.Color(51, 51, 51));
        jLabelTecnico.setText("Técnico:");

        jLabelValorTotal.setBackground(new java.awt.Color(51, 51, 51));
        jLabelValorTotal.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelValorTotal.setForeground(new java.awt.Color(51, 51, 51));
        jLabelValorTotal.setText("ValorTotal:");

        jComboBoxSituacao.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jComboBoxSituacao.setForeground(new java.awt.Color(51, 51, 51));
        jComboBoxSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na Bancada", "Entregue", "Retornou", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Orçamento Reprovado" }));

        jTextFieldEquipamento.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldEquipamento.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldEquipamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldEquipamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEquipamentoActionPerformed(evt);
            }
        });

        jTextFieldDefeito.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldDefeito.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldDefeito.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldDefeito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDefeitoActionPerformed(evt);
            }
        });

        jTextFieldServico.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldServico.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldServico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldServicoActionPerformed(evt);
            }
        });

        jTextFieldTecnico.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldTecnico.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldTecnico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTecnicoActionPerformed(evt);
            }
        });

        jTextFieldValorTotal.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldValorTotal.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldValorTotal.setText("0");
        jTextFieldValorTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldValorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldValorTotalActionPerformed(evt);
            }
        });

        jButtonAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/adicionar.png"))); // NOI18N
        jButtonAdicionar.setToolTipText("Emitir");
        jButtonAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAdicionar.setPreferredSize(new java.awt.Dimension(64, 64));
        jButtonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarActionPerformed(evt);
            }
        });

        jButtonConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/consulta_2.png"))); // NOI18N
        jButtonConsultar.setToolTipText("Consultar");
        jButtonConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonConsultar.setPreferredSize(new java.awt.Dimension(64, 64));
        jButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarActionPerformed(evt);
            }
        });

        jButtonAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/alterar.png"))); // NOI18N
        jButtonAlterar.setToolTipText("Alterar");
        jButtonAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAlterar.setPreferredSize(new java.awt.Dimension(64, 64));
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/deletar.png"))); // NOI18N
        jButtonDeletar.setToolTipText("Deletar");
        jButtonDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonDeletar.setPreferredSize(new java.awt.Dimension(64, 64));
        jButtonDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeletarActionPerformed(evt);
            }
        });

        jButtonLimparFormulario.setText("Limpar Formulário");
        jButtonLimparFormulario.setToolTipText("Limpar Formulário");
        jButtonLimparFormulario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonLimparFormulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimparFormularioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jButtonLimparFormulario))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(123, 123, 123)
                                    .addComponent(jPanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabelSituação)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboBoxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(123, 123, 123)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabelTecnico)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabelValorTotal)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldValorTotal))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabelServico)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldServico))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabelDefeito)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabelEquipamento)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldEquipamento))))))
                .addGap(66, 154, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSituação)
                            .addComponent(jComboBoxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(39, 39, 39)
                .addComponent(jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEquipamento)
                    .addComponent(jTextFieldEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDefeito)
                    .addComponent(jTextFieldDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelServico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTecnico)
                    .addComponent(jTextFieldValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValorTotal))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButtonLimparFormulario))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(129, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        );

        setBounds(0, 0, 855, 607);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDActionPerformed

    private void jTextFieldEquipamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEquipamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEquipamentoActionPerformed

    private void jTextFieldDefeitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDefeitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDefeitoActionPerformed

    private void jTextFieldServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldServicoActionPerformed

    private void jTextFieldConsultarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldConsultarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldConsultarClienteActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed
        /* Método para emitir ordem de serviços */
        emitirOS();
    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jTextFieldConsultarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldConsultarClienteKeyReleased
        /* Método para consultar clientes */
        consultarCliente();
    }//GEN-LAST:event_jTextFieldConsultarClienteKeyReleased

    private void jTableClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientesMouseClicked
        /* Setar os campos do fomulário com o conteúdo da tabela */
        setarFormulario();
    }//GEN-LAST:event_jTableClientesMouseClicked

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        /* Método para alterar ordem de serviços */
        alterarOS();
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonLimparFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparFormularioActionPerformed
        /* Método para realizar a limpeza dos campos do formulário */
        limparFormulario();
    }//GEN-LAST:event_jButtonLimparFormularioActionPerformed

    private void jButtonDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletarActionPerformed
        /* Método para deletar ordem de serviços */
        deletarOS();
    }//GEN-LAST:event_jButtonDeletarActionPerformed

    private void jTextFieldNOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNOSActionPerformed

    private void jTextFieldDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDataActionPerformed

    private void jRadioButtonOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonOSActionPerformed
        /* Se selecionado */
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_jRadioButtonOSActionPerformed

    private void jTextFieldTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTecnicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTecnicoActionPerformed

    private void jTextFieldValorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldValorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldValorTotalActionPerformed

    private void jButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarActionPerformed
        /* Método para consultar ordem de serviços */
        consultarOS();
    }//GEN-LAST:event_jButtonConsultarActionPerformed

    private void jRadioButtonOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonOrcamentoActionPerformed
        /* Se selecionado */
        tipo = "Orçamento";
    }//GEN-LAST:event_jRadioButtonOrcamentoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        /* Ao abrir o Form, marca o Radio Button Orçamento */
        jRadioButtonOrcamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void jTableClientesComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTableClientesComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableClientesComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAdicionar;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonConsultar;
    private javax.swing.JButton jButtonDeletar;
    private javax.swing.JButton jButtonLimparFormulario;
    private javax.swing.JComboBox<String> jComboBoxSituacao;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabelConsultarCliente;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JLabel jLabelDefeito;
    private javax.swing.JLabel jLabelEquipamento;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNOS;
    private javax.swing.JLabel jLabelServico;
    private javax.swing.JLabel jLabelSituação;
    private javax.swing.JLabel jLabelTecnico;
    private javax.swing.JLabel jLabelValorTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCliente;
    private javax.swing.JPanel jPanelInfo;
    private javax.swing.JRadioButton jRadioButtonOS;
    private javax.swing.JRadioButton jRadioButtonOrcamento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneClientes;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextField jTextFieldConsultarCliente;
    private javax.swing.JTextField jTextFieldData;
    private javax.swing.JTextField jTextFieldDefeito;
    private javax.swing.JTextField jTextFieldEquipamento;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNOS;
    private javax.swing.JTextField jTextFieldServico;
    private javax.swing.JTextField jTextFieldTecnico;
    private javax.swing.JTextField jTextFieldValorTotal;
    // End of variables declaration//GEN-END:variables
}
