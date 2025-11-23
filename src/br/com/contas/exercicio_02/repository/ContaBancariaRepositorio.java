/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.repository;

import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Thiago Tomaz
 */

//**** SUPER IMPORTANTE*****//
// O HashMap é a melhor estrutura possível para consultar existência por chave.
// Complexidade de O(1) na média — tempo constante

//Artigo utilizado par ao padrão MVCS https://www.devmedia.com.br/padrao-mvc-java-magazine/21995

public class ContaBancariaRepositorio {

    private HashMap<String,ContaBancaria> contasBancarias = new HashMap<>();
    
    public ContaBancaria cadastrarConta(ContaBancaria conta) {
        contasBancarias.put(conta.getNumeroConta(), conta);
        return conta;
        //return listarTodasContasBancariasRepository.put(conta.getNumeroConta(), conta);
        // por que assim não da certo? pois não é o comportamenteo desejado,veja:
        // -> Se você cadastrar uma conta nova → put() retorna null
        // -> Se você sobrescrever uma conta existente → put() retorna a conta antiga
    }

    public boolean consultarContaExistente(String chave) {
        return contasBancarias.containsKey(chave);
    }

    public ContaBancaria atualizarConta(ContaBancaria conta) {
        return contasBancarias.replace(conta.getNumeroConta(), conta);
        /*  O meto replace retorna o objeto antigo que foi atualizado ou null caso ele não seja encontrado
        para facilitar eu resolvi por meio da lógica, transformar esse retorno em verdadeiro e falso;
        Retorna:
            null → se não existia uma entrada com essa chave
            valorAntigo → se a chave existia e foi substituída.
        
        OUTRA FORMA DE FAZER
        public boolean atualizarConta(ContaBancaria conta) {
            return listarTodasContasBancariasRepository.replace(conta.getNumeroConta(), conta)!=null;
        }
        */
    }

    public ContaBancaria excluirContaBancaria(ContaBancaria conta) {
       return contasBancarias.remove(conta.getNumeroConta());
         
    }

    public ContaBancaria consultarContaBancaria(String chave) {
        return contasBancarias.get(chave);
    }

    
    public Collection<ContaBancaria> listarTodasContasBancariasRepository() {
        return contasBancarias.values();
    }
    
 
    
    /*
    Collection<ContaBancaria> -> É um tipo, uma interface da hierarquia de coleções do Java.
    É o tipo base de todas as coleções genéricas:
        * List
        * Set
        * Queue
    Exemplo: -> Collection<ContaBancaria> contas = new ArrayList<>();
    
    Você está dizendo:
    “Tenho uma coleção de contas, mas não preciso saber se é lista ou conjunto.”
    
    ================================================================================
    Collections -> Ela não é uma coleção,
    é apenas uma caixa de ferramentas para coleções.
    EX.: Collections.sort(lista);
    Collections.unmodifiableList(lista)
    */

}
