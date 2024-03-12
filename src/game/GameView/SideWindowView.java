package game.GameView;

import game.Controller.MouseGestures;
import game.modul.Game;
import game.modul.entityPackage.towerPackage.TowerType;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import menu.Main;
import menu.model.ButtonStyle;
import menu.model.SPButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class SideWindowView extends VBox {

    // police du texte affiche dans sidewindow
    public final static String FONT_PATH = Main.getMenuFonth();

    // titre de sideWindow
    private Label title;

    // sous tites de SideWindow
    private Label lives;
    private Label wave;
    private Label money;
    private Label explosion;

    // instance du Game actuel
    private Game game;
    // instance du GameView actuel
    private GameView gameView;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public SideWindowView(Game game, GameView gameView){
        this.game = game;
        this.gameView = gameView;

        // on ajoute le titre a sideWindiw
        title = setTitle("TOWERS");

        // on cree les sous titres de sideWindow
        Label[] listLabels = createLabels(Game.getWAVE(), Game.getMONEY(), Game.getLIVES());

        // on cree les bouttons de sideWindow
        SPButton explosionButton = createNewButton(game,"EXPLOSION");
        SPButton waveButton = createNewButton(game, "NEW WAVE");
        SPButton exitButton = createNewButton(game, "EXIT");

        // on ajoute tout a sideWindow
        this.getChildren().addAll(title,createTowerPane(TowerType.TOWER1_LEVEL1),createTowerPane(TowerType.TOWER2_LEVEL1),
                createTowerPane(TowerType.TOWER3_LEVEL1), listLabels[3], explosionButton, listLabels[0], listLabels[1], listLabels[2], waveButton, exitButton);
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setLayoutX(620);
        this.setLayoutY(50);


    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode renvoit en fonction d'un type de tower
     * une gridPane contenant l'image d'une tower, son nom,
     * son prix et un bouton permettant de l'acheter
     */
    public GridPane createTowerPane(TowerType towerType){
        MouseGestures mouseGestures = new MouseGestures();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setGridLinesVisible(false);

        Text name = setText(towerType.getName());
        Text price = setText("$  " + towerType.getPrice());

        ImageView image = new ImageView(new Image(towerType.getUrl(),towerType.getWidth(),towerType.getHeight(),false,true));
        SPButton button = new SPButton("BUY", ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed,ButtonStyle.ButtonFree.getWidth()/3, ButtonStyle.ButtonFree.getHeight()/2, ButtonStyle.ButtonFree.getFontValue()/2);

        button.setButtonId(towerType.getTowerId());
        mouseGestures.makeBuyAction(button);

        gridPane.add(name, 0,0);
        gridPane.add(image, 0,1);
        gridPane.add(price, 1,0);
        gridPane.add(button,1,1);
        gridPane.setHalignment(name, HPos.LEFT);
        gridPane.setHalignment(price, HPos.RIGHT);

        return gridPane;
    }


    /**
     * cette methode renvoit du texte avec la bonne police
     */
    private Text setText(String name) {
        Text text = new Text(name);

        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            text.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()/2));
        } catch (FileNotFoundException e) {
            text.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()/2));
        }

        return text;
    }


    /**
     * cette fonction renvoit un label en fonction d'un String
     */
    private Label setTitle(String text) {
        Label label = new Label(text);

        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()));
        }

        return label;
    }


    /**
     * cette methode met a jour les labels de sideWindow
     */
    public void updateLabels(int waveNumber, int moneyNumber, int livesNumber) {
        wave.setText("WAVE : " + waveNumber );
        money.setText("MONEY : " + moneyNumber );
        lives.setText("LIVES : " + livesNumber );
    }


    /**
     * cette methode cree les labels de sideWindow
     */
    public Label[] createLabels(int waveNumber, int moneyNumber, int livesNumber) {
        wave = new Label("WAVE : " + waveNumber );
        money = new Label("MONEY : " + moneyNumber );
        lives = new Label("LIVES : " + livesNumber );
        explosion = new Label("PRICE: 50 $");

        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            wave.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()/2));
            money.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()/2));
            lives.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()/2));
            explosion.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()/2));
            explosion.setTextFill(Color.BLACK);
        } catch (FileNotFoundException e) {
            wave.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()/2));
            money.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()/2));
            lives.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()/2));
            explosion.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()/2));
        }
        return new Label[]{lives, wave, money, explosion};
    }


    /**
     * cette methode cree les boutons de sideWindow
     */
    public SPButton createNewButton(Game game, String text) {
        SPButton newButton = new SPButton(text, ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed,ButtonStyle.ButtonFree.getWidth()/2, ButtonStyle.ButtonFree.getHeight()/2, ButtonStyle.ButtonFree.getFontValue()/2);

        newButton.setLayoutY(GameView.getHEIGHT() - newButton.getHEIGHT()/2 - 70);
        newButton.setLayoutX(685 - newButton.getWIDTH()/2);

        MouseGestures mg = new MouseGestures();
        if(text.equals("NEW WAVE")) {
            mg.newWaveAction(newButton, game);
        } else if(text.equals("EXIT")) {
            mg.exitAction(newButton, game, gameView);
        } else {
            mg.explosionAction(newButton, game);
        }
        return newButton;
    }

}
