/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
//import javax.swing.JOptionPane;
import com.sin.model.Avaliacoes_Fisicas;
import com.sin.model.Cliente;
import com.sin.model.Pagamento_Mensalidade;
import com.sin.model.Pessoa;
import com.sin.model.PlanoCliente;
import com.sin.model.Plano_de_Associacao;

public class Tela_Menu_Cliente_Controller implements Initializable {

    @FXML
    private Button btnLogOut;

    @FXML
    private TableColumn<PlanoCliente, String> colunaNomePlano;

    @FXML
    private DatePicker dataPickerInicio;

    @FXML
    private LineChart<String, Number> grafico;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageViewAssociado;

    @FXML
    private Hyperlink linkTelaAddPlano;

    @FXML
    private TableView<Plano_de_Associacao> tabelaPlano;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtCodigoClienteAssociado;

    @FXML
    private TextField txtCodigoClientePrincipal;

    @FXML
    private TextField txtDataFim;

    @FXML
    private TextField txtDataInicio;

    @FXML
    private TextField txtDuracaoPlano;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNomeClienteAssociado;

    @FXML
    private TextField txtNomeClientePrincipal;

    @FXML
    private TextField txtNomePlano;

    @FXML
    private TextField txtNumeroDeAvaliacoes;

    @FXML
    private TextField txtObjectivo;

    @FXML
    private TextField txtObjectivoClienteAssociado;

    @FXML
    private TextField txtObjectivoClientePrincipal;

    @FXML
    private TextField txtPagamento;

    @FXML
    private TextField txtPlanoAssociacao;

    @FXML
    private TextField txtPrecoPlano;

    @FXML
    private TextField txtQuantidadeDias;

    @FXML
    private TextField txtStatusClienteAssociado;

    @FXML
    private TextField txtStatusClientePrincipal;

    private XYChart.Series series;

    Cliente clienteNovosDados = new Cliente();
    Cliente clienteAssociadoNovosDados = new Cliente();
    PlanoCliente planoSelecionado = new PlanoCliente();
    Plano_de_Associacao planoAssociacaoAtualizar = new Plano_de_Associacao();

    @FXML
    void LimparCampos(ActionEvent event) {

        txtNomePlano.setText("");
        txtDuracaoPlano.setText("");

        txtPrecoPlano.setText("");

        planoSelecionado = null;
        planoAssociacaoAtualizar = null;
    }

    @FXML
    void RemoverPlano(ActionEvent event) {
        GenericDAO dao = new GenericDAO();
        Class<Cliente> classe = Cliente.class;
        if (clienteNovosDados != null && clienteNovosDados.getPlanoCliente() != null) {
            if (clienteNovosDados.getPlanoCliente().isStatus() == true) {
                clienteNovosDados.getPlanoCliente().setStatus(false);
                dao.Atualizar(classe, clienteNovosDados.getId(), clienteNovosDados);
              //  JOptionPane.showMessageDialog(null, "Plano Removido");
            }
        } else {
          //  JOptionPane.showMessageDialog(null, "Impossivel remover Plano");
        }
    }

    @FXML
    void guardarPlano(ActionEvent event) {
        GenericDAO dao = new GenericDAO();
        double valor = 0;
        Pagamento_Mensalidade pagamento = new Pagamento_Mensalidade();
        Class<Cliente> classeCliente = Cliente.class;
        Class<PlanoCliente> classePlano = PlanoCliente.class;

        if (clienteNovosDados != null && planoSelecionado != null && dataPickerInicio.getValue() != null
                && !txtDuracaoPlano.getText().isEmpty()) {

            LocalDate localDate = dataPickerInicio.getValue();
            ZoneId zoneId = ZoneId.systemDefault();
            Date date = Date.from(localDate.atStartOfDay(zoneId).toInstant());
            planoSelecionado.setCliente(clienteNovosDados);
            planoSelecionado.setDuracao(Integer.parseInt(txtDuracaoPlano.getText()));
            planoSelecionado.setDataInicio(date);
            planoSelecionado.setDuracaoEmMeses(planoSelecionado.getDuracao());
            planoSelecionado.setStatus(false);
            pagamento.setPlanoCliente(planoSelecionado);
            valor = planoSelecionado.getPlano().getPreco() * Integer.valueOf(txtDuracaoPlano.getText());
            pagamento.setSituacao("Pendente");
            pagamento.setValor(valor);

            if (!planoSelecionado.getPlano().getNome().equals("Plano Casal")) {
                if (clienteNovosDados.getPlanoCliente() != null) {
                    if (clienteNovosDados.getPlanoCliente().isStatus() == false) {
                        clienteNovosDados.getPlanoCliente().setPlano(planoAssociacaoAtualizar);
                        dao.Atualizar(classeCliente, clienteNovosDados.getId(), clienteNovosDados);
                        pagamento.setPlanoCliente(clienteNovosDados.getPlanoCliente());
                        pagamento.setSituacao("Pendente");
                        dao.add(pagamento);
                     //   JOptionPane.showMessageDialog(null, "Sucesso ao adicionar plano Total a pagar: " + valor);
                    } else {
                    //    JOptionPane.showMessageDialog(null, "Cliente com plano: " + clienteNovosDados.getPlanoCliente().getPlano().getNome() + "__ Estado: " + clienteNovosDados.getPlanoCliente().isStatus());
                    }
                } else {
                    dao.add(planoSelecionado);
                    dao.add(pagamento);
                    clienteNovosDados.setPlanoCliente(planoSelecionado);
                    dao.Atualizar(classeCliente, clienteNovosDados.getId(), clienteNovosDados);
                   // JOptionPane.showMessageDialog(null, "Sucesso ao adicionar plano Total a pagar: " + valor);
                }
            }

            if (planoSelecionado.getPlano().getNome().equals("Plano Casal")) {
                if (clienteAssociadoNovosDados != null) {
                    if (clienteNovosDados.getPlanoCliente() == null && clienteAssociadoNovosDados.getPlanoCliente() == null) {
                        // Nenhum dos clientes tem planos associados, você pode associá-los ao Plano Casal

                        // Crie um PlanoCliente para o cliente principal
                        PlanoCliente planoClienteNovo = new PlanoCliente();
                        planoClienteNovo.setCliente(clienteNovosDados);
                        planoClienteNovo.setPlano(planoAssociacaoAtualizar);
                        planoClienteNovo.setDuracao(planoSelecionado.getDuracao());
                        planoClienteNovo.setDataInicio(planoSelecionado.getDataInicio());
                        planoClienteNovo.setDuracaoEmMeses(planoSelecionado.getDuracao());
                        planoClienteNovo.setStatus(planoSelecionado.isStatus());

                        // Crie um PlanoCliente para o cliente associado
                        PlanoCliente planoClienteAssociadoNovo = new PlanoCliente();
                        planoClienteAssociadoNovo.setCliente(clienteAssociadoNovosDados);
                        planoClienteAssociadoNovo.setPlano(planoAssociacaoAtualizar);
                        planoClienteAssociadoNovo.setDuracao(planoSelecionado.getDuracao());
                        planoClienteAssociadoNovo.setDataInicio(planoSelecionado.getDataInicio());
                        planoClienteAssociadoNovo.setDuracaoEmMeses(planoSelecionado.getDuracao());
                        planoClienteAssociadoNovo.setStatus(planoSelecionado.isStatus());

                        // Persista ambos os Planos Clientes
                        dao.add(planoClienteNovo);
                        dao.add(planoClienteAssociadoNovo);

                        // Atualize o status do pagamento
                        pagamento.setPlanoCliente(planoClienteNovo);
                        pagamento.setSituacao("Pendente");
                        dao.add(pagamento);

                        // Atualize o status dos clientes
                        clienteNovosDados.setPlanoCliente(planoClienteNovo);
                        clienteAssociadoNovosDados.setPlanoCliente(planoClienteAssociadoNovo);
                        dao.Atualizar(classeCliente, clienteNovosDados.getId(), clienteNovosDados);
                        dao.Atualizar(classeCliente, clienteAssociadoNovosDados.getId(), clienteAssociadoNovosDados);

                     //   JOptionPane.showMessageDialog(null, "Sucesso ao adicionar Plano Casal. Total a pagar: " + valor);
                    }
                    if (clienteNovosDados.getPlanoCliente() == null && clienteAssociadoNovosDados.getPlanoCliente() != null
                            && !clienteAssociadoNovosDados.getPlanoCliente().isStatus()) {
                        // O cliente normal não tem plano associado, mas o cliente associado tem um plano inativo, portanto, você pode associá-los ao Plano Casal

                        clienteNovosDados.setPlanoCliente(planoSelecionado);
                        clienteAssociadoNovosDados.getPlanoCliente().setPlano(planoAssociacaoAtualizar);

                        //Gravar o planoCliente na base antes de gerar o pagamento
                        // Crie um PlanoCliente para o cliente principal
                        PlanoCliente planoClienteNovo = new PlanoCliente();
                        planoClienteNovo.setCliente(clienteNovosDados);
                        planoClienteNovo.setPlano(planoAssociacaoAtualizar);
                        planoClienteNovo.setDuracao(planoSelecionado.getDuracao());
                        planoClienteNovo.setDataInicio(planoSelecionado.getDataInicio());
                        planoClienteNovo.setDuracaoEmMeses(planoSelecionado.getDuracao());
                        planoClienteNovo.setStatus(planoSelecionado.isStatus());

                        dao.add(planoClienteNovo);

                        // Atualize o status do pagamento
                        pagamento.setPlanoCliente(clienteNovosDados.getPlanoCliente());
                        pagamento.setSituacao("Pendente");
                        dao.add(pagamento);

                        // Atualize os clientes e o Plano Cliente do associado
                        dao.Atualizar(classeCliente, clienteNovosDados.getId(), clienteNovosDados);
                        dao.Atualizar(classeCliente, clienteAssociadoNovosDados.getId(), clienteAssociadoNovosDados);

                      //  JOptionPane.showMessageDialog(null, "Sucesso ao adicionar Plano Casal. Total a pagar: " + valor);
                    }
                    if (clienteNovosDados.getPlanoCliente() != null && clienteAssociadoNovosDados.getPlanoCliente() == null
                            && !clienteNovosDados.getPlanoCliente().isStatus()) {
                        // O cliente associado não tem plano associado, mas o cliente normal tem um plano inativo, portanto, você pode associá-los ao Plano Casal
                        clienteNovosDados.getPlanoCliente().setPlano(planoAssociacaoAtualizar);
                        clienteAssociadoNovosDados.setPlanoCliente(planoSelecionado);
                        // Crie um PlanoCliente para o cliente associado
                        PlanoCliente planoClienteAssociadoNovo = new PlanoCliente();
                        planoClienteAssociadoNovo.setCliente(clienteAssociadoNovosDados);
                        planoClienteAssociadoNovo.setPlano(planoAssociacaoAtualizar);
                        planoClienteAssociadoNovo.setDuracao(planoSelecionado.getDuracao());
                        planoClienteAssociadoNovo.setDataInicio(planoSelecionado.getDataInicio());
                        planoClienteAssociadoNovo.setDuracaoEmMeses(planoSelecionado.getDuracao());
                        planoClienteAssociadoNovo.setStatus(planoSelecionado.isStatus());

                        dao.add(planoClienteAssociadoNovo);

                        // Atualize o status do pagamento
                        pagamento.setPlanoCliente(clienteNovosDados.getPlanoCliente());
                        pagamento.setSituacao("Pendente");
                        dao.add(pagamento);

                        // Atualize os clientes e o Plano Cliente do associado
                        dao.Atualizar(classeCliente, clienteNovosDados.getId(), clienteNovosDados);
                        dao.Atualizar(classeCliente, clienteAssociadoNovosDados.getId(), clienteAssociadoNovosDados);

                      //  JOptionPane.showMessageDialog(null, "Sucesso ao adicionar Plano Casal. Total a pagar: " + valor);
                    }
                    if (clienteNovosDados.getPlanoCliente() != null && clienteAssociadoNovosDados.getPlanoCliente() != null
                            && !clienteNovosDados.getPlanoCliente().isStatus() && !clienteNovosDados.getPlanoCliente().isStatus()) {
                        clienteNovosDados.getPlanoCliente().setPlano(planoAssociacaoAtualizar);
                        clienteAssociadoNovosDados.getPlanoCliente().setPlano(planoAssociacaoAtualizar);
                        pagamento.setPlanoCliente(clienteNovosDados.getPlanoCliente());
                        pagamento.setSituacao("Pendente");
                        dao.add(pagamento);

                        // Atualize os clientes e o Plano Cliente do associado
                        dao.Atualizar(classeCliente, clienteNovosDados.getId(), clienteNovosDados);
                        dao.Atualizar(classeCliente, clienteAssociadoNovosDados.getId(), clienteAssociadoNovosDados);
                    }

                } else {
                  //  JOptionPane.showMessageDialog(null, "Sem cliente Associado");
                }
            }

        } else {
           // JOptionPane.showMessageDialog(null, "Preencha os campos");
        }
    }

    public void pegarLinhaSelecionada(Plano_de_Associacao plano) {
        if (plano != null) {
            PlanoCliente planoCliente = new PlanoCliente();
            planoCliente.setPlano(plano);
            planoAssociacaoAtualizar = plano;
            planoSelecionado = planoCliente;
            txtNomePlano.setText(plano.getNome());
            txtPrecoPlano.setText(String.valueOf(plano.getPreco()));
        } else {
           // JOptionPane.showMessageDialog(null, "Selecione Uma linha");
        }
    }
    private ObservableList<Plano_de_Associacao> observableListPlano;
    private ObservableList<Cliente> observableListCliente;

    private void ListarPlano() {
        GenericDAO dao = new GenericDAO();
        Class<Plano_de_Associacao> classePlano = Plano_de_Associacao.class;

        List<Plano_de_Associacao> lista = (List<Plano_de_Associacao>) dao.listar(classePlano);

        colunaNomePlano.setCellValueFactory(new PropertyValueFactory<>("nome"));

        observableListPlano = FXCollections.observableArrayList(lista);
        tabelaPlano.setItems(observableListPlano);
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

    public void setPessoaCliente(Pessoa pessoa) {
        if (pessoa instanceof Cliente cliente) {
            //Ajuste para tela
            clienteNovosDados = cliente;

            if (clienteNovosDados.getPlanoCliente() != null) {
                long quantdi = clienteNovosDados.getPlanoCliente().calcularDiasDecorridos();
                txtQuantidadeDias.setText(String.valueOf(quantdi));
            }
            if (cliente.getClienteAssociado() != null) {
                clienteAssociadoNovosDados = cliente.getClienteAssociado();
            }

            txtNome.setText(cliente.getNome());
            txtCodigo.setText(String.valueOf(cliente.getCodigo()));
            if (cliente.getPlanoCliente() != null) {
                txtPagamento.setText(clienteNovosDados.getPlanoCliente().isStatus() ? "Activo" : "Inactivo");
                txtPlanoAssociacao.setText(cliente.getPlanoCliente().getPlano().getNome());
                //  txtPagamento.setText("Inativo");
                txtDataFim.setText(cliente.getPlanoCliente().getDataFim().toString());
                txtDataInicio.setText(cliente.getPlanoCliente().getDataInicio().toString());
            }
            txtObjectivo.setText(cliente.getObjectivo());
            if (cliente.getImagem() != null) {
                byte[] imagemBytes = cliente.getImagem();

                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
                imageView.setImage(imagem);

                Image imagem1 = new Image(new ByteArrayInputStream(imagemBytes));
                imageView1.setImage(imagem1);

                imageView1.setFitWidth(90); // Largura desejada
                imageView1.setFitHeight(100); // Altura desejada
                imageView1.setPreserveRatio(true);

                imageView.setFitWidth(90); // Largura desejada
                imageView.setFitHeight(100); // Altura desejada
                imageView.setPreserveRatio(true);
            }
            List<Avaliacoes_Fisicas> avaliacoes = cliente.getAvaliacoes();

            if (avaliacoes != null) {
                int quantidade = avaliacoes.size();
                txtNumeroDeAvaliacoes.setText(String.valueOf(quantidade));
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
            pegarLinhaSelecionada();
        }
    }

    public void pegarLinhaSelecionada() {
        txtNomeClientePrincipal.setText(clienteNovosDados.getNome());
        txtCodigoClientePrincipal.setText(clienteNovosDados.getCodigo());

        if (clienteNovosDados.getPlanoCliente() != null) {
            txtObjectivoClientePrincipal.setText(clienteNovosDados.getPlanoCliente().getPlano().getNome());
            txtStatusClientePrincipal.setText(clienteNovosDados.getPlanoCliente().isStatus() ? "Activo" : "Inactivo");

        }
        if (clienteAssociadoNovosDados != null) {
            txtNomeClienteAssociado.setText(clienteAssociadoNovosDados.getNome());
            txtCodigoClienteAssociado.setText(clienteAssociadoNovosDados.getCodigo());

            if (clienteAssociadoNovosDados.getPlanoCliente() != null) {
                txtObjectivoClienteAssociado.setText(clienteAssociadoNovosDados.getPlanoCliente().getPlano().getNome());
                txtStatusClienteAssociado.setText(clienteAssociadoNovosDados.getPlanoCliente().isStatus() ? "Activo" : "Inactivo");
            }
            if (clienteAssociadoNovosDados.getImagem() != null) {
                byte[] imagemBytes = clienteAssociadoNovosDados.getImagem();
                Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
                imageView.setFitWidth(79); // Largura desejada
                imageView.setFitHeight(93); // Altura desejada
                imageViewAssociado.setImage(imagem);
            } else {
             //   JOptionPane.showMessageDialog(null, "imagem nao encontrada");
            }
        }
        if (clienteNovosDados.getImagem() != null) {
            byte[] imagemBytes = clienteNovosDados.getImagem();
            Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
            imageView.setFitWidth(70); // Largura desejada
            imageView.setFitHeight(80); // Altura desejada
            imageView.setImage(imagem);
        } else {
          //  JOptionPane.showMessageDialog(null, "imagem nao encontrada");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabelaPlano.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pegarLinhaSelecionada(newValue)
        );
        ListarPlano();
    }

}
