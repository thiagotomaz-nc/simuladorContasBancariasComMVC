/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.enums;

/**
 *
 * @author Thiago Tomaz
 */
public enum EnumValidacaoCampos {
   
    CPF(14),//pois nesse caso ainda estou considerenado a pontuação
    CNPJ(14),
    NUMERO_CONTA(8);

    private final int tamanho;
    //O construtor do enum é instanciado automaticamente quando fizer EnumValidacaoCampos.CPF,
    // nesse momento equivale a EnumValidacaoCampos(14), ou seja, o valor do cpf é passado para o construtor
    private EnumValidacaoCampos(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }
}
