package editMap.View;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class EditMapView {

    // pane principale de EditMapView
    private static AnchorPane editPane;

    // scene principale de EditMapView
    private Scene editScene;

    // stage principal de EditMapView
    private Stage editStage;

    // instance de Grid qui correspond a la map creee par le joueur
    private Grid grid;

    // instance de window qui contient tous les boutons d'edition de map
    private Window window;

    // WIDTH et HEIGHT de la fenetre du jeu
    private static final int WIDTH = 800, HEIGHT = 600;

    // stage a afficher si on quitte la partie
    private Stage menuStage;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public EditMapView() {
        // on intialise tous les reglages pour l'interface graphique avant de lancer l'edition de map'
        initializeStage();

        // on ajoute window (contenant tous les boutons) a editPane
        window = new Window(this);
        editPane.getChildren().add(window);

    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    /**
     * cette methode initialise tous les reglages
     * pour pouvoir commencer l'edition de map'
     */
    private void initializeStage() {

        editPane = new AnchorPane();
        editScene = new Scene(editPane, WIDTH, HEIGHT);
        ImageView cadre = new ImageView(new Image("game/ressources/cadre.png", 800, 600, false, true));
        grid = new Grid();
        editPane.getChildren().addAll(cadre, grid);
        cadre.toBack();
        editStage = new Stage();
        editStage.setScene(editScene);
        editStage.setResizable(false);
    }


    /**
     * cette methode est appelee a partir d'un autre stage
     * pour afficher editStage et pouvoir lancer l'edition de map
     */
    public void createNewViewManager(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        editStage.show();
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public static AnchorPane getEditPane() { return editPane; }
    public Stage getEditStage() { return editStage; }
    public static int getWIDTH() { return WIDTH; }
    public static int getHEIGHT() { return HEIGHT; }
    public Window getWindow() {return window;}


}