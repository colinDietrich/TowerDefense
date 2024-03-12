package game.GameView;

import game.modul.map.Tile;
import game.modul.entityPackage.towerPackage.Tower;
import game.modul.entityPackage.towerPackage.TowerType;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;



public class Cell extends StackPane {

    // indice x de la Cell dans la matrice de Cells de MapView
    private int column;
    // indice y de la Cell dans la matrice de Cells de MapView
    private int row;

    // image de la tower a ajouter sur la Cell
    private ImageView towerPreview;

    // Tower present sur la Cell
    private Tower tower;

    // true si la Cell est un bloc normal
    private boolean isBlock;

    // true si la Cell possede deja une tower
    private boolean isAlreadyTaken = false;

    // type de la tower a afficher en preview
    // pas besoin d'etre reinitialisee
    static TowerType towerType = TowerType.TOWER1_LEVEL1;


    // ******************************
    // ******** CONSTRUCTEUR ********
    // ******************************
    public Cell(int column, int row, boolean isBlock) {
        this.column = column;
        this.row = row;
        this.isBlock = isBlock;
    }




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     *cette methode ajoute une image a la cell
     */
    public void addImage(ImageView image) {
        this.getChildren().add(image);
    }


    /**
     * cette methode remet a jour l'image de la tower si elle a evoluee
     */
    public void updateTowerLevel() {
        removeTowerPreview();
        towerPreview = new ImageView(new Image(tower.getTowerType().getUrl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
        this.getChildren().add(towerPreview);
        towerPreview.toFront();
    }


    /**
     * cette methode remet a jour l'image du rayon de la tower si elle a evoluee
     */
    public void updateRadius() {
        removeRadiusAndUpgradeButton();
        showRadius();
        showUpagradeButton();
        toFront();
    }


    /**
     *cette methode ajoute une tower sur la map
     */
    public void addTower() {
        isAlreadyTaken = true;
        // on cree une image qui affiche tower preview
        setTower();
    }


    /**
     * cette methode affiche une image "preview" de la tower
     */
    public void showTowerPreview() {
            // on cree une image qui affiche tower preview
            setTower();
    }


    /**
     * cette methode ajoute une image d'une tower a la Cell
     */
    public void setTower() {
        if (towerType.equals(TowerType.TOWER1_LEVEL1)) {
            towerPreview = new ImageView(new Image(TowerType.TOWER1_LEVEL1.getUrl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
        }
        else if(towerType.equals(TowerType.TOWER2_LEVEL1)){
            towerPreview = new ImageView(new Image(TowerType.TOWER2_LEVEL1.getUrl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
        }
        else if(towerType.equals(TowerType.TOWER3_LEVEL1)){
            towerPreview = new ImageView(new Image(TowerType.TOWER3_LEVEL1.getUrl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
        }
        this.getChildren().add(towerPreview);
        towerPreview.toFront();
    }


    /**
     * cette methode efface une image "preview" de la tower
     */
    public void removeTowerPreview() {
        this.getChildren().remove(towerPreview);
    }


    /**
     * cette methode fait tourner l'image de la tower selon un certain angle
     */
    public void rotateTower(double angle) {
        //towerPreview.setRotate(angle);
        RotateTransition rt = new RotateTransition(Duration.millis(1), towerPreview);
        rt.setToAngle(angle);
        rt.play();
    }


    /**
     * cette methode affiche l'image du rayon de la tower
     */
    public void showRadius() {
        MapView mapView = (MapView) getParent();
        mapView.setCircleRadius(getX(), getY(), tower.getTowerType().getRadius());
        toFront();
    }


    /**
     * cette methode affiche l'image du boutton permettant de faire evoluer la tower
     */
    public void showUpagradeButton() {
        MapView mapView = (MapView) getParent();
        mapView.createUpgradeButton(getX(), getY(), tower);
        toFront();
    }


    /**
     * cette methode efface l'image du boutton permettant de faire evoluer la tower
     * et efface l'image du rayon de la tower
     */
    public void removeRadiusAndUpgradeButton() {
        MapView mapView = (MapView) getParent();
        mapView.removeCircleRadius();
        mapView.removeUpgradeButton();
    }



    // ******************************
    // ********** GETTERS ***********
    // ******************************
    public boolean getIsAlreadyTaken() { return isAlreadyTaken; }
    public boolean getIsBlock() { return isBlock; }
    public int getX() { return column; }
    public int getY() {return row;}
    public static TowerType getTowerType() { return towerType; }
    public Tower getTower() { return tower; }



    // ******************************
    // ********** SETTERS ***********
    // ******************************
    public static void setTowerType(TowerType type) { towerType = type; }
    public void setTower(Tower tower) { this.tower = tower; }

}
