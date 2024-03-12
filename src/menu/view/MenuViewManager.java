package menu.view;

import editMap.View.EditMapView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import menu.model.*;

import java.util.ArrayList;
import java.util.List;

public class MenuViewManager extends ViewManager {


    // position initiale des boutons
    private final static int MENU_BUTTON_START_X = WIDTH/2;
    private final static int MENU_BUTTON_START_Y = 200;

    // liste des boutons contenus dans le menu
    List<SPButton> menuButton;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public MenuViewManager() {
        menuButton = new ArrayList<>();
        createButton();
        createTitle("TOWER DEFENSE");

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    /**
     * fonction qui permet la creation des differents boutons
     */
    private void createButton() {
        createStartButton();
        createHelpButton();
        createEditMapButton();
    }

    /**
     * creation du bouton de lancement du jeu
     */
    private void createStartButton() {
        SPButton startButton = new SPButton("PLAY", ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed, ButtonStyle.ButtonFree.getWidth(), ButtonStyle.ButtonFree.getHeight(), ButtonStyle.ButtonFree.getFontValue());
        addMenuButton(startButton);

        // lancement de la fenetre adequate lorsque le bouton est clique
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                ChooseLevelViewManager gameManager = new ChooseLevelViewManager();
                gameManager.createNewViewManager(stage);

            }
        });
    }

    /**
     * creation du bouton d'aide
     */
    private void createHelpButton() {
        SPButton helpButton = new SPButton("HELP", ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed, ButtonStyle.ButtonFree.getWidth(), ButtonStyle.ButtonFree.getHeight(), ButtonStyle.ButtonFree.getFontValue());
        addMenuButton(helpButton);

        // lancement de la fenetre adequate lorsque le bouton est clique
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                HelpViewManager helpManager = new HelpViewManager();
                helpManager.createNewViewManager(stage);

            }
        });
    }

    /**
     * creation du bouton de l'editeur de map
     */
    private void createEditMapButton() {
        SPButton editMapButton = new SPButton("EDIT MAP", ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed, ButtonStyle.ButtonFree.getWidth(), ButtonStyle.ButtonFree.getHeight(), ButtonStyle.ButtonFree.getFontValue());
        addMenuButton(editMapButton);

        // lancement de la fenetre adequate lorsque le bouton est clique
        editMapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                EditMapView editMapView = new EditMapView();
                editMapView.createNewViewManager(stage);

            }
        });
    }

    /**
     * ajout du bouton sur la mainpane
     */
    private void addMenuButton(SPButton button) {
        button.setLayoutX(MENU_BUTTON_START_X - (button.getWIDTH()/2));
        button.setLayoutY(MENU_BUTTON_START_Y + menuButton.size()*100);
        menuButton.add(button);
        pane.getChildren().add(button);
    }




    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public static int getWidth() {
        return WIDTH;
    }
    public static int getHeight() { return HEIGHT; }
    public Stage getMainStage() {
        return stage;
    }


}
