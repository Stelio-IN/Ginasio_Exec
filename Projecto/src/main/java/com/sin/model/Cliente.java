/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sin.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javafx.scene.image.Image;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author steli
 */
@Entity
public class Cliente extends Pessoa implements Serializable {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clienteAssociado_id")
    private Cliente clienteAssociado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "planoCliente_id")
    private PlanoCliente planoCliente;

  
    private String contato_emergencia;
    private String data_inscricao;
    private Double altura;
    private Double peso;

    private String esporte_que_Pratica;
    private String doenca;

    private String objectivo;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Avaliacoes_Fisicas> avaliacoes;

    public Image getImagemComoImage() {
        if (imagem != null) {
            return new Image(new ByteArrayInputStream(imagem));
        } else {
            return null; // Retorna null se não houver imagem
        }
    }

    // @OneToMany(mappedBy = "cliente")
    // private List<Avaliacoes_Fisicas> avaliacoes;
    public List<Avaliacoes_Fisicas> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacoes_Fisicas> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void associarCasal(Cliente parceiro) {
        if (parceiro != null && this.getGenero() != parceiro.getGenero()) {
            this.clienteAssociado = parceiro;
            parceiro.clienteAssociado = this;
        } else {
            // Lógica de tratamento para quando os clientes não são de sexo oposto
            System.out.println("A associação só é permitida entre pessoas de sexo oposto.");
        }
    }

    public PlanoCliente getPlanoCliente() {
        return planoCliente;
    }

    public void setPlanoCliente(PlanoCliente planoCliente) {
        this.planoCliente = planoCliente;
    }

    public void desassociarCasal(Cliente parceiro) {
        if (parceiro != null && parceiro == this.clienteAssociado) {
            this.clienteAssociado = null;
            parceiro.clienteAssociado = null;
            System.out.println("O casal foi desassociado com sucesso.");
        } else {
            System.out.println("Não há um casal associado para desassociar.");
        }
    }

    public String getObjectivo() {
        return objectivo;
    }

    public void setObjectivo(String objectivo) {
        this.objectivo = objectivo;
    }

    public Cliente getClinteAssociado() {
        return clienteAssociado;
    }

    public void setClinteAssociado(Cliente clinteAssociado) {
        this.clienteAssociado = clinteAssociado;
    }

    public String getDoenca() {
        return doenca;
    }

    public void setDoenca(String doenca) {
        this.doenca = doenca;
    }

    public String getEsporte_que_Pratica() {
        return esporte_que_Pratica;
    }

    public void setEsporte_que_Pratica(String esporte) {
        this.esporte_que_Pratica = esporte;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getContato_emergencia() {
        return contato_emergencia;
    }

    public void setContato_emergencia(String contato_emergencia) {
        this.contato_emergencia = contato_emergencia;
    }

    public String getData_inscricao() {
        return data_inscricao;
    }

    public void setData_inscricao(String data_inscricao) {
        this.data_inscricao = data_inscricao;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Cliente getClienteAssociado() {
        return clienteAssociado;
    }

    public void setClienteAssociado(Cliente clienteAssociado) {
        this.clienteAssociado = clienteAssociado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone_Alternativo() {
        return telefone_Alternativo;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public void setTelefone_Alternativo(String telefone_Alternativo) {
        this.telefone_Alternativo = telefone_Alternativo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getBilhete_Identificacao() {
        return bilhete_Identificacao;
    }

    public void setBilhete_Identificacao(String bilhete_Identificacao) {
        this.bilhete_Identificacao = bilhete_Identificacao;
    }

    public String getEstado_Civil() {
        return estado_Civil;
    }

    public void setEstado_Civil(String estado_Civil) {
        this.estado_Civil = estado_Civil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("id=").append(id);
        sb.append(", nome=").append(nome);
        sb.append(", genero=").append(genero);
        sb.append(", codigo=").append(codigo);
        sb.append(", nacionalidade=").append(nacionalidade);
        sb.append(", naturalidade=").append(naturalidade);
        sb.append(", email=").append(email);
        sb.append(", telefone=").append(telefone);
        sb.append(", telefone_Alternativo=").append(telefone_Alternativo);
        sb.append(", endereco=").append(endereco);
        sb.append(", bilhete_Identificacao=").append(bilhete_Identificacao);
        sb.append(", estado_Civil=").append(estado_Civil);
        sb.append(", imagem=").append(imagem);
        sb.append(", password=").append(password);
        sb.append(", isAtivo=").append(isAtivo);
        sb.append(", contato_emergencia=").append(contato_emergencia); // Adicione uma vírgula aqui
        sb.append(", data_inscricao=").append(data_inscricao);
        sb.append(", altura=").append(altura);
        sb.append(", peso=").append(peso);
        sb.append(", nascimento=").append(nascimento);
        sb.append(", esporte_que_Pratica=").append(esporte_que_Pratica);
        sb.append(", doenca=").append(doenca);
        sb.append('}');
        return sb.toString();
    }

}
