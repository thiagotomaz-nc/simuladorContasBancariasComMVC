/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.view.table;

import br.com.contas.exercicio_02.model.classes.OperacaoBancaria;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Thiago Tomaz
 */
/*  por que essa classe esta neste pacote, pois ela serve exclusivamente para exibir dados na tabela.
    Não participa do domínio, nem do serviço, nem do repositório.
*/
public class CacheOperacoesBancararias {
       
    private final ArrayList<OperacaoBancaria> OperacoesBancariasCache  = new ArrayList<>();
       
    public void atualizarCache(Collection<OperacaoBancaria> dadosContaBancaria) {
        this.OperacoesBancariasCache.clear();
        this.OperacoesBancariasCache.addAll(dadosContaBancaria);
                
       }
    
    public OperacaoBancaria consultarConta(int indice){
        return OperacoesBancariasCache.get(indice);    
    }
    
    public int sizeCache(){
        return OperacoesBancariasCache.size();
    }
    
    public ArrayList<OperacaoBancaria> getCacheContasBancarias(){
        return OperacoesBancariasCache;
    }
    
        
        
}
