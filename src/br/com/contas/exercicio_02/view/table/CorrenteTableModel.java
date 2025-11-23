/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.view.table;

import br.com.contas.exercicio_02.model.classes.ContaCorrente;
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
public class CorrenteTableModel extends AbstractTableModel {

    private HashMap<String, ContaCorrente> listaContasCorrentes;
    private ArrayList<String> listasChavesContaCorrentes;
    private String[] colunas = {"Id", "Número da Conta", "Nome do correntista", "Saldo", "Limite de crédito"};
    private NumberFormat formatoMoedaBrasil;

    public CorrenteTableModel(Locale local) {
        this.listaContasCorrentes = new HashMap<>();
        listasChavesContaCorrentes = new ArrayList<>();
        formatoMoedaBrasil = NumberFormat.getCurrencyInstance(local);
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }
   
    @Override
    public String getColumnName(int i) {
        return colunas[i]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getRowCount() {
        return listasChavesContaCorrentes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        //criando um objeto da classe conta corrente ulitizando a lista de chaves para acessar o hashMap
        ContaCorrente contaCorrente = listaContasCorrentes.get(listasChavesContaCorrentes.get(linha));
        
        //nesse switch é onde é preenchido cada célula da tabela
        switch (coluna) {
            case 0:
                return linha+1;
            case 1:
                return contaCorrente.getNumeroConta();
            case 2:
                return contaCorrente.getNome();
            case 3:
                return formatoMoedaBrasil.format(contaCorrente.getSaldo());
            case 4:
                return formatoMoedaBrasil.format(contaCorrente.getLimiteCredito());
            default:
		throw new IndexOutOfBoundsException("Erro no indice da coluna");
        }
      
    }
    
    //metodos responsaveis por adicionar linhas com os dados a tabela
    
    // o metodo esta preparado para adicionar uma linha que onde é adicionado uma contaCorrente por vez
    public void addRow(ContaCorrente contasCorrente){
        listaContasCorrentes.put(contasCorrente.getNumeroConta(), contasCorrente);
        listasChavesContaCorrentes.add(contasCorrente.getNumeroConta());
        fireTableRowsInserted(listasChavesContaCorrentes.indexOf(contasCorrente.getNumeroConta()), listasChavesContaCorrentes.indexOf(contasCorrente.getNumeroConta()));
    }
    
    //retornar a conta corrente pela linha selecionada da tabela;
    public ContaCorrente getContaCorrente(int linha){
        return listaContasCorrentes.get(listasChavesContaCorrentes.get(linha));
    }
    
     public ContaCorrente getContaCorrente(String chave){
        return listaContasCorrentes.get(chave);
    }
    
    //metodo responsavel por excluir a conta do hashmap e do arraylist, mantendo-os sicronizados
    public int deleteContaCorrente(int linha){
        
        ContaCorrente contaCorrente = listaContasCorrentes.get(listasChavesContaCorrentes.get(linha));
        String nome = contaCorrente.getNome();
        
       if (listaContasCorrentes.remove(contaCorrente.getNumeroConta()) !=null ){
           listasChavesContaCorrentes.remove(contaCorrente.getNumeroConta());
           JOptionPane.showMessageDialog(null, nome+" a sua conta foi removida com sucesso!!!");
           this.fireTableDataChanged();
           return 1;
       }else{
           return 0;
       }
        
    }
    //no momento preciso apenas atualizar os dados da conta no hashMap, ou seja, ainda não ha necessidade de alterar a lista com as chaves;
    public void updateContaCorrente(ContaCorrente contaCorrente){
        listaContasCorrentes.replace(contaCorrente.getNumeroConta(), contaCorrente);  
        this.fireTableDataChanged();
    }
    
 
}
