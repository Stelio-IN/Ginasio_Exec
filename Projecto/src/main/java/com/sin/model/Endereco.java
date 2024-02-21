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
public class Endereco implements Serializable{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private long id;
    private String numero_Casa;
    private String bairro;
    private String rua;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero_Casa() {
        return numero_Casa;
    }

    public void setNumero_Casa(String numero_Casa) {
        this.numero_Casa = numero_Casa;
    }


    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    @Override
    public String toString() {
        return "Endereco{" + "id=" + id + ", numero_Casa=" + numero_Casa + ", bairro=" + bairro + ", rua=" + rua + '}';
    }
}
