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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    static int numjogadas = 0;
    static BorderPane root = new BorderPane();
    static GridPane gPane = new GridPane();
    static Button[] array1 = new Button[16];
    static Celula[] arraycelula = new Celula[16];
    static long tempo = 0;
    static long tempofim = 0;
    static String tipo = "";
    static boolean playing = false;
    static boolean neverplaying = true;
    static boolean playerwon = false;
    static TableView table = new TableView();
    static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static Date date = new Date();
    static Image img = new Image("/img/puzzle.png", true);
    static ImageView imgview = new ImageView(img);

    static TabPane tabPane = new TabPane();

    static String[] ints = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", " "};
    static String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", " "};
    static String[] roms = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", " "};
    static String[] imgs = {"img0.png", "img1.png", "img2.png", "img3.png", "img4.png", "img5.png", "img6.png", "img7.png", "img8.png", "img9.png", "img10.png", "img11.png", "img12.png", "img13.png", "img14.png", " "};

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Puzzle");

        MenuBar mnbar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Info");
        MenuItem mniAutor = new MenuItem("Autores");
        MenuItem mniLing = new MenuItem("Linguagem");
        MenuItem mniJogo = new MenuItem("Novo Jogo");
        MenuItem mniSair = new MenuItem("Sair");

        menuHelp.getItems().addAll(mniAutor, mniLing);
        menuFile.getItems().addAll(mniJogo, mniSair);
        mnbar.getMenus().addAll(menuFile, menuHelp);
        //
        Tab tabNum = new Tab("Numerico");
        Tab tabAlf = new Tab("Alfabeto");
        Tab tabRom = new Tab("Romanos");
        Tab tabImg = new Tab("Imagem");
        Tab tabSol = new Tab("Imagem");
        Tab tabMelh = new Tab("Jogos");
        tabNum.setClosable(false);
        tabSol.setClosable(false);
        tabAlf.setClosable(false);
        tabRom.setClosable(false);
        tabImg.setClosable(false);
        tabMelh.setClosable(false);

        root.setTop(mnbar);
        final Label label = new Label("Jogos");
        TableColumn tcUser = new TableColumn("Jogador");
        TableColumn tcScore = new TableColumn("Pontuação");
        TableColumn tcMoves = new TableColumn("Jogadas");
        TableColumn tcMode = new TableColumn("Modo de Jogo");
        TableColumn tcTime = new TableColumn("Duração");
        TableColumn tcDate = new TableColumn("Data");
        tcMode.setMinWidth(110);
        tcUser.setMinWidth(110);
        tcDate.setMinWidth(110);
        table.getColumns().addAll(tcUser, tcScore, tcMoves, tcMode, tcTime, tcDate);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        tabMelh.setContent(vbox);

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
                switch (tipo) {
                    case "Numerico":
                        AddButtons(ints);//enviar param com tipo
                        tabPane = new TabPane();
                        tabNum.setContent(gPane);
                        tabPane.getTabs().add(tabNum);
                        tabPane.getTabs().add(tabMelh);
                        gPane.getStylesheets().add("css/style.css");
                        root.setCenter(tabPane);
                        root.requestLayout();
                        break;
                    case "Alfabeto":
                        AddButtons(chars);//enviar param com tipo
                        tabPane = new TabPane();
                        tabAlf.setContent(gPane);
                        tabPane.getTabs().add(tabAlf);
                        tabPane.getTabs().add(tabMelh);
                        gPane.getStylesheets().add("css/style.css");
                        root.setCenter(tabPane);
                        root.requestLayout();

                        break;
                    case "Romanos":
                        AddButtons(roms);//enviar param com tipo
                        tabPane = new TabPane();
                        tabRom.setContent(gPane);
                        tabPane.getTabs().add(tabRom);
                        tabPane.getTabs().add(tabMelh);
                        gPane.getStylesheets().add("css/style.css");
                        root.setCenter(tabPane);
                        root.requestLayout();

                        break;
                    case "Imagem": {
                        try {
                            default_package.Actions.cutImages(primaryStage);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    AddButtons(imgs);//enviar param com tipo
                    tabPane = new TabPane();
                    tabImg.setContent(gPane);
                    tabPane.getTabs().add(tabImg);
                    tabPane.getTabs().add(tabSol);
                    tabPane.getTabs().add(tabMelh);
                    gPane.getStylesheets().add("css/style.css");
                    root.setCenter(tabPane);
                    root.requestLayout();

                    break;

                }

            }
        });

        //end
        Scene scene;
        scene = new Scene(root, 600, 450);
        primaryStage.setWidth(600);
        primaryStage.setHeight(450);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                default_package.Actions.close(event);

            }
        });
        ReadFile();

        gPane.setAlignment(Pos.TOP_LEFT);

    }

    public void AddButtons(String[] array) {
        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(array));

        //Collections.shuffle(arr); //comentar sempre que quisermos testar
        int j = 1, y = 1;
        for (int i = 0; i < 16; i++) {

            int numButton = i;
            arraycelula[i] = new Celula();
            if (tipo.equals("Imagem")) {
                arraycelula[i].setStyle("-fx-background-image: url("
                        + "'file:" + arr.get(i) + "'"
                        + "); "
                        + "-fx-background-size: cover, auto;"
                        + " -fx-text-fill:transparent;"
                        + "-fx-fit-to-height: true;");
                arraycelula[i].setNum(arr.get(i));

            } else {
                arraycelula[i] = new Celula();
                arraycelula[i].setNum(arr.get(i));
            }
            arraycelula[i].setPos(i);

            arraycelula[i].setMaxWidth(Double.MAX_VALUE);

            arraycelula[i].setPrefHeight(200);
            arraycelula[i].setPrefWidth(200);

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
        }

        for (int i = 0; i < 16; i++) {
            arraycelula[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    Button button = (Button) e.getSource();
                    String search = button.getId();

                    int id = Integer.parseInt(button.getId());
                    String primeiro = "";
                    String click = arraycelula[id].getNum();
                    try {
                        if (arraycelula[id + 1].getHole() == true && id != 11) {
                            if (arraycelula[id + 1].getHole() == true && id != 7) {
                                if (arraycelula[id + 1].getHole() == true && id != 3) {
                                    primeiro = arraycelula[id + 1].getNum();
                                    arraycelula[id + 1].setNum(click);
                                    if (default_package.Main.tipo.equals("Imagem")) {
                                        arraycelula[id + 1].setStyle("-fx-background-image: url("
                                                + "'file:" + click + "'"
                                                + "); "
                                                + "-fx-background-size: cover, auto;"
                                                + " -fx-text-fill:transparent;"
                                                + "-fx-fit-to-height: true;");
                                    }
                                    arraycelula[id + 1].setHole(false);
                                    arraycelula[id + 1].setMov(true);
                                    arraycelula[id].setNum(" ");
                                    if (default_package.Main.tipo.equals("Imagem")) {
                                        arraycelula[id].setStyle(null);
                                        arraycelula[id].setStyle("-fx-background-image: url("
                                                + "'file:" + primeiro + "'"
                                                + "); "
                                                + "-fx-background-size: cover, auto;"
                                                + " -fx-text-fill:transparent;"
                                                + "-fx-fit-to-height: true;");

                                    }
                                    arraycelula[id].setHole(true);
                                    arraycelula[id].setMov(false);
                                    default_package.Actions.Validate();
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException erro) {

                    }
                    try {
                        if (arraycelula[id - 1].getHole() == true && id != 12) {
                            if (arraycelula[id - 1].getHole() == true && id != 8) {
                                if (arraycelula[id - 1].getHole() == true && id != 4) {
                                    primeiro = arraycelula[id - 1].getNum();
                                    arraycelula[id - 1].setNum(click);
                                    if (default_package.Main.tipo.equals("Imagem")) {
                                        arraycelula[id - 1].setStyle("-fx-background-image: url("
                                                + "'file:" + click + "'"
                                                + "); "
                                                + "-fx-background-size: cover, auto;"
                                                + " -fx-text-fill:transparent;"
                                                + "-fx-fit-to-height: true;");
                                    }
                                    arraycelula[id - 1].setHole(false);
                                    arraycelula[id - 1].setMov(true);
                                    arraycelula[id].setNum(" ");
                                    if (default_package.Main.tipo.equals("Imagem")) {
                                        arraycelula[id].setStyle(null);
                                        arraycelula[id].setStyle("-fx-background-image: url("
                                                + "'file:" + primeiro + "'"
                                                + "); "
                                                + "-fx-background-size: cover, auto;"
                                                + " -fx-text-fill:transparent;"
                                                + "-fx-fit-to-height: true;");
                                    }
                                    arraycelula[id].setHole(true);
                                    arraycelula[id].setMov(false);
                                    default_package.Actions.Validate();
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException erro) {

                    }
                    try {
                        if (arraycelula[id + 4].getHole() == true) {
                            primeiro = arraycelula[id + 4].getNum();
                            arraycelula[id + 4].setNum(click);
                            if (default_package.Main.tipo.equals("Imagem")) {
                                arraycelula[id + 4].setStyle("-fx-background-image: url("
                                        + "'file:" + click + "'"
                                        + "); "
                                        + "-fx-background-size: cover, auto;"
                                        + " -fx-text-fill:transparent;"
                                        + "-fx-fit-to-height: true;");
                            }
                            arraycelula[id + 4].setHole(false);
                            arraycelula[id + 4].setMov(true);
                            arraycelula[id].setNum(" ");
                            if (default_package.Main.tipo.equals("Imagem")) {
                                arraycelula[id].setStyle(null);
                                arraycelula[id].setStyle("-fx-background-image: url("
                                        + "'file:" + primeiro + "'"
                                        + "); "
                                        + "-fx-background-size: cover, auto;"
                                        + " -fx-text-fill:transparent;"
                                        + "-fx-fit-to-height: true;");

                            }
                            arraycelula[id].setHole(true);
                            arraycelula[id].setMov(false);
                            default_package.Actions.Validate();

                        }
                    } catch (IndexOutOfBoundsException erro) {

                    }
                    try {
                        if (arraycelula[id - 4].getHole() == true) {
                            primeiro = arraycelula[id - 4].getNum();
                            arraycelula[id - 4].setNum(click);
                            if (default_package.Main.tipo.equals("Imagem")) {
                                arraycelula[id - 4].setStyle("-fx-background-image: url("
                                        + "'file:" + click + "'"
                                        + "); "
                                        + "-fx-background-size: cover, auto;"
                                        + " -fx-text-fill:transparent;"
                                        + "-fx-fit-to-height: true;");
                            }
                            arraycelula[id - 4].setHole(false);
                            arraycelula[id - 4].setMov(true);
                            arraycelula[id].setNum(" ");
                            if (default_package.Main.tipo.equals("Imagem")) {
                                arraycelula[id].setStyle(null);
                                arraycelula[id].setStyle("-fx-background-image: url("
                                        + "'file:" + primeiro + "'"
                                        + "); "
                                        + "-fx-background-size: cover, auto;"
                                        + " -fx-text-fill:transparent;"
                                        + "-fx-fit-to-height: true;");

                            }
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
        String nome, data, tempo, jogadas, estado, tipo;

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
                    tipo = br.readLine();
                    tempJ = new Jogador(nome, data, Float.parseFloat(tempo));
                    jogos.add(new Jogo(tempJ, Integer.parseInt(jogadas), Boolean.parseBoolean(estado), tipo));

                    ct++;
                }
                br.close();
                fs.close();

            } catch (Exception e) {
            }
        }
        for (Jogo j : jogos) {
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
                pw.write(j.get(ct).getTipo() + "\n");
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
