/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.view.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Thiago Tomaz
 */
public class PoupancaTableDefaultRender extends DefaultTableCellRenderer {

    public PoupancaTableDefaultRender() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean hashFocus, int row, int coluna) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(jtable, o, isSelected, hashFocus, row, coluna); //To change body of generated methods, choose Tools | Templates.

        Object objeto = jtable.getValueAt(row, 3);
        Object objetoSSaldoPoupanca = jtable.getValueAt(row, 4);

        //primeira cor a ser usada é o branco
        Color corBackground = Color.WHITE;
        Color corFontes = Color.BLACK;

        double saldo = Double.parseDouble(objeto.toString().replace("R$", "").replace(" ", "").replace(".", "").replace(",", "."));
        double saldoPoupanca = Double.parseDouble(objetoSSaldoPoupanca.toString().replace("R$", "").replace(" ", "").replace(".", "").replace(",", "."));

        if (isSelected) {
            corBackground = Color.decode("#c59ae5");
        } else {
            if (saldo == 0) {
                corBackground = Color.RED;
            } else if (saldoPoupanca == 0) {
                corBackground = Color.ORANGE;
            } else if (row % 2 != 0) {
                //se o resto da linha por 2 não for 0, a linha é preenchida com outra cor para diferenciar
                corBackground = Color.decode("#d7f6fe");
            }
        }

        label.setBackground(corBackground);
        label.setForeground(corFontes);
        return label;
    }

}
