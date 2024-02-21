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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import com.sin.model.Plano_de_Associacao;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Admin_Menu_Plano_Associacao_Controller implements Initializable {

    @FXML
    private ToggleGroup statusGroup;
    @FXML
    private RadioButton radioButtonActivo = new RadioButton("Activo");

    @FXML
    private RadioButton radioButtonInactivo = new RadioButton("Inactivo");

    @FXML
    private TableView<Plano_de_Associacao> tabela;

    @FXML
    private TextArea txtAreaDiscr;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    private ImageView imageCamera;

    @FXML
    private TableColumn<Plano_de_Associacao, Image> colunaIMagem;

    @FXML
    private TableColumn<Plano_de_Associacao, String> colunaNome;

    @FXML
    private TableColumn<Plano_de_Associacao, String> colunaPreco;

    @FXML
    private TableColumn<Plano_de_Associacao, String> colunaStatus;

    private ObservableList< Plano_de_Associacao> observableList;

    private String caminhoDoArquivo;

    @FXML
    void CarregarImagem(ActionEvent event) {
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
    void Editar_Plano(ActionEvent event) throws IOException {
        Class<Plano_de_Associacao> classe = Plano_de_Associacao.class;
        Plano_de_Associacao plano = new Plano_de_Associacao();
        GenericDAO dao = new GenericDAO();

        plano.setId(Long.valueOf(txtId.getText()));
        plano.setNome(txtNome.getText());
        plano.setPreco(Double.valueOf(txtPreco.getText()));
        plano.setDescricao(txtAreaDiscr.getText());

        RadioButton pegarGenero = (RadioButton) statusGroup.getSelectedToggle();
        String status = pegarGenero.getText();

        if (status.equals("Activo")) {
            plano.setStatus(true);
        } else if (status.equals("Inactivo")) {
            plano.setStatus(false);
        }

        System.out.println(status);
        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            // Leitura da imagem do arquivo e armazenamento como um array de bytes
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);

            plano.setImagem(imagemBytes);

        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma imagem selecionada");
        }

        dao.Atualizar(classe, Long.valueOf(txtId.getText()), plano);
        JOptionPane.showMessageDialog(null, "Dados atualizados", "", JOptionPane.YES_OPTION);
        txtId.setText("");

        imageCamera.setImage(null);
        Listar(event);
    }

    @FXML
    void Excluir_Plano(ActionEvent event) {
        GenericDAO dao = new GenericDAO();

        Class<Plano_de_Associacao> plano = Plano_de_Associacao.class;
        // dao.removerLogico(func_Classe, Long.valueOf(txtId.getText()), dao);
        dao.removeFisico(plano, Long.valueOf(txtId.getText()));
        JOptionPane.showMessageDialog(null, "Removido COm sucesso");
        txtId.setText("");

        txtNome.setText("");
        txtAreaDiscr.setText("");
        txtPreco.setText("");
        imageCamera.setImage(null);
        Listar(event);
    }

    @FXML
    void Gravar_Plano(ActionEvent event) throws IOException {
        GenericDAO dao = new GenericDAO();
        RadioButton pegarGenero = (RadioButton) statusGroup.getSelectedToggle();
        String status = pegarGenero.getText();

        Plano_de_Associacao plano = new Plano_de_Associacao();
        if (status.equals("Activo")) {
            plano.setStatus(true);
        } else if (status.equals("Inactivo")) {
            plano.setStatus(false);
        }
        plano.setNome(txtNome.getText());
        plano.setDescricao(txtAreaDiscr.getText());
        plano.setPreco(Double.valueOf(txtPreco.getText()));

        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);
            plano.setImagem(imagemBytes);
        } else {
            System.out.println("Nenhum arquivo de imagem selecionado.");
        }

        dao.add(plano);
    }

    @FXML
    void Listar(ActionEvent event) {
        GenericDAO dao = new GenericDAO();
        Class<Plano_de_Associacao> classe = Plano_de_Associacao.class;

        List<Plano_de_Associacao> lista = (List<Plano_de_Associacao>) dao.listar(classe);

        colunaIMagem.setCellValueFactory(new PropertyValueFactory<>("imagem"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // tabela_Imagem.setCellValueFactory(new PropertyValueFactory<>("Imagem"));
        observableList = FXCollections.observableArrayList(lista);
        tabela.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue)
        );
        txtId.setDisable(true);
    }

    public void pegarLinhaSelecionada(Plano_de_Associacao plano) {
        if (plano != null) {
            txtId.setText(String.valueOf(plano.getId()));
            txtNome.setText(plano.getNome());
            txtAreaDiscr.setText(plano.getDescricao());
            txtPreco.setText(String.valueOf(plano.getPreco()));

            if (plano.getImagem() != null) {
                // Converta o array de bytes em uma Image
                byte[] imagemBytes = plano.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                // Defina a imagem no ImageView
                imageCamera.setImage(imagem);
            } else {
                JOptionPane.showMessageDialog(null, "imagem nao encontrada");
            }

        } else {
            txtId.setText("");
            txtNome.setText("");
            txtAreaDiscr.setText("");
            txtPreco.setText("");
        }
    }

}
