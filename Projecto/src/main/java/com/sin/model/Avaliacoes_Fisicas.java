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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author steli
 */
@Entity
public class Avaliacoes_Fisicas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne
    private Instrutor instrutor;
    private String data;

    private Double peso;
    private Double altura;
    private Double circunferencia_cintura;
    private Double circunferência_braco;
    private Double circunferência_coxa;
    private Double circunferência_panturrilha;
    private Double circunferência_quadril;
    private Double circunferência_peito;
    private Double indice_Massa_corporal;
    private Double nível_de_condicionamento_fisico;
    private Double forca_Muscular;
    private Double realizacao_Metas_pessoas;
    private Double satisfacao_do_Cliente;
    private Double capacidade_cardiovascular;
    private String discricao_comentarios;

    private Double Nota_da_avaliacao;

    public double calcularIMC() {
        if (peso > 0 && altura > 0) {
            return indice_Massa_corporal = peso / (altura * altura);
        }
        return -1;
    }

    public void classificarIMC(double imc) {
        if (imc < 18.5) {
            System.out.println("Abaixo do peso.");
        } else if (imc < 24.9) {
            System.out.println("Peso saudável.");
        } else if (imc < 29.9) {
            System.out.println("Sobrepeso.");
        } else if (imc < 34.9) {
            System.out.println("Obesidade Grau I.");
        } else if (imc < 39.9) {
            System.out.println("Obesidade Grau II.");
        } else {
            System.out.println("Obesidade Grau III.");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getNota_da_avaliacao() {
        return Nota_da_avaliacao;
    }

    public void setNota_da_avaliacao(Double Nota_da_avaliacao) {
        this.Nota_da_avaliacao = Nota_da_avaliacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getCircunferencia_cintura() {
        return circunferencia_cintura;
    }

    public void setCircunferencia_cintura(Double circunferencia_cintura) {
        this.circunferencia_cintura = circunferencia_cintura;
    }

    public Double getCircunferência_braco() {
        return circunferência_braco;
    }

    public void setCircunferência_braco(Double circunferência_braco) {
        this.circunferência_braco = circunferência_braco;
    }

    public Double getCircunferência_coxa() {
        return circunferência_coxa;
    }

    public void setCircunferência_coxa(Double circunferência_coxa) {
        this.circunferência_coxa = circunferência_coxa;
    }

    public Double getCircunferência_panturrilha() {
        return circunferência_panturrilha;
    }

    public void setCircunferência_panturrilha(Double circunferência_panturrilha) {
        this.circunferência_panturrilha = circunferência_panturrilha;
    }

    public Double getCircunferência_quadril() {
        return circunferência_quadril;
    }

    public void setCircunferência_quadril(Double circunferência_quadril) {
        this.circunferência_quadril = circunferência_quadril;
    }

    public Double getCircunferência_peito() {
        return circunferência_peito;
    }

    public void setCircunferência_peito(Double circunferência_peito) {
        this.circunferência_peito = circunferência_peito;
    }

    public Double getIndice_Massa_corporal() {
        return indice_Massa_corporal;
    }

    public void setIndice_Massa_corporal(Double indice_Massa_corporal) {
        this.indice_Massa_corporal = indice_Massa_corporal;
    }

    public Double getNível_de_condicionamento_fisico() {
        return nível_de_condicionamento_fisico;
    }

    public void setNível_de_condicionamento_fisico(Double nível_de_condicionamento_fisico) {
        this.nível_de_condicionamento_fisico = nível_de_condicionamento_fisico;
    }

    public Double getForca_Muscular() {
        return forca_Muscular;
    }

    public void setForca_Muscular(Double forca_Muscular) {
        this.forca_Muscular = forca_Muscular;
    }

    public Double getRealizacao_Metas_pessoas() {
        return realizacao_Metas_pessoas;
    }

    public void setRealizacao_Metas_pessoas(Double realizacao_Metas_pessoas) {
        this.realizacao_Metas_pessoas = realizacao_Metas_pessoas;
    }

    public Double getSatisfacao_do_Cliente() {
        return satisfacao_do_Cliente;
    }

    public void setSatisfacao_do_Cliente(Double satisfacao_do_Cliente) {
        this.satisfacao_do_Cliente = satisfacao_do_Cliente;
    }

    public Double getCapacidade_cardiovascular() {
        return capacidade_cardiovascular;
    }

    public void setCapacidade_cardiovascular(Double capacidade_cardiovascular) {
        this.capacidade_cardiovascular = capacidade_cardiovascular;
    }

    public String getDiscricao_comentarios() {
        return discricao_comentarios;
    }

    public void setDiscricao_comentarios(String discricao_comentarios) {
        this.discricao_comentarios = discricao_comentarios;
    }

}
