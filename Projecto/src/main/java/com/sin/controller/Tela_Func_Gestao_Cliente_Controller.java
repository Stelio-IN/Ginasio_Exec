package com.sin.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import com.sin.model.Cliente;

public class Tela_Func_Gestao_Cliente_Controller implements Initializable {

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
    private DatePicker dataChosser;

    @FXML
    private ToggleGroup genero;

    @FXML
    private ToggleGroup grupoDesport;

    @FXML
    private ToggleGroup grupoDoenca;

    @FXML
    private ImageView imageCamera;

    @FXML
    private ListView<Cliente> listView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField txtAltura;

    @FXML
    private TextField txtCodigoMembro;

    @FXML
    private TextField txtContacto;

    @FXML
    private TextField txtContactoEmergencia;

    @FXML
    private TextField txtDataInscricao;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtIdentificacao;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNumeroDaCasa;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private TextField txtRua;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada((Cliente) newValue)
        );

        carregarDesportos();
        carregarDoencas();
        carregarNacionalidade();
        carregarBairros();
        carregarCidade();
        carregarObjectivos();

    }

    /*
    Cliente para atualizar
     */
    private Cliente DadosAtualizados = null;
    GenericDAO dao = new GenericDAO();

    @FXML
    void Atualizar(ActionEvent event) {
        if (DadosAtualizados != null) {
            DadosAtualizados.setNome(txtNome.getText());
            DadosAtualizados.setContato_emergencia(txtContactoEmergencia.getText());
            DadosAtualizados.setTelefone(txtContacto.getText());
            DadosAtualizados.setEmail(txtEmail.getText());
            if (ComboBoxObjectivo.toString() != null) {
                DadosAtualizados.setObjectivo(ComboBoxObjectivo.toString());
            }
            DadosAtualizados.setBilhete_Identificacao(txtIdentificacao.getText());

            dao.Atualizar(Cliente.class, DadosAtualizados.getId(), DadosAtualizados);
        }
    }

    @FXML
    void Remover(ActionEvent event) {
        if (DadosAtualizados != null) {
            DadosAtualizados.setIsAtivo(false);
            dao.Atualizar(Cliente.class, DadosAtualizados.getId(), DadosAtualizados);
            System.out.println("Removido");
            System.out.println(DadosAtualizados.getNome() + "Estado " + DadosAtualizados.isIsAtivo());
        }
    }

    private String caminhoDoArquivo;

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
    void listarPesquisa(KeyEvent event) {
        listarPesquisa();
    }

    public void pegarLinhaSelecionada(Cliente cli) {
        if (cli != null) {
            DadosAtualizados = cli;
            txtCodigoMembro.setText(cli.getCodigo());
            txtNome.setText(cli.getNome());
            comboBoxNacionalidade.setValue(cli.getNacionalidade());
            txtIdentificacao.setText(cli.getBilhete_Identificacao());
            txtContacto.setText(cli.getTelefone());
            txtContactoEmergencia.setText(cli.getContato_emergencia());
            txtEmail.setText(cli.getEmail());
            txtPassword.setText(cli.getPassword());
            txtNumeroDaCasa.setText(cli.getEndereco().getNumero_Casa());
            txtRua.setText(cli.getEndereco().getRua());
            txtId.setText(cli.getId().toString());
            txtPeso.setText(cli.getPeso().toString());
            txtAltura.setText(cli.getAltura().toString());
            txtDataInscricao.setText(cli.getData_inscricao());

            String generoSelecionado = cli.getGenero();
            // Itere pelos Toggles no ToggleGroup
            for (Toggle toggle : genero.getToggles()) {
                RadioButton radioButton = (RadioButton) toggle; // Supondo que os Toggles sejam RadioButtons
                if (radioButton.getText().equals(generoSelecionado)) {
                    genero.selectToggle(radioButton);
                    break; // Saia do loop após encontrar a correspondência
                }
            }

            if (cli.getImagem() != null) {
                // Converta o array de bytes em uma Image
                byte[] imagemBytes = cli.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                // Definir largura e altura desejadas
                imageCamera.setFitWidth(90); // Largura desejada
                imageCamera.setFitHeight(100); // Altura desejada
                // Defina a imagem no ImageView
                imageCamera.setImage(imagem);
            }

        }
    }

    public void listarPesquisa() {
        EntityManagerFactory fabrica;
        EntityManager gerente;
        fabrica = Persistence.createEntityManagerFactory("SystemPU");
        gerente = fabrica.createEntityManager();

        ObservableList<Cliente> items = FXCollections.observableArrayList(); // Crie uma ObservableList de Cliente

        TypedQuery<Cliente> query = gerente.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nome AND c.isAtivo = true", Cliente.class);
        query.setParameter("nome", "%" + txtPesquisa.getText() + "%"); // O operador % é usado para consultas "LIKE"
        List<Cliente> resultados = query.getResultList();

        items.addAll(resultados); // Adicione objetos Cliente à listaPesquisa

        listView.setItems(items); // Defina a ObservableList de objetos Cliente no ListView

        // Defina a célula personalizada para mostrar apenas o nome na listaPesquisa
        listView.setCellFactory(new Callback<ListView<Cliente>, ListCell<Cliente>>() {
            @Override
            public ListCell<Cliente> call(ListView<Cliente> param) {
                return new ListCell<Cliente>() {
                    @Override
                    protected void updateItem(Cliente item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getNome());
                        }
                    }
                };
            }
        });

        gerente.close(); // Não se esqueça de fechar o EntityManager quando terminar
        fabrica.close(); // E a EntityManagerFactory também
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

//    public void listarPesquisa() {
//        EntityManagerFactory fabrica;
//        EntityManager gerente;
//        fabrica = Persistence.createEntityManagerFactory("SystemPU");
//        gerente = fabrica.createEntityManager();
//
//        ObservableList<Cliente> items = FXCollections.observableArrayList(); // Crie uma ObservableList de Cliente
//
//        TypedQuery<Cliente> query = gerente.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nome", Cliente.class);
//        query.setParameter("nome", "%" + txtPesquisa.getText() + "%"); // O operador % é usado para consultas "LIKE"
//        List<Cliente> resultados = query.getResultList();
//
//        items.addAll(resultados); // Adicione objetos Cliente à listaPesquisa
//
//        listView.setItems(items); // Defina a ObservableList de objetos Cliente no ListView
//
//        // Defina a célula personalizada para mostrar apenas o nome na listaPesquisa
//        listView.setCellFactory(new Callback<ListView<Cliente>, ListCell<Cliente>>() {
//            @Override
//            public ListCell<Cliente> call(ListView<Cliente> param) {
//                return new ListCell<Cliente>() {
//                    @Override
//                    protected void updateItem(Cliente item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty || item == null) {
//                            setText(null);
//                        } else {
//                            setText(item.getNome());
//                        }
//                    }
//                };
//            }
//        });
//
//        gerente.close(); // Não se esqueça de fechar o EntityManager quando terminar
//        fabrica.close(); // E a EntityManagerFactory também
//    }
}
