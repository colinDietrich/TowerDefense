package menu.view;

import game.GameView.GameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import menu.model.ButtonStyle;
import menu.model.SPButton;
import menu.model.SPLabel;
import menu.model.SPSubScene;

import java.util.ArrayList;
import java.util.List;

public class ChooseLevelViewManager extends ViewManager{

    // position initiale des boutons
    private final static int GAME_BUTTON_START_X = 5;
    private final static int GAME_BUTTON_START_Y = 500;

    // liste de boutons
    List<SPButton> gameViewButton = new ArrayList<>();

    // sous-scene qui va permettre le choix de niveau
    private SPSubScene level;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public ChooseLevelViewManager() {
        createTitle("CHOOSE YOUR LEVEL");
        createButton();
        createSubscene();
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * creation du titre et ajout a la pane
     */
    @Override
    public void createTitle(String text) {
        SPLabel title = new SPLabel(text);
        title.setLayoutX(MenuViewManager.getWidth()/2 - (title.getWIDTH()/2));
        title.setLayoutY(MenuViewManager.getHeight()/2 - 250);
        pane.getChildren().add(title);
    }

    /**
     * creation de la subscene et ajout a la pane
     */
    private void createSubscene() {
        level = new SPSubScene();
        pane.getChildren().addAll(level);
    }

    /**
     * fonction qui permet l'ajout du button a la liste et
     * a la pane
     */
    private void addButton(SPButton button) {
        gameViewButton.add(button);
        pane.getChildren().add(button);
    }

    /**
     * creation des differents buttons du stage
     */
    private void createButton() {
        createBackButton();
        createLeftArrowButton();
        createRightArrowButton();
        createStartButton();
    }
    /**
     * creation du button de retour au menu
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
     * creation du button de demarrage du jeu
     */
    private void createStartButton() {
        SPButton startButton = new SPButton("PLAY", ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed, ButtonStyle.ButtonFree.getWidth(), ButtonStyle.ButtonFree.getHeight(), ButtonStyle.ButtonFree.getFontValue());
        startButton.setLayoutY(GAME_BUTTON_START_Y - (startButton.getHEIGHT()/2));
        startButton.setLayoutX(WIDTH - startButton.getWIDTH() - 50);
        addButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                GameView newGame = new GameView();
                newGame.createNewViewManager(stage);

            }
        });
    }

    /**
     * creation de la fleche gauche du choix de niveau
     */
    private void createLeftArrowButton() {
        SPButton leftArrowButton = new SPButton("", ButtonStyle.LeftArrow, ButtonStyle.LeftArrow, ButtonStyle.LeftArrow.getWidth(), ButtonStyle.LeftArrow.getHeight(), ButtonStyle.LeftArrow.getFontValue());
        leftArrowButton.setLayoutY(GAME_BUTTON_START_Y - (leftArrowButton.getHEIGHT()/2));
        leftArrowButton.setLayoutX(WIDTH/2 - leftArrowButton.getWIDTH()*2);
        addButton(leftArrowButton);

        leftArrowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                level.moveSubscene(false);

            }
        });
    }

    /**
     * creation de la fleche droite du choix de niveau
     */
    private void createRightArrowButton() {
        SPButton rightArrowButton = new SPButton("", ButtonStyle.RightArrow, ButtonStyle.RightArrow, ButtonStyle.RightArrow.getWidth(), ButtonStyle.RightArrow.getHeight(), ButtonStyle.RightArrow.getFontValue());
        rightArrowButton.setLayoutY(GAME_BUTTON_START_Y - (rightArrowButton.getHEIGHT()/2));
        rightArrowButton.setLayoutX(WIDTH/2 + rightArrowButton.getWIDTH());
        addButton(rightArrowButton);

        rightArrowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                level.moveSubscene(true);

            }
        });
    }



}