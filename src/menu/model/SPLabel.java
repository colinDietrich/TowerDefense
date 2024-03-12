package menu.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import menu.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SPLabel extends Label {

    // police principale
    public final static String FONT_PATH = Main.getMenuFonth();

    // image qui encadre le titre
    public final static String BACKGROUND_IMAGE = "menu/model/ressources/newPanel.png";

    // largeur et hauteur du label
    private int WIDTH = 500;
    private int HEIGHT = 70;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public SPLabel(String text) {

        setPrefWidth(WIDTH);
        setPrefHeight(HEIGHT);
        setText(text);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE,WIDTH,HEIGHT,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(backgroundImage));

    }

    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public int getWIDTH() {
        return  WIDTH;
    }
    public int getHEIGHT() {
        return  HEIGHT;
    }



    // ******************************
    // ********** SETTERS ***********
    // ******************************
    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),40));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 40));
        }
    }
}