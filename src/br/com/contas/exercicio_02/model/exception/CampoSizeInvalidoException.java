/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.exception;

/**
 *
 * @author Thiago Tomaz
 */
public class CampoSizeInvalidoException extends Exception {

    public CampoSizeInvalidoException() {
    }

    public CampoSizeInvalidoException(String string) {
        super(string);
    }

    public CampoSizeInvalidoException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

}
