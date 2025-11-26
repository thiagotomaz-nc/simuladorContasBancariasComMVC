/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.view.table;

import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import br.com.contas.exercicio_02.model.classes.EnumTipoOperacoes;
import br.com.contas.exercicio_02.model.classes.OperacaoBancaria;
import br.com.contas.exercicio_02.model.util.ConfigDefaultMoedaBR;
import br.com.contas.exercicio_02.model.util.FormatarData;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Thiago Tomaz
 */
public class OperacoesBancariasTableModel extends AbstractTableModel {

    private CacheOperacoesBancararias operacoesBancariasCache = new CacheOperacoesBancararias();

    private String[] colunasPoupanca = {"#", "Código Operação", "Data da operação", "Conta Origem", "Conta Destino", "Operação","Valor da operação"};

    public OperacoesBancariasTableModel(CacheOperacoesBancararias operacoesBancariasCache) {
        this.operacoesBancariasCache = operacoesBancariasCache;
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
        return operacoesBancariasCache.sizeCache();
    }

    @Override
    public int getColumnCount() {
        return colunasPoupanca.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {

        OperacaoBancaria op = operacoesBancariasCache.consultarConta(linha);

        switch (coluna) {
            case 0:
                return String.format("%07d", linha + 1);
            case 1:
                return op.getCodigoOperacao();
            case 2:
                return FormatarData.dataFormatadaBr(op.getDataTransferencia());
            case 3:
                return op.getContaOrigem();
            case 4:
                return op.getContaDestino();
            case 5:
                return op.siglaOperacao();
            case 6:
                int negativo = (op.getTipoOperacoes()==EnumTipoOperacoes.DEBITAR || op.getTipoOperacoes()==EnumTipoOperacoes.DEBITAR_SALDO_POUPANCA)?-1:1;
                return  ConfigDefaultMoedaBR.moeda_foratada_brl(ConfigDefaultMoedaBR.ArredondarValor(op.getValorTransferido()*negativo));
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
