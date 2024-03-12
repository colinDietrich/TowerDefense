package interfaces;

import game.modul.map.Tile;

import java.util.List;

public interface GetNeighbours {

    /**
     * cette fonction renvoit la liste de Tiles
     * comprises dans le rayon d'une entite
     */
    public List<Tile> getNeighbours();


    /**
     * cette fonction cree une liste d'indices (x,y)
     * representant tous les indices des cases incluses
     * dans le rayon "n" donne en argument
     */
    public List<int[]> createIndex(int n);

}
