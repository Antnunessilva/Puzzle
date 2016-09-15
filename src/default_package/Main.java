/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;

import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import jdk.nashorn.internal.objects.NativeDebug;

/**
 *
 * @author main4db
 */
public class Main extends Application {

    static String currentPlayer = "";
    static boolean startGame;
    static ArrayList<Jogo> jogos = new ArrayList();
    static Jogador tmpJogador;
    static int numjogadas=0;
    static GridPane gPane = new GridPane();
    static Button[] array1 = new Button[16];
    static Celula[] arraycelula = new Celula[16];
    static long tempo = 0;
    static long tempofim = 0;
    static boolean playing=false;
    static boolean neverplaying=true;
    static boolean playerwon=false;
   static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static Date date = new Date();
    static Image img = new Image("/img/puzzle.png", true);
    static ImageView imgview = new ImageView(img);

    
    

    @Override
    public void start(Stage primaryStage) {
 
        primaryStage.setTitle("Puzzle");
        gPane.setPadding(new Insets(80, 80, 80, 80));
        MenuBar mnbar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Info");
        MenuItem mniAutor = new MenuItem("Autores");
        MenuItem mniLing = new MenuItem("Linguagem");
        MenuItem mniJogo = new MenuItem("Novo Jogo");
        MenuItem mniMelhor = new MenuItem("Melhor Jogo");
        MenuItem mniJogadores = new MenuItem("Jogadores");
        MenuItem mniSair = new MenuItem("Sair");

        menuHelp.getItems().addAll(mniAutor, mniLing);
        menuFile.getItems().addAll(mniJogo, mniMelhor, mniJogadores, mniSair);
        mnbar.getMenus().addAll(menuFile, menuHelp);

        gPane.getStylesheets().add("css/style.css");
        AddButtons();
        BorderPane root = new BorderPane();
        root.setTop(mnbar);
        root.setCenter(gPane);

        /*mniMelhor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
        mniSair.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               default_package.Actions.close(event);
            }
        });
        
        mniJogo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                default_package.Actions.ExecuteNewGame();
                

              
            }
        });

        //end
        Scene scene;
        scene = new Scene(root, 600, 450);

        primaryStage.setScene(scene);
        primaryStage.show();
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                default_package.Actions.close(event);
           
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        ReadFile();
    }

    public static void AddButtons() {
      /*  ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = 0; i < 16; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);
*/
      
        int j = 1, y = 1;
        for (int i = 0; i < 16; i++) {

            int numButton = i;
            arraycelula[i] = new Celula();
           // arraycelula[i].setNum(Integer.toString(arr.get(i)));
        //    ImageView dm = new ImageView(new Image(Main.class.getClass().getResourceAsStream("/img/example.png")));
       
      // arraycelula[i].setGraphic(new ImageView(img));
           
          //  arraycelula[0].setGraphic(imgview);
          
          
          
             arraycelula[i].setNum(""+i);
            arraycelula[i].setPos(i);
            
            arraycelula[i].setMaxWidth(Double.MAX_VALUE);
            arraycelula[i].getStyleClass().add("bttemp");
            gPane.add(arraycelula[i], y, j);
            y++;
            if (y == 5) {
                y = 1;
            }
            if ((i + 1) % 4 == 0) {
                j++;
            }
            if (i == 15) {
                arraycelula[i].setHole(true);
                arraycelula[i].setNum(" ");
            }
            arraycelula[i].setDisable(true);
        }arraycelula[0].setGraphic(imgview);
       
        for (int i = 0; i < 16; i++) {
            arraycelula[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    Button button = (Button) e.getSource();
                    String search = button.getId();

                    int id = Integer.parseInt(button.getId());

                    String click = arraycelula[id].getNum();
                    try {
                        if (arraycelula[id + 1].getHole() == true) {
                            arraycelula[id + 1].setNum(click);
                            arraycelula[id + 1].setHole(false);
                            arraycelula[id + 1].setMov(true);
                            arraycelula[id].setNum(" ");
                            arraycelula[id].setHole(true);
                            arraycelula[id].setMov(false);
                            default_package.Actions.Validate();
                        }
                    } catch (IndexOutOfBoundsException erro) {

                    }
                    try {
                        if (arraycelula[id - 1].getHole() == true) {
                            arraycelula[id - 1].setNum(click);
                            arraycelula[id - 1].setHole(false);
                            arraycelula[id - 1].setMov(true);
                            arraycelula[id].setNum(" ");
                            arraycelula[id].setHole(true);
                            arraycelula[id].setMov(false);
                            default_package.Actions.Validate();
                        }
                    } catch (IndexOutOfBoundsException erro) {

                    }
                    try {
                        if (arraycelula[id + 4].getHole() == true) {
                            arraycelula[id + 4].setNum(click);
                            arraycelula[id + 4].setHole(false);
                            arraycelula[id + 4].setMov(true);
                            arraycelula[id].setNum(" ");
                            arraycelula[id].setHole(true);
                            arraycelula[id].setMov(false);
                            default_package.Actions.Validate();

                        }
                    } catch (IndexOutOfBoundsException erro) {

                    }
                    try {
                        if (arraycelula[id - 4].getHole() == true) {
                            arraycelula[id - 4].setNum(click);
                            arraycelula[id - 4].setHole(false);
                            arraycelula[id - 4].setMov(true);
                            arraycelula[id].setNum(" ");
                            arraycelula[id].setHole(true);
                            arraycelula[id].setMov(false);
                            default_package.Actions.Validate();

                        }
                    } catch (IndexOutOfBoundsException erro) {

                    }
                    click = "";
                }
            });
        }
    }

    public static int LineCounter(String fname) {
        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fname)));
            lnr.skip(Long.MAX_VALUE);
            lnr.close();
            return lnr.getLineNumber();
        } catch (Exception e) {
            return 0;
        }
    }

    public static void ReadFile() {
        int ct = 0;
        File fi = new File("logs.txt");
        int lnct = LineCounter("logs.txt");
        String nome, data, tempo, jogadas, estado;

        if (fi.exists() && lnct != 0 && lnct % 5 == 0) {
            Jogador tempJ;
            try {
                FileInputStream fs = new FileInputStream("logs.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(fs));
                while (ct < lnct / 5) {
                    nome = br.readLine();
                    data = br.readLine();
                    tempo = br.readLine();
                    jogadas = br.readLine();
                    estado = br.readLine();
                    tempJ = new Jogador(nome, data, Float.parseFloat(tempo));
                    jogos.add(new Jogo(tempJ, Integer.parseInt(jogadas), Boolean.parseBoolean(estado)));

                    ct++;
                }
                br.close();
                fs.close();

            } catch (Exception e) {
            }
        }
        for(Jogo j:jogos)
        {
            System.out.println(j.getJogador().getNome() + " " + j.getJogador().getData() + " " + j.getJogador().getTempo() + " " + j.getJogada() + " " + j.getcompleto());
        }
    }

    public static void WriteFile(ArrayList<Jogo> j) {

        PrintWriter pw = null;
        FileWriter in = null;
        try {
            in = new FileWriter("logs.txt");
            pw = new PrintWriter(in);
            tmpJogador = new Jogador();
            for (int ct = 0; ct < j.size(); ct++) {
                pw.write(j.get(ct).getJogador().getNome() + "\n");
                pw.write(j.get(ct).getJogador().getData() + "\n");
                pw.write(Float.toString(j.get(ct).getJogador().getTempo()) + "\n");
                pw.write(Integer.toString(j.get(ct).getJogada()) + "\n");
                pw.write(Boolean.toString(j.get(ct).getcompleto()) + "\n");
            }

            pw.close();
            in.close();
        } catch (Exception ex) {
            pw.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
