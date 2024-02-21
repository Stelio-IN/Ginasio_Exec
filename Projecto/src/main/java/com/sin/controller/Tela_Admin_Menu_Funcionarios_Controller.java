/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
//import javax.swing.JOptionPane;
import com.sin.model.Funcionario;
import com.sin.model.Pessoa;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Admin_Menu_Funcionarios_Controller implements Initializable {

    @FXML
    private TableView<Funcionario> tabela;

    @FXML
    private TableColumn<Funcionario, String> tabela_Cargo;

    @FXML
    private TableColumn<Funcionario, String> tabela_Codigo;

    @FXML
    private TableColumn<Funcionario, String> tabela_Email;

    @FXML
    private TableColumn<Funcionario, String> tabela_Nome;

    @FXML
    private TableColumn<Funcionario, String> tabela_Salario;

    @FXML
    private TableColumn<?, ?> tabela_Situacao;

    /*
    metodo que pega o cliks do botao
     */
    @FXML
    void listarPesquisa(KeyEvent event) {
        ///lista();
    }

    @FXML
    private TextField txtCargo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private Button btncadastrar;

    @FXML
    private Button btneditar;

    @FXML
    private Button btnexcluir;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSalario;

    @FXML
    private TextField txtId;
    @FXML
    private TextArea txtArea;

    @FXML
    void cadastrar(ActionEvent event) {

        Funcionario func = new Funcionario();
        GenericDAO dao = new GenericDAO();
        func.setCargo(txtCargo.getText());
        func.setNome(txtNome.getText());
        func.setPassword(txtPassword.getText());
        func.setSalario(Double.valueOf(txtSalario.getText()));
        func.setCodigo(txtCodigo.getText());
        func.setEmail(txtEmail.getText());
        dao.add(func);

    }

    @FXML
    void editar(ActionEvent event) {

        Class<Pessoa> classe = Pessoa.class;
        Funcionario func = new Funcionario();
        GenericDAO dao = new GenericDAO();
        func.setId(Long.valueOf(txtId.getText()));
        func.setEmail(txtEmail.getText());
        func.setNome(txtNome.getText());
        func.setCodigo(txtCodigo.getText());
        func.setCargo(txtCargo.getText());
        func.setSalario(Double.valueOf(txtSalario.getText()));
        func.setPassword(txtPassword.getText());

        dao.Atualizar(classe, Long.valueOf(txtId.getText()), func);
        //JOptionPane.showMessageDialog(null, "Atualizado COm sucesso");
        txtId.setText("");
        txtEmail.setText("");
        txtNome.setText("");
        txtPassword.setText("");
        listar(event);
    }

    @FXML
    void excluir(ActionEvent event) {
        GenericDAO dao = new GenericDAO();

        Class<Funcionario> func_Classe = Funcionario.class;
        // dao.removerLogico(func_Classe, Long.valueOf(txtId.getText()), dao);
        dao.removeFisico(func_Classe, Long.valueOf(txtId.getText()));
       // JOptionPane.showMessageDialog(null, "Removido COm sucesso");
        txtId.setText("");
        txtEmail.setText("");
        txtNome.setText("");
        txtPassword.setText("");
        txtCargo.setText("");
        txtCodigo.setText("");
        txtSalario.setText("");
        listar(event);
    }

    private ObservableList<Funcionario> observableListe;

    @FXML
    void listar(ActionEvent event) {
        GenericDAO dao = new GenericDAO();

        Class<Funcionario> func_Classe = Funcionario.class;
        List<Funcionario> lista = (List<Funcionario>) dao.listar(func_Classe);

        tabela_Codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tabela_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tabela_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabela_Salario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        tabela_Cargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        tabela_Situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        observableListe = FXCollections.observableArrayList(lista);
        tabela.setItems(observableListe);
    }

    public void setPessoaAdmin(Pessoa pessoa) {
        if (pessoa instanceof Funcionario funcionario) {
            txtNome.setText(funcionario.getNome());
            txtId.setText(funcionario.getId().toString());
            txtCodigo.setText(String.valueOf(funcionario.getCodigo()));
            txtEmail.setText(funcionario.getEmail());
            txtCargo.setText(funcionario.getCargo());
            txtSalario.setText(funcionario.getSalario().toString());
            txtPassword.setText(funcionario.getPassword());

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//           tabela.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue.getValue()));

        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue)
        );

        txtId.setDisable(true);

        Bloqueio();
        txtId.setDisable(true);
        txtCodigo.setDisable(true);
        txtEmail.setDisable(true);
        txtCargo.setDisable(true);
        txtSalario.setDisable(true);
        txtPassword.setDisable(true);

        btncadastrar.setDisable(true);
        btneditar.setDisable(true);
        btnexcluir.setDisable(true);

        GenericDAO dao = new GenericDAO();
        Class<Pessoa> classe = Pessoa.class;
        int quant = dao.contar_Quantidade_Base(classe);
        System.out.println(quant);
        int anoAtual = Year.now().getValue(); // Obtém o ano atual
        String idUnico = "FUNC" + anoAtual + String.format("%04d", quant); // Formata o número com 4 dígitos
        txtCodigo.setText(idUnico);

    }

    public void pegarLinhaSelecionada(Funcionario pessoa) {
        if (pessoa != null) {
            txtId.setText(pessoa.getId().toString());
            txtCodigo.setText(String.valueOf(pessoa.getCodigo()));
            txtEmail.setText(pessoa.getEmail());
            txtNome.setText(pessoa.getNome());
            txtCargo.setText(pessoa.getCargo());
            txtSalario.setText(pessoa.getSalario().toString());
            txtPassword.setText(pessoa.getPassword());

        } else {
            txtId.setText("");
            txtCodigo.setText("");
            txtEmail.setText("");
            txtNome.setText("");
            txtCargo.setText("");
            txtSalario.setText("");
            txtPassword.setText("");
        }
    }

    // VALIDACAO
    private boolean validarPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validarClassificacao(String peso) {
        String regex = "^[0-5]{1}+(\\.[0-9]+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(peso);
        return matcher.matches();
    }

    private boolean validarSalario(String altura) {
        String regex = "^[0-9]+(\\.[0-9]+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(altura);
        return matcher.matches();
    }

    public void Bloqueio() {

        // NOME
        txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 7 && !newValue.matches(".*\\d.*")) {
                txtNome.setStyle("-fx-text-fill: red;");

                txtCargo.setDisable(true);
                txtPassword.setDisable(true);

            } else {
                txtNome.setStyle("");
                txtCargo.setDisable(false);
            }
        });

        // Cargo
        txtCargo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 7 && !newValue.matches(".*\\d.*")) {
                txtCargo.setStyle("-fx-text-fill: red;");

                txtSalario.setDisable(true);

            } else {
                txtCargo.setStyle("");
                txtSalario.setDisable(false);
            }
        });

        // E-MAIL
        txtEmail.textProperty().addListener((observable, oldvalue, newValue) -> {
            if (validarEmail(newValue)) {
                txtEmail.setStyle("");
                txtPassword.setDisable(false);
            } else {
                txtEmail.setStyle("-fx-text-fill: red");
                txtPassword.setDisable(true);
            }
        });

        // Salario
        txtSalario.textProperty().addListener((obersavable, oldvalue, newValue) -> {
            if (validarSalario(newValue)) {
                txtSalario.setStyle("");
                txtEmail.setDisable(false);

            } else {
                txtSalario.setStyle("-fx-text-fill: red");
                txtEmail.setDisable(true);

            }
        });

        // PASSWORD
        txtPassword.textProperty().addListener((observable, oldvalue, pass) -> {
            if (validarPassword(pass)) {
                txtPassword.setStyle("");
                btncadastrar.setDisable(false);
                btneditar.setDisable(false);
                btnexcluir.setDisable(false);

            } else {

                txtPassword.setStyle("-fx-text-fill: red");

            }
        });

    }

}
