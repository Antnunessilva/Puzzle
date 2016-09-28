/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;

import static default_package.Main.arraycelula;
import static default_package.Main.date;
import static default_package.Main.dateFormat;
import static default_package.Main.root;
import static default_package.Main.tmpJogador;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javax.imageio.ImageIO;

/**
 *
 * @author main4db
 */
public class Actions { //conversão de tempo no final - na salvaguarda de dados

    static int lastid = 0;
    static int lastid1 = 0;
    static File file = null;

    public static void ExecuteNewGame() {

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
        ObservableList<String> comboItem
                = FXCollections.observableArrayList(
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
        default_package.Main.numjogadas = 0;
        Main.tmpJogador = new Jogador();
        default_package.Main.tempo = 0;
        if (default_package.Main.tipo != "Imagem") {
            default_package.Main.tempo = System.currentTimeMillis(); //first time stamp
        }
        default_package.Main.tempo = System.currentTimeMillis(); //first time stamp
        default_package.Main.playing = true;
        default_package.Main.neverplaying = false;
        tmpJogador = new Jogador(default_package.Main.currentPlayer, dateFormat.format(date).toString(), 0);

    }

    public static void close(Event event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Deseja sair?");
        alert.setHeaderText("Pressione OK para sair!");
        alert.setContentText("Cancel para voltar!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && default_package.Main.playing) {// leave while playing, so far so good
            default_package.Main.tempofim = (System.currentTimeMillis() - default_package.Main.tempo) / 1000;
            default_package.Main.tmpJogador.setTempo(default_package.Main.tempofim);

            default_package.Main.jogos.add(new Jogo(default_package.Main.tmpJogador, default_package.Main.numjogadas, false, default_package.Main.tipo));
            default_package.Main.WriteFile(default_package.Main.jogos);
            erase();
            Platform.exit();
        } else if (result.get() == ButtonType.OK && !default_package.Main.playing) {
            if (Main.neverplaying) {
                erase();
                Platform.exit();
            } else {
                default_package.Main.WriteFile(default_package.Main.jogos);
                erase();
                Platform.exit();
            }

        } else {
            event.consume();
        }
    }

    public static void erase() {
        for (int i = 0; i < 16; i++) {
            File f1 = new File("img" + i + ".png");
            f1.delete();
        }
    }

    public static void cutImages(Stage star) throws FileNotFoundException, IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha o seu ficheiro!");
        File file = fileChooser.showOpenDialog(star);
        FileInputStream Fis = new FileInputStream(file);
        BufferedImage Image = ImageIO.read(Fis);
        default_package.Main.imagepick = true;

        Image img = new Image(file.toURI().toString());
        ImageView im = new ImageView(img);
        im.fitWidthProperty().bind(root.widthProperty());
        im.fitHeightProperty().bind(root.heightProperty());
        default_package.Main.tabSol.setContent(im);

        int linhas = 4;
        int colunas = 4;
        int bits = linhas * colunas;
        int chunkWidth = Image.getWidth() / colunas;
        int chunkHeight = Image.getHeight() / linhas;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[bits]; //criação de um array de imagens

        for (int x = 0; x < linhas; x++) {
            for (int y = 0; y < colunas; y++) {
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, Image.getType()); //nova imagem cortada
                Graphics2D gr = imgs[count++].createGraphics(); //definição da imagem
                gr.drawImage(Image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        for (int i = 0; i < imgs.length; i++) {
            ImageIO.write(imgs[i], "png", new File("img" + i + ".png")); //
        }
        default_package.Main.tempo = System.currentTimeMillis();

    }

    public static void Validate() { //metodo de validação
        Alert a = new Alert(AlertType.INFORMATION);
        String[] str = new String[16];

        switch (default_package.Main.tipo) {
            case "Numerico": {
                str = Arrays.copyOf(default_package.Main.ints, default_package.Main.ints.length);
                break;
            }
            case "Alfabeto": {
                str = Arrays.copyOf(default_package.Main.chars, default_package.Main.chars.length);
                break;
            }
            case "Romanos": {
                str = Arrays.copyOf(default_package.Main.roms, default_package.Main.roms.length);
                break;
            }
            case "Imagem": {
                str = Arrays.copyOf(default_package.Main.imgs, default_package.Main.imgs.length);
                break;
            }

        }
        int count = 0;
        for (int i = 0; i < 16; i++) {
            if (default_package.Main.arraycelula[i].getNum().equals(str[i])) {
                count++;
            }
        }
        if (count == 16) //fim do jogo
        {
            default_package.Main.tempofim = (System.currentTimeMillis() - default_package.Main.tempo) / 1000;
            default_package.Main.tmpJogador.setTempo(default_package.Main.tempofim);
            a.setTitle("Ganhou!");
            a.setHeaderText("Completou o puzzle! Parabéns " + default_package.Main.tmpJogador.getNome().toUpperCase());
            a.setContentText("Ganhou o jogo em " + default_package.Main.TimeConv(default_package.Main.tmpJogador.getTempo()));
            a.show();
            default_package.Main.gameover = true;
            default_package.Main.tempofim = (System.currentTimeMillis() - default_package.Main.tempo) / 1000;
            default_package.Main.playerwon = true;
            default_package.Main.playing = false;
            default_package.Main.jogos.add(new Jogo(default_package.Main.tmpJogador, default_package.Main.numjogadas, true, default_package.Main.tipo));
            if (default_package.Main.tipo.equals("Imagem")) {
                Anim();
            }
            btnstop();
        }
        default_package.Main.numjogadas++;
        count = 0;
    }

    public static void Anim() { //animação final

        ImageView ultimapeca = new ImageView();
        ultimapeca.setImage(new Image("file:img15.png"));
        ultimapeca.scaleXProperty();
        ultimapeca.setX(-ultimapeca.getImage().getWidth() / 2);
        ultimapeca.setY(300 - ultimapeca.getImage().getHeight());
        ultimapeca.setRotate(90);
        Path path = new Path();
        path.getElements().add(new MoveTo(20, 20));
        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(ultimapeca);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(true);

        ScaleTransition scaleTransition
                = new ScaleTransition(Duration.millis(2000), ultimapeca);
        scaleTransition.setToX(0f);
        scaleTransition.setToY(0f);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                pathTransition,
                scaleTransition);
        sequentialTransition.setCycleCount(1);
        sequentialTransition.play();
        root.getChildren().add(ultimapeca);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(3200),
                ae -> changelast()));
        timeline.play();

        btnstop();
    }

    public static void changelast() {
        arraycelula[15].setStyle("-fx-background-image: url("
                + "'file:img15.png'"
                + "); "
                + "-fx-background-size: cover, auto;"
                + " -fx-text-fill:transparent;"
                + "-fx-fit-to-height: true;");
    }

    public static void btnstop() {
        for (Celula c : default_package.Main.arraycelula) {
            c.setOnAction(null);
        }

    }

}
