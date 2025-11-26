/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.classes;

import br.com.contas.exercicio_02.view.TipoContaIG;

/**
 *
 * @author Thiago Tomaz
 */
public class OperacaoTransferenciaEntreContas extends OperacaoBancaria{

    @Override
    public String getDescricaoOperacao() {
        return "Transferência";
    }

    @Override
    public EnumTipoOperacoes getTipoOperacoes() {
       return EnumTipoOperacoes.TRANSFERIR;
    }
    
      @Override
    public String siglaOperacao() {
        return "T";
    }
    
}
