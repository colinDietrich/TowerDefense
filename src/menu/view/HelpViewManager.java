package menu.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import menu.Main;
import menu.model.ButtonStyle;
import menu.model.SPButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HelpViewManager extends ViewManager{

    // on importe la police du jeu
    public final static String FONT_PATH = Main.getMenuFonth();

    //position initiale des boutons
    private final static int GAME_BUTTON_START_X = 5;
    private final static int GAME_BUTTON_START_Y = 500;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public HelpViewManager() {
        createTitle("HELP MENU");
        createBackButton();
        createRulesText();
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    /**
     * creation du bouton de retour au menu
     */
    private void createBackButton() {
        SPButton backButton = new SPButton("BACK", ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed, ButtonStyle.ButtonFree.getWidth(), ButtonStyle.ButtonFree.getHeight(), ButtonStyle.ButtonFree.getFontValue());
        backButton.setLayoutY(GAME_BUTTON_START_Y - (backButton.getHEIGHT()/2));
        backButton.setLayoutX(50);
        addButton(backButton);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                MenuViewManager menuViewManager = new MenuViewManager();
                menuViewManager.createNewViewManager(stage);

            }
        });
    }

    /**
     * ajout du bouton sur la pane
     */
    private void addButton(SPButton button) {
        pane.getChildren().add(button);
    }


    /**
     * Creation du texte explicatif et ajout sur la pane
     */
    private void createRulesText() {
        Text rules = new Text("Bienvenue dans ce jeu de Tower Defense !\n" +
                "Vous disposer d'une carte qui comprend un chemin. Au bout de ce chemin" +
                " se trouve une sortie que vous aller devoir defendre.\n Sous la forme de " +
                "vagues, des ennemis vont apparaitre au début du chemin et il vous va falloir " +
                "placer des tours de défense afin de les attaquer.\n Vous dispoez d'un " +
                "capital de départ qui vous permet d'acheter de nouvelles tours ou d'améliorer " +
                "celles que vous possedez. Lorsqu'un ennemi arrive à la sortie, il vous enlève un" +
                " point de vie. Lorsque vous n'en avez plus, vous perdez la partie.\n Vous " +
                "diposez aussi d'un éditeur de carte qui vous permet d'en créer.");
        rules.setLayoutY(210);
        rules.setLayoutX(135);
        rules.setWrappingWidth(530);
        rules.setTextAlignment(TextAlignment.JUSTIFY);

        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            rules.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()*0.8));

        } catch (FileNotFoundException e) {
            rules.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()*0.8));
        }

        pane.getChildren().add(rules);
    }

}
