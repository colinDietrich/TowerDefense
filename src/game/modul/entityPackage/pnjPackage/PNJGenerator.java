package game.modul.entityPackage.pnjPackage;

import game.GameView.PNJView;
import game.modul.Game;
import game.modul.map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PNJGenerator{

    // instance de pnjView cree pour chaque pnjGenerator
    // qui gere toute l'interface graphique liee aux pnj presents sur la map
    private PNJView pnjView;

    // instance de la map utilisee dans "Game"
    private Map map;

    // true si la fonction "initialisation()" de pnjGenerator a ete executee
    private boolean initialisation = false;

    // numero de vague du "Game"
    private int wave;

    // nombre de pnj deja apparu sur la map
    private int numberPnjSpawn = 0;

    // liste contenant tous les id des pnj ayant ete crees par pnjGenerator
    private List<String> listOfPnjId = new ArrayList<>();

    // false si tous les pnj crees par pnjGenerator ont ete tues -> true sinon
    private boolean isRunning = true;




    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public PNJGenerator(Map map, int wave, PNJView view) {
        this.map = map;
        this.wave = wave;
        this.pnjView = view;

        Pnj.setPnjIncrementor(0);

        // on cree la liste de pnj qui vont etre envoyes par pnjGenerator
        createListPnjWave();

        // on enregistre tous les id des pnj crees par pnjGenerator
        for(Pnj pnj : Game.getListPnjOnMap()) {
            listOfPnjId.add(pnj.getPnjId());
        }

        initialisation();

    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * methode appelee par le timer de Game
     * pour ajouter et faire bouger les pnj sur la map
     */
    public void run() {

        // tant qu'il y'a encore des pnj sur la map -> on continue a appeler cette fonction pour les faire avancer
        if(Pnj.getPnjInstanceNumber() > 0){

            // on verifie si le dernier pnj envoye a quitte la case depart
            // si c'est le cas -> on peut envoyer un nouveau pnj
            updatePnjStart();

            // initialisation est true si tous les pnj ont ete crees
            if(initialisation) {
                // on prend tous les pnj de la liste qui ont quitte la case depart
                // et si ils ne sont pas null -> on les fait bouger
                moveListPnj();
            }

        } else {

            // si il n'y a plus de pnj -> on arrete pnjGenerator
            isRunning = false;
        }

    }



    /**
     * cette fonction verifie que le dernier pnj envoye par pnjGenerator
     * ait bien quite la case depart
     * si c'est la cas -> il permet au prochain pnj d'être ajoute au Game
     */
    public void updatePnjStart() {
        // on verifie a chaque fois que le pnj precedent a quiter la case start
        if(Game.getListPnjOnMap().get(numberPnjSpawn) != null) {
            // on increment le nombre de pnj cree par la classe "PnjGenerator"
            if(!Game.getListPnjOnMap().get(numberPnjSpawn).isPnjOnStartBlock(map) && numberPnjSpawn < Game.getListPnjOnMap().size()-1) {
                numberPnjSpawn += 1;
            }
        } else {
            if(numberPnjSpawn < Game.getListPnjOnMap().size()-1) {
                numberPnjSpawn += 1;
            }
        }
    }



    /**
     * cette methode permet de deplacer tous les pnj
     * crees par pnjGenerator sur la map
     */
    public void moveListPnj() {
        // on prend tous les pnj de la liste qui ont quitte la case depart
        // et si ils ne sont pas null -> on les fait bouger
        for (int i = 0; i < numberPnjSpawn+1; i++) {
            Pnj pnj = Game.getListPnjOnMap().get(i);
            if (pnj != null) {
                // on verifie que le pnj n'est pas arrive au bloc end
                if (pnj.getNextPosition(map) != null) {
                    pnj.move();
                    pnjView.move(pnj);
                    if(pnj.getFirstSpawn() < 10) {
                        pnj.setFirstSpawn(Game.getListPnjOnMap().get(i).getFirstSpawn()+1);
                    }
                } else {
                    // on enleve une vie au Game
                    Game.setLIVES(Game.getLIVES()-1);
                    // quand le pnj atteind l'arrivee ont le positionne en dehors de la map avant de l'enlever
                    pnj.removeEntity();
                }
            }
        }
    }



    /**
     * cette methode renvoit la postion d'un bloc start de la map au hasard
     */
    public int[] getStartPostion(List<int[]> listPosition) {
        int length = listPosition.size();
        Random rand = new Random();
        int index = rand.nextInt(length);
        return listPosition.get(index);
    }



    /**
     * cette methode renvoit une liste de pnj a envoyer selon le numero de la vague
     */
    public List<Pnj> createListPnjWave() {

        List<Pnj> listPnjWave = new ArrayList<>();

        for(int i = 0; i < wave; i++) {
            // on choisi une des positions start possibles differentes pour chaque pnj
            int[] startPosition = getStartPostion((map.getListStartPosition()));

            // trois premieres vagues -> on envoit le premier type de pnj
            if(wave <= 3) {
                listPnjWave.add(new Pnj(PNJType.PNJ1.getInitialHealth(), PNJType.PNJ1.getInitialSpeed(), PNJType.PNJ1.getInitialReward(), PNJType.PNJ1, startPosition));
            }

            // si vague plus grand que 3 -> on envoit deux types de pnj une fois sur deux
            else if(wave > 3 && wave <= 10) {
                if(i%2 == 0) {
                    listPnjWave.add(new Pnj(PNJType.PNJ1.getInitialHealth(), PNJType.PNJ1.getInitialSpeed(), PNJType.PNJ1.getInitialReward(), PNJType.PNJ1, startPosition));
                } else {
                    listPnjWave.add(new Pnj(PNJType.PNJ2.getInitialHealth(), PNJType.PNJ2.getInitialSpeed(), PNJType.PNJ2.getInitialReward(), PNJType.PNJ2, startPosition));
                }
            }

            // si vagque plus grand que 10 -> ont envoit trois types de pnj choisis au hasard
            else if(wave > 10) {
                PNJType[] listType = new PNJType[]{PNJType.PNJ1, PNJType.PNJ2, PNJType.PNJ3};
                Random rand = new Random();
                int index = rand.nextInt(listType.length);
                listPnjWave.add(new Pnj(listType[index].getInitialHealth(), listType[index].getInitialSpeed(), listType[index].getInitialReward(), listType[index], startPosition));
            }
        }
        return listPnjWave;
    }


    /**
     * ajoute tous les pnj a la map avant de lancer le timer de Game
     */
    public void initialisation() {
        // on ajoute tous les pnj necessaire à la case start
        if(!initialisation) {
            initialisation = true;
            for(Pnj pnj : Game.getListPnjOnMap()) {
                int[] position = new int[]{pnj.getX(), pnj.getY()};
                pnjView.addPNJ(position, pnj);
            }
        }
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public boolean getIsRunnig() { return isRunning; }


}
