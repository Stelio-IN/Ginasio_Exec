/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sin.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author steli
 */
@Entity
public class PlanoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cliente cliente;

    @ManyToOne
    private Plano_de_Associacao plano;

    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    private boolean status;
    private int duracao;

    public void atualizarPlano(Plano_de_Associacao novoPlano, Date novaDataInicio, boolean novoStatus, int novaDuracao) {
        this.plano = novoPlano;
        this.dataInicio = novaDataInicio;
        this.status = novoStatus;
        this.duracao = novaDuracao;
        setDuracaoEmMeses(novaDuracao);
    }
    // Método para ajustar a duração do plano em meses com base na data inicial
    public void setDuracaoEmMesesNew(int duracaoEmMeses, Date dataInicial) {
        // Obtém uma instância do Calendar, que nos permite manipular datas
        Calendar calendar = Calendar.getInstance();
        // Define a data inicial do calendário como a data fornecida
        calendar.setTime(dataInicial);
        // Adiciona a duração em meses fornecida à data inicial
        calendar.add(Calendar.MONTH, duracaoEmMeses);
        // Define a data de término como a nova data calculada
        this.dataFim = calendar.getTime();
    }


    public void setDuracaoEmMeses(int duracaoEmMeses) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataInicio);
        calendar.add(Calendar.MONTH, duracaoEmMeses);
        this.dataFim = calendar.getTime();
    }  
    /*
    public long calcularDiasDecorridos() {
        Date dataAtual = new Date(); // Obtém a data atual
        long diferencaEmMillis = dataFim.getTime() - dataAtual.getTime();

        if (diferencaEmMillis > 0) {
            // A data de fim ainda não foi alcançada, portanto, retornamos 0 dias
            this.status = true; // Define o status como ativo
            return diferencaEmMillis / (24 * 60 * 60 * 1000);
        } else {
            // Converte a diferença de milissegundos em dias
            long diferencaEmDias = Math.abs(diferencaEmMillis) / (24 * 60 * 60 * 1000);
            this.status = false; // Define o status como inativo
            return diferencaEmDias;
        }
    }
    */
    public long calcularDiasDecorridos() {
        Date dataAtual = new Date(); // Obtém a data atual

        // Verifica se a data atual está após a data de início do plano
        if (dataAtual.after(dataInicio)) {
            long diferencaEmMillis = dataFim.getTime() - dataAtual.getTime();

            if (diferencaEmMillis > 0) {
                // A data de fim ainda não foi alcançada, portanto, retornamos 0 dias
                this.status = true; // Define o status como ativo
                return diferencaEmMillis / (24 * 60 * 60 * 1000);
            } else {
                // Converte a diferença de milissegundos em dias
                long diferencaEmDias = Math.abs(diferencaEmMillis) / (24 * 60 * 60 * 1000);
                this.status = false; // Define o status como inativo
                return diferencaEmDias;
            }
        } else {
            // A data de início do plano ainda não foi alcançadasa
            this.status = false; // Define o status como inativo
            return 0;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Plano_de_Associacao getPlano() {
        return plano;
    }

    public void setPlano(Plano_de_Associacao plano) {
        this.plano = plano;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isStatus() {
        calcularDiasDecorridos();
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

}
