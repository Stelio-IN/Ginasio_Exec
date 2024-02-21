/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import javax.swing.JOptionPane;
import com.sin.model.Instrutor;
import com.sin.model.Pessoa;

/**
 * Está classe permite ao administrador tratar de todos os dados relativos ao
 * instrutor
 *
 * @author steli
 */
public class Tela_Admin_Menu_Instrutores_Controller implements Initializable {

    @FXML
    private TextField txtPesquisa;
    @FXML
    private TableView<Instrutor> tabela;

    @FXML
    private TableColumn<Instrutor, String> tabela_Especializacao;

    @FXML
    private TableColumn<Instrutor, String> tabela_Codigo;

    @FXML
    private TableColumn<Instrutor, String> tabela_Email;

    @FXML
    private TableColumn<Instrutor, String> tabela_Nome;

    @FXML
    private TableColumn<Instrutor, String> tabela_Salario;

    @FXML
    private TableColumn<Instrutor, String> tabela_Classificacao;

    @FXML
    private TableColumn<Instrutor, String> tabela_Situacao;

    @FXML
    private TextField txtEspecializacao;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSalario;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtClassificacao;

    @FXML
    private Button btncadastrar;

    @FXML
    private Button btneditar;

    @FXML
    private Button btnexcluir;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private ImageView imageCamera;

    private String caminhoDoArquivo;

    private ObservableList<Instrutor> observableListe;

    @FXML
    void pesquisar(ActionEvent event) {
        if (!txtPesquisa.getText().equals("")) {
           // JOptionPane.showMessageDialog(null, txtPesquisa.getText());
        } else {
           // JOptionPane.showMessageDialog(null, "Campo de Texto Vazio");
        }
    }

    /**
     * Método que carrega a imagem do diretório da máquina para o app , com o
     * objetivo de atribuir ao instrutor que está sendo cadastrado
     *
     * @param event
     */
    @FXML
    void carregarimg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar uma imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("Todos os arquivos", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            caminhoDoArquivo = selectedFile.getAbsolutePath();

            // Cria uma instância de Image com o arquivo selecionado
            Image imagemSelecionada = new Image(selectedFile.toURI().toString());

            // Atribui a imagem ao ImageView
            imageCamera.setImage(imagemSelecionada);
        } else {
            System.out.println("Nenhum arquivo selecionado.");
        }
    }

    /**
     * Método para cadastrar os instrutores no sistema
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cadastrar(ActionEvent event) throws IOException {

        Instrutor Instruto = new Instrutor();
        GenericDAO dao = new GenericDAO();
        Instruto.setEspecializacao(txtEspecializacao.getText());
        Instruto.setNome(txtNome.getText());
        Instruto.setPassword(txtPassword.getText());
        Instruto.setSalario(Double.valueOf(txtSalario.getText()));
        Instruto.setCodigo(txtCodigo.getText());
        Instruto.setEmail(txtEmail.getText());
        Instruto.setClassificacao(Double.valueOf(txtClassificacao.getText()));
        Instruto.setDescricao(txtDescricao.getText());
        // Instrução que verifica se o caminho do arquivo não é nulo ou vazio
        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            // Instrução que faz a leitura da imagem do arquivo e armazenamento como um array de bytes
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);
            Instruto.setImagem(imagemBytes);
        } else {
            System.out.println("Nenhum arquivo de imagem selecionado.");
        }
        dao.add(Instruto);
    }

    /**
     * Método para atualizar os dados relativos aos instrutores
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void editar(ActionEvent event) throws IOException {

        Class<Pessoa> classe = Pessoa.class;
        Instrutor Instru = new Instrutor();
        GenericDAO dao = new GenericDAO();
        Instru.setId(Long.valueOf(txtId.getText()));
        Instru.setEmail(txtEmail.getText());
        Instru.setNome(txtNome.getText());
        Instru.setCodigo(txtCodigo.getText());
        Instru.setEspecializacao(String.valueOf(txtEspecializacao.getText()));
        Instru.setSalario(Double.valueOf(txtSalario.getText()));
        Instru.setPassword(txtPassword.getText());
        Instru.setClassificacao(Double.valueOf(txtClassificacao.getText()));
        Instru.setDescricao(txtDescricao.getText());
        
        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            // Leitura da imagem do arquivo e armazenamento como um array de bytes
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);

            Instru.setImagem(imagemBytes);

        } else {
           // JOptionPane.showMessageDialog(null, "Nenhuma imagem selecionada");
        }

        dao.Atualizar(classe, Long.valueOf(txtId.getText()), Instru);
        //JOptionPane.showMessageDialog(null, "Dados atualizados", "", JOptionPane.YES_OPTION);
        txtId.setText("");
        txtEmail.setText("");
        txtNome.setText("");
        txtPassword.setText("");
        imageCamera.setImage(null);
        listar(event);
    }

    /**
     * Método que elimina de forma lógica o instrutor da base de dados
     *
     * @param event
     */
    @FXML
    void excluir(ActionEvent event) {
        GenericDAO dao = new GenericDAO();

        Class<Instrutor> Instr_Classe = Instrutor.class;
        // dao.removerLogico(func_Classe, Long.valueOf(txtId.getText()), dao);
        dao.removeFisico(Instr_Classe, Long.valueOf(txtId.getText()));
       // JOptionPane.showMessageDialog(null, "Removido COm sucesso");
        txtId.setText("");
        txtEmail.setText("");
        txtNome.setText("");
        txtPassword.setText("");
        txtEspecializacao.setText("");
        txtCodigo.setText("");
        txtSalario.setText("");
        txtClassificacao.setText("");
        listar(event);
    }

    /**
     * Método para apresentar todos os instrutores ativos no sistema
     *
     * @param event
     */
    @FXML
    void listar(ActionEvent event) {
        GenericDAO dao = new GenericDAO();

        Class<Instrutor> Instr_Classe = Instrutor.class;
        List<Instrutor> lista = (List<Instrutor>) dao.listar(Instr_Classe);

        tabela_Codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tabela_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tabela_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabela_Salario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        tabela_Especializacao.setCellValueFactory(new PropertyValueFactory<>("Especializacao"));
        tabela_Classificacao.setCellValueFactory(new PropertyValueFactory<>("Classificacao"));

        observableListe = FXCollections.observableArrayList(lista);
        tabela.setItems(observableListe);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue)
        );
        txtId.setDisable(true);

        Bloqueio();
        txtId.setDisable(true);
        txtCodigo.setDisable(true);
        txtEmail.setDisable(true);
        txtEspecializacao.setDisable(true);
        txtSalario.setDisable(true);
        txtPassword.setDisable(true);
        txtClassificacao.setDisable(true);
        txtDescricao.setDisable(true);
        btncadastrar.setDisable(true);
        btneditar.setDisable(true);
        btnexcluir.setDisable(true);
        
        
        GenericDAO dao = new GenericDAO();
        Class<Pessoa> classe = Pessoa.class;
        int quant = dao.contar_Quantidade_Base(classe);
        System.out.println(quant);
        int anoAtual = Year.now().getValue(); // Obtém o ano atual
        String idUnico = "INST" + anoAtual + String.format("%04d", quant); // Formata o número com 4 dígitos
        txtCodigo.setText(idUnico);
    }

    /**
     * Método para carregar os dados na tabela para os respectivos campos
     *
     * @param pessoa
     */
    public void pegarLinhaSelecionada(Instrutor pessoa) {
        if (pessoa != null) {
            txtId.setText(pessoa.getId().toString());
            txtCodigo.setText(String.valueOf(pessoa.getCodigo()));
            txtEmail.setText(pessoa.getEmail());
            txtNome.setText(pessoa.getNome());
            txtEspecializacao.setText(pessoa.getEspecializacao());
            txtSalario.setText(pessoa.getSalario().toString());
            txtPassword.setText(pessoa.getPassword());
            txtClassificacao.setText(pessoa.getClassificacao().toString());
            txtDescricao.setText(pessoa.getDescricao());

            if (pessoa.getImagem() != null) {
                //Instrução que converte o array de bytes em uma Image
                byte[] imagemBytes = pessoa.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                //Intrução que define a imagem no ImageView
                imageCamera.setImage(imagem);
            } else {
              //  JOptionPane.showMessageDialog(null, "imagem nao encontrada");
            }

        } else {
            txtId.setText("");
            txtCodigo.setText("");
            txtEmail.setText("");
            txtNome.setText("");
            txtEspecializacao.setText("");
            txtSalario.setText("");
            txtPassword.setText("");
            txtClassificacao.setText("");
            txtDescricao.setText("");
        }
    }

    public void setPessoaAdmin(Pessoa pessoa) {
        if (pessoa instanceof Instrutor instrutor) { // Realiza o type casting para Instrutor
            txtNome.setText(instrutor.getNome());
            txtId.setText(String.valueOf(instrutor.getId()));
            txtCodigo.setText(String.valueOf(instrutor.getCodigo()));
            txtEmail.setText(instrutor.getEmail());
            txtEspecializacao.setText(instrutor.getEspecializacao());
            txtSalario.setText(String.valueOf(instrutor.getSalario()));
            txtPassword.setText(instrutor.getPassword());
            txtClassificacao.setText(String.valueOf(instrutor.getClassificacao()));
            if (instrutor.getImagem() != null) {
                //Instrução que converte o array de bytes em uma Image
                byte[] imagemBytes = instrutor.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                //Intrução que define a imagem no ImageView
                imageCamera.setImage(imagem);
            }
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

                txtEspecializacao.setDisable(true);
                txtPassword.setDisable(true);

            } else {
                txtNome.setStyle("");
                txtEspecializacao.setDisable(false);
            }
        });

        // Especializacao
        txtEspecializacao.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 7 && !newValue.matches(".*\\d.*")) {
                txtEspecializacao.setStyle("-fx-text-fill: red;");

                txtClassificacao.setDisable(true);

            } else {
                txtEspecializacao.setStyle("");
                txtClassificacao.setDisable(false);
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

        // Classificacao
        txtClassificacao.textProperty().addListener((obersavable, oldvalue, newValue) -> {
            if (validarClassificacao(newValue)) {
                txtClassificacao.setStyle("");
                txtSalario.setDisable(false);
            } else {
                txtClassificacao.setStyle("-fx-text-fill: red");
                txtSalario.setDisable(true);

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
                txtDescricao.setDisable(false);
            } else {

                txtPassword.setStyle("-fx-text-fill: red");

            }
        });

    }

}
