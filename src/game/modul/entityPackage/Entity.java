package game.modul.entityPackage;

import game.GameView.MapView;
import game.modul.map.Tile;
import interfaces.Move;

public abstract class Entity {

    // indice (x,y) de l'entite dans la map
    protected int x;
    protected int y;

    // variable utile pour l'interface graphique
    // contient les coordonnees reelles (x,y) sur la fenetre
    protected int[] realCurrentPosition;

    // string unique pour chaque entite creee -> permet d'identifier l'entite
    protected String entityId;

    // nombre d'instances de la classe entite
    private static int ENTITY_INSTANCES;

    // mode de deplacement du pnj
    protected Move entityMove;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Entity(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.realCurrentPosition = getRealPosition(new int[]{x, y});
        this.entityId = name+"_"+ENTITY_INSTANCES;

        ENTITY_INSTANCES ++;
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    /**
     * cette methode traduit les coordonnees (x,y) de la map en position réelles (x',y') dans la fenetre
     */
    public int[] getRealPosition(int[] position) {
        int[] realPosition = new int[2];
        double realPositionX = (position[0]) * Tile.getTileSize() + Tile.getTileSize()*(0.5) + MapView.getSTROKE();
        double realPositionY = (position[1]) * Tile.getTileSize() + Tile.getTileSize()*(0.5) + MapView.getSTROKE();
        realPosition[0] = (int)realPositionX;
        realPosition[1] = (int)realPositionY;

        return realPosition;
    }


    /**
     * cette methode fait deplacer l'entite sur la map
     */
    public void move() {
        entityMove.move();
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    protected int getX() { return x; }
    protected int getY() { return y; }
    protected int[] getRealCurrentPosition() { return realCurrentPosition; }
    protected String getEntityId() { return entityId; }



    // ******************************
    // ********** SETTERS ***********
    // ******************************
    protected void setX(int newX) {this.x = newX; }
    protected void setY(int newY) {this.y = newY; }
    protected void setRealCurrentPosition(int[] newRealCurrentPosition) { this.realCurrentPosition = newRealCurrentPosition; }
}
