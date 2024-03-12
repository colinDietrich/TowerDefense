package editMap.View;

import editMap.controller.EditMouseGestures;
import editMap.modul.BlockStyle;
import game.GameView.GameView;
import game.modul.map.Tile;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import menu.Main;
import menu.model.ButtonStyle;
import menu.model.SPButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;




public class Window extends VBox {

    // police du texte affiche dans window
    public final static String FONT_PATH = Main.getMenuFonth();

    // titre a afficher dans la window
    private Label title;

    // instance de EditMapView actuel
    private EditMapView editMapView;




    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Window(EditMapView editMapView){

        this.editMapView = editMapView;

        // on cree une instance de editMouseGestures
        // pour pouvoir ajouter des eventHandler aux boutons de window
        EditMouseGestures mouseGestures = new EditMouseGestures();

        // on cree les differents boutons de window
        SPButton rotateButton = createNewButton("ROTATE", mouseGestures);
        SPButton exitButton = createNewButton("EXIT", mouseGestures);
        SPButton saveButton = createNewButton("SAVE", mouseGestures);
        SPButton deleteButton = createNewButton("DELETE", mouseGestures);

        // on ajoute les boutons sur la fenetre
        this.getChildren().addAll(rotateButton, deleteButton);
        for(BlockStyle style : BlockStyle.values()) {
            this.getChildren().add(createBlockPane(style, mouseGestures));
        }
        this.getChildren().addAll(saveButton, exitButton);

        // on place la window correctement dans la fenetre
        this.setSpacing(2);
        this.setAlignment(Pos.CENTER);
        this.setLayoutX(630);
        this.setLayoutY(38);

    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode renvoit en fonction d'un style de block
     * une gridPane contenant l'image du bloc, son nom
     * et un bouton permettant de l'ajouter a la map
     */
    public GridPane createBlockPane(BlockStyle blockStyle, EditMouseGestures mouseGestures){

        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(5);
        gridPane.setGridLinesVisible(false);

        Text name = setText(blockStyle.getName());

        ImageView image = new ImageView(new Image(blockStyle.getUrl(), Tile.getTileSize(),Tile.getTileSize(),false,true));
        SPButton button = new SPButton("SELECT", ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed,ButtonStyle.ButtonFree.getWidth()/3, ButtonStyle.ButtonFree.getHeight()/2, ButtonStyle.ButtonFree.getFontValue()/2);


        mouseGestures.makeSelectBlockAction(button, blockStyle);

        gridPane.add(name, 1,0);
        gridPane.add(image, 0,1);
        gridPane.add(button,1,1);
        gridPane.setHalignment(name, HPos.RIGHT);

        return gridPane;
    }


    /**
     * cette methode renvoit du texte avec la bonne police
     */
    private Text setText(String name) {
        Text text = new Text(name);

        // on essaye de charger la police kenvector -> si ça marche pas on prend la police par défaut
        try {
            text.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),ButtonStyle.ButtonFree.getFontValue()/3));
        } catch (FileNotFoundException e) {
            text.setFont(Font.font("Verdana", ButtonStyle.ButtonFree.getFontValue()/2));
        }

        return text;
    }


    /**
     * cette methode cree les boutons de window
     */
    public SPButton createNewButton(String text, EditMouseGestures mouseGestures) {
        SPButton newButton = new SPButton(text, ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed,ButtonStyle.ButtonFree.getWidth()/2, ButtonStyle.ButtonFree.getHeight()/2, ButtonStyle.ButtonFree.getFontValue()/2);

        newButton.setLayoutY(GameView.getHEIGHT() - newButton.getHEIGHT()/2 - 70);
        newButton.setLayoutX(685 - newButton.getWIDTH()/2);

        if(text.equals("ROTATE")) {
            mouseGestures.rotateAction(newButton);
        } else if (text.equals("EXIT")) {
            mouseGestures.exitAction(newButton, editMapView);
        } else if (text.equals("SAVE")) {
            mouseGestures.saveMapAction(newButton, editMapView);
        } else {
            mouseGestures.deleteBlockAction(newButton);
        }
        return newButton;
    }


}
