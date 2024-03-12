package game.modul.entityPackage.pnjPackage;


import game.modul.entityPackage.Entity;
import game.modul.Game;
import game.modul.map.Map;
import game.modul.map.Tile;
import interfaces.GetNeighbours;
import interfaces.RemoveEntity;


import java.util.ArrayList;
import java.util.List;

public class Pnj extends Entity implements GetNeighbours, RemoveEntity {

    // point de sante du PNJ
    private int health;

    // vitesse de deplacement du PNJ /!\ la speed doit tjrs etre un multiple des la size des tiles
    private int speed;

    // recompense (argent) recus lorsque le PNJ est tue
    private int	reward;

    // type du pnj
    protected PNJType type;

    // compte le nombre d'instances de la classe PNJ presents sur la map
    // doit etre reinitialisee avant de quiter la partie
    private static int PNJ_INSTANCE_NUMBER = 0;
    private static int PNJ_INCREMENTOR = 0;


    // Entier qui est incremente des que le pnj avance d'un pixel
    // permet de compter le nombre de pixel parcouru par le pnj
    private int firstSpawn = 0;

    // contient l'indice du pnj dans LIST_PNJ_ON_MAP de Game
    private int pnjIndex;
    



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Pnj(int health, int speed, int reward, PNJType type, int[] position){
        super(position[0], position[1], "PNJ");
        this.health = health;
        this.speed = speed;
        this.reward = reward;
        this.type = type;

        // on associe au pnj le mode de deplacement pnj de l'interface move
        entityMove = new MovePnj(this);

        Game.addPnj(this);

        pnjIndex = PNJ_INCREMENTOR;

        // on incremente les variables statiques d'instance de classe
        PNJ_INSTANCE_NUMBER += 1;
        PNJ_INCREMENTOR += 1;

    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode prend en entree la map et l'indice (x,y) d'une Tile
     * et renvoit une liste de Tiles comprises dans le voisinage de la Tile
     */
    @Override
    public List<Tile> getNeighbours() {

        List<Tile> neighbors = new ArrayList<>();

        List<int[]> points = createIndex(1);

        for(int[] index : points) {
            int dx = index[0];
            int dy = index[1];

            int newX = x + dx;
            int newY = y + dy;


            if(newX >= 0 && newX < Map.getCOLUMNS() && newY >= 0 && newY < Map.getROWS()) {
                neighbors.add(Game.getMAP().getMap()[newY][newX]);
            }
        }
        return neighbors;
    }

    /**
     * cette fonction cree une liste d'indices (x,y)
     * representant tous les indices des cases incluses
     * dans le rayon "n" donne en argument
     */
    @Override
    public List<int[]> createIndex(int n) {
        List<int[]> points = new ArrayList<>();
        for(int i1 = 0; i1 < 2*n+1; i1++) {
            for(int i2 = 0; i2 < 2*n+1; i2++) {
                // on bne prend pas l'indice (0,0) car il correspond a la tower
                if(i2-n != 0 || i1-n != 0) {
                    points.add(new int[]{i2-n, i1-n});
                }
            }
        }
        return points;
    }


    /**
     * cette methode renvoit, en fonction de la direction (haut, bas, droite, gauche),
     * une liste de tiles vers lesquels le PNJ peut se deplacer
     */
    public List<Tile> getNextTiles(String direction, List<Tile> neighborsTiles, Tile currentTile) {
        List<Tile> nextTiles = new ArrayList<>();
        for (Tile tile : neighborsTiles) {
            if(tile.isPath()) {
                if(direction.equals("D") && tile.getPosition()[1] > currentTile.getPosition()[1] && tile.getPosition()[0] == currentTile.getPosition()[0]) {
                    nextTiles.add(tile);
                }
                else if(direction.equals("U") && tile.getPosition()[1] < currentTile.getPosition()[1] && tile.getPosition()[0] == currentTile.getPosition()[0]) {
                    nextTiles.add(tile);
                }
                else if(direction.equals("L") && tile.getPosition()[0] < currentTile.getPosition()[0] && tile.getPosition()[1] == currentTile.getPosition()[1]) {
                    nextTiles.add(tile);
                }
                else if(direction.equals("R") && tile.getPosition()[0] > currentTile.getPosition()[0] && tile.getPosition()[1] == currentTile.getPosition()[1]) {
                    nextTiles.add(tile);
                }
            }
        }
        return nextTiles;
    }


    /**
     * cette methode renvoit l'indice de la prochaine Tile
     * vers laquelle le pnj va se deplacer
     */
    public int[] getNextPosition(Map map){

        // on recupere les donnees de map : la tile que le PNJ occupe (+ direction) + les tiles voisines
        List<Tile> neighborsTiles = getNeighbours();
        Tile currentTile = map.getMap()[y][x];
        String direction = currentTile.getDirection();

        // on determine les prochaines tiles vers lesquelles le PNJ peut se deplacer
        List<Tile> nextTiles = getNextTiles(direction, neighborsTiles, currentTile);

        // on selectionne aleatoirement une tile de toutes les tiles disponnibles
        int[] nextPosition;
        // on verifie qu'il y'a encore un chemin possible
        // sinon le png est arrive au bloc end et doit etre efface
        if(nextTiles.size() != 0) {
            Tile nextTile = nextTiles.get(0);
            nextPosition = new int[]{nextTile.getX(), nextTile.getY()};
        } else {
            nextPosition = null;
        }

        return nextPosition;
    }



    /**
     * renvoit true si le pnj se trouve sur la case "start"
     */
    public boolean isPnjOnStartBlock(Map map) {
        boolean bool = false;
        bool = map.getMap()[y][x].isStart();
        return bool;
    }

    /**
     * renvoit true si le pnj se trouve sur la case "end"
     */
    public boolean isPnjOnEndBlock(Map map) {
        boolean bool = false;
        bool = map.getMap()[y][x].isEnd();
        return bool;
    }

    /**
     * cette methode est appelee des qu'un pnj doit etre retire du jeu
     * soir parce qu'il a atteint l'arrivee soit parce qu'il est mort
     */
    @Override
    public void removeEntity() {
        // on retire le pnj de la fenetre
        Game.getPnjView().removePNJ(Game.getListPnjOnMap().get(pnjIndex));

        // on enleve le pnj de la liste des pnj present sur la map
        Game.getListPnjOnMap().set(pnjIndex, null);
        PNJ_INSTANCE_NUMBER -= 1;
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************

    // methode de entity
    @Override
    public int getX() { return super.getX(); }
    @Override
    public int getY() { return super.getY(); }
    @Override
    public int[] getRealCurrentPosition() { return super.getRealCurrentPosition(); }
    public String getPnjId() { return super.getEntityId(); }

    // methode de pnj
    public int getSpeed() { return speed; }
    public int getHealth() { return health; }
    public int getReward() { return reward; }
    public PNJType getPNJType(){ return type; }
    public static int getPnjInstanceNumber() { return PNJ_INSTANCE_NUMBER; }
    public static int getPnjIncrementor() { return PNJ_INCREMENTOR; }
    public int getFirstSpawn() {return firstSpawn; }



    // ******************************
    // ********** SETTERS ***********
    // ******************************

    // methode de entity
    @Override
    public void setX(int x) { super.setX(x); }
    @Override
    public void setY(int y) { super.setY(y); }
    @Override
    public void setRealCurrentPosition(int[] realCurrentPosition) { super.setRealCurrentPosition(realCurrentPosition); }

    // methode de pnj
    public static void setPnjInstanceNumber(int pnjInstanceNumber) { PNJ_INSTANCE_NUMBER = pnjInstanceNumber; }
    public void setHealth(int health) { this.health = health; }
    public void setFirstSpawn(int nb) {this.firstSpawn = nb; }
    public static void setPnjIncrementor(int pnjIncrementor) { PNJ_INCREMENTOR = pnjIncrementor; }

}
