package game.modul.entityPackage.towerPackage;

import game.modul.entityPackage.pnjPackage.Pnj;
import interfaces.Move;

public class MoveBullet implements Move {

    // bullet que move bullet doit faire bouger
    private Bullet bullet;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public MoveBullet(Bullet bullet) {
        this.bullet = bullet;
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    @Override
    public void move() {

        Pnj pnj = bullet.getPnj();
        int speed = bullet.getSpeed();
        // on recupere la position reelle du pnj depuis sa classe
        int[] realCurrentPosition = bullet.getRealCurrentPosition();

        if(pnj != null){

            updateBulletPosition(realCurrentPosition, speed, pnj);

        } else {
            bullet.getBulletView().removeBullet(bullet.getBulletId());
            bullet.removeEntity();
        }

    }

    /**
     * cette methode met a jour la position de la bullet
     * en fonction de la position du pnj qu'elle doit atteindre
     */
    public void updateBulletPosition(int[] realCurrentPosition, int speed, Pnj pnj) {

        if (realCurrentPosition[0] < pnj.getRealCurrentPosition()[0]) {
            realCurrentPosition[0] += speed;
            bullet.setRealCurrentPosition(realCurrentPosition);
        } else if (realCurrentPosition[0] > pnj.getRealCurrentPosition()[0]) {
            realCurrentPosition[0] -= speed;
            bullet.setRealCurrentPosition(realCurrentPosition);
        }
        if (realCurrentPosition[1] < pnj.getRealCurrentPosition()[1]) {
            realCurrentPosition[1] += speed;
            bullet.setRealCurrentPosition(realCurrentPosition);
        } else if (realCurrentPosition[1] > pnj.getRealCurrentPosition()[1]) {
            realCurrentPosition[1] -= speed;
            bullet.setRealCurrentPosition(realCurrentPosition);
        }
        if (realCurrentPosition[0] == pnj.getRealCurrentPosition()[0] && realCurrentPosition[1] == pnj.getRealCurrentPosition()[1]) {
            bullet.removehealth();
            bullet.getBulletView().removeBullet(bullet.getBulletId());
        }
    }


}
