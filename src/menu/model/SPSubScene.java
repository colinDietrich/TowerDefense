package menu.model;

import menu.Main;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import menu.view.MenuViewManager;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SPSubScene extends SubScene {

    // police principale
    private final static String FONT_PATH = Main.getMenuFonth();

    // chemin vers l'image du cadre qui entoure le niveau choisi
    // et celui du cadre qui représente le niveau
    private final static String BACKGROUND_IMAGE = "menu/model/ressources/panelChoosen.png";
    private final static String LEVEL_IMAGE = "menu/model/ressources/metalPanel_red.png";

    // taile de l'image du niveau
    private final static int LEVEL_IMAGE_SIZE = 200;

    // taille de l'image du cadre
    private final static int BACKGROUND_SIZE = 250;

    // liste de booleans qui dise si l'element est choisi ou non
    private boolean[] listIsChoosen;

    // liste des niveaux
    private String[] listLevel;

    private Label label;

    // Liste des images
    private ImageView[] listLevelImage;

    // liste des label pour les images
    private Label[] listlabel;

    private AnchorPane root;
    private AnchorPane levelPane;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public SPSubScene() {

        super(new AnchorPane(), MenuViewManager.getWidth(), BACKGROUND_SIZE+50);

        setListLevel();

        Image image = new Image(BACKGROUND_IMAGE,BACKGROUND_SIZE,BACKGROUND_SIZE,false,true);
        ImageView imageView = new ImageView(image);

        setText(listLevel[0]);

        levelPane = new AnchorPane();
        levelPane.setPrefWidth(MenuViewManager.getWidth());
        levelPane.setPrefHeight(BACKGROUND_SIZE);

        root = (AnchorPane) (this.getRoot());
        root.getChildren().addAll(imageView,label,levelPane);
        root.setBackground(null);

        levelPane.setLayoutX(0);
        levelPane.setLayoutY(0);

        addListLevel(listLevelImage, listlabel);

        imageView.setTranslateX(MenuViewManager.getWidth()/2 - BACKGROUND_SIZE/2);
        this.setLayoutX(0);
        this.setLayoutY(MenuViewManager.getHeight()/2-150);

        Main.setLevel(1);

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    private void setListLevel() {
        int i = 0;
        listLevel = new String[Main.getListLevel().size()];
        listLevelImage = new ImageView[Main.getListLevel().size()];
        listlabel = new Label[Main.getListLevel().size()];
        listIsChoosen = new boolean[Main.getListLevel().size()];
        for(int levelNumber : Main.getListLevel()) {
            String text = "LEVEL " + String.valueOf(levelNumber);
            listLevel[i] = text;
            listLevelImage[i] = new ImageView(new Image(LEVEL_IMAGE,LEVEL_IMAGE_SIZE
                    ,LEVEL_IMAGE_SIZE,false,true));
            listlabel[i] = setLabel(String.valueOf(i+1));
            if(i==0){
                listIsChoosen[i] = true;
            } else {
                listIsChoosen[i] = false;
            }
            i++;
        }
    }

    /**
     * cette fonction renvoit un label en fonction d'un String
     */
    private Label setLabel(String text) {
        Label label = new Label(text);

        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()*4));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()));
        }

        return label;
    }

    private void setText(String text) {

        if(label == null) { label = new Label(text); }
        else { label.setText(text); }

        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", 23));
        }

        label.setLayoutX(MenuViewManager.getWidth()/2 - label.getText().length()*8);
        label.setLayoutY(270);
    }

    private void addListLevel(ImageView[] listImage, Label[] listOfLabel) {
        int i = 0;

        for(ImageView image : listImage) {
            levelPane.getChildren().add(image);
            image.setLayoutX(root.getWidth()/2 +(2*i-1)*LEVEL_IMAGE_SIZE/2 + i*50);
            image.setLayoutY((root.getHeight())/2 - LEVEL_IMAGE_SIZE/2 - 25);
            i++;
        }

        i = 0;
        for(Label number : listOfLabel) {
            levelPane.getChildren().add(number);
            number.setLayoutX(root.getWidth()/2 + i*50 + i*(LEVEL_IMAGE_SIZE) -30);
            number.setLayoutY((root.getHeight())/2 - 40);
            i++;
        }
    }

    // if move == true -> deplace vers la droit >< else -> vers la gauche
    public void moveSubscene(boolean move) {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(levelPane);

        boolean isTransitionDone = false;
        int index = getLevelIndex();
        System.out.println("indexe : "+ index);

        if(move && index < listIsChoosen.length-1) {
            listIsChoosen[index] = false;
            index += 1;
            transition.setToX(-index*LEVEL_IMAGE_SIZE - index*50);
            isTransitionDone = true;
        } else if (!move && index > 0) {
            listIsChoosen[index] = false;
            index -= 1;
            transition.setToX(-index*LEVEL_IMAGE_SIZE - index*50);
            isTransitionDone = true;
        }
        if(isTransitionDone) {
            listIsChoosen[index] = true;
            setText(listLevel[index]);
            System.out.println("index update 1 = "+ index);
            Main.setLevel(index+1);
        }

        transition.play();
    }

    private int getLevelIndex() {
        int index = 0;
        int i = 0;
        for (boolean level : listIsChoosen) {
            if(level == true) {
                index = i;
            }
            i++;
        }
        return index;
    }

    public AnchorPane getPane() {
        return (AnchorPane)(this.getRoot());
    }


}