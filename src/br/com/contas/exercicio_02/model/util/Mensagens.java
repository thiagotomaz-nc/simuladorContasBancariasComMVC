/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.util;

import javax.swing.JOptionPane;

/**
 *
 * @author Thiago Tomaz
 */
public class Mensagens {
    
    public static void error(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void informacao(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem,"Informação",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static int confirmar(String questao){
        return JOptionPane.showConfirmDialog(null, questao, "Confirmar", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
    
}
