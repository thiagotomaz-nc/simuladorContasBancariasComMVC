package br.com.contas.exercicio_02.services;

import br.com.contas.exercicio_02.model.exception.ContaExistenteException;
import br.com.contas.exercicio_02.model.exception.NumeroContaVazioException;
import br.com.contas.exercicio_02.model.exception.SaldoInsuficienteException;
import br.com.contas.exercicio_02.model.classes.ContaBancaria;
import br.com.contas.exercicio_02.model.classes.ContaCorrente;
import br.com.contas.exercicio_02.model.classes.ContaPoupanca;
import br.com.contas.exercicio_02.repository.ContaBancariaRepositorio;
import java.util.Collection;
import javax.swing.JOptionPane;


//Artigo utilizado par ao padrão MVCS https://www.devmedia.com.br/padrao-mvc-java-magazine/21995

public class OperacoesBancariasServices {

    private ContaBancariaRepositorio contaBancariasBancariaRepositorio = new ContaBancariaRepositorio();

    public void creditarContaCorrente(ContaCorrente contaCorrente, double novoValor) {
        if (novoValor >= 0) {
            contaCorrente.crediditar(novoValor);
            JOptionPane.showMessageDialog(null, "Depósito na conta corrente realizado com sucesso\n\nTitular da conta: [ " + contaCorrente.getNome() + " ] \nNúmero da conta: [ " + contaCorrente.getNumeroConta() + " ]");
        } else {
            JOptionPane.showMessageDialog(null, "O valor não pode ser negativo");
        }
    }

    public void creditarContaPoupanca(ContaPoupanca contaPoupanca, double novoValor) {
        if (novoValor >= 0) {
            contaPoupanca.crediditar(novoValor);
            JOptionPane.showMessageDialog(null, "Depósito na conta poupança realizado com sucesso!\n\nTitular da conta: [ " + contaPoupanca.getNome() + " ]\nNúmero da conta: [" + contaPoupanca.getNumeroConta() + " ]");
        } else {
            // System.out.println("O valor não pode ser negativo");
            JOptionPane.showMessageDialog(null, "O valor não pode ser negativo");
        }
    }

    public void creditarEmPoupanca(ContaPoupanca contaPoupanca, double novoValor) {
        if (verificarSaldoSuficiente(contaPoupanca, novoValor)) {
            contaPoupanca.debitar(novoValor);
            contaPoupanca.setSaldoPoupanca(contaPoupanca.getSaldoPoupanca() + novoValor); // verificar depois
            JOptionPane.showMessageDialog(null, "Operação de débito do saldo da conta para crédito saldo da poupança realizada com sucesso!\nTitular da conta: [ " + contaPoupanca.getNome() + " ] \nNúmero da conta: [" + contaPoupanca.getNumeroConta() + " ]");

        } else {
            JOptionPane.showMessageDialog(null, "Saldo da conta insuficiente!\n\nNúmero da conta: [" + contaPoupanca.getNumeroConta() + " ]\nSaldo nesta conta na poupança: " + contaPoupanca.getSaldo());
        }
    }

    public boolean verificarSaldoSuficiente(ContaCorrente contaCorrente, double valor) {

        if (contaCorrente.mostraSaldoTotal() >= valor) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarSaldoSuficiente(ContaPoupanca contaPoupanca, double valor) {
        if (contaPoupanca.getSaldo() >= valor) {
            return true;
        } else {
            return false;
        }
    }

    public void debitarContaCorrente(ContaCorrente contaCorrente, double valorDebito) {
        if (verificarSaldoSuficiente(contaCorrente, valorDebito)) {
            contaCorrente.debitar(valorDebito);
            JOptionPane.showMessageDialog(null, "Débito realizado com sucesso\nTitular: " + contaCorrente.getNome() + "\nNúmero da conta: [ " + contaCorrente.getNumeroConta() + " ]");

        } else {
            JOptionPane.showMessageDialog(null, "Saldo insificiente para debitar na conta corrente.");
        }
    }

    public void debitarContaPoupanca(ContaPoupanca contaPoupanca, double valorDebito) {

        if (verificarSaldoSuficiente(contaPoupanca, valorDebito)) {
            contaPoupanca.debitar(valorDebito);
            JOptionPane.showMessageDialog(null, "Débito realizado na conta poupança com sucesso\nTitular: " + contaPoupanca.getNome() + "\nNúmero da conta: [" + contaPoupanca.getNumeroConta() + " ]");
        } else {
            JOptionPane.showMessageDialog(null, "Saldo insificiente para debitar conta poupança.");
        }
    }

    public void debitarDaPoupanca(ContaPoupanca contaPoupanca, double valorDebito) {
        if (vericarSaldoPoupancaSuficiente(contaPoupanca.getSaldoPoupanca(), valorDebito)) {
            double somaDebito = contaPoupanca.getSaldoPoupanca() - valorDebito;

            contaPoupanca.setSaldoPoupanca(somaDebito);
            contaPoupanca.crediditar(valorDebito);
            JOptionPane.showMessageDialog(null, "Operação de débito saldo da poupança  para crédito saldo da conta realizada com sucesso!\nTitular da conta: [ " + contaPoupanca.getNome() + " ] \nNúmero da conta: [" + contaPoupanca.getNumeroConta() + " ]");

        } else {
            JOptionPane.showMessageDialog(null, "Saldo da poupança insificiente para debitar conta poupança!\nSaldo poupança: " + contaPoupanca.getSaldoPoupanca());

        }
    }

    public boolean vericarSaldoPoupancaSuficiente(double saldPoupanca, double valor) {
        if (saldPoupanca >= valor) {
            return true;
        } else {
            return false;
        }
    }

    public void transferenciaBancaria(ContaCorrente contaOrigem, ContaCorrente contaDestino, double valor) {
        if (verificarSaldoSuficiente(contaOrigem, valor)) {
            contaOrigem.debitar(valor);
            contaDestino.crediditar(valor);
            JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nConta Destino: [ " + contaDestino.getNumeroConta() + " ]\nValor Transferido: " + valor);

        } else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar a transferência!\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nSaldo total: " + contaOrigem.getSaldo());
        }
    }

    public void transferenciaCorrenteParaPoupanca(ContaCorrente contaOrigem, ContaPoupanca contaDestino, double valor) {
        if (verificarSaldoSuficiente(contaOrigem, valor)) {
            contaOrigem.debitar(valor);
            contaDestino.crediditar(valor);
            JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nConta Destino: [ " + contaDestino.getNumeroConta() + " ]\nValor Transferido: " + valor);

        } else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar a transferência!\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nSaldo total: " + contaOrigem.getSaldo());

        }
    }

    public void transferenciaPoupancaParaCorrente(ContaPoupanca contaOrigem, ContaCorrente contaDestino, double valor) {
        if (verificarSaldoSuficiente(contaOrigem, valor)) {
            contaOrigem.debitar(valor);
            contaDestino.crediditar(valor);
            JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nConta Destino: [ " + contaDestino.getNumeroConta() + " ]\nValor Transferido: " + valor);

        } else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar a transferência!\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nSaldo total: " + contaOrigem.getSaldo());

        }
    }

    public void transferenciaPoupancaParaPoupanca(ContaPoupanca contaOrigem, ContaPoupanca contaDestino, double valor) {
        if (verificarSaldoSuficiente(contaOrigem, valor)) {
            contaOrigem.debitar(valor);
            contaDestino.crediditar(valor);
            JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nConta Destino: [ " + contaDestino.getNumeroConta() + " ]\nValor Transferido: " + valor);

        } else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar a transferência!\n\nConta Origem: [ " + contaOrigem.getNumeroConta() + " ]\nSaldo total: " + contaOrigem.getSaldo());

        }
    }

    
    //=============================================
    //*** Regras para poder cadastrar no repositorio***
    //=============================================
    public Collection<ContaBancaria> listarTodasContasBancarias() {
        return contaBancariasBancariaRepositorio.listarTodasContasBancariasRepository();
    }
    
   public ContaBancaria cadastrarConta(ContaBancaria conta) throws SaldoInsuficienteException, ContaExistenteException {
       //saldo não pode ser negativo
       if(conta.getSaldo() < 0){
           throw new SaldoInsuficienteException(conta.getNome()+", o seu saldo insuficiente para cadastrar a conta");
       }
       if(contaBancariasBancariaRepositorio.consultarContaExistente(conta.getNumeroConta())){
       throw new ContaExistenteException(conta.getNome()+" a conta já existe");
       }
       
       return contaBancariasBancariaRepositorio.cadastrarConta(conta);
   }

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
