/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Thiago Tomaz
 */
public class FormatarData {
       
    public static String dataFormatadaBr(LocalDateTime data){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(data);
    }
}
