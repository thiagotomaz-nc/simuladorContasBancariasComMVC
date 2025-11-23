package br.com.contas.exercicio_02.model.classes;

public class ContaPoupanca extends ContaBancaria {

    private double saldoPoupanca = 0;

    public double getSaldoPoupanca() {
        return saldoPoupanca;
    }

    public void setSaldoPoupanca(double saldoPoupanca) {
        this.saldoPoupanca = saldoPoupanca;
    }

    @Override
    public String showSaldo() {
       return "Olá, " + getNome() + ", seu saldo total é R$ " + (getSaldo()) + " reais.";
    }

    @Override
    public double getValorInternoConta() {
      return saldoPoupanca;
    }
    
    

}
