/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.controller;

import br.com.contas.exercicio_02.services.OperacoesBancarias;
import br.com.contas.exercicio_02.view.table.CacheContas;
import java.util.Collection;

/**
 *
 * @author Thiago Tomaz
 */

//Artigo utilizado par ao padrão MVCS https://www.devmedia.com.br/padrao-mvc-java-magazine/21995

public class ContasBancariasController {
    private final OperacoesBancarias operacoesServices;
    private final CacheContas cacheContas;

    public ContasBancariasController(OperacoesBancarias operacoesServices, CacheContas cacheContas) {
        this.operacoesServices = operacoesServices;
        this.cacheContas = cacheContas;
    }
    // --------------------------------------------
    // MÉTODOS DE OPERAÇÕES BANCÁRIAS
    // --------------------------------------------
    
    
    // --------------------------------------------
    // MÉTODO PARA ATUALIZAR CACHE + UI/TABELA
    // --------------------------------------------
    private void atualizarUI() {
        Collection dadosAtualizados = operacoesServices.listarTodasContasBancarias();
        cacheContas.atualizarCache(dadosAtualizados);
        // aqui você avisa seu TableModel para atualizar: tableModel.fireTableDataChanged();
    }
    
    
    
    
}
/*
 * Camada Controller – Funções e Limites
 *  O MAIS IMPORTANTE, o controller é quem controla a comunicação entre a interface Gráfica 
    e o Service.
 *    Ele recebe ações da UI e delega ao Service.
 *
 * 2. O Controller NÃO contém regra de negócio.
 *    Toda validação mais séria e cálculos ficam no Service.
 *    O Controller apenas:
 *       - lê valores dos campos da UI
 *       - chama o Service
 *       - atualiza a UI com o resultado
 *
 * 3. O Controller nunca acessa o Repository diretamente.
 *    Toda escrita/leitura de dados persistidos deve passar pelo Service.
 *
 * 4. Após operações como:
 *       - adicionar conta
 *       - remover conta
 *       - editar conta
 *    o Controller deve atualizar o ArrayList da UI via atualizarCache().
 *
 * 5. A UI trabalha com dois elementos:
 *       - ArrayList (cache) para exibição e edição por índice
 *       - HashMap/Repository para persistência e busca rápida
 *
 * 6. Não existe sincronização automática.
 *    Após qualquer modificação feita pelo Service, o Controller deve chamar:
 *
 *         atualizarCache(service.buscarTodas())
 *
 *    Isso garante que a tabela e a UI reflitam os dados atuais.

    7. O Controller é o único lugar onde deve aparecer JOptionPane;

    8. O Controller é o lugar CORRETO para capturar e tratar exceções vindas do Service.

 */
