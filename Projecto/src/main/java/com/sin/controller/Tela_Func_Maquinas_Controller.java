/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import com.sin.model.Administrador;
import com.sin.model.Equipamento;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Func_Maquinas_Controller implements Initializable {

   @FXML
    private TableView<Equipamento> tabela;

    @FXML
    private TableColumn<Equipamento, String> tabela_Marca;

    @FXML
    private TableColumn<Equipamento, String> tabela_Modelo;

    @FXML
    private TableColumn<Equipamento, String> tabela_Nome;

    @FXML
    private TableColumn<Equipamento, Byte> tabela_Imagem;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPesquisa;
    
    @FXML
    private TextArea txtAreaDiscri;

    @FXML
    private ImageView imageCamera;
    
    private ObservableList<Equipamento> observableList;

    private String caminhoDoArquivo;

    /**
     * Método que carrega a imagem do diretório da máquina para o app , que
     * posteriormente será atribuida a maquina que está sendo cadastrada
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
     * Método para cadastrar as máquinas
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cadastrar(ActionEvent event) throws IOException {
        GenericDAO dao = new GenericDAO();
        Equipamento equip = new Equipamento();
        equip.setNome(txtNome.getText());
        equip.setMarca(txtMarca.getText());
        equip.setModelo(txtModelo.getText());
        equip.setDiscricao(txtAreaDiscri.getText());

        //Instrução que verifica se o caminho do arquivo não é nulo ou vazio
        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            //Instrução que faz a leitura da imagem do arquivo e armazenamento como um array de bytes
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);
            equip.setImagem(imagemBytes);
        } else {
            System.out.println("Nenhum arquivo de imagem selecionado.");
        }
        dao.add(equip);
    }

    @FXML
    void pesquisar(ActionEvent event) {
        if (!txtPesquisa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, txtPesquisa.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Campo de Texto Vazio");
        }
    }

    /**
     * Método para editar os atributos relativos as máquinas
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void editar(ActionEvent event) throws IOException {
        Class<Equipamento> classe = Equipamento.class;
        GenericDAO dao = new GenericDAO();
        Equipamento equip = new Equipamento();

        equip.setId(Long.valueOf(txtId.getText()));
        equip.setNome(txtNome.getText());
        equip.setMarca(txtMarca.getText());
        equip.setModelo(txtModelo.getText());
        equip.setDiscricao(txtAreaDiscri.getText());
        // Verifique se o caminho do arquivo não é nulo ou vazio
        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            // Leitura da imagem do arquivo e armazenamento como um array de bytes
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);
            equip.setImagem(imagemBytes);
        } else {
            System.out.println("Nenhum arquivo de imagem selecionado.");
        }
        dao.Atualizar(classe, Long.valueOf(txtId.getText()), equip);
        JOptionPane.showMessageDialog(null, "Atualizado COm sucesso");
        txtId.setText("");
        txtMarca.setText("");
        txtNome.setText("");
        txtModelo.setText("");
        listar(event);

    }

    /**
     * Método para remover a máquina dos registos
     *
     * @param event
     */
    @FXML
    void excluir(ActionEvent event) {
        GenericDAO dao = new GenericDAO();
        Class<Equipamento> classe = Equipamento.class;
        dao.removeFisico(classe, Long.valueOf(txtId.getText()));
        txtId.setText("");
        txtMarca.setText("");
        txtNome.setText("");
        txtModelo.setText("");
        listar(event);
    }

    /**
     * Método para apresentar todas as máquinas ativas no négocio
     *
     * @param event
     */
    @FXML
    void listar(ActionEvent event) {
        GenericDAO dao = new GenericDAO();
        Class<Equipamento> classe = Equipamento.class;

        List<Equipamento> lista = (List<Equipamento>) dao.listar(classe);

        tabela_Nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tabela_Marca.setCellValueFactory(new PropertyValueFactory<>("Marca"));
        tabela_Modelo.setCellValueFactory(new PropertyValueFactory<>("Modelo"));
        // tabela_Imagem.setCellValueFactory(new PropertyValueFactory<>("Imagem"));

        observableList = FXCollections.observableArrayList(lista);
        tabela.setItems(observableList);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue)
        );
        txtId.setDisable(true);
    }

    /**
     * Método para carregar os dados na tabela para os respectivos campos
     *
     * @param pessoa
     */
    public void pegarLinhaSelecionada(Equipamento pessoa) {
        if (pessoa != null) {
            txtId.setText(String.valueOf(pessoa.getId()));
            txtNome.setText(pessoa.getNome());
            txtMarca.setText(pessoa.getMarca());
            txtModelo.setText(pessoa.getModelo());
            txtAreaDiscri.setText(pessoa.getDiscricao());
            
            if (pessoa.getImagem() != null) {
                // Converta o array de bytes em uma Image
                byte[] imagemBytes = pessoa.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                // Defina a imagem no ImageView
                imageCamera.setImage(imagem);
            } else {
                JOptionPane.showMessageDialog(null, "imagem nao encontrada");
            }

        } else {
            txtId.setText("");
            txtNome.setText("");
            txtMarca.setText("");
            txtModelo.setText("");
            txtAreaDiscri.setText("");

        }
    }    
    
}
