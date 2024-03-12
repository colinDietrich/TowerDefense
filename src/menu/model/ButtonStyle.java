package menu.model;

public enum ButtonStyle {
    ButtonFree("menu/model/ressources/newButton.png", 190, 49, 23),
    ButtonPressed("menu/model/ressources/newButtonPressed.png", 190, 49, 23),
    LeftArrow("menu/model/ressources/red_sliderLeft.png", 39,31, 23),
    RightArrow("menu/model/ressources/red_sliderRight.png", 39, 31, 23);

    // nom du fichier contenant l'image du style de button
    private String url;

    // hauteur et largeur de l'image du style de button
    private int width;
    private int height;

    // taille de la police
    private int fontValue;



    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    private ButtonStyle(String url, int width, int height, int fontValue) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.fontValue = fontValue;
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public String getUrl() {
        return url;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getFontValue() { return fontValue; }
}