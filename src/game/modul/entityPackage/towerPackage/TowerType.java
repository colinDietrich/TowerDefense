package game.modul.entityPackage.towerPackage;

public enum TowerType {
    TOWER1_LEVEL1("TOWER 1", "tower1_level1","game/ressources/Tower/tower1_level1.png", "game/ressources/Tower/bulletType1.gif", 36, 36, 10, 2, 1, 1),
    TOWER1_LEVEL2("TOWER 1","tower1_level2","game/ressources/Tower/tower1_level2.png", "game/ressources/Tower/bulletType1.gif", 36, 36, 20, 3, 1, 2),
    TOWER1_LEVEL3("TOWER 1","tower1_level3","game/ressources/Tower/tower1_level3.png", "game/ressources/Tower/bulletType1.gif", 36, 36, 30, 4, 2, 3),

    TOWER2_LEVEL1("TOWER 2","tower2_level1","game/ressources/Tower/tower2_level1.png", "game/ressources/Tower/bulletType2.gif", 36, 35,30, 2, 2, 2),
    TOWER2_LEVEL2("TOWER 2","tower2_level2","game/ressources/Tower/tower2_level2.png", "game/ressources/Tower/bulletType2.gif", 36, 35,40, 3, 2, 3),
    TOWER2_LEVEL3("TOWER 2","tower2_level3","game/ressources/Tower/tower2_level3.png", "game/ressources/Tower/bulletType2.gif", 36, 35,50, 4, 3, 3),

    TOWER3_LEVEL1("TOWER 3","tower3_level1","game/ressources/Tower/tower3_level1.png", "game/ressources/Tower/bulletType3.gif", 36, 35,40, 3, 3, 2),
    TOWER3_LEVEL2("TOWER 3","tower3_level2","game/ressources/Tower/tower3_level2.png", "game/ressources/Tower/bulletType3.gif", 36, 35,50, 4, 3, 3),
    TOWER3_LEVEL3("TOWER 3","tower3_level3","game/ressources/Tower/tower3_level3.png", "game/ressources/Tower/bulletType3.gif", 36, 35,60, 5, 4, 4);

    // nom de la tower affiche sur la fenetre
    private String name;

    // String qui permet d'identifier la tower
    private String towerId;

    // adresse de du fichier contenant l'image de la tower
    private String url;

    // adresse de du fichier contenant l'image de la bullet tiree par la tower
    private String bulleturl;

    // largeur de l'image de la tower
    private int width;

    // hauteur de l'image de la tower
    private int height;

    // prix de la tower
    private int price;

    // rayon d'action d la tower
    private int radius;

    // point d'attaque de la tower
    private int attack;

    // frequence d'attaque de la tower
    private int frequency;

    // frequence maximale de toutes les towers
    private int maxFrequency = 5;




    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    private TowerType(String name, String towerId, String url, String bulletUrl, int width, int height, int price, int radius, int attack, int frequency) {
        this.name = name;
        this.url = url;
        this.bulleturl = bulletUrl;
        this.width = width;
        this.height = height;
        this.price = price;
        this.radius = radius;
        this.towerId = towerId;
        this.attack = attack;
        this.frequency = frequency;
    }




    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public String getName() { return name; }
    public String getUrl() { return url; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getPrice() { return price; }
    public String getTowerId() { return towerId; }
    public int getRadius() { return radius; }
    public int getAttack() { return attack; }
    public int getFrequency() { return frequency; }
    public int getMaxFrequency() { return maxFrequency; }
    public String getBulleturl() { return bulleturl; }


}
