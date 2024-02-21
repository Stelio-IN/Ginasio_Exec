/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.sin.model.Cliente;
import com.sin.model.Endereco;
import com.sin.model.Ficha_Inscricao;
import com.sin.model.Pessoa;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Cadastrar_Cliente_Controller implements Initializable {

    @FXML
    private ComboBox<String> comboBoxDesporto;
    private List<String> desportos = new ArrayList<>();
    private ObservableList<String> obserDesportos;

    @FXML
    private ComboBox<String> comboBoxDoencas;
    private final List<String> doencas = new ArrayList<>();
    private ObservableList<String> obserdoencas;

    @FXML
    private ComboBox<String> comboBoxBairro;
    private final List<String> bairro = new ArrayList<>();
    private ObservableList<String> obserBairro;

    @FXML
    private ComboBox<String> comboBoxCidade;
    private final List<String> cidade = new ArrayList<>();
    private ObservableList<String> obserCidade;

    @FXML
    private ComboBox<String> comboBoxNacionalidade;
    private final List<String> nacionalidade = new ArrayList<>();
    private ObservableList<String> obserNacionalidade;

    @FXML
    private ComboBox<String> ComboBoxObjectivo;
    private final List<String> objectivo = new ArrayList<>();
    private ObservableList<String> obserObjectivo;

    @FXML
    private ToggleGroup genero;

    @FXML
    private ToggleGroup grupoDesport;

    @FXML
    private ToggleGroup grupoDoenca;

    @FXML
    private ToggleGroup grupoestadocivil;

    @FXML
    private TextField txtContactoAlternativo;

    @FXML
    private TextField txtContactoEmergencia;

    @FXML
    private TextField txtNumeroDaCasa;
    @FXML
    private TextField txtAltura;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtRua;

    @FXML
    private TextField txtPassword;

    @FXML
    private DatePicker dataChosser;

    @FXML
    private ImageView imageCamera;

    @FXML
    private TextField txtContacto;

    @FXML
    private TextField txtDataInscricao;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNumeroInsc;
    @FXML
    private TextField txtCodigoMembro;
    @FXML
    private TextField txtIdentificacao;

    @FXML
    private Button btncadastrar;

    String caminhoDoArquivo;

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

    private String idFatura;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializando com campos desabiliados
        comboBoxNacionalidade.setDisable(true);
        comboBoxDesporto.setDisable(true);
        comboBoxDoencas.setDisable(true);
        dataChosser.setDisable(true);
        txtIdentificacao.setDisable(true);
        txtNumeroDaCasa.setDisable(true);
        txtRua.setDisable(true);
        comboBoxCidade.setDisable(true);
        comboBoxBairro.setDisable(true);
        txtPeso.setDisable(true);
        txtAltura.setDisable(true);
        ComboBoxObjectivo.setDisable(true);
        txtEmail.setDisable(true);
        txtPassword.setDisable(true);
        txtContacto.setDisable(true);
        txtContactoEmergencia.setDisable(true);

        btncadastrar.setDisable(true);

        Inicializacao_1();
        Inicializacao_2();
    }

    public void Inicializacao_1() {
        limitarDatePicker();
        // Desabilite a edição manual da data
        dataChosser.setEditable(false);
        //Carregar combo
        carregarDesportos();
        carregarDoencas();
        carregarNacionalidade();
        carregarBairros();
        carregarCidade();
        carregarObjectivos();

        //Inicializando ficha de Inscricao e Data de inscriacao
        LocalDate dataAtual = LocalDate.now();
        // Escolha um formato de data desejado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataAtualFormatada = dataAtual.format(formatter);
        txtDataInscricao.setText(dataAtualFormatada);
        txtDataInscricao.setDisable(true);

        GenericDAO dao = new GenericDAO();
        Class<Pessoa> classe = Pessoa.class;
        int quant = dao.contar_Quantidade_Base(classe);
        System.out.println(quant);
        int anoAtual = Year.now().getValue(); // Obtém o ano atual
        String idUnico = "CLIE" + anoAtual + String.format("%04d", quant); // Formata o número com 4 dígitos
        System.out.println(idUnico);
        txtCodigoMembro.setText(idUnico);
        txtCodigoMembro.setDisable(true);

        idFatura = String.format("%05d", quant); // Formata o número com 4 dígitos 
        System.out.println(idFatura);

        txtNumeroInsc.setText(idFatura);

        txtNumeroInsc.setDisable(true);
        btncadastrar.setVisible(false);

    }

    public void Inicializacao_2() {
        Bloqueio();
    }

    void carregarObjectivos() {
        objectivo.add("Perder Peso");
        objectivo.add("Definir o abdômen");
        objectivo.add("Musculação");
        objectivo.add("Corpo em forma");
        obserObjectivo = FXCollections.observableArrayList(objectivo);
        ComboBoxObjectivo.setItems(obserObjectivo);
    }

    void carregarCidade() {
        cidade.add("Maputo");
        cidade.add("Matola");
        cidade.add("Marracuene");
        obserCidade = FXCollections.observableArrayList(cidade);
        comboBoxCidade.setItems(obserCidade);
    }

    void carregarBairros() {
        bairro.add("Alto Maé");
        bairro.add(" Malhangalene");
        bairro.add(" Polana");
        bairro.add("Chamanculo");
        bairro.add(" Maxaquene");
        bairro.add("Albazine");
        bairro.add(" Costa do Sol");
        bairro.add("Zimpeto");
        obserBairro = FXCollections.observableArrayList(bairro);
        comboBoxBairro.setItems(obserBairro);
    }

    void carregarDesportos() {
        desportos.add("Futebol");
        desportos.add("Basquetebol");
        desportos.add("Atletismo");
        desportos.add("Natacão");
        desportos.add("Natação");
        obserDesportos = FXCollections.observableArrayList(desportos);
        comboBoxDesporto.setItems(obserDesportos);
    }

    void carregarDoencas() {
        doencas.add(" Cardiovasculares");
        doencas.add(" Respiratórias");
        doencas.add("Gastrointestinais");
        doencas.add("Neurológicas");
        doencas.add(" Endócrinas");
        doencas.add(" Infecciosas");
        doencas.add("Musculoesqueléticas");
        doencas.add(" Renais");
        doencas.add(" Psiquiátricas");
        obserdoencas = FXCollections.observableArrayList(doencas);
        comboBoxDoencas.setItems(obserdoencas);
    }

    void carregarNacionalidade() {
        nacionalidade.add(" Moçambique");
        nacionalidade.add(" Africa do sul");
        nacionalidade.add("Angola");
        obserNacionalidade = FXCollections.observableArrayList(nacionalidade);
        comboBoxNacionalidade.setItems(obserNacionalidade);
    }

    private void limitarDatePicker() {
        // Defina as datas mínimas e máximas que você deseja permitir
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        LocalDate minDate = LocalDate.of(anoAtual - 90, 1, 1); // Data mínima
        LocalDate maxDate = LocalDate.of(anoAtual - 16, 12, 31); // Data máxima

        // Crie uma fábrica de células de data para limitar as datas permitidas
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate) || item.isAfter(maxDate));
                    }
                };
            }
        };

        dataChosser.setDayCellFactory(dayCellFactory);
    }

    @FXML
    void ativarComboDesportos(ActionEvent event) {
        comboBoxDesporto.setDisable(true);
    }

    @FXML
    void ativarComboDoencas(ActionEvent event) {
        comboBoxDoencas.setDisable(true);
    }

    @FXML
    void desativarComboDoencas(ActionEvent event) {
        comboBoxDoencas.setDisable(false);
    }

    @FXML
    void desabilitarComboDesportos(ActionEvent event) {
        comboBoxDesporto.setDisable(false);
    }

    @FXML
    void cadastrar(ActionEvent event) throws IOException {
        Cliente cliente = new Cliente();
        GenericDAO dao = new GenericDAO();

        //Radio Button 
        RadioButton pegarGenero = (RadioButton) genero.getSelectedToggle();
        String genero = pegarGenero.getText();

        RadioButton pegarEsporte = (RadioButton) grupoDesport.getSelectedToggle();
        String esporte = pegarEsporte.getText();

        RadioButton pegarDoenca = (RadioButton) grupoDoenca.getSelectedToggle();
        String doenca = pegarDoenca.getText();

//        RadioButton pegarEstadoCivil = (RadioButton) grupoestadocivil.getSelectedToggle();
//        String estadoCivil = pegarEstadoCivil.getText();
        //Pegando a data do 
        String dataAtualFormatada = "";
        if (dataChosser.getValue() != null) {
            // Obtenha o valor selecionado e faça algo com ele
            java.time.LocalDate dataSelecionada = dataChosser.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataAtualFormatada = dataSelecionada.format(formatter);

            System.out.println("Data selecionada: " + dataAtualFormatada);
        } else {
            System.out.println("Nenhuma data selecionada.");
        }

        cliente.setNome(txtNome.getText());
        cliente.setNacionalidade(comboBoxNacionalidade.getValue());
        cliente.setGenero(genero);
        //cliente.setEstado_Civil(estadoCivil);
        cliente.setNascimento(dataAtualFormatada);
        cliente.setBilhete_Identificacao(txtIdentificacao.getText());
        cliente.setTelefone(txtContacto.getText());
        //cliente.setTelefone_Alternativo(txtContactoAlternativo.getText());
        cliente.setContato_emergencia(txtContactoEmergencia.getText());
        cliente.setData_inscricao(txtDataInscricao.getText());
        cliente.setCodigo(txtCodigoMembro.getText());
        cliente.setEsporte_que_Pratica(comboBoxDesporto.getValue());
        cliente.setDoenca(comboBoxDoencas.getValue());
        cliente.setEmail(txtEmail.getText());
        cliente.setNaturalidade(comboBoxCidade.getValue());
        //cliente.setNome_Do_Conjuge(txtNomeConjuge.getText());
        cliente.setPassword(txtPassword.getText());
        cliente.setObjectivo(ComboBoxObjectivo.getValue());
        Endereco endereco = new Endereco();
        endereco.setBairro(comboBoxBairro.getValue());
        endereco.setRua(txtRua.getText());
        endereco.setNumero_Casa(txtNumeroDaCasa.getText());

        cliente.setEndereco(endereco);
        cliente.setIsAtivo(true);

        cliente.setPeso(Double.valueOf(txtPeso.getText()));

        cliente.setAltura(Double.valueOf(txtAltura.getText()));

        // Verifique se o caminho do arquivo não é nulo ou vazio
        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            // Leitura da imagem do arquivo e armazenamento como um array de bytes
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);

            cliente.setImagem(imagemBytes);

        } else {
            System.out.println("Nenhum arquivo de imagem selecionado.");
        }

        //System.out.println(cliente);
        dao.add(cliente);

        /*
        Ficha de inscricao sala
         */
        Ficha_Inscricao ficha = new Ficha_Inscricao();
        ficha.setNumero_Da_Ficha(idFatura);
        ficha.setCliente(txtNome.getText());
        ficha.setFuncionario("Funcionario maluco");
        ficha.setData_Da_Inscriacao(dataAtualFormatada);
        dao.add(ficha);

        Tela_Principal(event);
    }

    public void Tela_Principal(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/Tela_Menu_Func.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ////////////// VALIDACOES ///////////////////////////
    private boolean validarBI(String BI) {
        String regex = "^[0-9]{11}[A-Za-z]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(BI);
        return matcher.matches();
    }

    private boolean validarNome(String nome) {
        String regex = "[A-Za-z]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nome);
        return matcher.matches();
    }

    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validarNrCasa(String Nrcasa) {
        String regex = "^[0-9]{3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Nrcasa);
        return matcher.matches();
    }

    private boolean validarPeso(String peso) {
        String regex = "^[0-9]+(\\.[0-9]+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(peso);
        return matcher.matches();
    }

    private boolean validarAltura(String altura) {
        String regex = "^[0-9]+(\\.[0-9]+)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(altura);
        return matcher.matches();
    }

    private boolean validarPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validarContato(String contacto) {
        String regex = "^[8]+[2-7]+[0-9]{7}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contacto);
        return matcher.matches();
    }

    private boolean validarContatoAlternativo(String contacto) {
        String regex = "^[8]+[2-7]+[0-9]{7}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contacto);
        return matcher.matches();
    }

    ///////////// BLOQUEIO DE CAMPOS ////////////////////////
    public void Bloqueio() {

        // NOME
        txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 7 && !newValue.matches(".*\\d.*")) {
                txtNome.setStyle("-fx-text-fill: red;");
                comboBoxNacionalidade.setDisable(true);
                dataChosser.setDisable(true);

                txtIdentificacao.setDisable(true);
                txtNumeroDaCasa.setDisable(true);
                txtRua.setDisable(true);
                comboBoxCidade.setDisable(true);
                comboBoxBairro.setDisable(true);
                txtPeso.setDisable(true);
                txtAltura.setDisable(true);
                ComboBoxObjectivo.setDisable(true);
                txtEmail.setDisable(true);
                txtPassword.setDisable(true);
                txtContacto.setDisable(true);
                txtContactoEmergencia.setDisable(true);

            } else {
                txtNome.setStyle("");

                comboBoxNacionalidade.setDisable(false);
                dataChosser.setDisable(false);

            }
        });

        //Nacionalidade
        // Adicione um ChangeListener à ComboBox
        comboBoxNacionalidade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    txtIdentificacao.setDisable(false);

                } else {
                    txtIdentificacao.setDisable(true);
                }
            }
        });

        // BI
        txtIdentificacao.textProperty().addListener((observable, oldvalue, newValue) -> {
            if (validarBI(newValue) && newValue.length() <= 12) {
                // BI válido e comprimento igual ou menor que 12 caracteres, estilo padrão
                txtIdentificacao.setStyle("");
                txtNumeroDaCasa.setDisable(false);
            } else {
                // BI inválido ou comprimento maior que 12 caracteres, estilo de texto vermelho
                txtIdentificacao.setStyle("-fx-text-fill: red;");
                txtNumeroDaCasa.setDisable(true);
            }
        });

        // E-MAIL
        txtEmail.textProperty().addListener((observable, oldvalue, newValue) -> {
            if (validarEmail(newValue)) {
                txtEmail.setStyle("");
            } else {
                txtEmail.setStyle("-fx-text-fill: red");
            }
        });

        // NR CASA
        txtNumeroDaCasa.textProperty().addListener((observable, oldvalue, newValue) -> {
            if (validarNrCasa(newValue)) {
                txtNumeroDaCasa.setStyle("");
                txtRua.setDisable(false);
                comboBoxCidade.setDisable(false);
            } else {
                txtNumeroDaCasa.setStyle("-fx-text-fill: red");
                txtRua.setDisable(true);
                comboBoxCidade.setDisable(true);

            }
        });

        comboBoxCidade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    comboBoxBairro.setDisable(false);

                } else {
                    comboBoxBairro.setDisable(true);
                }
            }
        });

        comboBoxBairro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    txtPeso.setDisable(false);

                } else {
                    txtPeso.setDisable(true);
                }
            }
        });

        // PESO
        txtPeso.textProperty().addListener((obersavable, oldvalue, newValue) -> {
            if (validarPeso(newValue)) {
                txtPeso.setStyle("");
                txtAltura.setDisable(false);
            } else {
                txtPeso.setStyle("-fx-text-fill: red");
                txtAltura.setDisable(true);

            }
        });

        // ALTURA
        txtAltura.textProperty().addListener((obersavable, oldvalue, newValue) -> {
            if (validarAltura(newValue)) {
                txtAltura.setStyle("");
                comboBoxDesporto.setDisable(false);
                comboBoxDoencas.setDisable(false);
                ComboBoxObjectivo.setDisable(false);

            } else {
                txtAltura.setStyle("-fx-text-fill: red");
                comboBoxDesporto.setDisable(true);
                comboBoxDoencas.setDisable(true);
                ComboBoxObjectivo.setDisable(true);

            }
        });

//            // Desporto
//           comboBoxDesporto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (newValue != null) {
//                   comboBoxDoencas.setDisable(false);
//                    
//               
//                } else {
//                   comboBoxDoencas.setDisable(true);
//                }
//            }
//        });
//           
//            // Doencas
//           comboBoxDoencas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (newValue != null) {
//                   ComboBoxObjectivo.setDisable(false);
//                    
//               
//                } else {
//                   ComboBoxObjectivo.setDisable(true);
//                }
//            }
//        });
        // objectivo
        ComboBoxObjectivo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    txtEmail.setDisable(false);

                } else {
                    txtEmail.setDisable(true);
                }
            }
        });

        // EMAIL
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validarEmail(newValue)) {
                // txtEmail.getStyleClass().clear();
                txtEmail.setStyle("");
                txtPassword.setDisable(false);

            } else {
                ///txtEmail.getStyleClass().clear();
                txtEmail.setStyle("-fx-text-fill: red");
                txtPassword.setDisable(true);

            }
        });

        // PASSWORD
        txtPassword.textProperty().addListener((observable, oldvalue, pass) -> {
            if (validarPassword(pass)) {
                txtPassword.setStyle("");
                btncadastrar.setVisible(true);
                btncadastrar.setDisable(false);
                txtContacto.setDisable(false);
            } else {

                txtPassword.setStyle("-fx-text-fill: red");
                txtContacto.setDisable(true);

                txtContactoEmergencia.setDisable(true);
            }
        });

        // CONTACTO
        txtContacto.textProperty().addListener((observable, oldvalue, contato) -> {
            if (validarContato(contato)) {
                //   txtContacto.getStyleClass().clear();
                txtContacto.setStyle("");
                txtContactoEmergencia.setDisable(false);
            } else {
                // txtContacto.getStyleClass().clear();
                txtContacto.setStyle("-fx-text-fill: red");
                txtContactoEmergencia.setDisable(true);
            }
        });

        // CONTACTO ALTERNATIVO
        txtContactoEmergencia.textProperty().addListener((observable, oldvalue, contato) -> {
            if (validarContatoAlternativo(contato)) {
                /// txtContacto.getStyleClass().clear();
                txtContactoEmergencia.setStyle("");

            } else {
                ///  txtContactoAlternativo.getStyleClass().clear();
                txtContactoEmergencia.setStyle("-fx-text-fill: red");

            }
        });

    }
}
