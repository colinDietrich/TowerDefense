package game.modul.entityPackage.towerPackage;


import game.GameView.BulletView;
import game.GameView.Cell;
import game.modul.entityPackage.Entity;
import game.modul.Game;
import game.modul.map.Map;
import game.modul.entityPackage.pnjPackage.Pnj;
import game.modul.map.Tile;
import interfaces.GetNeighbours;

import java.util.ArrayList;
import java.util.List;



public class Tower extends Entity implements GetNeighbours {

    // correspond au type de la tower
    private TowerType towerType;

    // angle initial de la tower
    private double angle = 0;

    // niveau initial de la tower
    private int level = 1;

    // instance de BulletView qui gere l'interface graphique des bullets envoyees par la tower
    private BulletView bulletView;

    // permet de controller les differentes frequences des tower
    private int frequecyIncrementer;

    // true si la tower a le droit de tirer une bullet
    // cette variable permet de gerer les differentes frequences de tower avec frequencyIncrementer
    private boolean canShoot = false;

    // true si le joueur appuie sur le bouton upgrade de la tower
    private static boolean isUpgradeTransaction = false;

    // cell dans laquelle la tower est contenue
    private Cell cell;

    // true si la tower est entrain d'attaquer un pnj
    private boolean isBusy = false;

    // String qui contient l'id du pnj que la tower est entrain d'attaquer
    private Pnj pnjAttack;

    // int qui contient l'indice du pnj dans la liste "Game.getListPnjOnMap()"
    private int pnjAttackIndex;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Tower(int x, int y, TowerType towerType){
        super(x, y, "tower");
        this.towerType = towerType;
        this.pnjAttackIndex = 0;
        this.bulletView = new BulletView();

        // on associe a la tower le mode de deplacement tower de l'interface move
        entityMove = new MoveTower(this);
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette fonction renvoit la liste de Tiles
     * comprises dans le rayon de la tower
     */
    @Override
    public List<Tile> getNeighbours() {
        int radius = towerType.getRadius();

        List<Tile> neighbors = new ArrayList<>();

        List<int[]> points = createIndex(radius);

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
     * cette fonction cherche des pnj compris dans le radius de la tower
     */
    public void lookForPnj(List<Tile> listTiles) {
        // on reinitialise l'increment pour les frequences de tir des qu'il vise un nouveau pnj
        frequecyIncrementer = 0;
        // on cherche si un des pnj present sur la map est dans le voisinage de la tower
        for(Tile tile : listTiles) {
            int index = 0;
            for(Pnj pnj : Game.getListPnjOnMap()) {
                // on veridie d'abord que le pnj n'est pas deja mort
                if(pnj != null) {
                    // si on trouve un pnj -> on la tower peut l'attaquer
                    // une fois qu'on en a trouve un on arrete la boucle en mettant "isBusy" sur true
                    if(tile.getX() == pnj.getX() && tile.getY() == pnj.getY() && !isBusy) {
                        isBusy = true;
                        pnjAttack = pnj;
                        pnjAttackIndex = index;
                    }
                }
                index++;
            }
        }
    }


    /**
     * cette fonction met a jour l'interaction entre la tower et la pnj
     * elle verifie si le pnj est toujours dans le radius de la tower
     * si c'est la cas elle lui enleve de la vie
     */
    public void updateTowerPnjInteraction(List<Tile> listTiles) {

        // on verifie en fonction de la frequence de la tower si elle peut tirer ou non
        updateCanShoot();

        // boolen qui renseigne si le pnj est toujours dans le radius de la tower ou non
        boolean isStillInRadius = false;
        // on verifie que le pnj n'est pas null
        // car il a pu atteindre l'arrivee ou etre tue par une autre tower entre temps
        if(Game.getListPnjOnMap().get(pnjAttackIndex) != null) {

            // on verifie que le pnj est toujours dans le radius de la tower
            Pnj pnj = Game.getListPnjOnMap().get(pnjAttackIndex);
            if(canShoot && pnj.getFirstSpawn() > 9) {
                for(Tile tile : listTiles) {
                    if(tile.getX() == pnj.getX() && tile.getY() == pnj.getY()) {
                        isStillInRadius = true;
                    }
                }
                // si le pnj n'est plus dans le radius de la tower -> la tower peut attaquer d'autres ennemis
                if(!isStillInRadius) {
                    isBusy = false;
                }
                // si le pnj est encore dans le radius de la tower -> la tower l'attaque
                else {
                    // on enleve des points de vies au pnj
                    Bullet bullet = new Bullet(pnj, this, x, y);

                    // si on fait un test junit -> on utilise pas l'interface graphique
                    if(!Game.getIsJunitTest()) {
                        bullet.setBulletView(bulletView);
                        bulletView.createBullet(bullet);
                    }

                }
            }

        } else {
            isBusy = false;
        }
    }


    /**
     * cette methode permet de prendre en compte les
     * differentes frequence des towers
     */
    public void updateCanShoot() {
        frequecyIncrementer++;
        if(frequecyIncrementer > towerType.getMaxFrequency() - towerType.getFrequency()) {
            frequecyIncrementer = 0;
            canShoot = true;
        }
    }


    /**
     * cette fonction met a jour l'angle entre les tower et les pnj
     * et les fait viser les pnj qu'elles sont entrain d'attaquer
     */
    public void pointPnj() {
        List<Tile> listTiles = getNeighbours();

        // on verifie que le pnj n'est pas null
        // car il a pu atteindre l'arrivee ou etre tue par une autre tower entre temps
        if(Game.getListPnjOnMap().get(pnjAttackIndex) != null) {
            // boolen qui renseigne si le pnj est toujours dans le radius de la tower ou non
            boolean isStillInRadius = false;
            // on verifie que le pnj n'est pas null
            // car il a pu atteindre l'arrivee ou etre tue par une autre tower entre temps
            if(Game.getListPnjOnMap().get(pnjAttackIndex) != null) {

                // on verifie que le pnj est toujours dans le radius de la tower
                Pnj pnj = Game.getListPnjOnMap().get(pnjAttackIndex);
                for(Tile tile : listTiles) {
                    if(tile.getX() == pnj.getX() && tile.getY() == pnj.getY()) {
                        isStillInRadius = true;
                    }
                }
                // si le pnj n'est plus dans le radius de la tower -> la tower peut attaquer d'autres ennemis
                if(isStillInRadius) {
                    // on met a jour l'angle entre le pnj et la tower
                    move();
                    // on appelle l'animation qui va faire bouger la tower
                    cell.rotateTower(angle);
                }
            }

        }

    }


    /**
     * cette methode permet a une tower d'attaquer un pnj
     */
    public void attackPnj() {
        List<Tile> listTiles = getNeighbours();

        // on verifie que la tower n'est pas deja entrain d'attaquer un pnj
        // si ce n'est pas le cas on cherche des pnj a attaquer
        if(!isBusy) {
            lookForPnj(listTiles);
        }

        // si la tower est entrain d'attaquer
        // on a pas mis un else expres car cela permet a la tour d'attaquer
        // directement si elle trouve un penj dans son rayon
        if(isBusy) {
            updateTowerPnjInteraction(listTiles);
        }
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
    public String getTowerId() { return super.getEntityId(); }

    // methode de pnj
    public boolean getIsBusy() {return isBusy; }
    public TowerType getTowerType() { return towerType; }
    public static boolean getIsIsUpgradeTransaction() { return isUpgradeTransaction; }
    public int getLevel() { return level; }
    public double getAngle() { return angle; }
    public BulletView getBulletView() { return bulletView; }
    public int getPnjAttackIndex() { return pnjAttackIndex; }
    public Pnj getPnjAttack() { return pnjAttack; }



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
    public void setIsBusy(boolean bool) {this.isBusy = bool; }
    public void setCell(Cell cell) {this.cell = cell; }
    public static void setIsUpgradeTransaction(boolean isUpgradeTransaction) { Tower.isUpgradeTransaction = isUpgradeTransaction; }
    public void setTowerType(TowerType towerType) { this.towerType = towerType; }
    public void setLevel(int level) { this.level = level; }
    public void setAngle(double angle) { this.angle = angle; }
    public void setPnjAttack(Pnj pnjAttack) { this.pnjAttack = pnjAttack; }
    public void setFrequecyIncrementer(int frequecyIncrementer) { this.frequecyIncrementer = frequecyIncrementer; }
}
