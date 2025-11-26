package br.com.contas.exercicio_02.services;

import br.com.contas.exercicio_02.model.exception.ContaExistenteException;
import br.com.contas.exercicio_02.model.exception.SaldoInsuficienteException;
import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import br.com.contas.exercicio_02.model.classes.EnumTipoConta;
import br.com.contas.exercicio_02.model.classes.OperacaoBancaria;
import br.com.contas.exercicio_02.model.exception.NuloVazioInesxistenteException;

import br.com.contas.exercicio_02.model.exception.ListasVaziaException;
import br.com.contas.exercicio_02.model.exception.SaldoNegaticoException;
import br.com.contas.exercicio_02.model.util.ValidarValores;
import br.com.contas.exercicio_02.repository.ContaBancariaRepositorio;
import br.com.contas.exercicio_02.repository.OperacoesBancariasRepositorio;
import java.util.Collection;

//Artigo utilizado par ao padrão MVCS https://www.devmedia.com.br/padrao-mvc-java-magazine/21995
public class ContaBancariaServices {

    private ContaBancariaRepositorio contaBancariasBancariaRepositorio;
    private OperacoesBancariasRepositorio operacoesBancariasRepositorio;

    public ContaBancariaServices() {
        this.contaBancariasBancariaRepositorio = new ContaBancariaRepositorio();
        this.operacoesBancariasRepositorio = new OperacoesBancariasRepositorio();
    }

    //=============================================
    //*** Regras realizar as operações***
    //=============================================
    //=============================================
    //*** Regras o repositorio***
    //=============================================
    public Collection<ContaBancaria> listarTodasContasBancarias() {
        return contaBancariasBancariaRepositorio.listarTodasContasBancariasRepository();
    }

    public ContaBancaria cadastrarConta(ContaBancaria conta) throws ContaExistenteException, SaldoNegaticoException, NuloVazioInesxistenteException {
        //saldo não pode ser negativo
        //caso eles sejam utilizados novamente, transformar em metodos da classe services
        saldoPositivo(conta.getSaldo());
        saldoPositivo(conta.getInfoAdicionalConta());

        consultarContaExistente(conta.getNumeroConta());

        return contaBancariasBancariaRepositorio.cadastrarConta(conta);
    }

    public ContaBancaria editarConta(ContaBancaria contaBancaria) throws SaldoNegaticoException, NuloVazioInesxistenteException {
        saldoPositivo(contaBancaria.getSaldo());
        saldoPositivo(contaBancaria.getInfoAdicionalConta());
        ValidarValores.isNullEmpity(contaBancaria.getNumeroConta(), "Número da conta vazia!");

        return contaBancariasBancariaRepositorio.atualizarConta(contaBancaria);
    }

    public ContaBancaria excluirContaBancaria(ContaBancaria conta) {
        return contaBancariasBancariaRepositorio.excluirContaBancaria(conta);
    }

    //=============================================
    //*** Regras em metodos para serem reaproveitados***
    //=============================================
    public void saldoPositivo(double valor) throws SaldoNegaticoException {
        if (valor < 0) {
            throw new SaldoNegaticoException("Saldo(s) insuficiente para cadastrar a conta\nTente novamente!");
        }
    }

    public void consultarContaExistente(String numeroDaConta) throws ContaExistenteException {
        if (contaBancariasBancariaRepositorio.consultarContaExistente(numeroDaConta.trim())) {
            throw new ContaExistenteException("Número da conta indisponivel");
        }
    }

    public Collection<ContaBancaria> filtrarContas(EnumTipoConta tipoContaSelecionada) {
        if (tipoContaSelecionada == tipoContaSelecionada.BANCARIA) {
            return contaBancariasBancariaRepositorio.listarTodasContasBancariasRepository();
        }
        return contaBancariasBancariaRepositorio.filtrarContaBancaria(tipoContaSelecionada);
    }

    public ContaBancaria filtrarContaBancariaUnitaria(String text) throws ListasVaziaException {
        ContaBancaria contaBancaria = contaBancariasBancariaRepositorio.filtrarContaBancariaUnitaria(text);

        if (contaBancaria == null) {
            throw new ListasVaziaException("Nenhuma conta encontrada");
        }
        return contaBancaria;
    }

    public void cadastrarOperacaoBancariaDebitoCredito(OperacaoBancaria op, ContaBancaria contaBancaria) throws NuloVazioInesxistenteException, SaldoNegaticoException, ContaExistenteException {

        saldoPositivo(op.getValorTransferido());

        ContaBancaria cb = contaBancaria;

        if (cb != null) {
            creditarContaBancaria(cb, op);

        } else {
            throw new NuloVazioInesxistenteException("Conta Bancaria Inexistente");
        }
    }

    private void creditarContaBancaria(ContaBancaria cb, OperacaoBancaria op) throws ContaExistenteException {

        op.setCodigoOperacao(gerarChaveDasOperacoes());
        consultarOperacaoExistente(op.getCodigoOperacao());

        cb.crediditar(op.getValorTransferido());
        operacoesBancariasRepositorio.salvarOperacao(op.getCodigoOperacao(), op);

    }

    private String gerarChaveDasOperacoes() {
        Long millisegundos = System.currentTimeMillis();
        return millisegundos + "";
    }

    //=============================================
    //*** REPOSITORIO DE OPERACOES
    //=============================================
    public Collection<OperacaoBancaria> listarTodasOperacaoRepository() {
        return operacoesBancariasRepositorio.listarTodasOperacaoValuesRepository();

    }

    //=============================================
    //*** Cadastrar OPERAÇÕES DE CRÉDITO
    //=============================================
    public void cadastrarOperacaoBancariaDebito(OperacaoBancaria op, ContaBancaria contaBancaria) throws SaldoInsuficienteException, SaldoNegaticoException, NuloVazioInesxistenteException, ContaExistenteException {
        saldoPositivo(op.getValorTransferido());

        if (contaBancaria != null) {

            verificarSaldoSuficiente(getSaldo(contaBancaria), op.getValorTransferido());
            debitarDaConta(contaBancaria, op);
        } else {
            throw new NuloVazioInesxistenteException("Conta Bancaria Inexistente");
        }

    }

    //=============================================
    //*** OPERAÇÕES DE DÉBITO
    //=============================================
    private void debitarDaConta(ContaBancaria contaBancaria, OperacaoBancaria op) throws ContaExistenteException {
        op.setCodigoOperacao(gerarChaveDasOperacoes());
        consultarOperacaoExistente(op.getCodigoOperacao());
        contaBancaria.debitar(op.getValorTransferido());
        operacoesBancariasRepositorio.salvarOperacao(op.getCodigoOperacao(), op);

    }

    private double getSaldo(ContaBancaria contaBancaria) {
        if (contaBancaria.getTipoConta() == EnumTipoConta.CONTACORRENTE) {
            return contaBancaria.getSaldo() + contaBancaria.getInfoAdicionalConta();
        }

        return contaBancaria.getSaldo();
    }

    public void verificarSaldoSuficiente(double saldo, double valor) throws SaldoInsuficienteException {

        if (saldo < valor) {
            throw new SaldoInsuficienteException("O valor é maior que o saldo! Débito não realizado!");
        }

    }

    private void consultarOperacaoExistente(String codigoOperacao) throws ContaExistenteException {
        if (operacoesBancariasRepositorio.consultarOperacaoExistente(codigoOperacao)) {
            throw new ContaExistenteException("Operacão já existe no sistema!");
        }
    }

    public void cadastrarOperacaoBancariaCreditarSaldoPoupanca(OperacaoBancaria op, ContaBancaria contaBancaria) throws SaldoNegaticoException, SaldoInsuficienteException, ContaExistenteException {
        saldoPositivo(op.getValorTransferido());
        verificarSaldoSuficiente(getSaldo(contaBancaria), contaBancaria.getInfoAdicionalConta());

        op.setCodigoOperacao(gerarChaveDasOperacoes());
        consultarOperacaoExistente(op.getCodigoOperacao());
        
        contaBancaria.debitar(op.getValorTransferido());
        contaBancaria.setInfoAdicionalConta(contaBancaria.getInfoAdicionalConta()+op.getValorTransferido());
        
        operacoesBancariasRepositorio.salvarOperacao(op.getCodigoOperacao(), op);
    }
    
    public void cadastrarOperacaoBancariaDebitarSaldoPoupanca(OperacaoBancaria op, ContaBancaria contaBancaria) throws SaldoNegaticoException, SaldoInsuficienteException, ContaExistenteException {
        saldoPositivo(op.getValorTransferido());
        verificarSaldoSuficiente(contaBancaria.getInfoAdicionalConta(),getSaldo(contaBancaria));

        op.setCodigoOperacao(gerarChaveDasOperacoes());
        consultarOperacaoExistente(op.getCodigoOperacao());
        
        contaBancaria.setInfoAdicionalConta(contaBancaria.getInfoAdicionalConta() - op.getValorTransferido());
        contaBancaria.crediditar(op.getValorTransferido());
        
        operacoesBancariasRepositorio.salvarOperacao(op.getCodigoOperacao(), op);
    }
        
    
    //*************************************
    //*** FIM DAS OPERACOES
    //*************************************


}

/*
 * Camada Service – Responsabilidades e Garantias
 *
 * 1. Contém as regras de negócio associadas à ContaBancaria.
 *    Aqui ficam validações, cálculos e políticas (ex.: evitar saldo negativo).
 *
 * 2. Faz a mediação entre o Controller e o Repository.
 *    O Controller apenas chama o Service; nunca acessa o Repository diretamente.
 *
 * 3. Garante consistência dos dados:
 *      - Valida entrada
 *      - Verifica integridade
 *      - Aplica regras de negócio
 *
 * 4. Todas as operações de manipulação de contas (criar, editar, remover)
 *    devem passar por métodos do Service, nunca pelo Controller.
 *
 * 5. O Service atualiza o HashMap/Repository e retorna os objetos atualizados.
 *    A UI deve refletir estas atualizações usando atualizarCache().
 *
 * 6. O Service NÃO deve conhecer UI, JTable ou componentes visuais.
 *    Ele trabalha somente com objetos de domínio (ContaBancaria).
 *
 * **Fluxo esperado:**
 * Controller → Service → Repository → (Service retorna objeto atualizado) → UI atualiza cache.
 */
