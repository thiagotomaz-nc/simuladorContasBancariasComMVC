/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.classes;

/**
 *
 * @author Thiago Tomaz
 */
public class OperacaoDebitoSaldoPoupanca extends OperacaoBancaria {

    @Override
    public String getDescricaoOperacao() {
        return "Débito SP";
    }

    @Override
    public EnumTipoOperacoes getTipoOperacoes() {
        return EnumTipoOperacoes.DEBITAR_SALDO_POUPANCA;
    }

    @Override
    public String siglaOperacao() {
        return "DSP";
    }
    
    

}
