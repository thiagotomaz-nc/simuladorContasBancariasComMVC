package br.com.contas.exercicio_02.model.classes;

public abstract class ContaBancaria {

    private String nome;
    private double saldo;
    private String numeroConta;
    private EnumTipoConta tipoConta;

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

    // ** METODOS ABSTRATOS ** //
    public abstract String showSaldo();
    //
    public abstract String getDescricaoInfoAdicionalCampo();
    public abstract void setInfoAdicionalConta(double valor);
    //serve tanto para retornar o valor do limite da conta corrente como o saldo da poupança, pois ambas são valores distintas e interna de cada conta.
    public abstract double getInfoAdicionalConta();
    //informa se o campo onde a informação adicional esta pode ser editado ou não;
    public abstract boolean isInfoAdicionalConta();
    //serve tanto para retornar o valor do limite da conta corrente como o saldo da poupança, pois ambas são valores distintas e interna de cada conta.
    public abstract String getDescricaoConta();
    //cada subclasse vai retornar o seu tipo de conta
    public abstract EnumTipoConta getTipoConta();
}
