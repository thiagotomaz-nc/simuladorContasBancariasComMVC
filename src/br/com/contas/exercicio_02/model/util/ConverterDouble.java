/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.util;

import br.com.contas.exercicio_02.model.exception.DoubleFormatClassCastException;
import br.com.contas.exercicio_02.model.exception.NumeroDaContaFormatoException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Thiago Tomaz
 */
public class ConverterDouble {

    public static double converterObjectToDouble(Object value) throws DoubleFormatClassCastException {
        try {
            Number numero = (Number) value;
            return new BigDecimal(numero.doubleValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } catch (ClassCastException | NumberFormatException ex) {
            throw new DoubleFormatClassCastException("Formato do número real incorreto!");
        }
    }

    public static double converterStringToDouble(String value) throws NumeroDaContaFormatoException {
        try {
                 return new BigDecimal(Double.parseDouble(value.trim())).setScale(2, RoundingMode.HALF_UP).doubleValue();
        } catch (NumberFormatException ex) {
            throw new NumeroDaContaFormatoException("Formato de número inteiro invalido!");
        }

    }
}
