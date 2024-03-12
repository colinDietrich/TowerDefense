package game.modul.map;

import menu.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Map {

    // WIDTH et HEIGHT representent la largeur et la hauteur de la map
    private static final int WIDTH = 540;
    private final static int HEIGHT = 540;

    // ROWS et COLUMNS representent le nombre de lignes et de colonnes que contient en lesquelles la map est séparée
    private static int ROWS = 15;
    private static int COLUMNS = 15;

    // utile pour l'interface graphique
    // STROKE represente la largeur du cadre ajoute a la fentetre
    private static final int STROKE = 30;

    // matrice de Tiles correspondant a la map
    private Tile[][] map = new Tile[ROWS][COLUMNS];



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Map() {

        // on recupere le fichier texte contenant la map
        LoadMap file = null;
        try {
            file = new LoadMap(Main.getListLevelUrl().get(Main.getLevel()-1));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la map : nom du fichier texte = " + Main.getListLevelUrl().get(Main.getLevel()-1));
            e.printStackTrace();
        }

        // on cree la matrice de Tiles correspondant a la map
        String[][] grid = file.getGrid();
        setMap(grid);
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode transforme la matrice de String
     * recuperee a partir d'un ficher texte en une matrice
     * de Tiles correspondant a la map
     */
    public void setMap(String[][] grid) {
        for(int y = 0; y < ROWS; y++) {
            for(int x = 0; x < COLUMNS; x++) {
                map[y][x] = new Tile(x, y, grid[y][x]);
            }
        }
    }


    /**
     * cette methode renvoit une liste d'entiers contenant
     * toutes les positions des blocs "start" de la map
     */
    public List<int[]> getListStartPosition() {
        List<int[]> listposition = new ArrayList<>();
        for(int y = 0; y < ROWS; y++) {
            for(int x = 0; x < COLUMNS; x++) {
                if(map[y][x].isStart()) {
                    listposition.add(map[y][x].getPosition());
                }
            }
        }
        return listposition;
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public static int getROWS() { return ROWS; }
    public static int getCOLUMNS() { return COLUMNS; }
    public static int getWIDTH() { return WIDTH; }
    public static int getHEIGHT() { return HEIGHT; }
    public Tile[][] getMap() {return map; }
    public static int getSTROKE() { return STROKE; }

}
