package game.GameView;

import game.Controller.MouseGestures;
import game.modul.map.Map;
import game.modul.map.Tile;
import game.modul.entityPackage.towerPackage.Tower;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import menu.model.ButtonStyle;
import menu.model.SPButton;



public class MapView extends Pane {

    // ROWS et COLUMNS representent le nombre de lignes et de colonnes que contient en lesquelles la map est séparée
    private int ROWS;
    private int COLUMNS;

    // STROKE represente la largeur du cadre ajoute a la fentetre
    private static int STROKE = 30;

    // WIDTH et HEIGHT representent la largeur et la hauteur de la map
    private double width;
    private double height;

    // cerlce qui affiche le rayon d'une tower
    private Circle circleRadius;

    // bouton qui permet de faire evoluer une tower
    private SPButton upgradeButton;

    // Cell qui est selectionne lorsque le joueur a clique dessus
    private Cell selectionnedCell;

    // instance de la map utilisee dans Game
    private Map mapInstance;

    // matrice de Cells de la map -> similaire a la matrice de Tiles de la map
    // mais pour l'interface graphique
    private Cell[][] cells;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public MapView(Map mapInstance) {

        this.mapInstance = mapInstance;
        this.COLUMNS = Map.getCOLUMNS();
        this.ROWS = Map.getROWS();
        this.width = Map.getWIDTH();
        this.height = Map.getHEIGHT();

        // on initialise la matrice de Cells
        cells = new Cell[ROWS][COLUMNS];

        // on cree et ajoute les cells a la map
        displayMapSettings();

        // on place MapView sur la fenetre
        this.setLayoutX(MapView.getSTROKE());
        this.setLayoutY(MapView.getSTROKE());

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cree et ajoute les Cells de la map
     */
    public void displayMapSettings() {

        // On recupere les donnees de la classe Map
        Tile[][] map = mapInstance.getMap();

        // on cree une instance de mouse gesture pour gere les evenement de la souris sur la map
        MouseGestures mouseGesture = new MouseGestures();


        // on configure la matrice de Cells
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {

                // on recupere l'url de l'image correspondant a la tile situee en position (row, column) de map
                Tile tile = map[row][column];
                // on cree une image correspondant a la tile
                ImageView image = new ImageView(new Image(tile.getType().getUrl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
                image.setRotate(tile.getAngle());

                Cell cell = new Cell(column, row, tile.isBlock());
                cell.addImage(image);
                mouseGesture.makePreview(cell);

                this.add(cell, column, row);
            }
        }
    }


    /**
     * ajoute les Cells a la matrice et sur la fenetre
     */
    public void add(Cell cell, int column, int row) {

        cells[row][column] = cell;

        double w = width / COLUMNS;
        double h = height / ROWS;
        double x = w * column;
        double y = h * row;

        cell.setLayoutX(x);
        cell.setLayoutY(y);
        cell.setPrefWidth(w);
        cell.setPrefHeight(h);

        getChildren().add(cell);

    }


    /**
     * cree le cercle qui correspond au rayon de la tower
     */
    public void setCircleRadius(int x, int y, int radius) {
        circleRadius = new Circle();
        double w = width / COLUMNS;
        double h = height / ROWS;
        circleRadius.setCenterX(x*w + w/2);
        circleRadius.setCenterY(y*h + h/2);
        circleRadius.setRadius(radius*w);
        circleRadius.setFill(Color.GRAY);
        circleRadius.setOpacity(0.5);
        getChildren().add(circleRadius);
        circleRadius.toFront();
    }


    /**
     * efface le cercle qui correspond au rayon de la tower
     */
    public void removeCircleRadius() {
        getChildren().remove(circleRadius);
    }


    /**
     * cree le bouton permettant de faire evoluer une tower
     */
    public void createUpgradeButton(int x, int y, Tower tower) {
        double w = width / COLUMNS;
        double h = height / ROWS;
        MouseGestures mg = new MouseGestures();
        String text = "UPGRADE " +tower.getTowerType().getPrice()+" $";

        if(tower.getLevel() >= 3) {
            text = "NO UPGRADE";
        }
        upgradeButton = new SPButton(text, ButtonStyle.ButtonFree, ButtonStyle.ButtonPressed,ButtonStyle.ButtonFree.getWidth()/3, ButtonStyle.ButtonFree.getHeight()/2, ButtonStyle.ButtonFree.getFontValue()/3);

        upgradeButton.setLayoutY(GameView.getHEIGHT() - upgradeButton.getHEIGHT()/2 - 70);
        upgradeButton.setLayoutX(685 - upgradeButton.getWIDTH()/2);
        getChildren().add(upgradeButton);
        upgradeButton.setLayoutX(x*w - w/3);
        upgradeButton.setLayoutY(y*h + h);

        mg.makeUpgradeAction(upgradeButton);
    }

    /**
     * efface le bouton permettant de faire evoluer une tower
     */
    public void removeUpgradeButton() {
        getChildren().remove(upgradeButton);
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public static int getSTROKE() { return STROKE; }
    public Cell getSelectionnedCell() { return selectionnedCell; }
    public void setSelectionnedCell(Cell selectionnedCell) { this.selectionnedCell = selectionnedCell; }
}
