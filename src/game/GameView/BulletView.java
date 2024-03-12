package game.GameView;

import game.modul.entityPackage.towerPackage.Bullet;
import game.modul.map.Tile;
import interfaces.GetUserData;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class BulletView implements GetUserData {



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode ajoute une bullet sur la fenetre
     */
    public void createBullet(Bullet bullet) {
        int x = bullet.getRealCurrentPosition()[0];
        int y = bullet.getRealCurrentPosition()[1];

        ImageView bulletImage = new ImageView(new Image(bullet.getTower().getTowerType().getBulleturl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
        bulletImage.setLayoutX((double)x - (double)Tile.getTileSize()/2);
        bulletImage.setLayoutY((double)y - (double)Tile.getTileSize()/2);
        bulletImage.setUserData(bullet.getBulletId());

        GameView.getGamePane().getChildren().add(bulletImage);

        bulletImage.toFront();
    }


    /**
     * cette methode fait bouger la bullet sur la map
     */
    public void moveBullet(Bullet bullet) {

        // on recupere la position reelle du pnj depuis sa classe
        int[] realCurrentPosition = bullet.getRealCurrentPosition();

        ImageView bulletImage = (ImageView) getByUserData(GameView.getGamePane(), bullet.getBulletId());
        if(bulletImage != null){
            bulletImage.setLayoutX(realCurrentPosition[0] - (double)Tile.getTileSize()/2);
            bulletImage.setLayoutY(realCurrentPosition[1] - (double)Tile.getTileSize()/2);
        }
    }


    /**
     * cette methode efface la bullet de la map en fonction de son id (userData)
     */
    public void removeBullet(String bulletId) {
        ImageView bulletImage = (ImageView) getByUserData(GameView.getGamePane(), bulletId);
        GameView.getGamePane().getChildren().remove(bulletImage);
    }

    /**
     * cette methode renvoit un node appartenant a une pane a partir de son id (userData)
     */
    @Override
    public Node getByUserData(AnchorPane pane, Object data) {
        Node node = null;
        for (Node n : pane.getChildren()) {
            if (data.equals(n.getUserData())) {
                node =n;
            }
        }
        return node;
    }



}
