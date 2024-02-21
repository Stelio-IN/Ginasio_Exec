/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sin.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author steli
 */
@Entity
public class Ficha_Inscricao {
    @Id
    private String numero_Da_Ficha;
    private String cliente;
    private String funcionario;
    private String data_Da_Inscriacao;

    public String getNumero_Da_Ficha() {
        return numero_Da_Ficha;
    }

    public void setNumero_Da_Ficha(String numero_Da_Ficha) {
        this.numero_Da_Ficha = numero_Da_Ficha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getData_Da_Inscriacao() {
        return data_Da_Inscriacao;
    }

    public void setData_Da_Inscriacao(String data_Da_Inscriacao) {
        this.data_Da_Inscriacao = data_Da_Inscriacao;
    }
    
    
    
    
    
}
