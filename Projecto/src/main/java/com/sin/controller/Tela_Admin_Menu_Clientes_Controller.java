/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.sin.model.Avaliacoes_Fisicas;
import com.sin.model.Cliente;
import com.sin.model.Pessoa;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Admin_Menu_Clientes_Controller implements Initializable {

    @FXML
    private TableColumn<Cliente, String> colunaCodigo;

    @FXML
    private TableColumn<Cliente, String> colunaNome;

    @FXML
    private TableColumn<Cliente, String> colunaObjectivo;

    @FXML
    private TableColumn<Cliente, String> colunaPlanoAssociacao;

    @FXML
    private TableView<Cliente> tabela;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDataFim;

    @FXML
    private TextField txtDataInicio;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtObjectivo;

    @FXML
    private TextField txtPagamento;

    @FXML
    private TextField txtPlanoAssociacao;
    @FXML
    private ImageView imageView;

    private ObservableList<Cliente> observableListe;

    @FXML
    void listar(ActionEvent event) {
        GenericDAO dao = new GenericDAO();

        Class<Cliente> Instr_Classe = Cliente.class;
        List<Cliente> lista = (List<Cliente>) dao.listar(Instr_Classe);

        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPlanoAssociacao.setCellValueFactory(new PropertyValueFactory<>("plano_de_associacao"));
        colunaObjectivo.setCellValueFactory(new PropertyValueFactory<>("objectivo"));

        observableListe = FXCollections.observableArrayList(lista);

        tabela.setItems(observableListe);
    }

    @FXML
    private LineChart<String, Number> grafico;
    XYChart.Series series;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue)
        );
        txtId.setDisable(true);

    }

    public void pegarLinhaSelecionada(Cliente cliente) {
        grafico.getData().clear();
        if (cliente != null) {
            txtId.setText(String.valueOf(cliente.getId()));
            txtNome.setText(cliente.getNome());
            txtCodigo.setText(cliente.getCodigo());
             txtObjectivo.setText(cliente.getObjectivo());

            if (cliente.getImagem() != null) {
                // Converta o array de bytes em uma Image
                byte[] imagemBytes = cliente.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                // Defina a imagem no ImageView
                imageView.setImage(imagem);

                // Definir largura e altura desejadas
                imageView.setFitWidth(79); // Largura desejada
                imageView.setFitHeight(93); // Altura desejada

                // Preservar a proporção da imagem enquanto ajusta as dimensões
                imageView.setPreserveRatio(true);
            }
            if (cliente.getPlanoCliente() != null) {
                txtPagamento.setText(cliente.getPlanoCliente().isStatus() ? "Activo" : "Inactivo");
                txtDataFim.setText(cliente.getPlanoCliente().getDataFim().toString());
                txtDataInicio.setText(cliente.getPlanoCliente().getDataInicio().toString());
                txtPlanoAssociacao.setText(cliente.getPlanoCliente().getPlano().getNome());
            }else{
                txtPagamento.setText("");
                txtDataFim.setText("");
                txtDataInicio.setText("");
                txtPlanoAssociacao.setText(""); 
            }

            List<Avaliacoes_Fisicas> avaliacoes = cliente.getAvaliacoes();

            if (avaliacoes != null) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();

                for (int i = 0; i < avaliacoes.size(); i++) {
                    Avaliacoes_Fisicas avaliacao = avaliacoes.get(i);
                    Number nota = avaliacao.getNota_da_avaliacao();

                    if (nota != null) {
                        series.getData().add(new XYChart.Data(Integer.toString(i + 1), nota.doubleValue()));
                    }
                }

                grafico.getData().add(series);

            }

        } else {
            txtId.setText("");
            txtNome.setText("");
            txtCodigo.setText("");
            txtPagamento.setText("");
            txtPlanoAssociacao.setText("");
            txtDataFim.setText("");
            txtDataInicio.setText("");
            txtObjectivo.setText("");

        }
    }

    public void setPessoaAdmin(Pessoa pessoa) {
        if (pessoa instanceof Cliente cliente) {
            txtNome.setText(cliente.getNome());
            txtId.setText(String.valueOf(cliente.getId()));
            txtCodigo.setText(String.valueOf(cliente.getCodigo()));
            if (cliente.getPlanoCliente() != null) {
                txtPagamento.setText(cliente.getPlanoCliente().isStatus() ? "Activo" : "Inactivo");
                txtDataFim.setText(cliente.getPlanoCliente().getDataFim().toString());
                txtDataInicio.setText(cliente.getPlanoCliente().getDataInicio().toString());
                txtPlanoAssociacao.setText(cliente.getPlanoCliente().getPlano().getNome());
            }else{
                txtPagamento.setText("");
                txtDataFim.setText("");
                txtDataInicio.setText("");
                txtPlanoAssociacao.setText(""); 
            }
            txtObjectivo.setText(cliente.getObjectivo());
            if (cliente.getImagem() != null) {
                byte[] imagemBytes = cliente.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
                imageView.setImage(imagem);

                imageView.setFitWidth(79); // Largura desejada
                imageView.setFitHeight(93); // Altura desejada
                imageView.setPreserveRatio(true);
            }
            List<Avaliacoes_Fisicas> avaliacoes = cliente.getAvaliacoes();

            if (avaliacoes != null) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();

                for (int i = 0; i < avaliacoes.size(); i++) {
                    Avaliacoes_Fisicas avaliacao = avaliacoes.get(i);
                    Number nota = avaliacao.getNota_da_avaliacao();

                    if (nota != null) {
                        series.getData().add(new XYChart.Data(Integer.toString(i + 1), nota.doubleValue()));
                    }
                }

                grafico.getData().add(series);
            }
        }
    }

}
