/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.controller;

import br.com.contas.exercicio_02.Exception.ContaExistenteException;
import br.com.contas.exercicio_02.Exception.RegrasDeNegocioException;
import br.com.contas.exercicio_02.Exception.SaldoInsuficienteException;
import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import br.com.contas.exercicio_02.model.classes.ContaCorrente;
import br.com.contas.exercicio_02.model.classes.ContaPoupanca;
import br.com.contas.exercicio_02.model.classes.TipoConta;
import br.com.contas.exercicio_02.services.OperacoesBancarias;
import br.com.contas.exercicio_02.util.Mensagens;
import br.com.contas.exercicio_02.view.AcaoView;
import br.com.contas.exercicio_02.view.ContasBancariaEditarCadastrar;
import br.com.contas.exercicio_02.view.table.CacheContas;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Thiago Tomaz
 */
//Artigo utilizado par ao padrão MVCS https://www.devmedia.com.br/padrao-mvc-java-magazine/21995
public class ContasBancariasController {

    private final OperacoesBancarias operacoesServices;
    private final CacheContas cacheContas;
    private JFrame frame;

    public ContasBancariasController(JFrame parent, OperacoesBancarias operacoesServices, CacheContas cacheContas) {
        this.operacoesServices = operacoesServices;
        this.cacheContas = cacheContas;
        this.frame = parent;
    }

    // --------------------------------------------
    // MÉTODOS DE OPERAÇÕES BANCÁRIAS
    // --------------------------------------------
    public void cadastrarConta(ContaBancaria conta) {
        try {
            operacoesServices.cadastrarConta(conta);
            Mensagens.informacao("Conta cadastrada com sucesso!\nTitular: " + conta.getNome() + "\nNúmero da Conta: " + conta.getNumeroConta());
           cacheContas.atualizarCache(operacoesServices.listarTodasContasBancarias());
                 
        } catch (SaldoInsuficienteException | ContaExistenteException ex) {
            Mensagens.error(ex.getMessage());
        }
    }

    // --------------------------------------------
    // MÉTODO PARA ATUALIZAR CACHE + UI/TABELA
    // --------------------------------------------
    private void atualizarUI() {
        Collection dadosAtualizados = operacoesServices.listarTodasContasBancarias();
        cacheContas.atualizarCache(dadosAtualizados);
        // aqui você avisa seu TableModel para atualizar: tableModel.fireTableDataChanged();
    }

    // --------------------------------------------
    // MÉTODO PARA JANELAS
    // No padrão MVC, o Controller é justamente o lugar onde a lógica de fluxo da aplicação deve ficar,
    // sem incluir regras de negócio pesadas (essas ficam no Service/Model).
    // --------------------------------------------
    public void efetuarNovaConta(TipoConta tipo) {
        if (tipo == null) {
            return;
        }
        abrirViewContaBancaria(AcaoView.CADASTRAR, tipo);
    }

    private void abrirViewContaBancaria(AcaoView acaoView, TipoConta tipo) {
        ContasBancariaEditarCadastrar contasBancariaEditarCadastrar = new ContasBancariaEditarCadastrar(frame, true, acaoView, tipo, cadastroContaBancaria(tipo));
        contasBancariaEditarCadastrar.setTitle(getAcaoView(acaoView) + " " + getTitulo(tipo));
        contasBancariaEditarCadastrar.isVisibletxtInfoAdicionalConta(true);
        contasBancariaEditarCadastrar.setVisible(true);

        cadastrarConta(contasBancariaEditarCadastrar.getContaBancaria());
    }

    public String getTitulo(TipoConta tipo) {
        switch (tipo) {
            case CONTACORRENTE:
                return "Conta Corrente";
            case CONTAPOUPANCA:
                return "Conta Poupança";
            default:
                Mensagens.error("Conta não identificada");
                throw new IllegalArgumentException("Conta não identificada: " + tipo);
        }

    }

    public ContaBancaria cadastroContaBancaria(TipoConta tipo) {

        switch (tipo) {
            case CONTACORRENTE:
                return new ContaCorrente();
            case CONTAPOUPANCA:
                return new ContaPoupanca();
            default:
                Mensagens.error("Conta não identificada");
                throw new IllegalArgumentException("Conta não identificada: " + tipo);
        }

    }

    public String getAcaoView(AcaoView acaoView) {
        switch (acaoView) {
            case CADASTRAR:
                return "Cadastrar";
            case EDITAR:
                return "Editar";
            default:
                Mensagens.error("Conta não identificada");
                throw new IllegalArgumentException("Conta não identificada: " + acaoView);
        }

    }

    public boolean getInfoAdicional(TipoConta tipo) {
        switch (tipo) {
            case CONTACORRENTE:
                return false;
            case CONTAPOUPANCA:
                return true;
            default:
                Mensagens.error("Conta não identificada");
                throw new IllegalArgumentException("Conta não identificada: " + tipo);

        }
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

    7. O Controller é o único lugar onde deve aparecer JOptionPane e janelas;

    8. O Controller é o lugar CORRETO para capturar e tratar exceções vindas do Service.

 */
