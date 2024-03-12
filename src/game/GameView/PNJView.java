package game.GameView;

import game.modul.entityPackage.pnjPackage.PNJType;
import game.modul.entityPackage.pnjPackage.Pnj;
import game.modul.map.Tile;
import interfaces.GetUserData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class PNJView implements GetUserData {

    // base qui sert a creer un id different pour chaque barre de vie
    private String healthBarID = "health";

    // la largeur de la barre de vie
    private int healthBarStroke = 10;



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode ajoute le PNJ en argument sur la fenetre
     */
    public void addPNJ(int[] position, Pnj pnj) {

        // on cree l'image correspondant au pnj
        Image image = new Image(pnj.getPNJType().getUrl(), pnj.getPNJType().getWidth(), pnj.getPNJType().getHeight(), false, true);
        ImageView PNJImage = new ImageView(image);
        // on cree la barre de vie
        Rectangle healthRectangle = createHealthRectangle(pnj);

        PNJImage.setUserData(pnj.getPnjId());
        healthRectangle.setUserData(pnj.getPnjId()+healthBarID);

        // on traduit les coordonnees (x,y) de la map en position réelles (x',y') dans la fenetre
        int realXPostion = getRealPosition(position)[0];
        int realYPosition = getRealPosition(position)[1];

        // on garde en memoire dans la classe PNJ la position
        // que devront prendre les pnj des qu'ils apparaissent dans la fenetre
        pnj.setRealCurrentPosition(getRealPosition(position));

        // on ajoute le pnj au gamePane en (-50,-50) pour qu'il n'apparaisse pas encore sur la fenetre
        GameView.getGamePane().getChildren().addAll(PNJImage, healthRectangle);

        PNJImage.setLayoutX(-50);
        PNJImage.setLayoutY(-50);
        PNJImage.toFront();

        healthRectangle.toFront();
        healthRectangle.setLayoutX(-50);
        healthRectangle.setLayoutY(-50-healthBarStroke);

    }


    /**
     * cette methode efface le PNJ en argument de la fenetre
     */
    public void removePNJ(Pnj pnj) {
        ImageView PNJView = (ImageView) getByUserData(GameView.getGamePane(), pnj.getPnjId());
        double oldX = pnj.getRealCurrentPosition()[0] - (double)Tile.getTileSize()/2;
        double oldY =  pnj.getRealCurrentPosition()[1] - (double)Tile.getTileSize()/2;
        GameView.getGamePane().getChildren().remove(PNJView);

        Rectangle healthRectangle = (Rectangle) getByUserData(GameView.getGamePane(), pnj.getPnjId()+healthBarID);
        GameView.getGamePane().getChildren().remove(healthRectangle);

        ImageView explosionImage = new ImageView(new Image(PNJType.getExplosionUrl(), Tile.getTileSize(), Tile.getTileSize(), false, true));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    GameView.getGamePane().getChildren().add(explosionImage);
                    explosionImage.toFront();
                    explosionImage.setLayoutX(oldX);
                    explosionImage.setLayoutY(oldY);
                }),
                new KeyFrame(Duration.seconds(0.5), e -> { GameView.getGamePane().getChildren().remove(explosionImage); })
        );
        timeline.play();

    }


    /**
     * cette methode traduit les coordonnees (x,y) de la map en position réelles (x',y') dans la fenetre
     */
    public int[] getRealPosition(int[] position) {
        int[] realPosition = new int[2];
        realPosition[0] = (position[0] + 1 / 2) * Tile.getTileSize() + MapView.getSTROKE();
        realPosition[1] = (position[1] + 1 / 2) * Tile.getTileSize() + MapView.getSTROKE();
        return realPosition;
    }


    /**
     * cette methode fait bouger le pnj et sa barre de vie sur la map
     */
    public void move(Pnj pnj) {

        // on recupere la position reelle du pnj depuis sa classe
        int[] realCurrentPosition = pnj.getRealCurrentPosition();

        double[] realLayoutPosition = new double[]{pnj.getRealCurrentPosition()[0] - 0.5 * pnj.getPNJType().getWidth(), pnj.getRealCurrentPosition()[1] - 0.5 * pnj.getPNJType().getHeight()};
        int [] layoutPosition = new int[]{(int)realLayoutPosition[0], (int)realLayoutPosition[1]};

        ImageView PNJImage = (ImageView) getByUserData(GameView.getGamePane(), pnj.getPnjId());
        Rectangle healthRectangle = (Rectangle) getByUserData(GameView.getGamePane(), pnj.getPnjId()+healthBarID);
        if(PNJImage != null &&  healthRectangle!= null) {
            PNJImage.setLayoutX(layoutPosition[0]);
            PNJImage.setLayoutY(layoutPosition[1]);

            Rectangle healthRectangleUpdate = setHealthRectangle(healthRectangle,pnj);
            healthRectangleUpdate.setLayoutX(layoutPosition[0]);
            healthRectangleUpdate.setLayoutY(layoutPosition[1]-healthBarStroke);

        }

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


    /**
     * cette methode cree une bar de vie pour le pnj en argument
     */
    public Rectangle createHealthRectangle(Pnj pnj){
        Rectangle healthRectangle = new Rectangle();
        double initialHealth = pnj.getPNJType().getInitialHealth();
        healthRectangle.setWidth(initialHealth * 6);;
        healthRectangle.setHeight(6);
        healthRectangle.setFill(Color.GREEN);
        healthRectangle.setStroke(Color.BLACK);
        return healthRectangle;
    }


    /**
     * cette methode met a jour la bar de vie du pnj en argument
     * si il perd de la vie
     */
    public Rectangle setHealthRectangle(Rectangle healthRectangle, Pnj pnj){
        double currentHealth = pnj.getHealth();
        healthRectangle.setWidth(currentHealth * 6);
        return  healthRectangle;
    }


}
