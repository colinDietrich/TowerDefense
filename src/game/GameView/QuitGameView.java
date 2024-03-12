package game.GameView;

import game.Controller.MouseGestures;
import game.modul.Game;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import menu.Main;
import menu.model.ButtonStyle;
import menu.model.SPButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class QuitGameView extends Pane {

    // image contenant la fenetre affichee a la fin du jeu
    private ImageView window;

    // url du fichier conteant l'image de window
    private String windowUrl = "game/ressources/finalCadre.png";

    // width et height de l'image de window
    private int windowWidth = 300;
    private int windowHeight = 200;

    // police du texte utilise dans quitGameView
    public final static String FONT_PATH = Main.getMenuFonth();



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public QuitGameView(Game game, GameView gameView, String text) {

        // on cree la window de fin et on la place
        createWindow();
        setLayoutX(GameView.getWIDTH()/2 - windowWidth/2-100);
        setLayoutY(GameView.getHEIGHT()/2 - windowHeight/2-20);

        // on ajoute window sur la fenetre
        GameView.getGamePane().getChildren().add(this);
        toFront();

        // on ajoute le titre de la window
        Label title = setTitle(text);
        title.setLayoutX(windowWidth/2 - title.getText().length()*10);
        title.setLayoutY(20);
        getChildren().add(title);

        // on ajoute le bouton a la window
        SPButton exitButton = createButton("BACK TO MENU", game, gameView);
        getChildren().add(exitButton);

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode ajoude l'image window a la fenetre
     */
    private void createWindow() {
        window = new ImageView(new Image(windowUrl, windowWidth, windowHeight, false, true));
        getChildren().add(window);
    }


    /**
     * cette methode renvoit le boutton permettant de quitter la partie
     */
    private SPButton createButton(String text, Game game, GameView gameView) {

        SPButton newButton = new SPButton(text, ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed,ButtonStyle.ButtonFree.getWidth(), ButtonStyle.ButtonFree.getHeight(), ButtonStyle.ButtonFree.getFontValue()/2);

        newButton.setLayoutY(windowHeight - newButton.getHEIGHT() - 20);
        newButton.setLayoutX(windowWidth/2 - newButton.getWIDTH()/2);

        MouseGestures mg = new MouseGestures();
        mg.exitAction(newButton, game, gameView);

        return newButton;
    }


    /**
     * cette methode renvoit le titre de quitGameView
     */
    private Label setTitle(String text) {
        Label label = new Label(text);

        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),30));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()));
        }
        return label;
    }
}
