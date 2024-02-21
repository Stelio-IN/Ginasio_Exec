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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.sin.model.Administrador;
import com.sin.model.Pessoa;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Admin_Registrar_Controller implements Initializable {

    @FXML
    private ImageView imageCamera;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPassword;

    private String caminhoDoArquivo;

    @FXML
    void cadastrar(ActionEvent event) throws IOException {
        Administrador admi = new Administrador();
        admi.setCodigo(txtCodigo.getText());
        admi.setEmail(txtEmail.getText());
        admi.setNome(txtNome.getText());
        admi.setPassword(txtPassword.getText());

        // Verifique se o caminho do arquivo não é nulo ou vazio
        if (caminhoDoArquivo != null && !caminhoDoArquivo.isEmpty()) {
            // Leitura da imagem do arquivo e armazenamento como um array de bytes
            Path imagePath = Paths.get(caminhoDoArquivo);
            byte[] imagemBytes = Files.readAllBytes(imagePath);
            admi.setImagem(imagemBytes);
        } else {
            System.out.println("Nenhum arquivo de imagem selecionado.");
        }

        GenericDAO dao = new GenericDAO();
        dao.add(admi);

        // System.out.println(admi);
        System.out.println("IN");
    }

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

    /*
    metodo para preencher a imageview 
     */
    void retornar_Imagem_Banco(ActionEvent event) {
        GenericDAO dao = new GenericDAO();
        Administrador administrador = (Administrador) dao.logarEmail("admin");

        if (administrador != null && administrador.getImagem() != null) {
            // Converta o array de bytes em uma Image
            byte[] imagemBytes = administrador.getImagem();
            Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

            // Defina a imagem no ImageView
            imageCamera.setImage(imagem);
            System.out.println("Encontrado");
        } else {
            System.out.println("Nao Encontrado");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        GenericDAO dao = new GenericDAO();
        Class<Pessoa> classe = Pessoa.class;
        int quant = dao.contar_Quantidade_Base(classe);
        System.out.println(quant);
        int anoAtual = Year.now().getValue(); // Obtém o ano atual
        String idUnico = "ADMN" + anoAtual + String.format("%04d", quant); // Formata o número com 4 dígitos
        txtCodigo.setText(idUnico);
    }

}
