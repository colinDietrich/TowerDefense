package menu.model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import menu.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SPButton extends Button {

    // police principale
    private final String FONT_PATH = Main.getMenuFonth();

    // style du button lorsqu'il ne'est pas presse
    private  String BUTTON_FREE_STYLE;

    // style du button lorsqu'il est presse
    private  String BUTTON_PRESSED_STYLE;

    // largeur et hauteur des boutons
    private  int BUTTON_WIDTH;
    private  int BUTTON_HEIGHT;

    // taille de la police
    private int FONT_VALUE = 23;

    // id du button qui permettra de l'identifier parmis une liste
    private String buttonId;

    //image du button
    private ImageView buttonImage = new ImageView();



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public SPButton(String text, ButtonStyle styleFree, ButtonStyle stylePressed, int width, int height, int fontValue) {

        BUTTON_FREE_STYLE = styleFree.getUrl();
        BUTTON_PRESSED_STYLE = stylePressed.getUrl();
        BUTTON_WIDTH = width;
        BUTTON_HEIGHT = height;
        FONT_VALUE = fontValue;

        buttonImage.setImage(new Image(BUTTON_FREE_STYLE, BUTTON_WIDTH, BUTTON_HEIGHT, false, true));

        setButtonFont();
        setText(text);
        setPrefWidth(BUTTON_WIDTH);
        setPrefHeight(BUTTON_HEIGHT);
        setStyle(translateToCssStyle(BUTTON_FREE_STYLE));
        initializeButtonListeners();
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    private String translateToCssStyle(String url) {
        return "-fx-background-color: transparent;" +
                " -fx-background-repeat: no-repeat;" +
                "-fx-background-size: " + BUTTON_WIDTH + "px "+ BUTTON_HEIGHT + "px;" +
                " -fx-background-image: url('"+ url + "');";
    }

    /**
     * creation des listeners qui vont permettre d'effectuer des actions
     * lorsque les boutons sont cliques.
     */
    private void initializeButtonListeners() {

        // si souris est entrain de cliquer bouton -> on change son style
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setButtonPressedStyle();
            }
        });

        // si souris relache le bouton -> on change son style
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setButtonFreeStyle();
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }

    /**
     * changement de style : free -> pressed
     */
    private void setButtonPressedStyle() {
        setStyle(translateToCssStyle(BUTTON_PRESSED_STYLE));
        setPrefHeight(BUTTON_HEIGHT);
        setLayoutY(getLayoutY()+4);
    }

    /**
     * changement de style : pressed -> free
     */
    private void setButtonFreeStyle() {
        setStyle(translateToCssStyle(BUTTON_FREE_STYLE));
        setPrefHeight(BUTTON_HEIGHT);
        setLayoutY(getLayoutY()-4);
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public String getButtonId(){return buttonId;}
    public int getWIDTH() {
        return  BUTTON_WIDTH;
    }
    public int getHEIGHT() { return  BUTTON_HEIGHT; }



    // ******************************
    // ********** SETTERS ***********
    // ******************************
    private void setButtonFont() {
        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_VALUE));
        } catch (FileNotFoundException e) {
            System.out.println("fontError"+e);
        }

    }
    public void setButtonId(String buttonId) { this.buttonId = buttonId; }

}
