/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.sin.model.Cliente;
import com.sin.model.Equipamento;
import com.sin.model.Ficha_Inscricao;
import com.sin.model.Funcionario;
import com.sin.model.Instrutor;
import com.sin.model.Pessoa;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Menu_Func_Controller implements Initializable {

    @FXML
    private ListView<Ficha_Inscricao> ListaAtividades;

    @FXML
    private AnchorPane panelGeral;
    @FXML
    private BorderPane borderPane = new BorderPane();

    @FXML
    private Label lblDashboard;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnGestaoCliente;

    @FXML
    private Button btnInstrutor;

    @FXML
    private Button btnMaquinas;

    @FXML
    private Button btnPacotes;

    @FXML
    private AreaChart<String, Number> GraficoArea;

    @FXML
    private Label txtQuantidadePlanoCasal;

    @FXML
    private Label txtQuantidadePlanoEspecial;

    @FXML
    private Label txtQuantidadePlanoEstudante;

    @FXML
    private Label txtQuantidadePlanoNormal;

    private int quantidade_plano_id_1 = 0;
    private int quantidade_plano_id_2 = 0;
    private int quantidade_plano_id_3 = 0;
    private int quantidade_plano_id_4 = 0;

    private void contabilizar() {
        quantidade_plano_id_1 = 0;
        quantidade_plano_id_2 = 0;
        quantidade_plano_id_3 = 0;
        quantidade_plano_id_4 = 0;
        GenericDAO dao = new GenericDAO();
        Class<Equipamento> classe = Equipamento.class;
        List<Pessoa> pessoas = dao.listarTodosParaRelatorio(Pessoa.class);
        if (pessoas != null) {
            for (int i = 0; i < pessoas.size(); i++) {
                Pessoa pessoa = pessoas.get(i);
                if (pessoa instanceof Cliente cliente) {
                    /*
                    Contabilizar clientes e sei la quantos 
                     */
                    if (cliente.getPlanoCliente() != null) {
                        if (cliente.getPlanoCliente().getPlano().getId() == 1) {
                            quantidade_plano_id_1++;
                        }
                        if (cliente.getPlanoCliente().getPlano().getId() == 2) {
                            quantidade_plano_id_2++;
                        }
                        if (cliente.getPlanoCliente().getPlano().getId() == 3) {
                            quantidade_plano_id_3++;
                        }
                        if (cliente.getPlanoCliente().getPlano().getId() == 4) {
                            quantidade_plano_id_4++;
                        }
                    }

                }
            }
            txtQuantidadePlanoEspecial.setText(String.valueOf(quantidade_plano_id_4));
            txtQuantidadePlanoCasal.setText(String.valueOf(quantidade_plano_id_2));
            txtQuantidadePlanoEstudante.setText(String.valueOf(quantidade_plano_id_1));
            txtQuantidadePlanoNormal.setText(String.valueOf(quantidade_plano_id_3));
        }
    }

    public void carregarTela(String tela, Pessoa pessoa) {
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(tela + ".fxml"));
            root = loader.load();

            // Verifique se a tela carregada possui um controlador associado
            if (loader.getController() instanceof Tela_Func_Avalicoes_Clientes_Controller) {
                Tela_Func_Avalicoes_Clientes_Controller controller = loader.getController();

                // Passe a pessoa para o controlador da tela
                controller.setPessoa(pessoa);
            }
        } catch (IOException ex) {
            System.out.println("erro");
          //  java.util.logging.Logger.getLogger(Tela_Menu_Admin_Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        borderPane.setRight(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contabilizar();

        btnDashboard.setStyle("-fx-background-color: #00ff001e;");
        btnPacotes.setStyle("-fx-background-color: transparent;");
        btnInstrutor.setStyle("-fx-background-color: transparent;");
        btnMaquinas.setStyle("-fx-background-color: transparent;");
        btnGestaoCliente.setStyle("-fx-background-color: transparent;");
        btnClientes.setStyle("-fx-background-color: transparent;");

        //           // Inicialize o gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Faturamento");
        GraficoArea.getData().add(series);

        // Crie um Timeline para atualizar automaticamente o gráfico a cada 5 segundos
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            // Limpe os dados existentes no gráfico
            series.getData().clear();

            // Adicione novos dados fictícios à série
            Random random = new Random();
            for (int i = 1; i <= 10; i++) {
                series.getData().add(new XYChart.Data<>("Mês " + i, random.nextInt(100)));
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private Label txtNomeFunc;
    private Pessoa pessoa;

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        txtNomeFunc.setText(pessoa.getNome());
        System.out.println(pessoa.getNome());
    }

//      @FXML
//    void Tela_Realizar_Testes(ActionEvent event) {
//        carregarTela("/view/Tela_Func_Avalicoes_Clientes");
//    }
    @FXML
    void abrirTelaRegistro(ActionEvent event) {
        carregarTela("/view/Tela_Cadastrar_Cliente_1", pessoa);

        btnClientes.setStyle("-fx-background-color: #00ff001e;");
        btnPacotes.setStyle("-fx-background-color: transparent;");
        btnInstrutor.setStyle("-fx-background-color: transparent;");
        btnMaquinas.setStyle("-fx-background-color: transparent;");
        btnGestaoCliente.setStyle("-fx-background-color: transparent;");
        btnDashboard.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void Tela_Realizar_Testes(ActionEvent event) {
        // Chame o método carregarTela passando a pessoa
        carregarTela("/view/Tela_Func_Avalicoes_Clientes", pessoa);
    }

    @FXML
    void Tela_Func_AddPlano(ActionEvent event) {
        carregarTela("/view/Tela_Func_AddPlano", pessoa);
    }

    @FXML
    void Tela_Func_Associar_Cliente(ActionEvent event) {
        carregarTela("/view/Tela_Func_Associar_Clientes", pessoa);
    }

    @FXML
    void Tela_Func_Pagamento(ActionEvent event) {
        carregarTela("/view/Tela_Func_Pagamentos_", pessoa);
    }

    @FXML
    void tela_Admin_Menu_Clientes(ActionEvent event) {
        carregarTela("/view/Tela_Cadastrar_Cliente_1", pessoa);

        btnClientes.setStyle("-fx-background-color: #00ff001e;");
        btnPacotes.setStyle("-fx-background-color: transparent;");
        btnInstrutor.setStyle("-fx-background-color: transparent;");
        btnMaquinas.setStyle("-fx-background-color: transparent;");
        btnGestaoCliente.setStyle("-fx-background-color: transparent;");
        btnDashboard.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void Tela_Geral(ActionEvent event) {
        borderPane.setRight(panelGeral);

        btnDashboard.setStyle("-fx-background-color: #00ff001e;");
        btnPacotes.setStyle("-fx-background-color: transparent;");
        btnInstrutor.setStyle("-fx-background-color: transparent;");
        btnMaquinas.setStyle("-fx-background-color: transparent;");
        btnGestaoCliente.setStyle("-fx-background-color: transparent;");
        btnClientes.setStyle("-fx-background-color: transparent;");

    }

    @FXML
    public void plano_Associa(ActionEvent event) {
        carregarTela("/view/Tela_Func_PlanoAss", pessoa);
        btnPacotes.setStyle("-fx-background-color: #00ff001e;");
        btnDashboard.setStyle("-fx-background-color: transparent;");
        btnInstrutor.setStyle("-fx-background-color: transparent;");
        btnMaquinas.setStyle("-fx-background-color: transparent;");
        btnGestaoCliente.setStyle("-fx-background-color: transparent;");
        btnClientes.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void Tela_Instrutores(ActionEvent event) {
        carregarTela("/view/Tela_Func_Instrutores", pessoa);
        btnInstrutor.setStyle("-fx-background-color: #00ff001e;");
        btnPacotes.setStyle("-fx-background-color: transparent;");
        btnDashboard.setStyle("-fx-background-color: transparent;");
        btnMaquinas.setStyle("-fx-background-color: transparent;");
        btnGestaoCliente.setStyle("-fx-background-color: transparent;");
        btnClientes.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void Tela_Maquinas(ActionEvent event) {
        carregarTela("/view/Tela_Func_Maquinas", pessoa);
        btnMaquinas.setStyle("-fx-background-color: #00ff001e;");
        btnPacotes.setStyle("-fx-background-color: transparent;");
        btnInstrutor.setStyle("-fx-background-color: transparent;");
        btnDashboard.setStyle("-fx-background-color: transparent;");
        btnGestaoCliente.setStyle("-fx-background-color: transparent;");
        btnClientes.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void Tela_gestao_cliente(ActionEvent event) {
        carregarTela("/view/Tela_Func_Gestao_Cliente", pessoa);
        btnGestaoCliente.setStyle("-fx-background-color: #00ff001e;");
        btnPacotes.setStyle("-fx-background-color: transparent;");
        btnInstrutor.setStyle("-fx-background-color: transparent;");
        btnMaquinas.setStyle("-fx-background-color: transparent;");
        btnDashboard.setStyle("-fx-background-color: transparent;");
        btnClientes.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void BtnSair(ActionEvent event) throws IOException {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("LogOut");
        alerta.setHeaderText("Quer realmente terminar seccao");
        alerta.setContentText("Fazer backup antes de sair!");
        if (alerta.showAndWait().get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getResource("/view/Tela_Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    //////////////////////////////////////////////////
    public void carregarNotificacoesDeCadastro() {
        // Suponha que você tenha uma lista de notificações de cadastro
        List<Ficha_Inscricao> notificacoes = obterNotificacoesDeCadastro();

        // Percorra a lista de notificações e crie Labels para exibi-las no AnchorPane
        double y = 10; // Posição inicial
        for (Ficha_Inscricao notificacao : notificacoes) {
            Label label = new Label();
            label.setText(notificacao.getFuncionario());
            label.setLayoutX(10); // Posição X
            label.setLayoutY(y); // Posição Y
            y += 20; // Incremento para a próxima notificação
            //ListaAtividades.getChildren().add(label);
        }
    }

    // Método fictício para obter notificações de cadastro (substitua pelo seu serviço ou fonte de dados real)
    private List<Ficha_Inscricao> obterNotificacoesDeCadastro() {
        // Simule a obtenção de notificações da sua fonte de dados (banco de dados, arquivo, etc.)
        // Retorna uma lista de notificações fictícias para este exemplo
        List<Ficha_Inscricao> notificacoes = new ArrayList<>();
        notificacoes.add(new Ficha_Inscricao());
        // Adicione mais notificações conforme necessário
        return notificacoes;
    }
}
