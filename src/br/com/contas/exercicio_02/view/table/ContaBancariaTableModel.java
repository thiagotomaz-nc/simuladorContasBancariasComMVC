/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.view.table;

import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import br.com.contas.exercicio_02.model.classes.ContaCorrente;
import br.com.contas.exercicio_02.model.classes.ContaPoupanca;
import br.com.contas.exercicio_02.util.ConfigDefaultMoedaBR;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Thiago Tomaz
 */
public class ContaBancariaTableModel extends AbstractTableModel {

    private CacheContas cacheContas = new CacheContas();
    private String[] colunasPoupanca = {"#", "Número da conta", "Nome do titular", "Saldo", "Tipo Conta","Saldo da poupança/Limite"};

    public ContaBancariaTableModel(CacheContas cacheContas) {
        this.cacheContas = cacheContas;
    }

    @Override
    public String getColumnName(int i) {
        return colunasPoupanca[i];
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public int getRowCount() {
        return cacheContas.sizeCache();
    }

    @Override
    public int getColumnCount() {
        return colunasPoupanca.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {

        ContaBancaria cb = cacheContas.consultarConta(linha);

        switch (coluna) {
            case 0:
                return linha + 1;
            case 1:
                return cb.getNumeroConta();
            case 2:
                return cb.getNome();
            case 3:
                return ConfigDefaultMoedaBR.moeda_foratada_brl(ConfigDefaultMoedaBR.ArredondarValor(cb.getSaldo()));
            case 4:
                return cb.getDescricaoConta();
            case 5:
                return ConfigDefaultMoedaBR.moeda_foratada_brl(ConfigDefaultMoedaBR.ArredondarValor(cb.getInfoAdicionalConta()));
            default:
                throw new IndexOutOfBoundsException("erro nas colunas");
        }

    }

    public void atualizarTabela() {
        fireTableDataChanged();

    }

    // acima percebe-se um código limpo, livre de ifs desnecessarios graças ao polimorfismo;
    // a tabela esta apenas exibindo os dados da lista nada mais. Essa é a função dela, nada de se preocupar com regras de necogio ou algo a parte, simplesmente exibir os dados;
}
