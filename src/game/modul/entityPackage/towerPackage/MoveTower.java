package game.modul.entityPackage.towerPackage;

import game.modul.entityPackage.pnjPackage.Pnj;
import interfaces.Move;

public class MoveTower implements Move {

    // tower que move tower doit faire bouger
    private Tower tower;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public MoveTower(Tower tower) {
        this.tower = tower;
    }



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************
    @Override
    public void move() {
        Pnj pnj = tower.getPnjAttack();

        if(pnj != null) {
            //on recupere les coordonnees du pnj et de la tour -> on les convertit en coordonnees reelles sur la map
            double x2 = pnj.getRealCurrentPosition()[0]+0.5*pnj.getPNJType().getHeight() - tower.getRealCurrentPosition()[0];
            double y2 = pnj.getRealCurrentPosition()[1]+0.5*pnj.getPNJType().getWidth() - tower.getRealCurrentPosition()[1];
            double x1 = 0;
            double y1 = -1;
            double norme1 = Math.sqrt(Math.pow(x1,2) + Math.pow(y1,2)); // d'office egale a 1 mais bon
            double norme2 = Math.sqrt(Math.pow(x2,2) + Math.pow(y2,2));
            double argument = ((x1*x2) + (y1*y2)) / (norme1 * norme2);
            double angle1;
            if(x2>0){
                angle1 =  ((Math.acos(argument))/(2*Math.PI)) *360;
            } else {
                angle1 = -((Math.acos(argument))/(2*Math.PI)) *360;
            }

            tower.setAngle(angle1);
        }

    }

}
