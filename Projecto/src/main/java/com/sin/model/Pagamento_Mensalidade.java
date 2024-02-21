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
import javax.persistence.ManyToOne;

/**
 *
 * @author steli
 */
@Entity
public class Pagamento_Mensalidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private PlanoCliente planoCliente;
    private String data_Pagamento;
    private Double valor;
    private boolean status;
    
    //Pendente, pago , cancelado 
    private String situacao;

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    
    public PlanoCliente getPlanoCliente() {
        return planoCliente;
    }

    public void setPlanoCliente(PlanoCliente planoCliente) {
        this.planoCliente = planoCliente;
    }

   
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData_Pagamento() {
        return data_Pagamento;
    }

    public void setData_Pagamento(String data_Pagamento) {
        this.data_Pagamento = data_Pagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    

}
