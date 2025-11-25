package br.com.contas.exercicio_02.model.classes;

public class ContaCorrente extends ContaBancaria {

    private  double LIMITE_CREDITO = 100;

    public ContaCorrente() {
        super();
    }
    
    public double getLimiteCredito(){
        return LIMITE_CREDITO;
    }
           
    public double mostraSaldoTotal() {
        return getSaldo() + LIMITE_CREDITO;
    }
      
    @Override
    public String showSaldo(){
        return "Olá, " + getNome() + ", seu saldo total é R$ " + (getSaldo() + LIMITE_CREDITO) + " reais.";
    }

    @Override
    public double getInfoAdicionalConta() {
      return LIMITE_CREDITO;
    }

    @Override
    public String getDescricaoConta() {
        return "Conta Corrente";
    }

    @Override
    public EnumTipoConta getTipoConta() {
        return EnumTipoConta.CONTACORRENTE;
    }

    @Override
    public boolean isInfoAdicionalConta() {
       return false;
    }

    @Override
    public void setInfoAdicionalConta(double valor) {
       
    }

    @Override
    public String getDescricaoInfoAdicionalCampo() {
       return "Limite Conta Corrente [R$]:";
    }
    
    


    }
