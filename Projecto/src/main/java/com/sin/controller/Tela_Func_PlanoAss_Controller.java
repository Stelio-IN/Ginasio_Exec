/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
//import javax.swing.JOptionPane;
import com.sin.model.Plano_de_Associacao;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Func_PlanoAss_Controller implements Initializable {

    @FXML
    private TableColumn<Plano_de_Associacao, String> colunaNome;

    @FXML
    private TableColumn<Plano_de_Associacao, String> colunaPreco;

    @FXML
    private TableColumn<Plano_de_Associacao, String> colunaStatus;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<Plano_de_Associacao> tabela;

    @FXML
    private Text txtDiscr;

    @FXML
    private Label txtNomePlano;

    @FXML
    private Label txtPreco;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Listar();
        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue)
        );
    }

    private ObservableList< Plano_de_Associacao> observableList;
  
   private void Listar() {
        GenericDAO dao = new GenericDAO();
        Class<Plano_de_Associacao> classe = Plano_de_Associacao.class;

        List<Plano_de_Associacao> lista = (List<Plano_de_Associacao>) dao.listar(classe);

        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        observableList = FXCollections.observableArrayList(lista);
        tabela.setItems(observableList);
    }

    public void pegarLinhaSelecionada(Plano_de_Associacao plano) {
        if (plano != null) {
            txtNomePlano.setText(plano.getNome());
            txtPreco.setText(String.valueOf(plano.getPreco()));
            txtDiscr.setText(plano.getDescricao());

            if (plano.getImagem() != null) {
                // Converta o array de bytes em uma Image
                byte[] imagemBytes = plano.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                // Defina a imagem no ImageView
                imageView.setImage(imagem);
                 imageView.setFitWidth(95); // Largura desejada
            imageView.setFitHeight(91); // Altura desejada
            } else {
               // JOptionPane.showMessageDialog(null, "imagem nao encontrada");
            }

        } else {
           // JOptionPane.showMessageDialog(null, "Selecione Uma linha");
        }
    }
}
