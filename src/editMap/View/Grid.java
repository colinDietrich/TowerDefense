package editMap.View;

import editMap.controller.EditMouseGestures;
import game.modul.map.Map;
import javafx.scene.layout.Pane;


public class Grid extends Pane {

    // ROWS et COLUMNS representent le nombre de lignes et de colonnes que contient en lesquelles la map est séparée
    private int ROWS;
    private int COLUMNS;

    // STROKE represente la largeur du cadre ajoute a la fentetre
    private static int STROKE = 30;

    // WIDTH et HEIGHT representent la largeur et la hauteur de la map
    private double width;
    private double height;

    // matrice d'EditCell correspondant a la map
    private static EditCell[][] cells;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Grid() {

        this.COLUMNS = Map.getCOLUMNS();
        this.ROWS = Map.getROWS();
        this.width = Map.getWIDTH();
        this.height = Map.getHEIGHT();

        // on initialise la matrice d'editCells
        cells = new EditCell[ROWS][COLUMNS];

        displayMapSettings();

        this.setLayoutX(STROKE);
        this.setLayoutY(STROKE);

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cree et ajoute les EditCells de la map
     */
    public void displayMapSettings() {


        // on cree une instance de mouse gesture pour gere les evenement de la souris sur la map
        EditMouseGestures mouseGesture = new EditMouseGestures();


        // on configure la matrice d'EditCells
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {

                EditCell cell = new EditCell(column, row);
                mouseGesture.makePreview(cell);

                this.add(cell, column, row);
            }
        }
    }


    /**
     * ajoute les EditCells a la matrice et sur la fenetre
     */
    public void add(EditCell cell, int column, int row) {

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



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public static EditCell[][] getCells() { return cells; }

}