/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;

import static default_package.Main.arraycelula;
import static default_package.Main.date;
import static default_package.Main.dateFormat;
import static default_package.Main.tmpJogador;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.WindowEvent;



/**
 *
 * @author main4db
 */
public class Actions { //conversão de tempo no final - na salvaguarda de dados 
    	public static void ExecuteNewGame(){
            
              TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText("Bem vindo!");
                dialog.setTitle("Novo Jogo");
                dialog.setContentText("Nome (mais de 5 carateres): ");
                //dialog.setGraphic(new ImageView(this.getClass().getResource("img/puzzle.png").toString()));

                Optional<String> result = dialog.showAndWait();
                if(result.get().length() > 4){
                    //NewGame(true);
                    default_package.Main.currentPlayer = result.get();
                    for(Button bt : arraycelula){
                        bt.setDisable(false);
                    }
                }
                default_package.Main.numjogadas=0;
                Main.tmpJogador=new Jogador();
                default_package.Main.tempo=System.currentTimeMillis(); //first time stamp
                default_package.Main.playing=true;
                default_package.Main.neverplaying=false;
                //System.out.println(dateFormat.format(date).toString() + " teste data HOJE");
                
                tmpJogador = new Jogador(default_package.Main.currentPlayer, dateFormat.format(date).toString(), 0);
    
         
	}
        public static void close(Event event)
        {
             Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Deseja sair?");
                alert.setHeaderText("Pressione ok para sair!");
                alert.setContentText("Cancel para voltar!");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK && default_package.Main.playing) {
                   default_package.Main.tempofim=(System.currentTimeMillis() - default_package.Main.tempo) /1000; 
                   default_package.Main.tmpJogador.setTempo(default_package.Main.tempofim);
                    
                   default_package.Main.jogos.add(new Jogo(default_package.Main.tmpJogador, default_package.Main.numjogadas, false));
                   default_package.Main.WriteFile(default_package.Main.jogos);
                    
                    Platform.exit();
                }else if(result.get() == ButtonType.OK && !default_package.Main.playing) 
                {
                    if(Main.neverplaying)
                    {
                        System.out.println("no if");
                        Platform.exit();
                    }
                    else
                    {
                        System.out.println("no else");
                     //   jogos.add(default_package.Main.tmpJogador,);
                        default_package.Main.WriteFile(default_package.Main.jogos);
                        Platform.exit(); 
                    }
                    
                }else
                {
                     event.consume();
                  
                }
        }
        public static void Validate()
        {
           

            Alert a = new Alert(AlertType.INFORMATION);
            String[] ints = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14"," "};
            String[] chars= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"," "};
            String[] Bin={"0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110"," ",};
            int count=0;
            for(int i=0; i<16; i++)
            {
                if(default_package.Main.arraycelula[i].getNum().equals(ints[i]))
                {
                    count++;
                }
            }
            if(count==16) //fim do jogo
            {
                 default_package.Main.tmpJogador.setTempo(default_package.Main.tempofim);
               a.setTitle("Ganhou!");
               a.setHeaderText("Completou o puzzle! Parabéns " + default_package.Main.tmpJogador.getNome());
               a.setContentText("Ganhou o jogo em "+ default_package.Main.tmpJogador.getTempo());
               a.show();
               
               
               default_package.Main.tempofim=(System.currentTimeMillis() - default_package.Main.tempo) /1000;
               default_package.Main.playerwon=true;
               default_package.Main.playing=false;
               default_package.Main.jogos.add(new Jogo(default_package.Main.tmpJogador, default_package.Main.numjogadas, true));
               
            }
            default_package.Main.numjogadas++;
            count=0;
        }
}
