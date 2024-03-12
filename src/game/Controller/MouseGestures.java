package game.Controller;

import game.GameView.Cell;
import game.GameView.GameView;
import game.GameView.MapView;
import game.modul.Game;
import game.modul.entityPackage.pnjPackage.PNJType;
import game.modul.entityPackage.pnjPackage.Pnj;
import game.modul.entityPackage.towerPackage.Tower;
import game.modul.entityPackage.towerPackage.TowerType;
import game.modul.map.Map;
import game.modul.map.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import menu.model.SPButton;
import menu.view.MenuViewManager;





public class MouseGestures {

    // true si une tower est entrain d'etre positionnee sur la map
    private static boolean isBuyAction = false;

    // true si une wave est en cours
    private static boolean isButtonWavePressed = false;

    // true si le joueur a appuye sur le bouton exit
    private static boolean isButtonExitPressed = false;

    // true si le joueur a appuye sur le bouton explosion
    private static boolean isExplosionButton = false;




    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode ajoute un listener a un Button
     * qui permet d'acheter une tower
     */
    public void makeBuyAction(SPButton button){

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isBuyAction && !Game.getIsGameDone()) {
                    if(button.getButtonId().equals(TowerType.TOWER1_LEVEL1.getTowerId()) && Game.getMONEY() >= TowerType.TOWER1_LEVEL1.getPrice()){
                        Cell.setTowerType(TowerType.TOWER1_LEVEL1);
                        Game.setMONEY(Game.getMONEY() - TowerType.TOWER1_LEVEL1.getPrice());
                        isBuyAction = true;
                    }
                    else if(button.getButtonId().equals(TowerType.TOWER2_LEVEL1.getTowerId()) && Game.getMONEY() >= TowerType.TOWER2_LEVEL1.getPrice()){
                        Cell.setTowerType(TowerType.TOWER2_LEVEL1);
                        Game.setMONEY(Game.getMONEY() - TowerType.TOWER2_LEVEL1.getPrice());
                        isBuyAction = true;
                    }
                    else if(button.getButtonId().equals(TowerType.TOWER3_LEVEL1.getTowerId()) && Game.getMONEY() >= TowerType.TOWER3_LEVEL1.getPrice()){
                        Cell.setTowerType(TowerType.TOWER3_LEVEL1);
                        Game.setMONEY(Game.getMONEY() - TowerType.TOWER3_LEVEL1.getPrice());
                        isBuyAction = true;
                    }
                }
            }
        });

    }



    /**
     * cette methode permet d'ajouter deux listenner a une Cell
     * le premier est appele si la souris de l'utilisateur passe sur une Cell
     * le second est appele si la souris de l'utilisateur clique sur une Cell
     */
    public void makePreview(Node node) {


        // ce listener est appele si la souris de l'utilisateur passe sur une Cell
        node.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(!Game.getIsGameDone()) {

                    // affiche ou enleve l'image preview d'une tower avant de placer une tower sur la map
                    if (newValue && !((Cell) node).getIsAlreadyTaken() && ((Cell) node).getIsBlock() && isBuyAction) {
                        ((Cell) node).showTowerPreview();
                    } else if (oldValue && !((Cell) node).getIsAlreadyTaken() && ((Cell) node).getIsBlock() && isBuyAction){
                        ((Cell) node).removeTowerPreview();
                    }

                    // affiche le rayon de la tour si la souris de l'utilisateur passe dessus
                    if(newValue && !isBuyAction && ((Cell) node).getIsAlreadyTaken() && !Tower.getIsIsUpgradeTransaction()) {
                        ((Cell) node).showRadius();((Cell) node).showUpagradeButton();
                    } else if (!isBuyAction && ((Cell) node).getIsAlreadyTaken() && !Tower.getIsIsUpgradeTransaction()) {
                        ((Cell) node).removeRadiusAndUpgradeButton();
                    }
                }
            }
            });

        // ce listener est appele si la souris de l'utilisateur clique sur une Cell
        node.setOnMousePressed(onMousePressedEventHandler);

    }



    /**
     *  cet eventHandler permet d'ajouter une tower a la map
     *  et d'afficher le bouton permettant de faire evoluer une tour de facon permanente
     */
    EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

        Cell cell = (Cell) event.getSource();

        if(!Game.getIsGameDone()) {

            if (event.isPrimaryButtonDown() && !cell.getIsAlreadyTaken() && cell.getIsBlock() && isBuyAction) {
                // on enleve l'image de preview avant de mettre la definitive
                cell.removeTowerPreview();
                Game.addTower(cell.getX(), cell.getY(), Cell.getTowerType(), cell);
                cell.addTower();
                isBuyAction = false;
            } else if (event.isPrimaryButtonDown() && cell.getIsAlreadyTaken()) {
                if(Tower.getIsIsUpgradeTransaction()) {
                    Tower.setIsUpgradeTransaction(false);
                } else {
                    Tower.setIsUpgradeTransaction(true);
                    MapView mapView = (MapView) cell.getParent();
                    mapView.setSelectionnedCell(cell);
                }
            }

        }
    };


    /**
     * cette methode ajoute un listenner a un bouton
     * qui permet de faire evoluer une tour sur la map
     */
    public void makeUpgradeAction(SPButton button) {

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MapView mapView = (MapView) button.getParent();
                Cell cell = mapView.getSelectionnedCell();
                TowerType nextType = null;
                if(cell.getTower().getLevel() == 1) {
                    System.out.println("level1");
                    switch (cell.getTower().getTowerType()) {
                        case TOWER1_LEVEL1:
                            nextType = TowerType.TOWER1_LEVEL2;
                            System.out.println(nextType.getTowerId());
                            cell.getTower().setLevel(2);
                            break;
                        case TOWER2_LEVEL1:
                            nextType = TowerType.TOWER2_LEVEL2;
                            cell.getTower().setLevel(2);
                            break;
                        case TOWER3_LEVEL1:
                            nextType = TowerType.TOWER3_LEVEL2;
                            cell.getTower().setLevel(2);
                            break;
                    }
                } else if(cell.getTower().getLevel() == 2) {
                    switch (cell.getTower().getTowerType()) {
                        case TOWER1_LEVEL2:
                            nextType = TowerType.TOWER1_LEVEL3;
                            cell.getTower().setLevel(3);
                            break;
                        case TOWER2_LEVEL2:
                            nextType = TowerType.TOWER2_LEVEL3;
                            cell.getTower().setLevel(3);
                            break;
                        case TOWER3_LEVEL2:
                            nextType = TowerType.TOWER3_LEVEL3;
                            cell.getTower().setLevel(3);
                            break;
                    }
                }
                if(nextType != null) {
                    if(Game.getMONEY() >= nextType.getPrice()) {
                        cell.getTower().setTowerType(nextType);
                        cell.updateTowerLevel();
                        cell.updateRadius();
                        Game.setMONEY(Game.getMONEY() - nextType.getPrice());
                    }
                }

            }
        });


    }


    /**
     * cette methode ajoute un listenner a un bouton
     * qui permet de lancer une nouvelle vague dans Game
     */
    public void newWaveAction(SPButton button, Game game) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isButtonWavePressed && !Game.getIsGameDone()) {
                    isButtonWavePressed = true;
                    game.createNewWave();
                }
            }
        });
    }


    /**
     * cette methode ajoute un listenner a un bouton
     * qui permet de quitter le jeu
     */
    public void exitAction(SPButton button, Game game, GameView gameView) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isButtonExitPressed) {
                    isButtonExitPressed = true;
                    try {
                        game.getTimer().cancel();
                        game.getTimer().purge();
                    } catch (Exception e) {
                        System.out.println("pas de timer");
                    }
                    game.quitGame();
                    MenuViewManager menuViewManager = new MenuViewManager();
                    menuViewManager.createNewViewManager(gameView.getGameStage());
                }
            }
        });
    }

    public void explosionAction(SPButton button, Game game) {

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isExplosionButton && isButtonWavePressed && !Game.getIsGameDone() && Game.getMONEY() >= 50) {
                    Game.setMONEY(Game.getMONEY() - 50);
                    isExplosionButton = true;
                    try {
                        game.getTimer().cancel();
                        game.getTimer().purge();
                    } catch (Exception e) {
                        System.out.println("pas de timer");
                    }

                    for(Pnj pnj : Game.getListPnjOnMap()) {
                        if(pnj != null) {
                            pnj.removeEntity();
                        }
                    }
                    game.cleanBullet();
                    setIsButtonWavePressed(false);

                    ImageView explosionImage = new ImageView(new Image("game/ressources/explosionFinal.gif", Map.getWIDTH(), Map.getHEIGHT(), false, true));
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.ZERO, e -> {
                                GameView.getGamePane().getChildren().add(explosionImage);
                                explosionImage.setLayoutX(Map.getSTROKE());
                                explosionImage.setLayoutY(Map.getSTROKE());
                                explosionImage.toFront();
                            }),
                            new KeyFrame(Duration.seconds(2), e -> { GameView.getGamePane().getChildren().remove(explosionImage); })
                    );
                    timeline.play();
                }
            }
        });

    }


    // ******************************
    // ********** SETTERS ***********
    // ******************************
    public static void setIsButtonWavePressed(boolean isButtonWavePressed) { MouseGestures.isButtonWavePressed = isButtonWavePressed; }
    public static void setIsBuyAction(boolean isBuyAction) { MouseGestures.isBuyAction = isBuyAction; }
    public static void setIsButtonExitPressed(boolean isButtonExitPressed) { MouseGestures.isButtonExitPressed = isButtonExitPressed; }
    public static void setIsExplosionButton(boolean isExplosionButton) { MouseGestures.isExplosionButton = isExplosionButton; }
}
