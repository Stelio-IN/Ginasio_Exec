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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author steli
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Equipamento.findAll", query = "SELECT e FROM Equipamento e")})
public class Equipamento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String marca;
    private String modelo;
    @Lob
    protected byte[] imagem;
    private boolean status; 
    
    private double vida_Util;
    private String data_Entrada;
    private String discricao;

    public double getVida_Util() {
        return vida_Util;
    }

    public void setVida_Util(double vida_Util) {
        this.vida_Util = vida_Util;
    }

    public String getData_Entrada() {
        return data_Entrada;
    }

    public void setData_Entrada(String data_Entrada) {
        this.data_Entrada = data_Entrada;
    }

    public String getDiscricao() {
        return discricao;
    }

    public void setDiscricao(String discricao) {
        this.discricao = discricao;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Equipamento{" + "id=" + id + ", nome=" + nome + ", marca=" + marca + ", modelo=" + modelo + ", imagem=" + imagem + ", status=" + status + '}';
    }
    
    

}
