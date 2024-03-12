package game.modul.entityPackage.pnjPackage;

public enum PNJType {
    PNJ1("game/ressources/PNJ/fly.gif", 18, 17, 4, 2, 4),
    PNJ2("game/ressources/PNJ/monster.gif", 24, 24, 7, 3, 7),
    PNJ3("game/ressources/PNJ/taurus.gif", 32, 28, 15, 1, 9);

    // nom du fichier contenant l'image du pnj
    private String url;

    private static String explosionUrl = "game/ressources/PNJ/explosionPnj4.gif";

    // hauteur et largeur de l'image du pnj
    private int width;
    private int height;

    // points de vie intials du pnj
    private int initialHealth;

    // vitesse de deplacement du pnj
    private int initialSpeed;

    // recompense (argent) gagne lorsque le pnj est tue
    private int initialReward;


    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    private PNJType(String url, int width, int height, int initialHealth, int initialSpeed, int initialReward) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.initialHealth = initialHealth;
        this.initialSpeed = initialSpeed;
        this.initialReward = initialReward;
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public String getUrl() { return url; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getInitialHealth() { return initialHealth; }
    public int getInitialSpeed() { return initialSpeed; }
    public int getInitialReward() { return initialReward; }
    public static String getExplosionUrl() { return explosionUrl; }

}
