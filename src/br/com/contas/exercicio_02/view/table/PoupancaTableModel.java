/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.view.table;

import br.com.contas.exercicio_02.model.classes.ContaCorrente;
import br.com.contas.exercicio_02.model.classes.ContaPoupanca;
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
public class PoupancaTableModel extends AbstractTableModel {
    
    //posteriormente, analisar a seguinte questão! , Ao utilizar um HasMap combinado com o ArrayList,  se tem uma duplicidade da utilização do número da conta, sendo assim, é bom analisar outra forma contornar essa situação.
    private HashMap<String, ContaPoupanca> listaContasPoupanca;
    private ArrayList<String> listaChavesPoupanca;
    private String[] colunasPoupanca = {"id","Número da conta", "Nome do titular","Saldo","Saldo da poupança" };
    private NumberFormat formatoMoedaBrasil;
    
    public PoupancaTableModel(Locale localBrasil) {
        listaContasPoupanca = new HashMap<>();
        listaChavesPoupanca = new ArrayList<>();
        formatoMoedaBrasil=NumberFormat.getCurrencyInstance(localBrasil);   
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
        return listaChavesPoupanca.size();
    }

    @Override
    public int getColumnCount() {
        return colunasPoupanca.length;
    }
    
    
    
    @Override
    public Object getValueAt(int linha, int coluna) {
      ContaPoupanca cp = listaContasPoupanca.get(listaChavesPoupanca.get(linha));
        switch(coluna){
          case 0:
              return linha+1;
          case 1:
              return cp.getNumeroConta();
          case 2:
              return cp.getNome();
          case 3:
              return formatoMoedaBrasil.format(cp.getSaldo());
          case 4:
              return formatoMoedaBrasil.format(cp.getSaldoPoupanca());
            default:
                throw new IndexOutOfBoundsException("erro nas colunas");
      }
    }
    
    //metodos responsaveis por adicionar linhas com os dados a tabela
    
    // o metodo esta preparado para adicionar uma linha que onde é adicionado uma contaCorrente por vez
    public void addRow(ContaPoupanca contaPoupanca){
        listaContasPoupanca.put(contaPoupanca.getNumeroConta(), contaPoupanca);
        listaChavesPoupanca.add(contaPoupanca.getNumeroConta());
        fireTableRowsInserted(listaChavesPoupanca.indexOf(contaPoupanca.getNumeroConta()), listaChavesPoupanca.indexOf(contaPoupanca.getNumeroConta()));
    }
    
    //retornar a conta corrente pela linha selecionada da tabela;
    public ContaPoupanca getContaPoupanca(int linha){
        return listaContasPoupanca.get(listaChavesPoupanca.get(linha));
    }
    
    public ContaPoupanca getContaPoupanca(String chave){
        return listaContasPoupanca.get(chave);
    }
    
    //metodo responsavel por excluir a conta do hashmap e do arraylist, mantendo-os sicronizados
    public int deleteContaPoupanca(int linha){
        
        ContaPoupanca contaPoupanca = listaContasPoupanca.get(listaChavesPoupanca.get(linha));
        String nome = contaPoupanca.getNome();
        
       if (listaContasPoupanca.remove(contaPoupanca.getNumeroConta()) !=null ){
           listaChavesPoupanca.remove(contaPoupanca.getNumeroConta());
           JOptionPane.showMessageDialog(null, nome+" a sua conta foi removida com sucesso!!!");
           this.fireTableDataChanged();
           return 1;
       }else{
           return 0;
       }
        
    }
    
    //no momento preciso apenas atualizar os dados da conta no hashMap, ou seja, ainda não ha necessidade de alterar a lista com as chaves;
    public void updateContaCorrente(ContaPoupanca contaPoupanca){
        listaContasPoupanca.replace(contaPoupanca.getNumeroConta(), contaPoupanca);  
        this.fireTableDataChanged();
    }
    
    
}
