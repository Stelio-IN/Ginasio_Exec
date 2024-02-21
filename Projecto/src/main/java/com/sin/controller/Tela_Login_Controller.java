/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//import javax.swing.JOptionPane;
import com.sin.model.Administrador;
import com.sin.model.Cliente;
import com.sin.model.Funcionario;
import com.sin.model.Pessoa;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Login_Controller implements Initializable {

    @FXML
    private TextField txtEmail;

    @FXML
    private Button botaoLogin;

//    @FXML
//    private TextField txtPassword;
    @FXML
    private AnchorPane PainelTelaLogin;

    private BooleanProperty emailValido = new SimpleBooleanProperty(false);
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    private ImageView visualizarPassword;

    @FXML
    private Label atencao;

    @FXML
    private ImageView NaovisualizarPassword;

    @FXML
    private Text passwordRequisitos;

    private Scene cena;

    @FXML
    private Label lblDadosIncorrectos;

    @FXML
    private TextField txtAuxPass;

    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnPassword;
    private boolean isMouseOver = false;

    Notificacao notifica = new Notificacao();

    String pegarPass() {
        String pass = txtPassword.getText();
        return pass;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPassword.setOnMouseEntered(event -> {
            isMouseOver = true;
            txtAuxPass.setVisible(true);
            txtAuxPass.textProperty().bind(Bindings.concat(txtPassword.getText()));
            txtPassword.setVisible(false);
            NaovisualizarPassword.setVisible(true);
            visualizarPassword.setVisible(false);
        });

        btnPassword.setOnMouseExited(event -> {
            isMouseOver = false;
            txtPassword.setVisible(true);
            txtAuxPass.setVisible(false);
            txtPassword.setPromptText(txtPassword.getText());
            NaovisualizarPassword.setVisible(false);
            visualizarPassword.setVisible(true);
        });
        lblDadosIncorrectos.setVisible(false);

        // Configurar um ouvinte para verificar o conteúdo do TextField enquanto o usuário digita
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validarEmail(newValue) || validarCodigo(newValue)) {
                txtEmail.getStyleClass().clear();
                txtEmail.getStyleClass().add("txtfield_confirmado");
                emailValido.set(true);
            } else {
                txtEmail.getStyleClass().clear();
                txtEmail.getStyleClass().add("txtfield_nao_confirmado");
                emailValido.set(false);
            }
        });

        // PASSWORD
        txtPassword.textProperty().addListener((observable, oldvalue, pass) -> {
            if (validarPassword(pass)) {
                txtPassword.getStyleClass().clear();
                txtPassword.getStyleClass().add("txtfield_confirmado");
                emailValido.set(true);
            } else {
                txtPassword.getStyleClass().clear();
                txtPassword.getStyleClass().add("txtfield_nao_confirmado");
                emailValido.set(false);
//                 atencao.setVisible(true);
//                passwordRequisitos.setVisible(true);

            }
        });

        // Vincular o botão a propriedade emailValido para habilitar/desabilitar
        botaoLogin.disableProperty().bind(Bindings.not(emailValido));

        // Configurar um evento para o botão de login
        //botaoLogin.setOnAction(this::btnLogar);
        // Visibilidade iniciais
        atencao.setVisible(false);
        passwordRequisitos.setVisible(false);
        NaovisualizarPassword.setVisible(false);
        visualizarPassword.setVisible(true);

    }

    @FXML
    void close(ActionEvent event) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("FECHAR");
        alerta.setHeaderText("Quer realment Fechar");
        alerta.setContentText("Deseja salvar antes de Fechar");
        if (alerta.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) PainelTelaLogin.getScene().getWindow();
            System.out.println("Exit exito");
            stage.close();
        }
    }

//    @FXML
//    public void Tela_Principal(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("/view/Tela_Menu_Admin.fxml"));
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
    /* @FXML
    public void Tela_de_Entrada(ActionEvent event, String caminho) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(caminho));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/
    public void Tela_de_Entrada(ActionEvent event, String caminho, Pessoa pessoa) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
        Parent root = loader.load();

        if (pessoa instanceof Administrador) {
            Tela_Menu_Admin_Controller controller = loader.getController();
            controller.setPessoa(pessoa);
        } else if (pessoa instanceof Funcionario) {
            Tela_Menu_Func_Controller controller = loader.getController();
            controller.setPessoa(pessoa);
        } else if (pessoa instanceof Cliente) {
            Tela_Menu_Cliente_Controller controller = loader.getController();
            controller.setPessoaCliente(pessoa);
        }
//        // Acessa o controlador da próxima tela
//        Tela_Menu_Admin_Controller controller = loader.getController();
//
//        // Passa os dados da pessoa para o controlador
//        controller.setPessoa(pessoa);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /*
    //Usando notificacoes
    @FXML
    void btnLogar(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        GenericDAO bb = new GenericDAO();

        Pessoa pessoa = (Pessoa) bb.logarEmailOuCodigo(txtEmail.getText());

        if (pessoa != null && pessoa.getPassword().equals(txtPassword.getText())) {
            
            notifica.notificacaoSucesso();

            if (pessoa instanceof Administrador) {
                Tela_de_Entrada(event, "/view/Tela_Menu_Admin.fxml", pessoa);
            } else if (pessoa instanceof Cliente) {
                Tela_de_Entrada(event, "/view/Tela_Menu_Cliente.fxml", pessoa);
            } else if (pessoa instanceof Funcionario) {
                Tela_de_Entrada(event, "/view/Tela_Menu_Func.fxml", pessoa);
            }

        } else {
               notifica.notificacaoWarning();
        }
    }
    */
   
    @FXML
    void btnLogar(ActionEvent event) {
        String email = txtEmail.getText();
        GenericDAO bb = new GenericDAO();

        Pessoa pessoa = (Pessoa) bb.logarEmailOuCodigo(txtEmail.getText());

        if (pessoa != null && pessoa.getPassword().equals(txtPassword.getText())) {
            try {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Login");
                alerta.setHeaderText("Login efetuado com sucesso!!!!");

                if (alerta.showAndWait().get() == ButtonType.OK) {
                    if (pessoa instanceof Administrador) {
                        Tela_de_Entrada(event, "/view/Tela_Menu_Admin.fxml", pessoa);
                    } else if (pessoa instanceof Cliente) {
                        Tela_de_Entrada(event, "/view/Tela_Menu_Cliente.fxml", pessoa);
                    } else if (pessoa instanceof Funcionario) {
                        Tela_de_Entrada(event, "/view/Tela_Menu_Func.fxml", pessoa);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Trate ou registre erros adequadamente
            }

        } else {
            lblDadosIncorrectos.setVisible(true);
            atencao.setVisible(true);
            passwordRequisitos.setVisible(true);

        }
    }
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validarPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validarCodigo(String input) {
        // Cria um padrão de expressão regular para 4 letras seguidas de 8 números
        String padrao = "^[A-Za-z]{4}\\d{8}$";
        // Compila o padrão em um objeto Pattern
        Pattern pattern = Pattern.compile(padrao);
        // Cria um objeto Matcher para a entrada fornecida
        Matcher matcher = pattern.matcher(input);
        // Verifica se a entrada corresponde ao padrão
        return matcher.matches();
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
