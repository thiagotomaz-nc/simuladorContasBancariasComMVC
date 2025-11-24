/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.view.table;

import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JTable;

/**
 *
 * @author Thiago Tomaz
 */
/*  por que essa classe esta neste pacote, pois ela serve exclusivamente para exibir dados na tabela.
    Não participa do domínio, nem do serviço, nem do repositório.
*/
public class CacheContas {
        private final ArrayList<ContaBancaria> cacheContas  = new ArrayList<>();
        
    

       
        
   
    public void atualizarCache(Collection<ContaBancaria> dadosContaBancaria) {
        this.cacheContas.clear();
        this.cacheContas.addAll(dadosContaBancaria);
       
        /*
        * Vantagens dessa implementação
        
        1. Se você alterar um objeto já existente (saldo, nome, etc.)
           REFLETE no ArrayList
           REFLETE no HashMap
        
        2. A edição no seguinte formato cacheContas.get(indice) tem a comlpexidade de o(1), similar ao hashMap
           isso, significa que posso utiliza-lo para edições sem comprometer o desempenho e integridade das informações;
        
        3. O cache sempre pode ser sincronizado com o repositório
           chamando apenas atualizarCache(repositorio.findAll()),
           evitando inconsistências e sincronizações manuais.
        
        4. custo da operação addAll, o que é copiado para o ArrayList? Somente as referências dos objetos, não os objetos em si. Então:
        O HashMap tem os objetos. O ArrayList só aponta para os mesmos objetos.
           
        ** ATENÇÃO:
        NOVAS CONTAS não aparecem automaticamente no cache.
        CONTAS REMOVIDAS não somem do cache.
        */
        
       }
    
    public ContaBancaria consultarConta(int indice){
        return cacheContas.get(indice);    
    }
    
    public int sizeCache(){
        return cacheContas.size();
    }
    
    
        
        
}
