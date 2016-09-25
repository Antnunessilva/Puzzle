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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.Pair;
import javax.imageio.ImageIO;



/**
 *
 * @author main4db
 */
public class Actions { //conversão de tempo no final - na salvaguarda de dados 
    	public static void ExecuteNewGame(){
                
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Novo Jopo");
                dialog.setHeaderText("Escolha o tipo de Jogo.");
                ButtonType jogar = new ButtonType("Jogar", ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(jogar, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));
                TextField username = new TextField();
                ObservableList<String> comboItem = 
                FXCollections.observableArrayList(
                    "Numerico",
                    "Alfabeto",
                    "Imagem",
                    "Romanos"
                );
                ComboBox comboTipo = new ComboBox(comboItem);
                comboTipo.setValue(comboItem.get(0));
                
                
                grid.add(new Label("Username:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Tipo de Jogo:"), 0, 1);
                grid.add(comboTipo, 1, 1);
                dialog.getDialogPane().setContent(grid);
                Platform.runLater(() -> username.requestFocus());
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == jogar) {
                        default_package.Main.currentPlayer = username.getText();
                        default_package.Main.tipo = comboTipo.getValue().toString();
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();          
                default_package.Main.numjogadas=0;
                Main.tmpJogador=new Jogador();
                default_package.Main.tempo=System.currentTimeMillis(); //first time stamp
                default_package.Main.playing=true;
                default_package.Main.neverplaying=false;
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
                    
                   default_package.Main.jogos.add(new Jogo(default_package.Main.tmpJogador, default_package.Main.numjogadas, false,default_package.Main.tipo));
                   default_package.Main.WriteFile(default_package.Main.jogos);
                    
                    Platform.exit();
                }else if(result.get() == ButtonType.OK && !default_package.Main.playing) 
                {
                    if(Main.neverplaying)
                    {
                        Platform.exit();
                    }
                    else
                    {
                        default_package.Main.WriteFile(default_package.Main.jogos);
                        Platform.exit(); 
                    }
                    
                }else
                {
                     event.consume();
                  
                }
        }
        
        
         public static void cutImages(Stage star) throws FileNotFoundException, IOException
        {
           
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
         
            
        File file = fileChooser.showOpenDialog(star);
        FileInputStream Fis = new FileInputStream(file);
        BufferedImage Image = ImageIO.read(Fis);
        
        int linhas =4;
        int colunas =4;
        int bits = linhas * colunas;
        int chunkWidth = Image.getWidth() / colunas; 
        int chunkHeight = Image.getHeight() / linhas;
        int count = 0; 
        BufferedImage imgs[] = new BufferedImage[bits]; //criação de um array de imagens 
        for(int x=0; x< linhas; x++)
        {
            for(int y=0; y<colunas; y++)
            {
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, Image.getType()); //nova imagem cortada 
                Graphics2D gr = imgs[count++].createGraphics(); //definição da imagem
                 gr.drawImage(Image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                 gr.dispose();
            }
        }
        for (int i = 0; i < imgs.length; i++) {
            ImageIO.write(imgs[i], "png", new File("img" + i + ".png")); //
        }
}
        
        public static void Validate() //FALTA quando se ganha na parte imagens - animação de hover na ultima
        {
            Alert a = new Alert(AlertType.INFORMATION);
  String[] str = new String[16];
     
     switch(default_package.Main.tipo)
     {
         case "Numerico":
         {
            str=Arrays.copyOf(default_package.Main.ints, default_package.Main.ints.length);
            break;
         }
         case "Alfabeto":
         {
             str=Arrays.copyOf(default_package.Main.chars, default_package.Main.chars.length);
             break;
         }
         case "Romanos":
         {
             str=Arrays.copyOf(default_package.Main.roms, default_package.Main.roms.length);
             break;
         }
         case "Imagem":
         {
            
              str=Arrays.copyOf(default_package.Main.imgs, default_package.Main.imgs.length);
              break;
         }
        
     }
         
     
            int count=0;
            for(int i=0; i<16; i++)
            {
                if(default_package.Main.arraycelula[i].getNum().equals(str[i]))
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
               default_package.Main.jogos.add(new Jogo(default_package.Main.tmpJogador, default_package.Main.numjogadas, true,default_package.Main.tipo));
             // ANIMAÇÂO FINAL DE ULTIMA PEÇA WIP
               /*  ImageView ultimapeca = new ImageView();
                ultimapeca.setImage(new Image("file:img0.png"));
                ultimapeca.setX(-ultimapeca.getImage().getWidth() / 2);
                ultimapeca.setY(300 - ultimapeca.getImage().getHeight());
                ultimapeca.setRotate(90);
                PathElement[] path = 
      {
         new MoveTo(0, 300),
         new ArcTo(100, 100, 0, 100, 400, false, false),
         new LineTo(300, 400),
         new ArcTo(100, 100, 0, 400, 300, false, false),
         new LineTo(400, 100),
         new ArcTo(100, 100, 0, 300, 0, false, false),
         new LineTo(100, 0),
         new ArcTo(100, 100, 0, 0, 100, false, false),
         new LineTo(0, 300),
         new ClosePath()
      };
                
             Path road = new Path();
      road.setStroke(Color.BLACK);
      road.setStrokeWidth(75);
      road.getElements().addAll(path);   
       PathTransition anim = new PathTransition();
       
      anim.setNode(ultimapeca);
      anim.setPath(road);
      anim.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
      anim.setInterpolator(Interpolator.LINEAR);
      anim.setDuration(new Duration(6000));
      anim.setCycleCount(Timeline.INDEFINITE);
      root.getChildren().addAll(road, divider, car);
      
       anim.play();*/
                
            }
            default_package.Main.numjogadas++;
            count=0;
        }
}
