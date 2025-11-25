/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.contas.exercicio_02.model.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JDialog;

/**
 *
 * @author Thiago Tomaz
 */
public class ConfigDefaultSistema {
    //static → pertence à classe, não ao objeto
    //Como a classe IconeSistema não precisa ser instanciada (você nunca faz new IconeSistema()),
    //as constantes também devem ser acessíveis diretamente pela classe.
    private static final String COLOR_PRINCIPAL = "#EE6F15";
    private static final String COLOR_BACKGROUND = "#FAFAFA";
    private static final String ICONE_SISTEMA = "/Resources/logo/logo_bank_2.png";
    private static final String BACKGORUND_DIALOGS = "/Resources/background_Dialogs_01.png";
    

   //Uso de método estático para representar um recurso único e global do sistema, garantindo reuso, eficiência e simplicidade de acesso.
    public static Image getICONE_SISTEMA() {
       //Procure o recurso (imagem) relativo ao local onde a classe IconeSistema está carregada.
        return Toolkit.getDefaultToolkit().getImage(ConfigDefaultSistema.class.getResource(ICONE_SISTEMA));
    }
    
  
    
    
    public static Color getColor(){
        return  Color.decode(COLOR_PRINCIPAL);
    }
    
    public static Color getColorBackground(){
        return Color.decode(COLOR_BACKGROUND);
    }

    public static String getBACKGORUND_DIALOGS() {
        return BACKGORUND_DIALOGS;
    }
    
    
    
    public static void AplicarTransparencia(JDialog dialog){
       
        dialog.setBackground(new Color(0, 0, 0, 0));
    }
    
    
        
 /**
 * Classe utilitária responsável por fornecer o ícone padrão do sistema.
 *
 * O método getIcone() é declarado como 'static' porque o ícone do sistema
 * é um recurso único e compartilhado entre todas as janelas (JFrames, JDialogs, etc.).
 * 
 * Em vez de criar uma nova instância desta classe a cada uso, o método estático
 * permite acessar o mesmo ícone diretamente pela classe, garantindo:
 *
 * 1. Reuso: o mesmo ícone é utilizado em todo o sistema, sem duplicação.
 * 2. Eficiência: a imagem é carregada uma única vez na memória (cache interno).
 * 3. Simplicidade: não é necessário instanciar a classe para usar o recurso.
 *
 * Conceito: quando um dado ou comportamento é global, imutável e não depende
 * do estado de um objeto, faz sentido que ele pertença à classe em si,
 * e não a uma instância dela. Nesse caso, o uso de 'static' representa
 * justamente essa característica de recurso único e compartilhado.
 */         

}
