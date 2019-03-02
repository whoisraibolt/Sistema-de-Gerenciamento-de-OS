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
public class JanelaCliente extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form JanelaCliente
     */
    public JanelaCliente() {
        initComponents();

        conexao = ModuloConexao.conector();
        /* System.out.println(conexao); */
    }

    /* Método para adicionar clientes */
    private void adicionar() {
        String SQL = "INSERT INTO cliente(\n" +
        "CPFCliente, \n" +
        "RGCliente, \n" +
        "nomeCliente, \n" +
        "dataNascimentoCliente, \n" +
        "sexoCliente,\n" +
        "CEPCliente, \n" +
        "enderecoCliente, \n" +
        "bairroCliente, \n" +
        "complementoCliente, \n" +
        "cidadeCliente, \n" +
        "UFCliente, \n" +
        "telefoneFixoCliente, \n" +
        "telefoneCelularCliente, \n" +
        "emailCliente, \n" +
        "skypeCliente, \n" +
        "observacoesCliente) \n" +
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            /* Consulta ao banco de dados em função do que foi digitado */
            pst = conexao.prepareStatement(SQL);

            pst.setString(1, jTextFieldCPF.getText());
            pst.setString(2, jTextFieldRG.getText());
            pst.setString(3, jTextFieldNome.getText());
            pst.setString(4, jTextFieldDataNascimento.getText());
            pst.setString(5, jTextFieldSexo.getText());
            pst.setString(6, jTextFieldCEP.getText());
            pst.setString(7, jTextFieldEndereco.getText());
            pst.setString(8, jTextFieldBairro.getText());
            pst.setString(9, jTextFieldComplemento.getText());
            pst.setString(10, jTextFieldCidade.getText());
            pst.setString(11, jComboBoxEstados.getSelectedItem().toString());
            pst.setString(12, jTextFieldTelefoneFixo.getText());
            pst.setString(13, jTextFieldTelefoneCelular.getText());
            pst.setString(14, jTextFieldEmail.getText());
            pst.setString(15, jTextFieldSkype.getText());
            pst.setString(16, jTextAreaObservacoes.getText());

            /* Validação dos campos obrigatórios */
            if(
            (jTextFieldNome.getText().isEmpty()) ||
            (jTextFieldDataNascimento.getText().isEmpty()) ||
            (jTextFieldSexo.getText().isEmpty()) ||
            (jTextFieldCEP.getText().isEmpty()) ||
            (jTextFieldEndereco.getText().isEmpty()) ||
            (jTextFieldBairro.getText().isEmpty()) ||
            (jTextFieldCidade.getText().isEmpty()) ||
            (jComboBoxEstados.getSelectedItem().toString().isEmpty()) ||
            (jTextFieldTelefoneFixo.getText().isEmpty()) ||
            (jTextFieldTelefoneCelular.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
            } else {
                /* Realiza a atualização */
                int adicionado = pst.executeUpdate();

                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");

                    /* Limpeza dos campos */
                    jTextFieldID.setText(null);
                    jTextFieldCPF.setText(null);
                    jTextFieldRG.setText(null);
                    jTextFieldNome.setText(null);
                    jTextFieldDataNascimento.setText(null);
                    jTextFieldSexo.setText(null);
                    jTextFieldCEP.setText(null);
                    jTextFieldEndereco.setText(null);
                    jTextFieldBairro.setText(null);
                    jTextFieldComplemento.setText(null);
                    jTextFieldCidade.setText(null);
                    jComboBoxEstados.setSelectedItem(null);
                    jTextFieldTelefoneFixo.setText(null);
                    jTextFieldTelefoneCelular.setText(null);
                    jTextFieldEmail.setText(null);
                    jTextFieldSkype.setText(null);
                    jTextAreaObservacoes.setText(null);

                    /**
                     * Chama o método para consultar clientes
                     * Desta forma os dados aparecem atualizados
                    */
                    consultar();
                }
            }
        } catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ModuloConexao.closeConnection(conexao, pst);
        }
    }

    /* Método para consultar clientes */
    private void consultar(){
        String SQL = "SELECT * FROM cliente WHERE nomeCliente like ?";

        try {
            /* Consulta ao banco de dados em função do que foi digitado */
            pst = conexao.prepareStatement(SQL);

            pst.setString(1, jTextFieldConsultar.getText() + "%"); /* % continuação do texto */

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
    private void setarFormulario(){
        int setar = jTableClientes.getSelectedRow();

        jTextFieldID.setText(jTableClientes.getModel().getValueAt(setar, 0).toString());
        jTextFieldCPF.setText(jTableClientes.getModel().getValueAt(setar, 1).toString());
        jTextFieldRG.setText(jTableClientes.getModel().getValueAt(setar, 2).toString());
        jTextFieldNome.setText(jTableClientes.getModel().getValueAt(setar, 3).toString());
        jTextFieldDataNascimento.setText(jTableClientes.getModel().getValueAt(setar, 4).toString());
        jTextFieldSexo.setText(jTableClientes.getModel().getValueAt(setar, 5).toString());
        jTextFieldCEP.setText(jTableClientes.getModel().getValueAt(setar, 6).toString());
        jTextFieldEndereco.setText(jTableClientes.getModel().getValueAt(setar, 7).toString());
        jTextFieldBairro.setText(jTableClientes.getModel().getValueAt(setar, 8).toString());
        jTextFieldComplemento.setText(jTableClientes.getModel().getValueAt(setar, 9).toString());
        jTextFieldCidade.setText(jTableClientes.getModel().getValueAt(setar, 10).toString());
        jComboBoxEstados.setSelectedItem(jTableClientes.getModel().getValueAt(setar, 11).toString());
        jTextFieldTelefoneFixo.setText(jTableClientes.getModel().getValueAt(setar, 12).toString());
        jTextFieldTelefoneCelular.setText(jTableClientes.getModel().getValueAt(setar, 13).toString());
        jTextFieldEmail.setText(jTableClientes.getModel().getValueAt(setar, 14).toString());
        jTextFieldSkype.setText(jTableClientes.getModel().getValueAt(setar, 15).toString());
        jTextAreaObservacoes.setText(jTableClientes.getModel().getValueAt(setar, 16).toString());

        /* Desabilita o botão adicionar */
        /* jButtonAdicionar.setEnabled(false); */
    }

    /* Método para realizar a limpeza dos campos do formulário */
    private void limparFormulario(){
        /* Limpeza dos campos */
        jTextFieldID.setText(null);
        jTextFieldCPF.setText(null);
        jTextFieldRG.setText(null);
        jTextFieldNome.setText(null);
        jTextFieldDataNascimento.setText(null);
        jTextFieldSexo.setText(null);
        jTextFieldCEP.setText(null);
        jTextFieldEndereco.setText(null);
        jTextFieldBairro.setText(null);
        jTextFieldComplemento.setText(null);
        jTextFieldCidade.setText(null);
        jComboBoxEstados.setSelectedItem(null);
        jTextFieldTelefoneFixo.setText(null);
        jTextFieldTelefoneCelular.setText(null);
        jTextFieldEmail.setText(null);
        jTextFieldSkype.setText(null);
        jTextAreaObservacoes.setText(null);

        /**
         * Chama o método para consultar clientes
         * Desta forma os dados aparecem atualizados
        */
        consultar();
    }

    /* Método para alterar clientes */
    private void alterar() {
        String SQL = "UPDATE cliente SET \n" +
        "CPFCliente=?, \n" +
        "RGCliente=?, \n" +
        "nomeCliente=?, \n" +
        "dataNascimentoCliente=?, \n" +
        "sexoCliente=?, \n" +
        "CEPCliente=?, \n" +
        "enderecoCliente=?, \n" +
        "bairroCliente=?, \n" +
        "complementoCliente=?, \n" +
        "cidadeCliente=?, \n" +
        "UFCliente=?, \n" +
        "telefoneFixoCliente=?, \n" +
        "telefoneCelularCliente=?, \n" +
        "emailCliente=?, \n" +
        "skypeCliente=?, \n" +
        "observacoesCliente=? \n" +
        "WHERE idCliente=?";

        try {
            /* Consulta ao banco de dados em função do que foi digitado */
            pst = conexao.prepareStatement(SQL);

            pst.setString(1, jTextFieldCPF.getText());
            pst.setString(2, jTextFieldRG.getText());
            pst.setString(3, jTextFieldNome.getText());
            pst.setString(4, jTextFieldDataNascimento.getText());
            pst.setString(5, jTextFieldSexo.getText());
            pst.setString(6, jTextFieldCEP.getText());
            pst.setString(7, jTextFieldEndereco.getText());
            pst.setString(8, jTextFieldBairro.getText());
            pst.setString(9, jTextFieldComplemento.getText());
            pst.setString(10, jTextFieldCidade.getText());
            pst.setString(11, jComboBoxEstados.getSelectedItem().toString());
            pst.setString(12, jTextFieldTelefoneFixo.getText());
            pst.setString(13, jTextFieldTelefoneCelular.getText());
            pst.setString(14, jTextFieldEmail.getText());
            pst.setString(15, jTextFieldSkype.getText());
            pst.setString(16, jTextAreaObservacoes.getText());
            pst.setString(17, jTextFieldID.getText());

            /* Validação dos campos obrigatórios */
            if(
            (jTextFieldNome.getText().isEmpty()) ||
            (jTextFieldDataNascimento.getText().isEmpty()) ||
            (jTextFieldSexo.getText().isEmpty()) ||
            (jTextFieldCEP.getText().isEmpty()) ||
            (jTextFieldEndereco.getText().isEmpty()) ||
            (jTextFieldBairro.getText().isEmpty()) ||
            (jTextFieldCidade.getText().isEmpty()) ||
            (jComboBoxEstados.getSelectedItem().toString().isEmpty()) ||
            (jTextFieldTelefoneFixo.getText().isEmpty()) ||
            (jTextFieldTelefoneCelular.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "É necessário preencher todos os campos obrigatórios!");
            } else {
                /* Realiza a atualização */
                int adicionado = pst.executeUpdate();

                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso!");

                    /* Limpeza dos campos */
                    jTextFieldID.setText(null);
                    jTextFieldCPF.setText(null);
                    jTextFieldRG.setText(null);
                    jTextFieldNome.setText(null);
                    jTextFieldDataNascimento.setText(null);
                    jTextFieldSexo.setText(null);
                    jTextFieldCEP.setText(null);
                    jTextFieldEndereco.setText(null);
                    jTextFieldBairro.setText(null);
                    jTextFieldComplemento.setText(null);
                    jTextFieldCidade.setText(null);
                    jComboBoxEstados.setSelectedItem(null);
                    jTextFieldTelefoneFixo.setText(null);
                    jTextFieldTelefoneCelular.setText(null);
                    jTextFieldEmail.setText(null);
                    jTextFieldSkype.setText(null);
                    jTextAreaObservacoes.setText(null);

                    /**
                     * Chama o método para consultar clientes
                     * Desta forma os dados aparecem atualizados
                    */
                    consultar();
                }
            }
        } catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            ModuloConexao.closeConnection(conexao, pst);
        }
    }

    /* Método para deletar clientes */
    private void deletar(){
        /* Caixa de diálogo */
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);

        /* Deleta o cliente */
        if(confirmar == JOptionPane.YES_OPTION) {
            String SQL = "DELETE FROM cliente WHERE idCliente=?";

            try {
                pst = conexao.prepareStatement(SQL);

                pst.setString(1, jTextFieldID.getText());

                /* Realiza a atualização */
                int apagado = pst.executeUpdate();

                if(apagado > 0){
                    JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");

                    /* Limpeza dos campos */
                    jTextFieldID.setText(null);
                    jTextFieldCPF.setText(null);
                    jTextFieldRG.setText(null);
                    jTextFieldNome.setText(null);
                    jTextFieldDataNascimento.setText(null);
                    jTextFieldSexo.setText(null);
                    jTextFieldCEP.setText(null);
                    jTextFieldEndereco.setText(null);
                    jTextFieldBairro.setText(null);
                    jTextFieldComplemento.setText(null);
                    jTextFieldCidade.setText(null);
                    jComboBoxEstados.setSelectedItem(null);
                    jTextFieldTelefoneFixo.setText(null);
                    jTextFieldTelefoneCelular.setText(null);
                    jTextFieldEmail.setText(null);
                    jTextFieldSkype.setText(null);
                    jTextAreaObservacoes.setText(null);

                     /**
                     * Chama o método para consultar clientes
                     * Desta forma os dados aparecem atualizados
                    */
                    consultar();
                }
            } catch (HeadlessException | SQLException e){
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
        jPanel1 = new javax.swing.JPanel();
        jLabel = new javax.swing.JLabel();
        jLabelConsultar = new javax.swing.JLabel();
        jLabelID = new javax.swing.JLabel();
        jLabelCPF = new javax.swing.JLabel();
        jLabelRG = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabelDataNascimento = new javax.swing.JLabel();
        jLabelSexo = new javax.swing.JLabel();
        jLabelCEP = new javax.swing.JLabel();
        jLabelEndereco = new javax.swing.JLabel();
        jLabelBairro = new javax.swing.JLabel();
        jLabelComplemento = new javax.swing.JLabel();
        jLabelCidade = new javax.swing.JLabel();
        jLabelUF = new javax.swing.JLabel();
        jLabelTelefoneFixo = new javax.swing.JLabel();
        jLabelTelefoneCelular = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelSkype = new javax.swing.JLabel();
        jLabelObservacoes = new javax.swing.JLabel();
        jTextFieldConsultar = new javax.swing.JTextField();
        jTextFieldID = new javax.swing.JTextField();
        jTextFieldCPF = new javax.swing.JTextField();
        jTextFieldRG = new javax.swing.JTextField();
        jTextFieldNome = new javax.swing.JTextField();
        jTextFieldDataNascimento = new javax.swing.JTextField();
        jTextFieldSexo = new javax.swing.JTextField();
        jTextFieldCEP = new javax.swing.JTextField();
        jTextFieldEndereco = new javax.swing.JTextField();
        jTextFieldBairro = new javax.swing.JTextField();
        jTextFieldComplemento = new javax.swing.JTextField();
        jTextFieldCidade = new javax.swing.JTextField();
        jTextFieldTelefoneFixo = new javax.swing.JTextField();
        jTextFieldTelefoneCelular = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldSkype = new javax.swing.JTextField();
        jScrollPaneObservacoes = new javax.swing.JScrollPane();
        jTextAreaObservacoes = new javax.swing.JTextArea();
        jButtonAdicionar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonDeletar = new javax.swing.JButton();
        jScrollPaneClientes = new javax.swing.JScrollPane();
        jTableClientes = new javax.swing.JTable();
        jComboBoxEstados = new javax.swing.JComboBox<>();
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
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(595, 559));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(null);
        jPanel1.setPreferredSize(new java.awt.Dimension(595, 559));

        jLabel.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel.setForeground(new java.awt.Color(51, 51, 51));
        jLabel.setText("* Campos Obrigatórios");

        jLabelConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/consultar.png"))); // NOI18N

        jLabelID.setBackground(new java.awt.Color(51, 51, 51));
        jLabelID.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelID.setForeground(new java.awt.Color(51, 51, 51));
        jLabelID.setText("ID:");

        jLabelCPF.setBackground(new java.awt.Color(51, 51, 51));
        jLabelCPF.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelCPF.setForeground(new java.awt.Color(51, 51, 51));
        jLabelCPF.setText("CPF:");

        jLabelRG.setBackground(new java.awt.Color(51, 51, 51));
        jLabelRG.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelRG.setForeground(new java.awt.Color(51, 51, 51));
        jLabelRG.setText("RG:");

        jLabelNome.setBackground(new java.awt.Color(51, 51, 51));
        jLabelNome.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelNome.setForeground(new java.awt.Color(51, 51, 51));
        jLabelNome.setText("*Nome:");

        jLabelDataNascimento.setBackground(new java.awt.Color(51, 51, 51));
        jLabelDataNascimento.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelDataNascimento.setForeground(new java.awt.Color(51, 51, 51));
        jLabelDataNascimento.setText("*Data de Nascimento:");

        jLabelSexo.setBackground(new java.awt.Color(51, 51, 51));
        jLabelSexo.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelSexo.setForeground(new java.awt.Color(51, 51, 51));
        jLabelSexo.setText("*Sexo:");

        jLabelCEP.setBackground(new java.awt.Color(51, 51, 51));
        jLabelCEP.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelCEP.setForeground(new java.awt.Color(51, 51, 51));
        jLabelCEP.setText("*CEP:");

        jLabelEndereco.setBackground(new java.awt.Color(51, 51, 51));
        jLabelEndereco.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelEndereco.setForeground(new java.awt.Color(51, 51, 51));
        jLabelEndereco.setText("*Endereço:");

        jLabelBairro.setBackground(new java.awt.Color(51, 51, 51));
        jLabelBairro.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelBairro.setForeground(new java.awt.Color(51, 51, 51));
        jLabelBairro.setText("*Bairro:");

        jLabelComplemento.setBackground(new java.awt.Color(51, 51, 51));
        jLabelComplemento.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelComplemento.setForeground(new java.awt.Color(51, 51, 51));
        jLabelComplemento.setText("Complemento:");

        jLabelCidade.setBackground(new java.awt.Color(51, 51, 51));
        jLabelCidade.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelCidade.setForeground(new java.awt.Color(51, 51, 51));
        jLabelCidade.setText("*Cidade:");

        jLabelUF.setBackground(new java.awt.Color(51, 51, 51));
        jLabelUF.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelUF.setForeground(new java.awt.Color(51, 51, 51));
        jLabelUF.setText("*UF:");

        jLabelTelefoneFixo.setBackground(new java.awt.Color(51, 51, 51));
        jLabelTelefoneFixo.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelTelefoneFixo.setForeground(new java.awt.Color(51, 51, 51));
        jLabelTelefoneFixo.setText("*Telefone Fixo:");

        jLabelTelefoneCelular.setBackground(new java.awt.Color(51, 51, 51));
        jLabelTelefoneCelular.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelTelefoneCelular.setForeground(new java.awt.Color(51, 51, 51));
        jLabelTelefoneCelular.setText("*Telefone Celular:");

        jLabelEmail.setBackground(new java.awt.Color(51, 51, 51));
        jLabelEmail.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelEmail.setForeground(new java.awt.Color(51, 51, 51));
        jLabelEmail.setText("E-mail:");

        jLabelSkype.setBackground(new java.awt.Color(51, 51, 51));
        jLabelSkype.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelSkype.setForeground(new java.awt.Color(51, 51, 51));
        jLabelSkype.setText("Skype:");

        jLabelObservacoes.setBackground(new java.awt.Color(51, 51, 51));
        jLabelObservacoes.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelObservacoes.setForeground(new java.awt.Color(51, 51, 51));
        jLabelObservacoes.setText("Observações:");

        jTextFieldConsultar.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldConsultar.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldConsultarActionPerformed(evt);
            }
        });
        jTextFieldConsultar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldConsultarKeyReleased(evt);
            }
        });

        jTextFieldID.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldID.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldID.setEnabled(false);
        jTextFieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDActionPerformed(evt);
            }
        });

        jTextFieldCPF.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldCPF.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldCPF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCPFActionPerformed(evt);
            }
        });

        jTextFieldRG.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldRG.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldRG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldRG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRGActionPerformed(evt);
            }
        });

        jTextFieldNome.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldNome.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeActionPerformed(evt);
            }
        });

        jTextFieldDataNascimento.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldDataNascimento.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldDataNascimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldDataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDataNascimentoActionPerformed(evt);
            }
        });

        jTextFieldSexo.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldSexo.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldSexo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSexoActionPerformed(evt);
            }
        });

        jTextFieldCEP.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldCEP.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldCEP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldCEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCEPActionPerformed(evt);
            }
        });

        jTextFieldEndereco.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldEndereco.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldEndereco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEnderecoActionPerformed(evt);
            }
        });

        jTextFieldBairro.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldBairro.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldBairro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBairroActionPerformed(evt);
            }
        });

        jTextFieldComplemento.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldComplemento.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldComplemento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldComplemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldComplementoActionPerformed(evt);
            }
        });

        jTextFieldCidade.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldCidade.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldCidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCidadeActionPerformed(evt);
            }
        });

        jTextFieldTelefoneFixo.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldTelefoneFixo.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldTelefoneFixo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldTelefoneFixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTelefoneFixoActionPerformed(evt);
            }
        });

        jTextFieldTelefoneCelular.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldTelefoneCelular.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldTelefoneCelular.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldTelefoneCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTelefoneCelularActionPerformed(evt);
            }
        });

        jTextFieldEmail.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldEmail.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmailActionPerformed(evt);
            }
        });

        jTextFieldSkype.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextFieldSkype.setForeground(new java.awt.Color(51, 51, 51));
        jTextFieldSkype.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jTextFieldSkype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSkypeActionPerformed(evt);
            }
        });

        jTextAreaObservacoes.setColumns(20);
        jTextAreaObservacoes.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTextAreaObservacoes.setForeground(new java.awt.Color(51, 51, 51));
        jTextAreaObservacoes.setRows(5);
        jTextAreaObservacoes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jScrollPaneObservacoes.setViewportView(jTextAreaObservacoes);

        jButtonAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/upgradeinformatica/icones/adicionar.png"))); // NOI18N
        jButtonAdicionar.setToolTipText("Adicionar");
        jButtonAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAdicionar.setPreferredSize(new java.awt.Dimension(64, 64));
        jButtonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarActionPerformed(evt);
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

        jScrollPaneClientes.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CPF", "RG", "Nome", "Data de Nascimento", "Sexo", "CEP", "Endereço", "Bairro", "Complemento", "Cidade", "UF", "Telefone Fixo", "Telefone Celular", "E-mail", "Skype", "Observações"
            }
        ));
        jTableClientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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

        jComboBoxEstados.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jComboBoxEstados.setForeground(new java.awt.Color(51, 51, 51));
        jComboBoxEstados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));
        jComboBoxEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEstadosActionPerformed(evt);
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelConsultar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel))
                    .addComponent(jScrollPaneClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelCidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelEmail)
                    .addComponent(jLabelObservacoes)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabelTelefoneFixo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldTelefoneFixo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabelTelefoneCelular)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabelSkype)
                            .addGap(6, 6, 6)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldEmail)
                                .addComponent(jTextFieldSkype, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(77, 77, 77)
                            .addComponent(jButtonLimparFormulario))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPaneObservacoes, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelBairro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(157, 157, 157)
                                        .addComponent(jLabelUF)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextFieldBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelComplemento)
                                        .addGap(3, 3, 3)
                                        .addComponent(jTextFieldComplemento))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelEndereco)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldEndereco))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelDataNascimento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelSexo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelCEP)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCEP))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelCPF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabelRG)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldRG, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCPF)
                    .addComponent(jLabelRG)
                    .addComponent(jTextFieldCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelID)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNome)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDataNascimento)
                    .addComponent(jLabelSexo)
                    .addComponent(jLabelCEP)
                    .addComponent(jTextFieldDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEndereco)
                    .addComponent(jTextFieldEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelComplemento)
                    .addComponent(jTextFieldComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBairro)
                    .addComponent(jTextFieldBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCidade)
                    .addComponent(jTextFieldCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUF)
                    .addComponent(jComboBoxEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTelefoneFixo)
                    .addComponent(jLabelTelefoneCelular)
                    .addComponent(jTextFieldTelefoneCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTelefoneFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSkype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSkype))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelObservacoes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPaneObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButtonLimparFormulario))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jButtonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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

    private void jTextFieldCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCPFActionPerformed

    private void jTextFieldRGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRGActionPerformed

    private void jTextFieldNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeActionPerformed

    private void jTextFieldDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDataNascimentoActionPerformed

    private void jTextFieldSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSexoActionPerformed

    private void jTextFieldCEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCEPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCEPActionPerformed

    private void jTextFieldEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEnderecoActionPerformed

    private void jTextFieldBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBairroActionPerformed

    private void jTextFieldComplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldComplementoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldComplementoActionPerformed

    private void jTextFieldCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCidadeActionPerformed

    private void jTextFieldTelefoneFixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTelefoneFixoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTelefoneFixoActionPerformed

    private void jTextFieldTelefoneCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTelefoneCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTelefoneCelularActionPerformed

    private void jTextFieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmailActionPerformed

    private void jTextFieldSkypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSkypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSkypeActionPerformed

    private void jTextFieldConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldConsultarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldConsultarActionPerformed

    private void jButtonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarActionPerformed
        /* Método para adicionar clientes */
        adicionar();
    }//GEN-LAST:event_jButtonAdicionarActionPerformed

    private void jComboBoxEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEstadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEstadosActionPerformed

    private void jTextFieldConsultarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldConsultarKeyReleased
        /* Método para consultar clientes */
        consultar();
    }//GEN-LAST:event_jTextFieldConsultarKeyReleased

    private void jTableClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientesMouseClicked
        /* Setar os campos do fomulário com o conteúdo da tabela */
        setarFormulario();
    }//GEN-LAST:event_jTableClientesMouseClicked

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        /* Método para alterar clientes */
        alterar();
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jTextFieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDActionPerformed

    private void jButtonLimparFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparFormularioActionPerformed
        /* Método para realizar a limpeza dos campos do formulário */
        limparFormulario();
    }//GEN-LAST:event_jButtonLimparFormularioActionPerformed

    private void jButtonDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletarActionPerformed
        /* Método para deletar clientes */
        deletar();
    }//GEN-LAST:event_jButtonDeletarActionPerformed

    private void jTableClientesComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTableClientesComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableClientesComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdicionar;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonDeletar;
    private javax.swing.JButton jButtonLimparFormulario;
    private javax.swing.JComboBox<String> jComboBoxEstados;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabelBairro;
    private javax.swing.JLabel jLabelCEP;
    private javax.swing.JLabel jLabelCPF;
    private javax.swing.JLabel jLabelCidade;
    private javax.swing.JLabel jLabelComplemento;
    private javax.swing.JLabel jLabelConsultar;
    private javax.swing.JLabel jLabelDataNascimento;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelEndereco;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelObservacoes;
    private javax.swing.JLabel jLabelRG;
    private javax.swing.JLabel jLabelSexo;
    private javax.swing.JLabel jLabelSkype;
    private javax.swing.JLabel jLabelTelefoneCelular;
    private javax.swing.JLabel jLabelTelefoneFixo;
    private javax.swing.JLabel jLabelUF;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneClientes;
    private javax.swing.JScrollPane jScrollPaneObservacoes;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextArea jTextAreaObservacoes;
    private javax.swing.JTextField jTextFieldBairro;
    private javax.swing.JTextField jTextFieldCEP;
    private javax.swing.JTextField jTextFieldCPF;
    private javax.swing.JTextField jTextFieldCidade;
    private javax.swing.JTextField jTextFieldComplemento;
    private javax.swing.JTextField jTextFieldConsultar;
    private javax.swing.JTextField jTextFieldDataNascimento;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldEndereco;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldRG;
    private javax.swing.JTextField jTextFieldSexo;
    private javax.swing.JTextField jTextFieldSkype;
    private javax.swing.JTextField jTextFieldTelefoneCelular;
    private javax.swing.JTextField jTextFieldTelefoneFixo;
    // End of variables declaration//GEN-END:variables
}
