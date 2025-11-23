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
    public double getValorInternoConta() {
      return LIMITE_CREDITO;
    }

   

   

    

    }
