/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.Exception;

/**
 *
 * @author Thiago Tomaz
 */
public class NumeroContaVazioException extends Exception{

    public NumeroContaVazioException() {
    }

    public NumeroContaVazioException(String string) {
        super(string);
    }

    public NumeroContaVazioException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
}
