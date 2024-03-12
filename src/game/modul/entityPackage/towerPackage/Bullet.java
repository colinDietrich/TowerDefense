package game.modul.entityPackage.towerPackage;

import game.GameView.BulletView;
import game.modul.entityPackage.Entity;
import game.modul.Game;
import game.modul.entityPackage.pnjPackage.Pnj;
import interfaces.RemoveEntity;

public class Bullet extends Entity implements RemoveEntity {

    // nombre d'instances de la classe bullet
    private static int BULLET_INSTANCE = 0;

    // pnj que la bullet doit atteindre
    private Pnj pnj;

    // tower qui a cree la bullet
    private Tower tower;

    // indice de la bullet dans la liste "BULLET_ON_MAP" de Game
    private int bulletindex;

    // vitesse de deplacement de la bullet
    private int speed = 1;

    // instance de Bulletview qui gere l'interface graphique de bullet
    private BulletView bulletView;




    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Bullet(Pnj pnj, Tower tower, int x, int y) {
        super(x, y, "tower");
        this.pnj = pnj;
        this.tower = tower;

        // on place la bullet au bout du canon de la tower
        realCurrentPosition = new int[] {getRealPosition(new int[]{x, y})[0] + getStrokeAroundTower()[0] , getRealPosition(new int[]{x, y})[1] + getStrokeAroundTower()[1] };

        // on incremente le nombre d'instances de bullet
        BULLET_INSTANCE += 1;

        // on ajoute la bullet a la liste "BULLET_ON_MAP" de Game
        Game.addBulletOnMap(this);

        // on enregistre l'indice de la bullet de la liste "BULLET_ON_MAP" de Game
        bulletindex = Game.getBulletNumber();

        // on associe a la bullet le mode de deplacement bullet de l'interface move
        entityMove = new MoveBullet(this);
    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************


    /**
     * cette methode enleve la vie au pnj lorsque la bullet l'a atteint
     */
    public void removehealth() {
        pnj.setHealth(pnj.getHealth() - tower.getTowerType().getAttack());
        removeEntity();

        // si le pnj n'a plus de vies on le retire de la fenetre
        // et la tower peut de nouveau attaquer d'autres pnj
        if(pnj.getHealth() <= 0) {
            tower.setIsBusy(false);
            Game.setMONEY(Game.getMONEY() + pnj.getReward());
            try {
                pnj.removeEntity();
                pnj = null;
            } catch (Exception e) {
                bulletView.removeBullet(entityId);
            }
        }
    }


    /**
     * cette fonction met la bullet dans la liste "BULLET_ON_MAP" de Game
     * sur null lorsque celle-ci est effacee
     */
    @Override
    public void removeEntity() {
        Game.getBulletOnMap().set(bulletindex-1, null);
    }


    /**
     * cette methode renvoit la distance necessaire a ajouter a getRealPosition
     * pour que la bullet apparaisse au bout du canon de la tower
     */
    public int[] getStrokeAroundTower() {
        int[] stroke = new int[2];
        double angle = tower.getAngle();
        double strokeX = 18 * Math.sin(angle*((Math.PI)/180));
        double strokeY = -18 * Math.cos(angle*((Math.PI)/180));
        stroke[0] = (int) strokeX;
        stroke[1] = (int) strokeY;
        return stroke;
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
    public String getBulletId() { return super.getEntityId(); }

    // methode de pnj
    public static int getBulletInstance() { return BULLET_INSTANCE; }
    public Pnj getPnj() { return pnj; }
    public Tower getTower() { return tower; }
    public int getBulletindex() { return bulletindex; }
    public int getSpeed() { return speed; }
    public BulletView getBulletView() { return bulletView; }




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
    public void setBulletView(BulletView bulletView) { this.bulletView = bulletView; };


}

