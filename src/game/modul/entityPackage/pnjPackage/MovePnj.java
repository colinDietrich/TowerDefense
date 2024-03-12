package game.modul.entityPackage.pnjPackage;

import game.modul.Game;
import interfaces.Move;

public class MovePnj implements Move {

    // pnj que move pnj doit faire bouger
    private Pnj pnj;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public MovePnj(Pnj pnj) {
        this.pnj = pnj;
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    @Override
    public void move() {

        int[] nextPosition = pnj.getNextPosition(Game.getMAP());

        int[] realNextPosition = pnj.getRealPosition(nextPosition);

        // on recupere la position reelle du pnj depuis sa classe
        int[] realCurrentPosition = pnj.getRealCurrentPosition();


        if (realCurrentPosition[0] < realNextPosition[0]) {
            realCurrentPosition[0] += pnj.getSpeed();
            pnj.setRealCurrentPosition(realCurrentPosition);
        } else if (realCurrentPosition[0] > realNextPosition[0]) {
            realCurrentPosition[0] -= pnj.getSpeed();
            pnj.setRealCurrentPosition(realCurrentPosition);
        }
        if (realCurrentPosition[1] < realNextPosition[1]) {
            realCurrentPosition[1] += pnj.getSpeed();
            pnj.setRealCurrentPosition(realCurrentPosition);
        } else if (realCurrentPosition[1] > realNextPosition[1]) {
            realCurrentPosition[1] -= pnj.getSpeed();
            pnj.setRealCurrentPosition(realCurrentPosition);
        }
        if (realCurrentPosition[0] == realNextPosition[0] && realCurrentPosition[1] == realNextPosition[1]) {
            pnj.setX(nextPosition[0]);
            pnj.setY(nextPosition[1]);
        }
    }

}
