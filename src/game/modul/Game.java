package game.modul;

import game.Controller.MouseGestures;
import game.GameView.*;
import game.modul.map.Map;
import game.modul.entityPackage.pnjPackage.PNJGenerator;
import game.modul.entityPackage.pnjPackage.Pnj;
import game.modul.entityPackage.towerPackage.Bullet;
import game.modul.entityPackage.towerPackage.TowerType;
import game.modul.entityPackage.towerPackage.Tower;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    // argent du joueur
    private static int MONEY;

    // numero de la vague
    private static int WAVE;

    // instance de la map utilisee
    private static Map MAP;

    // nombre de vies restantes du joueur
    private static int LIVES;

    // instance de pnjView qui gere l'interface graphique des pnj sur la map
    private static PNJView pnjView;

    // instance de gameView qui gere l'interface graphique generale du jeu
    private GameView gameView;

    // si true -> le jeu est fini (soit gagne, soit perdu)
    private static boolean isGameDone = false;

    // le numero de vague a atteindre pour gagner la partie
    private final static int finalWave = 20;

    // la liste des pnj presents sur la map
    private static List<Pnj> LIST_PNJ_ON_MAP;

    // la liste des towers presents sur la map
    private static List<Tower> LIST_TOWER_ON_MAP;


    // la liste des bullets presents sur la map
    private static List<Bullet> BULLET_ON_MAP = new ArrayList<>();

    // nombre de bullets presentes sur la map
    private static int BULLET_NUMBER = 0;

    // timer qui generent les pnj, towers et bullets ainsi que leurs deplacements
    private Timer timer;

    // on met ce boolean sur true lorsqu'on veut faire des test unitaires
    // -> cela permet de ne pas utiliser l'interface graphique
    private static boolean isJunitTest = false;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Game(int money, Map map, int lives, int wave) {
        MONEY = money;
        MAP = map;
        LIVES = lives;
        WAVE = wave;
        LIST_TOWER_ON_MAP = new ArrayList<>();
        LIST_PNJ_ON_MAP = new ArrayList<>();

    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode configure le game over et affiche la fenetre de fin du jeu
     */
    public void gameOver() {
        QuitGameView quit = new QuitGameView(this, gameView, "GAME OVER");
        isGameDone = true;
    }


    /**
     * cette methode configure la victoire et affiche la fenetre de fin du jeu
     */
    public void win() {
        QuitGameView quit = new QuitGameView(this, gameView, "YOU WON");
        isGameDone = true;
    }


    /**
     * cette methode envoie une nouvelle vague de pnj et s'occupe
     * de son actualisation jusqu'a ce que la vague soit finie
     */
    public void createNewWave() {
        // on met a jour la variable wave
        WAVE += 1;    // on cree une nouvelle view qui va permettre d'afficher les pnj
        pnjView = new PNJView();
        BULLET_ON_MAP = new ArrayList<>();
        LIST_PNJ_ON_MAP = new ArrayList<>();
        BULLET_NUMBER = 0;
        MouseGestures.setIsExplosionButton(false);

        // on cree un nouvel objet pnj generator qui gere l'envoi des pnj sur la map
        // elle remet egalement a jour la liste des pnj presents sur la map "LIST_PNJ_ON_MAP"
        PNJGenerator pnjGenerator = new PNJGenerator(MAP, WAVE, pnjView);

        // on recupere les differentes taches a effectuer par le timer
        List<TimerTask> listTimerTask = createTimerTask(pnjGenerator);

        // on lance le timer et ses differentes taches
        timer = new Timer();
        timer.schedule(listTimerTask.get(0), 0, 31);
        timer.schedule(listTimerTask.get(1), 0,173);
        timer.schedule(listTimerTask.get(2), 0,5);


    }


    /**
     * cette methode permet de creer les different timertask
     * que le timer devra executer
     * elle renvoie une liste de ces taches
     */
    public List<TimerTask> createTimerTask(PNJGenerator pnjGenerator) {
        List<TimerTask> listTimerTask = new ArrayList<>();

        final TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(pnjGenerator.getIsRunnig()) {
                        pnjGenerator.run();
                    } else {
                        endTimer();
                    }
                });
            }
        };
        listTimerTask.add(task1);

        final TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(pnjGenerator.getIsRunnig()) {
                        for(Tower tower : LIST_TOWER_ON_MAP) {
                            tower.attackPnj();
                        }
                    }
                });
            }
        };
        listTimerTask.add(task2);

        final TimerTask task3 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(pnjGenerator.getIsRunnig()) {
                        for(Tower tower : LIST_TOWER_ON_MAP) {
                            tower.pointPnj();
                            for(Bullet bullet : BULLET_ON_MAP) {
                                if(bullet != null) {
                                    bullet.move();
                                    tower.getBulletView().moveBullet(bullet);
                                }
                            }
                        }
                    }
                });
            }
        };
        listTimerTask.add(task3);

        return listTimerTask;
    }


    /**
     * cette methode arrete le timer et gere les reglages
     * a faire a la fin d'une wave
     */
    public void endTimer() {
        timer.cancel();
        timer.purge();
        cleanBullet();
        MouseGestures.setIsButtonWavePressed(false);
        if(LIVES <= 0) {
            System.out.println("-----Game over-----");
            gameOver();
        }
        if(WAVE >= finalWave) {
            win();
        }
    }


    /**
     * cette fonction permet d'enlever les bullet qui restent lorsqu'une vague est finie
     * genre les bullet qui ont atteint 0 ennemis avant que la vague se termine
     */
    public void cleanBullet() {
        for(Tower tower : LIST_TOWER_ON_MAP) {
            tower.pointPnj();
            for(Bullet bullet : BULLET_ON_MAP) {
                System.out.println("bullet : " + bullet);
                if(bullet != null) {
                    tower.getBulletView().removeBullet(bullet.getBulletId());
                    BULLET_ON_MAP.set(bullet.getBulletindex()-1, null);
                }
            }
        }
    }


    /**
     * cette fonction s'assure que toutes les variables statiques
     * qui doivent etre reinitialisees le soient bien avnt de qitter le jeu
     */
    public void quitGame() {
        // variables static de MouseGestures
        MouseGestures.setIsButtonWavePressed(false);
        MouseGestures.setIsButtonExitPressed(false);
        MouseGestures.setIsBuyAction(false);
        MouseGestures.setIsExplosionButton(false);
        // variables static de PnjNormal
        Pnj.setPnjInstanceNumber(0);
        Pnj.setPnjIncrementor(0);
        // variables tower et pnj de la map
        for(Pnj pnj : LIST_PNJ_ON_MAP) {
            pnj = null;
        }
        LIST_PNJ_ON_MAP = null;
        // variables static de Tower
        Tower.setIsUpgradeTransaction(false);
        for(Tower tower : LIST_TOWER_ON_MAP) {
            tower = null;
        }
        LIST_TOWER_ON_MAP = null;

        isGameDone = false;
    }




    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public static int getMONEY() { return MONEY; }
    public static int getWAVE() { return WAVE; }
    public static Map getMAP() { return MAP; }
    public static int getLIVES() { return LIVES; }
    public static List<Pnj> getListPnjOnMap() { return LIST_PNJ_ON_MAP; }
    public static List<Tower> getListTowerOnMap() { return LIST_TOWER_ON_MAP; }
    public static List<Bullet> getBulletOnMap() { return BULLET_ON_MAP; }
    public static int getBulletNumber() { return BULLET_NUMBER; }
    public static PNJView getPnjView() { return pnjView; }
    public Timer getTimer() { return timer; }
    public static boolean getIsGameDone() { return isGameDone;}
    public static boolean getIsJunitTest() { return isJunitTest; }

    public boolean is_LIST_PNJ_ON_MAP_empty() {
        boolean bool = false;
        if(LIST_PNJ_ON_MAP.size() == 0) {
            bool = true;
        }
        return bool;
    }




    // ******************************
    // ********** SETTERS ***********
    // ******************************
    public static void setListPnjOnMap(List<Pnj> listPnjOnMap) { LIST_PNJ_ON_MAP = listPnjOnMap; }
    public static void setListTowerOnMap(List<Tower> listTowerOnMap) { LIST_TOWER_ON_MAP = listTowerOnMap; }
    public static void addPnj(Pnj pnj) { LIST_PNJ_ON_MAP.add(pnj); }
    public void setGameView(GameView gameView) { this.gameView = gameView; }
    public static void SetIsJunitTest(boolean bool) {isJunitTest = bool; }

    public static void setMONEY(int MONEY) {
        Game.MONEY = MONEY;
        GameView.getSideWindow().updateLabels(WAVE, MONEY, LIVES);
    }

    public static void setWAVE(int WAVE) {
        Game.WAVE = WAVE;
        GameView.getSideWindow().updateLabels(WAVE, MONEY, LIVES);
    }

    public static void setMAP(Map MAP) { Game.MAP = MAP; }

    public static void setLIVES(int LIVES) {
        Game.LIVES = LIVES;
        GameView.getSideWindow().updateLabels(WAVE, MONEY, LIVES);
    }

    public static void addBulletOnMap(Bullet bullet) {
        BULLET_ON_MAP.add(bullet);
        BULLET_NUMBER ++;
    }

    public static void addTower(int posX, int posY, TowerType type, Cell cell) {
        Tower tower = new Tower(posX, posY, type);
        tower.setCell(cell);
        cell.setTower(tower);
        LIST_TOWER_ON_MAP.add(tower);
    }


}
