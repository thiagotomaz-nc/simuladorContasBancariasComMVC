/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JDialog;

/**
 *
 * @author Thiago Tomaz
 */

/*

https://www.devmedia.com.br/formatando-numeros-com-numberformat/7369

*/
public class ConfigDefaultMoedaBR {
   
    //static → pertence à classe, não ao objeto
     
    public static String MOEDA_FORMATADA_BRL(double valor) {
      // o DecimalFormat é um subclasse do NumberFormat,
        return  new DecimalFormat("#,##0.00").format(valor);
    }
    
    //significa arredondar um número para 2 casas decimais usando
    // RoundingMode.HALF_UP -> utilizar a regra mais comum do mundo real — o arredondamento tradicional (o famoso "5 arredonda para cima").
    public static double ArredondarValor(double valor){
        return new BigDecimal(valor).setScale(2,RoundingMode.HALF_UP).doubleValue();
    }
   
}
