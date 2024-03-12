package game.modul.entityPackage.towerPackage;

import game.modul.Game;
import game.modul.entityPackage.pnjPackage.PNJType;
import game.modul.entityPackage.pnjPackage.Pnj;
import game.modul.map.Map;
import game.modul.map.Tile;
import menu.Main;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TowerTest {


    /**
     * ce test verifie que si un pnj se trouve dans le rayon d'action
     * de la tower, celle-ci l'enregistre pour ensuite pouvoir l'attaquer
     */
    @Test
    public void lookForPnjTest() {

        // on simule un environnement semblable a celui dans lequel on joue
        Main.getFiles();
        Map map = new Map();
        Game game = new Game(1000, map, 10, 0);

        // on cree une tower a l'emplacement (1,2)
        Tower tower = new Tower(1, 2, TowerType.TOWER1_LEVEL1);

        // on place un pnj dans le rayon d'action de la tower -> emplacement (1,1)
        Pnj pnj = new Pnj(3, 1, 2, PNJType.PNJ1, new int[]{1,1});
        List<Pnj> listPnjonMap = new ArrayList<>();
        listPnjonMap.add(pnj);
        Game.setListPnjOnMap(listPnjonMap);

        // on demande a la tower de chercher un pnj dans son rayon d'action
        // si elle en trouve un elle l'enregistre dans son attribut pnjAttack
        List<Tile> listTiles = tower.getNeighbours();
        tower.lookForPnj(listTiles);

        // Normalement la tower doit avoir trouve pnj et donc pnjAttack
        // et le pnj declare ci-dessus doivent etre egaux
        assertEquals(tower.getPnjAttack(), pnj);
    }


    /**
     * ce test verifie que si un pnj se trouve dans le rayon d'action
     * de la tower, celle-ci envoie bien une bullet vers le pnj
     */
    @Test
    public void updateTowerPnjInteractionTest() {

        // on simule un environnement semblable a celui dans lequel on joue
        Main.getFiles();
        Map map = new Map();
        Game game = new Game(1000, map, 10, 0);
        Game.SetIsJunitTest(true);

        // on cree une tower a l'emplacement (1,2)
        Tower tower = new Tower(1, 2, TowerType.TOWER1_LEVEL1);

        // on place un pnj dans le rayon d'action de la tower -> emplacement (1,1)
        Pnj pnj = new Pnj(3, 1, 2, PNJType.PNJ1, new int[]{1,1});
        List<Pnj> listPnjonMap = new ArrayList<>();
        listPnjonMap.add(pnj);
        Game.setListPnjOnMap(listPnjonMap);

        // on donne a la tower un pnj a attaquer -> pnjAttack
        // on lui demande de lancer une bullet sur le pnj si celui-ci est dans son rayon d'action
        List<Tile> listTiles = tower.getNeighbours();
        tower.setIsBusy(true);
        tower.setPnjAttack(pnj);
        tower.setFrequecyIncrementer(tower.getTowerType().getMaxFrequency() - tower.getTowerType().getFrequency());
        pnj.setFirstSpawn(10);
        tower.updateTowerPnjInteraction(listTiles);



        // Normalement la tower doit avoir trouve pnj et donc pnjAttack
        // si c'est le cas une la tower a envoye une bullet sur le pnj et donc une nouvelle
        // bullet a ete creee -> le nombre total de bullet sur la map doit etre egal a 1
        assertEquals(Game.getBulletNumber(), 1);
    }


}
