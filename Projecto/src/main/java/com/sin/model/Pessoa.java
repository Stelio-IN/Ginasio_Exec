/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sin.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author steli
 */
@Entity
@Table(name = "pessoa")
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p"),
    @NamedQuery(name = "Pessoa.findById", query = "SELECT p FROM Pessoa p WHERE p.id = :id"),
    @NamedQuery(name = "Pessoa.findByNome", query = "SELECT p FROM Pessoa p WHERE p.nome = :nome"),
    @NamedQuery(name = "Pessoa.findByEmail", query = "SELECT p FROM Pessoa p WHERE p.email = :email"),
    @NamedQuery(name = "Pessoa.findByName", query = "SELECT p FROM Pessoa p WHERE p.nome LIKE :nome"),
    @NamedQuery(name = "Pessoa.findByCodigo", query = "SELECT p FROM Pessoa p WHERE p.codigo = :codigo")
})

public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    protected Long id;
    protected String nome;
    protected String genero;
    protected String codigo;
    protected String nacionalidade;
    protected String naturalidade; 
    protected String email;   
    protected String telefone;
    protected String nascimento;
    protected String telefone_Alternativo;

    @OneToOne(cascade = CascadeType.ALL) // Isso indica uma associação "um para um"
    @JoinColumn(name = "endereco_id") // Isso cria uma coluna "endereco_id" na tabela Pessoa
    protected Endereco endereco;
    protected String bilhete_Identificacao; 
    protected String estado_Civil;
    @Lob
    protected byte[] imagem;
    protected String password;
    protected boolean isAtivo;

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    
    
    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
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
        sb.append("Pessoa{");
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
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
