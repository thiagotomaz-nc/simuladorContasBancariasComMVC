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
    public double getInfoAdicionalConta() {
      return saldoPoupanca;
    }

    @Override
    public String getDescricaoConta() {
      return "Conta Poupanca";
    }

    @Override
    public EnumTipoConta getTipoConta() {
        return EnumTipoConta.CONTAPOUPANCA;
    }

    @Override
    public boolean isInfoAdicionalConta() {
        return true;
    }

    @Override
    public void setInfoAdicionalConta(double valor) {
        saldoPoupanca=valor;
    }

    @Override
    public String getDescricaoInfoAdicionalCampo() {
      return "Saldo da Poupança [R$]:";
    }

 
    
    

}
