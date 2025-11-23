package br.com.contas.exercicio_02.model.classes;

public abstract class ContaBancaria {

    private String nome;
    private double saldo;
    private String numeroConta;

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void crediditar(double credito) {
        saldo += credito;
    }

    public void debitar(double debito) {
        saldo -= debito; // verificar se saldo é maior que 0
    }

    //inicio dos metodos abstratos
    public abstract String showSaldo();
    
    //serve tanto para retornar o valor do limite da conta corrente como o saldo da poupança, pois ambas são valores distintas e interna de cada conta.
    public abstract double getValorInternoConta();
}
