package game.modul.entityPackage.towerPackage;

import game.modul.Game;
import game.modul.entityPackage.pnjPackage.PNJType;
import game.modul.entityPackage.pnjPackage.Pnj;
import game.modul.map.Map;
import menu.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BulletTest {


    /**
     * ce test verifie que lorsque une bullet atteint un pnj
     * celle-ci lui enleve bien le nombre de vie correspondant
     * a la tower qui a envoye la bullet
     */
    @Test
    public void removeHealthTest() {
        // on simule un environnement semblable a celui dans lequel on joue
        Main.getFiles();
        Map map = new Map();
        Game game = new Game(1000, map, 10, 0);
        Pnj pnj = new Pnj(3, 1, 2, PNJType.PNJ1, new int[]{0,0});
        Tower tower = new Tower(10, 10, TowerType.TOWER1_LEVEL1);

        // on cree une bullet qui va atteindre pnj
        Bullet bullet = new Bullet(pnj, tower, 0, 0);

        // on recupere la vie initiale du pnj ainsi que les points
        // de vie que la bullet doit enlever
        int initialHealth = pnj.getHealth();
        int attack = tower.getTowerType().getAttack();

        // la bullet atteint le pnj
        bullet.removehealth();

        // on verifie si le test a bien fonctionne
        assertEquals(pnj.getHealth(), initialHealth - attack);
    }



}
