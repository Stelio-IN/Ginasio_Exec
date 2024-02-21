/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sin.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author steli
 */
@Entity
public class Relatorios  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String data_de_geracao;
    private Tipo_Relatorio tipo_de_relatorio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_de_geracao() {
        return data_de_geracao;
    }

    public void setData_de_geracao(String data_de_geracao) {
        this.data_de_geracao = data_de_geracao;
    }

    public Tipo_Relatorio getTipo_de_relatorio() {
        return tipo_de_relatorio;
    }

    public void setTipo_de_relatorio(Tipo_Relatorio tipo_de_relatorio) {
        this.tipo_de_relatorio = tipo_de_relatorio;
    }

    
    
    
    
    }
