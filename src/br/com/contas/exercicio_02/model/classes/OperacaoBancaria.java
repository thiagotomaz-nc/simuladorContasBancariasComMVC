/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.classes;

import java.time.LocalDateTime;

/**
 *
 * @author Thiago Tomaz
 */
//classe para representar as operações bancarias
public abstract class OperacaoBancaria {
    
    private String codigoOperacao;
    private String contaOrigem;
    private String contaDestino;
    private double valorTransferido;
    private LocalDateTime dataTransferencia;

    public String getCodigoOperacao() {
        return codigoOperacao;
    }

    public void setCodigoOperacao(String codigoOperacao) {
        this.codigoOperacao = codigoOperacao;
    }
      
    
    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        this.contaOrigem = contaOrigem.trim();
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        this.contaDestino = contaDestino.trim();
    }

    public double getValorTransferido() {
        return valorTransferido;
    }

    public void setValorTransferido(double valorTransferido) {
        this.valorTransferido = valorTransferido;
    }

    public LocalDateTime getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDateTime dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }
    
    //Aplicando o polimorfismo
    public abstract String getDescricaoOperacao();
    public abstract EnumTipoOperacoes getTipoOperacoes();
    public abstract String siglaOperacao();
     
}
