package menu.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import menu.model.SPLabel;

public abstract class ViewManager {

    // hauteur et largeur de la fenetre
    protected static final int HEIGHT = 600;
    protected static final int WIDTH = 800;

    // pane, scene et satge du ViewManager
    protected AnchorPane pane;
    protected Scene scene;
    protected Stage stage;

    // stage precedant le stage actuel
    // il est utlise dans la methode createNewViewManager
    protected Stage oldStage;

    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public ViewManager() {
        initializeStage();
        createBackground();
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    /**
     * initialisation de la pane, scene, et du stage
     */
    void initializeStage() {
        pane = new AnchorPane();
        scene = new Scene(pane, WIDTH, HEIGHT);
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
    }

    /**
     * creation du fond d'ecran
     */
    void createBackground() {
        Image backgroundImage = new Image("menu/ressources/background2.gif",800, 600, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(background));
    }

    /**
     * cette fonction permet le changement de fenetre
     */
    public void createNewViewManager(Stage oldStage) {
        this.oldStage = oldStage;
        this.oldStage.hide();
        stage.show();
    }

    /**
     * creation du titre et ajout sur la pane
     */
    public void createTitle(String text) {
        SPLabel title = new SPLabel(text);
        title.setLayoutX(WIDTH/2 - (title.getWIDTH()/2));
        title.setLayoutY(HEIGHT/2 - 200);
        pane.getChildren().add(title);
    }
}

