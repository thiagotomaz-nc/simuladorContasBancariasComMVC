/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.controller;

import br.com.contas.exercicio_02.model.exception.ContaExistenteException;
import br.com.contas.exercicio_02.model.exception.NumeroContaVazioException;
import br.com.contas.exercicio_02.model.exception.SaldoInsuficienteException;
import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import br.com.contas.exercicio_02.model.classes.ContaCorrente;
import br.com.contas.exercicio_02.model.classes.ContaPoupanca;
import br.com.contas.exercicio_02.model.classes.EnumTipoConta;
import br.com.contas.exercicio_02.model.exception.NuloVazioInesxistenteException;
import br.com.contas.exercicio_02.model.exception.ListasVaziaException;
import br.com.contas.exercicio_02.services.ContaBancariaServices;
import br.com.contas.exercicio_02.model.util.Mensagens;
import br.com.contas.exercicio_02.model.util.ValidarValores;
import br.com.contas.exercicio_02.view.ContaBancariaDepositarDebitar;
import br.com.contas.exercicio_02.view.EnumAcaoView;
import br.com.contas.exercicio_02.view.ContasBancariaEditarCadastrar;
import br.com.contas.exercicio_02.view.OperacoesBancariasView;
import br.com.contas.exercicio_02.view.TipoContaIG;
import br.com.contas.exercicio_02.model.classes.EnumTipoOperacoes;
import br.com.contas.exercicio_02.model.classes.OperacaoCredito;
import br.com.contas.exercicio_02.model.classes.OperacaoDebito;
import br.com.contas.exercicio_02.model.classes.OperacaoTransferenciaEntreContas;
import br.com.contas.exercicio_02.model.classes.OperacaoBancaria;
import br.com.contas.exercicio_02.model.classes.OperacaoCreditoSaldoPoupanca;
import br.com.contas.exercicio_02.model.classes.OperacaoDebitoSaldoPoupanca;
import br.com.contas.exercicio_02.model.exception.ClassNotPopancaException;
import br.com.contas.exercicio_02.model.exception.ContasDestinoOrigemIguaisException;
import br.com.contas.exercicio_02.model.exception.SaldoNegaticoException;
import br.com.contas.exercicio_02.view.TransferenciaEntreContasBancariasIG;
import br.com.contas.exercicio_02.view.table.CacheContas;
import br.com.contas.exercicio_02.view.table.CacheOperacoesBancararias;
import br.com.contas.exercicio_02.view.table.ContaBancariaTableModel;
import br.com.contas.exercicio_02.view.table.OperacoesBancariasTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Thiago Tomaz
 */
//Artigo utilizado par ao padrão MVCS https://www.devmedia.com.br/padrao-mvc-java-magazine/21995
public class ContaBancariaController {

    private final ContaBancariaServices contaBancariaServices;
    private final CacheContas cacheContas;
    private JFrame frame;
    private ContaBancariaTableModel contaBancariaModeloTable;
    private static final Logger LOGGER = Logger.getLogger(ContaBancariaController.class.getName());
    private EnumAcaoView acaoView;

    private OperacoesBancariasTableModel operacoesBancariasTableModel;
    private CacheOperacoesBancararias cacheOperacoesBancararias;

    // por que utilizar o static, pois sem o static teria que ter um Logger novo para cada objeto, o que não faz sentido.
    // Logger não precisa saber quantos objetos existem.
    // Ele só precisa saber qual classe está registrando mensagens.
    public ContaBancariaController(JFrame parent) {
        this.contaBancariaServices = new ContaBancariaServices();
        this.cacheContas = new CacheContas();
        this.frame = parent;
        this.contaBancariaModeloTable = new ContaBancariaTableModel(cacheContas);
        this.cacheOperacoesBancararias = new CacheOperacoesBancararias();
        this.operacoesBancariasTableModel = new OperacoesBancariasTableModel(cacheOperacoesBancararias);
    }

    // --------------------------------------------
    // MODELOS DE TABELAS
    // --------------------------------------------
    public ContaBancariaTableModel getContaBancariaModeloTable() {
        return contaBancariaModeloTable;
    }

    public OperacoesBancariasTableModel getOperacaoModeloTable() {
        return operacoesBancariasTableModel;
    }

    // --------------------------------------------
    // MÉTODOS DE CONTAS BANCÁRIAS
    // --------------------------------------------
    private void cadastrarConta(ContaBancaria conta) {
        try {
            contaVaziaNaoInstanciada(conta);
            contaBancariaServices.cadastrarConta(conta);
            Mensagens.informacao("Conta cadastrada com sucesso!\nTitular: " + conta.getNome() + "\nNúmero da Conta: " + conta.getNumeroConta());
            atualizarCacheDeContasBancarias();

        } catch (SaldoNegaticoException | ContaExistenteException ex) {
            Mensagens.error(ex.getMessage());
        } catch (NuloVazioInesxistenteException ex) {
            LOGGER.info("Conta bancaria não instanciada!");
        }
    }

    private void editarConta(ContaBancaria contaBancaria) {
        try {
            contaVaziaNaoInstanciada(contaBancaria);
            contaBancariaServices.editarConta(contaBancaria);
            Mensagens.informacao("Conta atualizada com sucesso!\nTitular: " + contaBancaria.getNome() + "\nNúmero da Conta: " + contaBancaria.getNumeroConta());
            atualizarCacheDeContasBancarias();
        } catch (SaldoNegaticoException | NuloVazioInesxistenteException ex) {
            Mensagens.error(ex.getMessage());
        }
    }

    public void excluirContaBancaria(int linha) {
        if (linha > -1) {
            ContaBancaria contaBancariaExcluir = cacheContas.consultarConta(linha);
            int resposta = Mensagens.confirmar("Deseja realmente excluir essa conta?\n\nNúmero: " + contaBancariaExcluir.getNumeroConta() + "\nTitular: " + contaBancariaExcluir.getNome());
            if (resposta == JOptionPane.YES_OPTION) {
                if (contaBancariaServices.excluirContaBancaria(contaBancariaExcluir) == null) {
                    Mensagens.error("Conta não encontrara");
                } else {
                    atualizarCacheDeContasBancarias();
                    Mensagens.informacao("Conta excluida com sucesso");
                }

            }
        }
    }

    // --------------------------------------------
    // MÉTODO PARA ATUALIZAR CACHE + IG da TABELA
    // --------------------------------------------
    private void atualizarCacheDeContasBancarias() {
        cacheContas.atualizarCache(contaBancariaServices.listarTodasContasBancarias());
        this.contaBancariaModeloTable.fireTableDataChanged();
    }

    private void atualizarCacheDeOperacoes() {
        cacheOperacoesBancararias.atualizarCache(contaBancariaServices.listarTodasOperacaoRepository());
        this.operacoesBancariasTableModel.fireTableDataChanged();
    }

    // --------------------------------------------
    // MÉTODOS DE OPERAÇÕES BANCÁRIAS
    // --------------------------------------------
    public void abrirOperacoesBancariasView() {
        if (cacheOperacoesBancararias.sizeCache() == 0) {
            Mensagens.error("Nenhuma Operação de transferência realizada!");
            return;
        }

        OperacoesBancariasView operacoesBancariasView = new OperacoesBancariasView(frame, true, this);
        operacoesBancariasView.setTitle("Operações Bancárias");
        operacoesBancariasView.setVisible(true);
    }

    public void abrirViewRealizarOperacoesDebitoCredito(EnumTipoOperacoes tipoOperacoes, String titulo) {
        try {
            verificarTotalContasCadastradas();
        } catch (ListasVaziaException ex) {
            Mensagens.error(ex.getMessage());
            return;
        }

        OperacaoBancaria op = getOperacao(tipoOperacoes);

        ContaBancariaDepositarDebitar contasBancariaEditarCadastrar = new ContaBancariaDepositarDebitar(frame, true, op, titulo, this, null);
        contasBancariaEditarCadastrar.setVisible(true);

        try {
            operacaoVaziaNaoInstanciada(op);
        } catch (NuloVazioInesxistenteException ex) {
            return;
        }

        if (op.getTipoOperacoes() == EnumTipoOperacoes.CREDITAR) {
            cadastrarOperacaoBancariaCredito(op, contasBancariaEditarCadastrar.getContaBancaria());
        } else if (op.getTipoOperacoes() == EnumTipoOperacoes.DEBITAR) {
            //trocar a operação numero Conta destino para numero conta origem
            //por que a troca, pois o alvo do debito é o numero da conta de origem 
            String aux = op.getContaOrigem();
            op.setContaOrigem(op.getContaDestino());
            op.setContaDestino(aux);
            cadastrarOperacaoBancariaDebito(op, contasBancariaEditarCadastrar.getContaBancaria());
        } else if (op.getTipoOperacoes() == EnumTipoOperacoes.CREDITAR_SALDO_POUPANCA) {
            op.setContaOrigem(op.getContaDestino());
            cadastrarOperacaoBancariaCreditarSaldoPoupanca(op, contasBancariaEditarCadastrar.getContaBancaria());
        } else if (op.getTipoOperacoes() == EnumTipoOperacoes.DEBITAR_SALDO_POUPANCA) {
            op.setContaOrigem(op.getContaDestino());
            cadastrarOperacaoBancariaDebitarSaldoPoupanca(op, contasBancariaEditarCadastrar.getContaBancaria());
        }
    }

    private void cadastrarOperacaoBancariaDebitarSaldoPoupanca(OperacaoBancaria op, ContaBancaria contaBancaria) {
        try {
            verificarIsPoupanca(contaBancaria);
            contaBancariaServices.cadastrarOperacaoBancariaDebitarSaldoPoupanca(op, contaBancaria);
            atualizarCacheDeOperacoes();
            atualizarCacheDeContasBancarias();
            Mensagens.informacao("Débito no saldo da poupança realizada com sucesso!");
        } catch (SaldoNegaticoException | SaldoInsuficienteException | ContaExistenteException | ClassNotPopancaException ex) {
            Mensagens.error(ex.getMessage());
        }
    }

    private void cadastrarOperacaoBancariaCreditarSaldoPoupanca(OperacaoBancaria op, ContaBancaria contaBancaria) {
        try {
            verificarIsPoupanca(contaBancaria);
            contaBancariaServices.cadastrarOperacaoBancariaCreditarSaldoPoupanca(op, contaBancaria);
            atualizarCacheDeOperacoes();
            atualizarCacheDeContasBancarias();
            Mensagens.informacao("Crédito no saldo da poupança realizada com sucesso!");
        } catch (SaldoNegaticoException | SaldoInsuficienteException | ContaExistenteException | ClassNotPopancaException ex) {
            Mensagens.error(ex.getMessage());
        }
    }

    public OperacaoBancaria getOperacao(EnumTipoOperacoes tipoConta) {
        switch (tipoConta) {
            case CREDITAR:
                return new OperacaoCredito();
            case DEBITAR:
                return new OperacaoDebito();
            case TRANSFERIR:
                return new OperacaoTransferenciaEntreContas();
            case CREDITAR_SALDO_POUPANCA:
                return new OperacaoCreditoSaldoPoupanca();
            case DEBITAR_SALDO_POUPANCA:
                return new OperacaoDebitoSaldoPoupanca();
            default:
                throw new IllegalArgumentException("Operação encontrada");
        }
    }

    private void cadastrarOperacaoBancariaCredito(OperacaoBancaria op, ContaBancaria contaBancaria) {
        try {
            contaBancariaServices.cadastrarOperacaoBancariaDebitoCredito(op, contaBancaria);
            Mensagens.informacao("Operação realizada com sucesso!");
            atualizarCacheDeContasBancarias();
            atualizarCacheDeOperacoes();
        } catch (ContaExistenteException | SaldoNegaticoException | NuloVazioInesxistenteException ex) {
            Mensagens.error(ex.getMessage());
        }
    }

    private void cadastrarOperacaoBancariaDebito(OperacaoBancaria op, ContaBancaria contaBancaria) {
        try {
            contaBancariaServices.cadastrarOperacaoBancariaDebito(op, contaBancaria);
            Mensagens.informacao("Operação realizada com sucesso!");
            atualizarCacheDeContasBancarias();
            atualizarCacheDeOperacoes();
        } catch (SaldoInsuficienteException | SaldoNegaticoException | NuloVazioInesxistenteException | ContaExistenteException ex) {
            Mensagens.error(ex.getMessage());
        }
    }

    private void operacaoVaziaNaoInstanciada(OperacaoBancaria operacaoBancaria) throws NuloVazioInesxistenteException {
        if (operacaoBancaria == null || operacaoBancaria.getContaDestino() == null) {
            throw new NuloVazioInesxistenteException();
        }
    }

    private void verificarIsPoupanca(ContaBancaria contaBancaria) throws ClassNotPopancaException {
        if (contaBancaria.getTipoConta() != EnumTipoConta.CONTAPOUPANCA) {
            throw new ClassNotPopancaException("Operação disponivel apenas para contas do tipo poupança!");
        }
    }

    // --------------------------------------------
    // MÉTODO PARA JANELAS CONTAS BANCARIAS
    // No padrão MVC, o Controller é justamente o lugar onde a lógica de fluxo da aplicação deve ficar,
    // sem incluir regras de negócio pesadas (essas ficam no Service/Model).
    // --------------------------------------------
    public void efetuarNovaContaView(EnumTipoConta tipo) {
        if (tipo == null) {
            return;
        }
        acaoView = EnumAcaoView.CADASTRAR;
        novaContaBancariaView(tipo);
    }

    private void novaContaBancariaView(EnumTipoConta tipo) {

        ContaBancaria contaBancariaNova = tipoContaBancaria(tipo);
        String nomeAcao = getPreTituloAcaoView(acaoView);
        abrirViewContaBancariaView(nomeAcao, contaBancariaNova);
    }

    public void editarContaBancariaView(EnumAcaoView acaoView, int linha) {
        if (linha > -1) {
            this.acaoView = acaoView;
            String nomeAcao = getPreTituloAcaoView(this.acaoView);
            abrirViewContaBancariaView(nomeAcao, cacheContas.consultarConta(linha));
        }
    }

    private void abrirViewContaBancariaView(String preTitulo, ContaBancaria conta) {

        ContasBancariaEditarCadastrar contasBancariaEditarCadastrar = new ContasBancariaEditarCadastrar(frame, true, conta);
        contasBancariaEditarCadastrar.setTitle(preTitulo + " " + conta.getDescricaoConta());
        contasBancariaEditarCadastrar.setVisible(true);
        contasBancariaEditarCadastrar.setNomeDoBotao(preTitulo + " " + conta.getDescricaoConta());

        getCadastrarEditarContaBancaria(contasBancariaEditarCadastrar.getContaBancaria());

    }

    public ContaBancaria tipoContaBancaria(EnumTipoConta tipo) {

        switch (tipo) {
            case CONTACORRENTE:
                return new ContaCorrente();
            case CONTAPOUPANCA:
                return new ContaPoupanca();
            default:
                Mensagens.error("Conta não identificada");
                return null;
        }

    }

    public String getPreTituloAcaoView(EnumAcaoView acaoView) {
        switch (acaoView) {
            case CADASTRAR:
                return "Cadastrar";
            case EDITAR:
                return "Editar";
            default:
                Mensagens.error("Conta não identificada");
                return "";
        }

    }

    private void getCadastrarEditarContaBancaria(ContaBancaria contaBancaria) {
        switch (acaoView) {
            case CADASTRAR:
                cadastrarConta(contaBancaria);
                break;
            case EDITAR:
                editarConta(contaBancaria);
                break;
            default:
                Mensagens.error("Operação não encontrada");

        }
    }

    public void FiltrarConta(EnumTipoConta tipoContaSelecionada) {
        if (cacheContas.sizeCache() == 0) {
            Mensagens.error("Nenhuma conta Cadastrada!");
            return;
        }
        ArrayList<ContaBancaria> resultadoFiltro = new ArrayList<>(contaBancariaServices.filtrarContas(tipoContaSelecionada));
        if (resultadoFiltro.size() > 0) {
            cacheContas.atualizarCache(resultadoFiltro);
            contaBancariaModeloTable.atualizarTabela();
        }
    }

    public void consultarContaBancariaNumeroDaContaViewPrincipal(String text) {
        try {
            ValidarValores.isNullEmpity(text.trim(), "O campo esta vazio ou incompleto!");

            ArrayList<ContaBancaria> consultaTemp = new ArrayList<>();
            consultaTemp.add(contaBancariaServices.filtrarContaBancariaUnitaria(text));
            cacheContas.atualizarCache(consultaTemp);
            contaBancariaModeloTable.atualizarTabela();

            Mensagens.informacao("Conta encontrada com sucesso!");

        } catch (NuloVazioInesxistenteException ex) {
            Mensagens.error(ex.getMessage());
            return;
        } catch (ListasVaziaException ex) {
            Mensagens.error(ex.getMessage());
            return;
        }
    }

    public ContaBancaria consultarContaBancariaNumeroDaConta(String text) {
        try {
            verificarTotalContasCadastradas();
            ValidarValores.isNullEmpity(text.trim(), "O campo esta vazio ou incompleto!");
            ContaBancaria cb = contaBancariaServices.filtrarContaBancariaUnitaria(text);
            return cb;

        } catch (NuloVazioInesxistenteException | ListasVaziaException ex) {
            Mensagens.error(ex.getMessage());
            return null;
        }
    }

    public void validarViewContaOrigemDestino(String text, TransferenciaEntreContasBancariasIG janela, int indiceConta) {

        ContaBancaria contaBancaria = consultarContaBancariaNumeroDaConta(text);

        if (contaBancaria != null) {
            janela.isContaInValida(contaBancaria, "/resources/icones_32/check.png", indiceConta);
        } else {
            janela.isContaInValida(contaBancaria, "", indiceConta);
        }
    }

    private void verificarTotalContasCadastradas() throws ListasVaziaException {
        if (cacheContas.getCacheContasBancarias().size() == 0) {
            throw new ListasVaziaException("Cadastre 1 conta para efetuar alguma operação báncaria");
        }
    }

    private void contaVaziaNaoInstanciada(ContaBancaria conta) throws NuloVazioInesxistenteException {
        if (conta == null) {
            throw new NuloVazioInesxistenteException();
        }
    }

    public void abrirViewTransferenciaEntreContas(EnumTipoOperacoes enumTipoOperacoes, String text) {

        TransferenciaEntreContasBancariasIG transferenciaEntreContasBancariasIG = new TransferenciaEntreContasBancariasIG(frame, true, this, text);
        transferenciaEntreContasBancariasIG.setVisible(true);

        ContaBancaria[] conta = transferenciaEntreContasBancariasIG.getContasBancariasOrigemDestino();

        if (conta != null) {
            realizarTransferenciaEntreContas(conta, transferenciaEntreContasBancariasIG.getValorTransferencia());
        }

    }

    private void realizarTransferenciaEntreContas(ContaBancaria[] conta, double valorTransferencia) {
        try {
            OperacaoBancaria op = getOperacao(EnumTipoOperacoes.TRANSFERIR);
            op.setValorTransferido(valorTransferencia);
            op.setDataTransferencia(LocalDateTime.now());
            op.setContaOrigem(conta[0].getNumeroConta());
            op.setContaDestino(conta[1].getNumeroConta());
            verificarContaOrigemDestinoIguais(op);

            contaBancariaServices.transferenciaEntreContas(op, conta);

            atualizarCacheDeOperacoes();
            atualizarCacheDeContasBancarias();
            Mensagens.informacao("Transferencia entre as conta: [ " + conta[0].getNumeroConta() + " : " + conta[1].getNumeroConta() + " ] realizada com sucesso!");
        } catch (SaldoNegaticoException | SaldoInsuficienteException | ContaExistenteException | ContasDestinoOrigemIguaisException ex) {
            Mensagens.error(ex.getMessage());
        }
    }

    private void verificarContaOrigemDestinoIguais(OperacaoBancaria op) throws ContasDestinoOrigemIguaisException {
        if (op.getContaOrigem().trim().equals(op.getContaDestino())) {
            throw new ContasDestinoOrigemIguaisException("As contas de Origem e destino não podem ser a mesma!");
        }

    }

    public void consultarViewContaBancariaNumeroDaConta(String text, ContaBancariaDepositarDebitar janela) {
        ContaBancaria contaBancaria = consultarContaBancariaNumeroDaConta(text);
        if (contaBancaria != null) {
            janela.contaValida(contaBancaria);
        } else {
            janela.contaInvalida(contaBancaria);
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
