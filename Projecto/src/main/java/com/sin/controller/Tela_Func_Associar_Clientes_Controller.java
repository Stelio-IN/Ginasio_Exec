/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;
import com.sin.model.Cliente;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Func_Associar_Clientes_Controller implements Initializable {

    @FXML
    private ImageView imageViewPrimeiro;

    @FXML
    private ImageView imageViewPrimeiroAssociado;

    @FXML
    private ImageView imageViewSegundoAssociado;
    @FXML
    private ImageView imageViewSegundo;

    @FXML
    private ListView<Cliente> listView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField txtCodigoPrimeiroCliente;

    @FXML
    private TextField txtCodigoSegundoCliente;

    @FXML
    private TextField txtGeneroPrimeiroCliente;

    @FXML
    private TextField txtGeneroSegundoCliente;

    @FXML
    private TextField txtNomePrimeiroAssociado;

    @FXML
    private TextField txtNomePrimeiroCliente;

    @FXML
    private TextField txtNomeSegundoAssociado;

    @FXML
    private TextField txtNomeSegundoCliente;
    
    Notificacao notifica = new Notificacao();

    @FXML
    private TextField txtPesquisa;
    private Cliente cliente1 = new Cliente();
    private Cliente cliente2 = new Cliente();
    private Cliente cliente3 = new Cliente();
    private Cliente cliente4 = new Cliente();

    GenericDAO dao = new GenericDAO();
    Class<Cliente> classe = Cliente.class;

    public void pegarLinhaSelecionada(Cliente cli) {
        if (cli != null) {

            if (txtNomePrimeiroCliente.getText().equals("")) {
                cliente1 = cli;
                txtNomePrimeiroCliente.setText(cli.getNome());
                txtCodigoPrimeiroCliente.setText(cli.getCodigo());
                txtGeneroPrimeiroCliente.setText(cli.getGenero());

                if (cli.getClinteAssociado() != null) {
                    cliente2 = cli.getClinteAssociado();
                    txtNomePrimeiroAssociado.setText(cli.getClinteAssociado().getNome());
                    if (cli.getClinteAssociado().getImagem() != null) {
                        // Converta o array de bytes em uma Image
                        byte[] imagemBytes = cli.getClinteAssociado().getImagem();
                        Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                        // Definir largura e altura desejadas
                        imageViewPrimeiroAssociado.setFitWidth(79); // Largura desejada
                        imageViewPrimeiroAssociado.setFitHeight(93); // Altura desejada
                        // Defina a imagem no ImageView
                        imageViewPrimeiroAssociado.setImage(imagem);
                    }
                }else{
                    Image imageLimpar = new Image("/img/adicionar-usuario.png");
                    txtNomePrimeiroAssociado.setText("");
                    imageViewPrimeiroAssociado.setFitWidth(79); // Largura desejada
                    imageViewPrimeiroAssociado.setFitHeight(93); // Altura desejada
                    // Defina a imagem no ImageView
                    imageViewPrimeiroAssociado.setImage(imageLimpar);
                    
                }
                if (cli.getImagem() != null) {
                    // Converta o array de bytes em uma Image
                    byte[] imagemBytes = cli.getImagem();
                    Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                    // Definir largura e altura desejadas
                    imageViewPrimeiro.setFitWidth(128); // Largura desejada
                    imageViewPrimeiro.setFitHeight(99); // Altura desejada
                    // Defina a imagem no ImageView
                    imageViewPrimeiro.setImage(imagem);
                } 
            } else if ((txtCodigoPrimeiroCliente.getText().equals(cli.getCodigo()) == false)) {
    // if ((txtCodigoPrimeiroCliente.getText().equals(cli.getCodigo()) == false) && (txtGeneroPrimeiroCliente.getText().equals(cli.getGenero())) == false) {
                cliente3 = cli;
                txtNomeSegundoCliente.setText(cli.getNome());
                txtCodigoSegundoCliente.setText(cli.getCodigo());
                txtGeneroSegundoCliente.setText(cli.getGenero());

                if (cli.getClinteAssociado() != null) {
                    cliente4 = cli.getClinteAssociado();
                    txtNomeSegundoAssociado.setText(cli.getClinteAssociado().getNome());
                    if (cli.getClinteAssociado().getImagem() != null) {
                        // Converta o array de bytes em uma Image
                        byte[] imagemBytes = cli.getClinteAssociado().getImagem();
                        Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                        // Definir largura e altura desejadas
                        imageViewSegundoAssociado.setFitWidth(128); // Largura desejada
                        imageViewSegundoAssociado.setFitHeight(99); // Altura desejada
                        // Defina a imagem no ImageView
                        imageViewSegundoAssociado.setImage(imagem);
                    }
                }else{
                    txtNomeSegundoAssociado.setText("");
                    Image imageLimpar = new Image("/img/adicionar-usuario.png");
                    imageViewSegundoAssociado.setFitWidth(79); // Largura desejada
                    imageViewSegundoAssociado.setFitHeight(93); // Altura desejada
                    // Defina a imagem no ImageView
                    imageViewSegundoAssociado.setImage(imageLimpar);
                }
                if (cli.getImagem() != null) {
                    // Converta o array de bytes em uma Image
                    byte[] imagemBytes = cli.getImagem();
                    Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

                    // Definir largura e altura desejadas
                    imageViewSegundo.setFitWidth(128); // Largura desejada
                    imageViewSegundo.setFitHeight(99); // Altura desejada
                    // Defina a imagem no ImageView
                    imageViewSegundo.setImage(imagem);
                }
            }
        }
    }

//    public void pegarLinhaSelecionada(Cliente cli) {
//        if (cli != null) {
//            if (txtNomePrimeiroCliente.getText().equals("")) {
//
//                txtNomePrimeiroCliente.setText(cli.getNome());
//                txtCodigoPrimeiroCliente.setText(cli.getCodigo());
//                txtGeneroPrimeiroCliente.setText(cli.getGenero());
//
//                if (cli.getClinteAssociado() != null) {
//                    txtNomePrimeiroAssociado.setText(cli.getClinteAssociado().getNome());
//                    if (cli.getClinteAssociado().getImagem() != null) {
//                        // Converta o array de bytes em uma Image
//                        byte[] imagemBytes = cli.getImagem();
//                        Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
//
//                        // Definir largura e altura desejadas
//                        imageViewPrimeiroAssociado.setFitWidth(79); // Largura desejada
//                        imageViewPrimeiroAssociado.setFitHeight(93); // Altura desejada
//                        // Defina a imagem no ImageView
//                        imageViewPrimeiroAssociado.setImage(imagem);
//                    }
//                }
//                if (cli.getImagem() != null) {
//                    // Converta o array de bytes em uma Image
//                    byte[] imagemBytes = cli.getImagem();
//                    Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
//
//                    // Definir largura e altura desejadas
//                    imageViewPrimeiro.setFitWidth(79); // Largura desejada
//                    imageViewPrimeiro.setFitHeight(93); // Altura desejada
//                    // Defina a imagem no ImageView
//                    imageViewPrimeiro.setImage(imagem);
//                } else {
//                    JOptionPane.showMessageDialog(null, "imagem nao encontrada");
//                }
//            } else if ((txtCodigoPrimeiroCliente.getText().equals(cli.getCodigo()) == false) && (txtGeneroPrimeiroCliente.getText().equals(cli.getGenero())) == false) {
//                txtNomeSegundoCliente.setText(cli.getNome());
//                txtCodigoSegundoCliente.setText(cli.getCodigo());
//                txtGeneroSegundoCliente.setText(cli.getGenero());
//
//                if (cli.getClinteAssociado() != null) {
//                    txtNomeSegundoAssociado.setText(cli.getClinteAssociado().getNome());
//                    if (cli.getClinteAssociado().getImagem() != null) {
//                        // Converta o array de bytes em uma Image
//                        byte[] imagemBytes = cli.getImagem();
//                        Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
//
//                        // Definir largura e altura desejadas
//                        imageViewSegundoAssociado.setFitWidth(79); // Largura desejada
//                        imageViewSegundoAssociado.setFitHeight(93); // Altura desejada
//                        // Defina a imagem no ImageView
//                        imageViewSegundoAssociado.setImage(imagem);
//                    }
//                }
//                if (cli.getImagem() != null) {
//                    // Converta o array de bytes em uma Image
//                    byte[] imagemBytes = cli.getImagem();
//                    Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
//
//                    // Definir largura e altura desejadas
//                    imageViewSegundo.setFitWidth(99); // Largura desejada
//                    imageViewSegundo.setFitHeight(113); // Altura desejada
//                    // Defina a imagem no ImageView
//                    imageViewSegundo.setImage(imagem);
//                }
//            }
//        }
//    }
//    public void listaPesquisa() {
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
//        //expressao Lapda
//        listView.setCellFactory((ListView<Cliente> param) -> new ListCell<Cliente>() {
//            @Override
//            protected void updateItem(Cliente item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    setText(item.getNome());
//                }
//            }
//        });
//
//        gerente.close(); // Não se esqueça de fechar o EntityManager quando terminar
//        fabrica.close(); // E a EntityManagerFactory também
//    }
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

    @FXML
    void listarPesquisa(KeyEvent event) {
        //   listaPesquisa();
        listarPesquisa();
    }

    @FXML
    void AssociarClientes(ActionEvent event) {
        if (cliente1 != null && cliente3 != null && cliente1.getClienteAssociado() == null && cliente3.getClienteAssociado() == null ) {
            cliente1.associarCasal(cliente3);

            dao.Atualizar(classe, cliente1.getId(), cliente1);
            dao.Atualizar(classe, cliente3.getId(), cliente3);
            notifica.notificacaoSucesso("Clentes associado com sucesso!!");
           // JOptionPane.showMessageDialog(null, "Clentes associado com sucesso!!");
            limparCampos(event);
        } else {
            notifica.notificacaoFalha("Erro, um dos clientes te um associado!!");
            //JOptionPane.showMessageDialog(null, "Erro, um dos clientes te um associado!!");
        }
    }

    @FXML
    void desassociarClientes(ActionEvent event) {
        if (cliente1 != null && cliente2 != null) {
            cliente1.desassociarCasal(cliente2);
            dao.Atualizar(classe, cliente1.getId(), cliente1);
            dao.Atualizar(classe, cliente2.getId(), cliente2);
            limparCampos(event);
            System.out.println(cliente1.toString());
            System.out.println(cliente2.toString());
            notifica.notificacaoSucesso("Cliente "+cliente1.getNome()+"Dessasociado de "+cliente2.getNome());
          //  JOptionPane.showMessageDialog(null, "Cliente "+cliente1.getNome()+"Dessasociado de "+cliente2.getNome());
            limparCampos( event);
        } else {
            notifica.notificacaoFalha("Erro, Clientes "+cliente1.getNome() +" sem associado!!");
            //JOptionPane.showMessageDialog(null, "Erro, Clientes "+cliente1.getNome() +" sem associado!!");
        }
    }

    @FXML
    void limparCampos(ActionEvent event) {
         //new Tela_Menu_Func_Controller().plano_Associa(event);
        txtCodigoPrimeiroCliente.setText("");
        txtNomePrimeiroCliente.setText("");
        txtGeneroPrimeiroCliente.setText("");
        txtNomePrimeiroAssociado.setText("");

        txtCodigoSegundoCliente.setText("");
        txtNomeSegundoCliente.setText("");
        txtGeneroSegundoCliente.setText("");
        txtNomeSegundoAssociado.setText("");
        txtPesquisa.setText("");

        Image imageLimpar = new Image("/img/adicionar-usuario.png");
        imageViewPrimeiro.setImage(imageLimpar);
        imageViewPrimeiroAssociado.setImage(imageLimpar);
        imageViewSegundo.setImage(imageLimpar);
        imageViewSegundoAssociado.setImage(imageLimpar);

        imageViewPrimeiro.setFitWidth(128); // Largura desejada
        imageViewPrimeiro.setFitHeight(99); // Altura desejada

        imageViewSegundo.setFitWidth(128); // Largura desejada
        imageViewSegundo.setFitHeight(99); // Altura desejada

        imageViewSegundoAssociado.setFitWidth(128); // Largura desejada
        imageViewSegundoAssociado.setFitHeight(99); // Altura desejada

        imageViewPrimeiroAssociado.setFitWidth(128); // Largura desejada
        imageViewPrimeiroAssociado.setFitHeight(99); // Altura desejada
      
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada((Cliente) newValue)
        );
    }
    
}
