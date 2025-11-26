/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.repository;

import br.com.contas.exercicio_02.model.classes.OperacoesBancarias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thiago Tomaz
 */
//**** SUPER IMPORTANTE*****//
// O HashMap é a melhor estrutura possível para consultar existência por chave.
// Complexidade de O(1) na média — tempo constante
public class OperacoesBancariasRepositorio {

    private HashMap<String, OperacoesBancarias> operacoesBancarias = new HashMap<>();

    public OperacoesBancarias salvarOperacao(String chave, OperacoesBancarias operacao) {
        operacoesBancarias.put(chave, operacao);
        return operacao;
    }

    public boolean consultarOperacaoExistente(String chave) {
        return operacoesBancarias.containsKey(chave);
    }

    public OperacoesBancarias atualizarOperacao(String chave, OperacoesBancarias operacao) {
        return operacoesBancarias.replace(chave, operacao);
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

    public OperacoesBancarias excluirOperacao(String chaveOperacaoBancaria) {
        return operacoesBancarias.remove(chaveOperacaoBancaria);
    }

    public OperacoesBancarias consultarOperacao(String chave) {
        return operacoesBancarias.get(chave);
    }

   

    public Collection<OperacoesBancarias> listarTodasOperacaoValuesRepository() {
        return Collections.unmodifiableCollection(operacoesBancarias.values());//Forma correta, cria-se uma copia e retorna

        /*
        return operacoesBancarias
        ATENÇÃO: 
        Ao retornar dessa forma permite-se que o hasmap seja Modificado diretamente (adicionar, remover, alterar valores)
        Isso quebra o encapsulamento do seu repositório. Normalmente, você quer que o repositório controle como os dados são alterados;
         */
    }

    public Collection<OperacoesBancarias> filtrarOperacoesContaBancariaUnitaria(String numeroContaOrigem)  {
        //Percorre-se todo o hashMap pelos values dele
        ArrayList<OperacoesBancarias> operacoesFiltradas = new ArrayList();

        for (OperacoesBancarias op : listarTodasOperacaoValuesRepository()) {
            if (op.getContaOrigem().equals(numeroContaOrigem.trim())) {
                operacoesFiltradas.add(op);
            }
        }
        return Collections.unmodifiableCollection(operacoesFiltradas);
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
