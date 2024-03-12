package game.GameView;

import game.modul.Game;
import game.modul.map.Map;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class GameView {

    // pane principale de gameView
    private static AnchorPane gamePane;

    // scene principale de gameView
    private Scene gameScene;

    // stage principale de gameView
    private Stage gameStage;

    // instance de mapView qui gere l'interface graphique de la map
    private MapView mapPane;

    // instance de sideWindow qui contient tous les boutons necessaires pour jouer
    private static SideWindowView window;

    // instance de la map utilisee pour la partie
    private Map map;

    // instance du Game
    private Game game;

    // WIDTH et HEIGHT de la fenetre du jeu
    private static final int WIDTH = 800, HEIGHT = 600;

    // stage a afficher si on quitte la partie
    private Stage menuStage;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public GameView() {
        // on intialise tous les reglages pour l'interface graphique avant de lancer le jeu
        initializeStage();

        // on cree une instance de Game pour commencer une nouvelle partie
        game = new Game(1000, map, 10, 0);
        game.setGameView(this);

        // on ajoute sideWindow (contenant tous les boutons) a gamePane
        window = new SideWindowView(game, this);
        gamePane.getChildren().add(window);

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode initialise tous les reglages
     * pour pouvoir commencer le jeu
     */
    private void initializeStage() {
        map = new Map();

        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        ImageView cadre = new ImageView(new Image("game/ressources/cadre.png", 800, 600, false, true));
        mapPane = new MapView(map);
        gamePane.getChildren().addAll(cadre, mapPane);
        cadre.toBack();
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);


    }


    /**
     * cette methode est appelee a partir d'un autre stage
     * pour afficher gameStage et pouvoir lancer le jeu
     */
    public void createNewViewManager(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public static AnchorPane getGamePane() { return gamePane; }
    public Stage getGameStage() { return gameStage; }
    public static int getWIDTH() { return WIDTH; }
    public static int getHEIGHT() { return HEIGHT; }
    public static SideWindowView getSideWindow() {return window;}


}
