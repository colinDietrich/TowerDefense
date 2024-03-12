package editMap.View;

import editMap.modul.BlockStyle;
import game.modul.map.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class EditCell extends StackPane {

    // indice x de la Cell dans la matrice de Cells de MapView
    private int column;
    // indice y de la Cell dans la matrice de Cells de MapView
    private int row;

    // image du block a ajouter sur la Cell
    private ImageView blockImage;

    // style du block sur la cell
    private BlockStyle blockStyle = BlockStyle.MapNormal;

    // true si la cell possede deja un bloc
    private boolean isAlreadyTaken = false;

    // image d'une fleche qui est ajoutee ssi le boc est un chemin pour le pnj
    // cela permet de definir le sens de parcours du chemin
    private ImageView arrow;

    // code permettant d'identifier et d'enregistrer dans un fichier texte le bloc de la cell
    private String code;

    // style du bloc a afficher en preview
    private static BlockStyle BLOCK_STYLE_PREVIEW = BlockStyle.MapNormal;

    // style css pour afficher le contour de la cell en noir
    private String cellStyleNormal = "-fx-border-color: black; -fx-border-width: 1px";

    // style css pour afficher le contour de la cell en rouge
    private String cellStyleSellectionned = "-fx-border-color: crimson; -fx-border-width: 1px";



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public EditCell(int column, int row) {
        this.column = column;
        this.row = row;
        this.code = blockStyle.getCodeName();

        // on initialise l'image arrow
        arrow = blockImage = new ImageView(new Image("mainRessources/arrow.png", Tile.getTileSize(), Tile.getTileSize(), false, true));

        // on affiche le contour de la cell
        showNormalGrid();

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode affiche le contour de la cell en mode normal
     */
    public void showNormalGrid(){
        this.setStyle(cellStyleNormal);
    }


    /**
     * cette methode affiche le contour de la cell lorsqu'elle est selectionnee
     */
    public void showSelectionnedGrid() { this.setStyle(cellStyleSellectionned); }


    /**
     * cette methode affiche l'image preview du bloc sur la cell
     */
    public void showBlockPreview() { setBlock(); }


    /**
     * cette methode efface l'image preview du bloc sur la cell
     */
    public void removeBlockPreview() {
        isAlreadyTaken = false;
        this.getChildren().remove(blockImage);
        if(blockStyle == BlockStyle.Path || blockStyle == BlockStyle.Start || blockStyle == BlockStyle.End) {
            this.getChildren().remove(arrow);
        }
    }


    /**
     * cette methode ajoute definitivement l'image du bloc sur la cell
     */
    public void addBlock() {
        isAlreadyTaken = true;
        setBlock();
        code = blockStyle.getCodeName();
    }


    /**
     * cette methode ajoute une image d'un bloc a la Cell
     */
    public void setBlock() {
        blockStyle = BLOCK_STYLE_PREVIEW;
        blockImage = new ImageView(new Image(blockStyle.getUrl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
        this.getChildren().add(blockImage);
        blockImage.toFront();
        if(blockStyle == BlockStyle.Path) {
            this.getChildren().add(arrow);
            arrow.toFront();
        }
    }


    /**
     * cette methode fait tourner le bloc sur la cell
     * par des multiples de 90 degres
     * et change le code du bloc sur l'angle correspondant
     */
    public void rotateBlock(int angle) {
        System.out.println("code initiale : " + code);
        blockImage.setRotate(angle);
        code = String.valueOf(code.charAt(0)) + String.valueOf(code.charAt(1)) + String.valueOf(code.charAt(2)) + String.valueOf(angle/90);
        System.out.println("code apres rotation : " + code);

        // si c'est un block chemin, start ou end -> on change le sens
        if(blockStyle == BlockStyle.Path || blockStyle == BlockStyle.Start || blockStyle == BlockStyle.End) {
            arrow.setRotate(angle);
            String letter;
            switch (angle/90) {
                case 0 :
                    letter = "U";
                    break;
                case 1 :
                    letter = "R";
                    break;
                case 2 :
                    letter = "D";
                    break;
                case 3 :
                    letter = "L";
                    break;
                default :
                    letter = "U";
            }
            code = String.valueOf(code.charAt(0)) + String.valueOf(code.charAt(1)) + letter +  String.valueOf(code.charAt(3));
            System.out.println("code apres rotation : " + code);
        }

    }


    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public boolean getIsAlreadyTaken() { return isAlreadyTaken; }
    public int getX() { return column; }
    public int getY() { return row; }
    public String getCode() { return code; }



    // ******************************
    // ********** SETTERS ***********
    // ******************************
    public void setBlockStyle(BlockStyle style) { blockStyle = style; }
    public static void setBlockStylePreview(BlockStyle bockStylePreview) { BLOCK_STYLE_PREVIEW = bockStylePreview; }


}