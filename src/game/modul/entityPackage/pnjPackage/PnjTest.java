package game.modul.entityPackage.pnjPackage;

import game.modul.Game;
import game.modul.map.Map;
import menu.Main;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class PnjTest {


    /**
     * ce test verifie que pour une map donnee, le pnj se
     * deplace bien le long d'un chemin
     * et non n'importe ou sur la map
     */
    @Test
    public void getNextPositionTest() {
        // on simule un environnement semblable a celui dans lequel on joue
        Main.getFiles();
        Map map = new Map();
        Game game = new Game(1000, map, 10, 0);

        // on place le pnj en position (11,0) du level 1
        // la prochaine case vers laquelle il doit se deplacer selon
        // le fichier texte de la map est la case -> (11,2)
        Pnj pnj = new Pnj(3, 1, 2, PNJType.PNJ1, new int[]{11,1});

        // on demande au pnj de trouver la prochaine case vers laquelle il doit aller
        int[] nextPosition = pnj.getNextPosition(map);

        for(int i : nextPosition) {
            System.out.println(i);
        }

        // on verifie que le pnj ait trouve la bonne case
        assertArrayEquals(nextPosition, new int[]{11,2});
    }



    /**
     * ce test verifie que si un pnj est sur un block start,
     * la fonction isPnjOnStartBlock renvoie bien true
     * et inversement
     */
    @Test
    public void isPnjOnStartBlockTest() {
        // on simule un environnement semblable a celui dans lequel on joue
        Main.getFiles();
        Map map = new Map();
        Game game = new Game(1000, map, 10, 0);

        // on place le pnj en position (11,0) ce qui corrspond
        // a la case depart de la map du level 1
        Pnj pnj = new Pnj(3, 1, 2, PNJType.PNJ1, new int[]{11,0});

        // on demande au pnj si il est sur la case depart
        boolean isOnStartBlock = pnj.isPnjOnStartBlock(map);

        // on verifie que le pnj renvoie bien true si il est sur le block start
        assertEquals(isOnStartBlock, true);

    }

}
