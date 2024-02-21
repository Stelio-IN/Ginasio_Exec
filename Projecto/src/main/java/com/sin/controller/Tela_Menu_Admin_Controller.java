/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.sin.model.Cliente;
import com.sin.model.Equipamento;
import com.sin.model.Funcionario;
import com.sin.model.Instrutor;
import com.sin.model.Pessoa;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import com.sin.model.Pagamento_Mensalidade;
import com.sin.model.PlanoCliente;

/**
 * FXML Controller class
 *
 * @author steli
 */
public class Tela_Menu_Admin_Controller implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane panelGeral;

    @FXML
    private AreaChart<String, Number> GraficoArea;

    @FXML
    void tela_Admin_Menu_Clientes(ActionEvent event) {
        carregarTela("/view/Tela_Admin_Menu_Clientes");
    }

    @FXML
    void tela_Admin_Menu_Funcionarios(ActionEvent event) {
        carregarTela("/view/Tela_Admin_Menu_Funcionarios");
    }

    @FXML
    void tela_Admin_Menu_Geral(ActionEvent event) {
        contabilizar();
        borderPane.setRight(panelGeral);
    }

    @FXML
    void tela_Admin_Menu_Instrutores(ActionEvent event) {
        carregarTela("/view/Tela_Admin_Menu_Instrutores");
    }

    @FXML
    void tela_Admin_Menu_Maquinas(ActionEvent event) {
        carregarTela("/view/Tela_Admin_Menu_Maquinas");
    }

    private void carregarTela(String tela) {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(tela + ".fxml"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Tela_Menu_Admin_Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        borderPane.setRight(root);
    }
    ///////////////////////////////////////////

    @FXML
    private ImageView imageViewAdmin;
    @FXML
    private TextField txtNomeAdmin;

    private Pessoa pessoa;

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        txtNomeAdmin.setEditable(false);
        txtNomeAdmin.setAlignment(javafx.geometry.Pos.CENTER);
        txtNomeAdmin.setText(pessoa.getNome());
        if (pessoa.getImagem() != null) {
            // Converta o array de bytes em uma Image
            byte[] imagemBytes = pessoa.getImagem();
            Image imagem = new Image(new ByteArrayInputStream(imagemBytes));

            // Defina a imagem no ImageView
            imageViewAdmin.setImage(imagem);

            // Definir largura e altura desejadas
            imageViewAdmin.setFitWidth(79); // Largura desejada
            imageViewAdmin.setFitHeight(93); // Altura desejada

            // Preservar a proporção da imagem enquanto ajusta as dimensões
            imageViewAdmin.setPreserveRatio(true);
        }
    }

    @FXML
    void logout(ActionEvent event) throws IOException {

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

    @FXML
    private AnchorPane painelPesquisaGoogle;

    @FXML
    private Label txtQuanClientes;

    @FXML
    private Label txtQuanFuncionarios;

    @FXML
    private Label txtQuanInstrutores;
    @FXML
    private Label txtQuanMaquinas;
    @FXML
    private Label txtQuantidadePlanoCasal;

    @FXML
    private Label txtQuantidadePlanoEspecial;

    @FXML
    private Label txtQuantidadePlanoEstudante;

    @FXML
    private Label txtQuantidadePlanoNormal;

    @FXML
    private Label txtPlanosActivos;

    @FXML
    private Label txtPlanosInactivos;

    @FXML
    private Label txtFaturamento;

    private int faturamento = 0;
    private int quandidade_Clientes = 0;
    private int quandidade_Funcionarios = 0;
    private int quandidade_Instrutores = 0;
    private int quandidade_Maquinas = 0;

    private int quantidade_plano_id_1 = 0;
    private int quantidade_plano_id_2 = 0;
    private int quantidade_plano_id_3 = 0;
    private int quantidade_plano_id_4 = 0;
    private int quandidade_Homens = 0;
    private int quandidade_Mulheres = 0;

    private void contabilizar() {
        quandidade_Instrutores = 0;
        quandidade_Funcionarios = 0;
        quandidade_Clientes = 0;
        quantidade_plano_id_1 = 0;
        quantidade_plano_id_2 = 0;
        quantidade_plano_id_3 = 0;
        quantidade_plano_id_4 = 0;
        quandidade_Mulheres = 0;
        quandidade_Homens = 0;
        int ativos = 0;
        int inativos = 0;

        GenericDAO dao = new GenericDAO();
        Class<Equipamento> classe = Equipamento.class;
        List<Pessoa> pessoas = dao.listarTodosParaRelatorio(Pessoa.class);
        quandidade_Maquinas = dao.contar_Quantidade_Base(classe);
        if (pessoas != null) {
            for (int i = 0; i < pessoas.size(); i++) {
                Pessoa pessoa = pessoas.get(i);
                if (pessoa instanceof Cliente cliente) {
                    quandidade_Clientes++;

                    /*
                       * Comtabilizar homens e mulheres
                     */
                    if (cliente.getGenero().equals("Masculino")) {
                        quandidade_Homens++;
                        System.out.println("Cliente " + cliente.getNome() + " é masculino.");
                    } else {
                        quandidade_Mulheres++;
                        System.out.println("Cliente " + cliente.getNome() + " é feminino.");
                    }
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
                        /*
                            verificar quantidade de planos ativos e inativos
                         */
                        if (cliente.getPlanoCliente().isStatus()) {
                            ativos++;
                        } else {
                            inativos++;
                        }
                    }

                } else if (pessoa instanceof Instrutor) {
                    quandidade_Instrutores++;
                } else if (pessoa instanceof Funcionario) {
                    quandidade_Funcionarios++;
                }

            }
        } else {
            // Trate o caso em que a lista está vazia ou ocorreu um erro
            quandidade_Clientes = 0;
            quandidade_Funcionarios = 0;
            quandidade_Instrutores = 0;
        }
        txtQuanMaquinas.setText(String.valueOf(quandidade_Maquinas));
        txtQuanClientes.setText(String.valueOf(quandidade_Clientes));
        txtQuanFuncionarios.setText(String.valueOf(quandidade_Funcionarios));
        txtQuanInstrutores.setText(String.valueOf(quandidade_Instrutores));

        txtQuantidadePlanoEspecial.setText(String.valueOf(quantidade_plano_id_4));
        txtQuantidadePlanoCasal.setText(String.valueOf(quantidade_plano_id_2));
        txtQuantidadePlanoEstudante.setText(String.valueOf(quantidade_plano_id_1));
        txtQuantidadePlanoNormal.setText(String.valueOf(quantidade_plano_id_3));

        txtPlanosActivos.setText(String.valueOf(ativos));
        txtPlanosInactivos.setText(String.valueOf(inativos));

    }

    /*
        Metodo para verificar quantidade de planos ativos e inativos
     */
 /*
    private void planos_ativos_inativos() {
        GenericDAO dao = new GenericDAO();
        Class<PlanoCliente> classe = PlanoCliente.class;
        List<PlanoCliente> planos = (List<PlanoCliente>) dao.listar(classe);
        int ativos = 0;
        int inativos = 0;
        if (planos != null) {
            for (int i = 0; i < planos.size(); i++) {
                if (planos.get(i).getCliente() != null) {
                    if (planos.get(i).isStatus()) {
                        ativos++;
                    } else {
                        inativos++;
                    }
                }
            }
        }
        txtPlanosActivos.setText(String.valueOf(ativos));
        txtPlanosInactivos.setText(String.valueOf(inativos));
    }
     */

    private void preencher_Pagamento() {
        GenericDAO dao = new GenericDAO();
        Class<Pagamento_Mensalidade> classe = Pagamento_Mensalidade.class;
        List<Pagamento_Mensalidade> pagamentos = (List<Pagamento_Mensalidade>) dao.listar(classe);
        
        faturamento =0;
        
        if (pagamentos!= null)
            for (int i = 0; i < pagamentos.size(); i++) 
                faturamento += pagamentos.get(i).getValor();
        
        txtFaturamento.setText(String.valueOf(faturamento) + " MT");
               
    }

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // quantidadeDeInscritos();
        contabilizar();
        preencher_Pagamento();
        // carregarDadosDoBanco();
        //  planos_ativos_inativos();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Homens", quandidade_Homens),
                new PieChart.Data("Mulheres", quandidade_Mulheres)
        );

        // Vincular os nomes e valores dos dados aos rótulos do gráfico
        pieChartData.forEach(data -> {
            data.nameProperty().bind(
                    Bindings.concat(data.getName(), " Total: ", data.pieValueProperty())
            );
        });

        // Configure o PieChart
        pieChart.setData(pieChartData);
        //chamando o grafico de barra
        GraficoBarra();
        //////////////////////////////////////////////////////////////////////////////////////////   
//           // Inicialize o gráfico
        Series<String, Number> series = new Series<>();
        series.setName("Dados");
        GraficoArea.getData().add(series);

        // Crie um Timeline para atualizar automaticamente o gráfico a cada 5 segundos
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
            // Limpe os dados existentes no gráfico
            series.getData().clear();

            // Adicione novos dados fictícios à série
            Random random = new Random();
            for (int i = 1; i <= 10; i++) {
                series.getData().add(new Data<>("Mês " + i, random.nextInt(100)));
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                pegarLinhaSelecionada(newValue);
            }
        });

        txtPesquisa.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // Se o foco for perdido (newValue == false)
                txtPesquisa.clear(); // Limpe o conteúdo do TextField
                listView.setVisible(false);
                painelPesquisaGoogle.setVisible(false);
                scrollPanePesquisa.setVisible(false);
            }
        });
        listView.setVisible(false);
        painelPesquisaGoogle.setVisible(false);
        scrollPanePesquisa.setVisible(false);

    }

//      @FXML
//    private HBox graficoContainer;
    //private BarChart<?, ?> barChart;
    // Crie os eixos
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    // Crie o gráfico de barras
    @FXML
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    void GraficoBarra() {
        barChart.setTitle("Clientes Idades");

        // Criar uma série de dados
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Idades");

        series.getData().add(new XYChart.Data<>("17-23", 15));
        series.getData().add(new XYChart.Data<>("24-40", 30));
        series.getData().add(new XYChart.Data<>("41-09", 45));
        // Adicione mais dados conforme necessário

        // Adicione a série ao gráfico
        barChart.getData().add(series);
    }

    @FXML
    private TextField txtPesquisa;

    @FXML
    private ListView<Pessoa> listView;
    @FXML
    private ScrollPane scrollPanePesquisa;

    @FXML
    void listarPesquisa(KeyEvent event) {
        listaPesquisa();
    }

    public void listaPesquisa() {
        EntityManagerFactory fabrica;
        EntityManager gerente;
        fabrica = Persistence.createEntityManagerFactory("SystemPU");
        gerente = fabrica.createEntityManager();

        ObservableList<Pessoa> items = FXCollections.observableArrayList(); // Crie uma ObservableList de Pessoa

        TypedQuery<Pessoa> query = gerente.createQuery(
                "SELECT p FROM Pessoa p WHERE p.nome LIKE :nome AND NOT TYPE(p) = model.Administrador",
                Pessoa.class
        );
        query.setParameter("nome", "%" + txtPesquisa.getText() + "%"); // O operador % é usado para consultas "LIKE"
        List<Pessoa> resultados = query.getResultList();
        listView.setVisible(true);
        painelPesquisaGoogle.setVisible(true);
        scrollPanePesquisa.setVisible(true);

        items.addAll(resultados); // Adicione objetos Pessoa à listaPesquisa

        listView.setItems(items); // Defina a ObservableList de objetos Pessoa no ListView

        // Defina a célula personalizada para mostrar apenas o nome na listaPesquisa
        listView.setCellFactory((ListView<Pessoa> param) -> new ListCell<Pessoa>() {
            @Override
            protected void updateItem(Pessoa item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });

        gerente.close(); // Não se esqueça de fechar o EntityManager quando terminar
        fabrica.close(); // E a EntityManagerFactory também
    }

    /*
    private void carregarDadosDoBanco() {
        GenericDAO dao = new GenericDAO();
        List<Pessoa> pessoas = dao.listarTodosParaRelatorio(Pessoa.class
        );
        if (pessoas != null) {
            for (int i = 0; i < pessoas.size(); i++) {
                Pessoa pessoa = pessoas.get(i);
                if (pessoa instanceof Cliente) {

                    if (pessoa.getGenero().equals("Masculino")) {
                        quandidade_Homens++;
                    } else if (pessoa.getGenero().equals("Feminino")) {
                        quandidade_Mulheres++;
                    }
                }
            }
        }
    }
     */
    @FXML
    void Tela_Admin_Planos_Associacao(ActionEvent event) {
        carregarTela("/view/Tela_Admin_Menu_Plano_Associacao");
    }

    @FXML
    void Tela_Cadastrar_Admin(ActionEvent event) {
        carregarTela("/view/Tela_Admin_Registrar");
    }

    @FXML
    void Tela_Atualizar_Admin(ActionEvent event) {
        carregarTela("/view/Tela_Admin_Registrar");

    }

    public void pegarLinhaSelecionada(Pessoa pessoa) {
        try {
            if (pessoa instanceof Cliente) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Tela_Admin_Menu_Clientes.fxml"));
                Parent root = loader.load();
                Tela_Admin_Menu_Clientes_Controller controller = loader.getController();
                controller.setPessoaAdmin(pessoa);
                borderPane.setRight(root);
            } else if (pessoa instanceof Funcionario) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Tela_Admin_Menu_Funcionarios.fxml"));
                Parent root = loader.load();
                Tela_Admin_Menu_Funcionarios_Controller controller = loader.getController();
                controller.setPessoaAdmin(pessoa);
                borderPane.setRight(root);
            } else if (pessoa instanceof Instrutor) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Tela_Admin_Menu_Instrutores.fxml"));
                Parent root = loader.load();
                Tela_Admin_Menu_Instrutores_Controller controller = loader.getController();
                controller.setPessoaAdmin(pessoa);
                borderPane.setRight(root);
            } else {
                // Trate o caso em que a instância não corresponda a nenhum dos tipos conhecidos
                // Pode ser apropriado lançar uma exceção ou tomar outra ação, se necessário.
            }
        } catch (IOException ex) {
            // Lida com exceções de carregamento de FXML
            ex.printStackTrace();
        }
    }

}
