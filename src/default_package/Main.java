/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;

import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author main4db
 */
public class Main extends Application {

    static GridPane gPane = new GridPane();
    static Button[] array1 = new Button[16];
    static Celula[] arraycelula = new Celula[16];

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

        Scene scene;
        scene = new Scene(root, 600, 450);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void AddButtons() {
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = 0; i < 16; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);

        int j = 1, y = 1;
        for (int i = 0; i < 16; i++) {

            int numButton = i;
            arraycelula[i] = new Celula();
            arraycelula[i].setNum(Integer.toString(arr.get(i)));
            //arraycelula[i].setNum(""+i);
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
        }

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
