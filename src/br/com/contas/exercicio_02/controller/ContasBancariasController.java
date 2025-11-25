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
import br.com.contas.exercicio_02.model.exception.CampoNuloVazioException;
import br.com.contas.exercicio_02.model.exception.ListasVaziaException;
import br.com.contas.exercicio_02.services.ContaBancariaServices;
import br.com.contas.exercicio_02.model.util.Mensagens;
import br.com.contas.exercicio_02.model.util.ValidationValores;
import br.com.contas.exercicio_02.view.EnumAcaoView;
import br.com.contas.exercicio_02.view.ContasBancariaEditarCadastrar;
import br.com.contas.exercicio_02.view.TipoContaIG;
import br.com.contas.exercicio_02.view.table.CacheContas;
import br.com.contas.exercicio_02.view.table.ContaBancariaTableModel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Thiago Tomaz
 */
//Artigo utilizado par ao padrão MVCS https://www.devmedia.com.br/padrao-mvc-java-magazine/21995
public class ContasBancariasController {

    private final ContaBancariaServices contaBancariaServices;
    private final CacheContas cacheContas;
    private JFrame frame;
    private ContaBancariaTableModel contaBancariaModeloTable;
    private static final Logger LOGGER = Logger.getLogger(ContasBancariasController.class.getName());
    private EnumAcaoView acaoView;
   
    // por que utilizar o static, pois sem o static teria que ter um Logger novo para cada objeto, o que não faz sentido.
    // Logger não precisa saber quantos objetos existem.
    // Ele só precisa saber qual classe está registrando mensagens.

    public ContasBancariasController(JFrame parent) {
        this.contaBancariaServices = new ContaBancariaServices();
        this.cacheContas = new CacheContas();
        this.frame = parent;
        this.contaBancariaModeloTable = new ContaBancariaTableModel(cacheContas);
    }

    public ContaBancariaTableModel getContaBancariaModeloTable() {
        return contaBancariaModeloTable;
    }

    // --------------------------------------------
    // MÉTODOS DE OPERAÇÕES BANCÁRIAS
    // --------------------------------------------
    private void cadastrarConta(ContaBancaria conta) {
        try {
            contaBancariaServices.cadastrarConta(conta);
            Mensagens.informacao("Conta cadastrada com sucesso!\nTitular: " + conta.getNome() + "\nNúmero da Conta: " + conta.getNumeroConta());
            atualizarCacheDeContasBancarias();

        } catch (SaldoInsuficienteException | ContaExistenteException ex) {
            Mensagens.error(ex.getMessage());
        } catch (NumeroContaVazioException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    private void editarConta(ContaBancaria contaBancaria) {
        try {
            contaBancariaServices.editarConta(contaBancaria);
            Mensagens.informacao("Conta atualizada com sucesso!\nTitular: " + contaBancaria.getNome() + "\nNúmero da Conta: " + contaBancaria.getNumeroConta());
            atualizarCacheDeContasBancarias();
        } catch (SaldoInsuficienteException ex) {
            Mensagens.error(ex.getMessage());
        } catch (NumeroContaVazioException ex) {
            LOGGER.info(ex.getMessage());
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
    
    

    // --------------------------------------------
    // MÉTODO PARA JANELAS
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
                return;

        }

    }

    public void FiltrarConta(EnumTipoConta tipoContaSelecionada) {
        ArrayList<ContaBancaria> resultadoFiltro = new ArrayList<>(contaBancariaServices.filtrarContas(tipoContaSelecionada));
        if(resultadoFiltro.size() >0){
            cacheContas.atualizarCache(resultadoFiltro);
            contaBancariaModeloTable.atualizarTabela();
        }
    }

    public void consultarContaBancariaNumeroDaConta(String text) {
        try {
            ValidationValores.isNullEmpity(text.trim(), "O campo esta vazio ou incompleto!");
            
            ArrayList<ContaBancaria> consultaTemp = new ArrayList<>();
            consultaTemp.add(contaBancariaServices.filtrarContaBancariaUnitaria(text));
            cacheContas.atualizarCache(consultaTemp);
            contaBancariaModeloTable.atualizarTabela();
            
            Mensagens.informacao("Conta encontrada com sucesso!");
            
        } catch (CampoNuloVazioException ex) {
            Mensagens.error(ex.getMessage());
            return;
        } catch (ListasVaziaException ex) {
           Mensagens.error(ex.getMessage());
           return;
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
