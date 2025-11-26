/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.util;

import br.com.contas.exercicio_02.model.exception.NuloVazioInesxistenteException;
import br.com.contas.exercicio_02.model.exception.CampoSizeInvalidoException;
import br.com.contas.exercicio_02.model.exception.CaractereInvalidoEspacoBrancoException;
import br.com.contas.exercicio_02.model.enums.EnumValidacaoCampos;
import br.com.contas.exercicio_02.model.exception.NumeroDaContaFormatoException;

/**
 *
 * @author Thiago Tomaz
 */
public class ValidarValores {

    public static void isNullEmpity(String valor, String mensagem) throws NuloVazioInesxistenteException {
        if (valor.trim() == null || valor.isEmpty()) {
            throw new NuloVazioInesxistenteException(mensagem);
        }
    }

    public static void validarTamanho(String valor, EnumValidacaoCampos campo) throws CampoSizeInvalidoException {
        if (valor.trim().length() < campo.getTamanho()) {
            throw new CampoSizeInvalidoException("O Campo [ Número da conta ] deve ter pelo menos 8 números");
        }
    }
    
    public static void caractereInvalidoEspacoBranco(String valor) throws CaractereInvalidoEspacoBrancoException {
        if (valor.trim().contains(" ")) {
            throw new CaractereInvalidoEspacoBrancoException("Remova os espaços em branco para continuar!");
        }
    }
    
    public static void isNumeroContaValido(String numeroConta) throws NumeroDaContaFormatoException{
            if(numeroConta.trim().equals("000000-0")){
                throw new NumeroDaContaFormatoException("Número da conta inválido!");
            }
    }
}
