package editMap.controller;

import editMap.View.EditCell;
import editMap.View.EditMapView;
import editMap.View.Grid;
import editMap.modul.BlockStyle;
import editMap.modul.SaveMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import menu.model.SPButton;
import menu.view.MenuViewManager;

public class EditMouseGestures {

    // true si on a selectionne une editCell
    private static boolean isRotateDeleteAction = false;

    // true si le joueur a appuye sur le bouton exit
    private static boolean isButtonExitPressed = false;

    // true si le joueur a appuye sur le bouton saveMap
    private static boolean isButtonSavePressed = false;

    // derniere EditCell qui a ete selectionnee
    private static EditCell rotatingDeletingCell;

    // nombre de rotation effectuees sur la EditCell
    private static int rotateNumber = 0;



    // ******************************
    // ***** METHODES DE CLASSE *****
    // ******************************

    /**
     * cette methode ajoute un listener a un bouton
     * pour pouvoir selectionner un style de bloc et l'ajouter sur la map
     */
    public void makeSelectBlockAction(SPButton button, BlockStyle blockStyle){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isRotateDeleteAction) {
                    EditCell.setBlockStylePreview(blockStyle);
                }
            }
        });
    }



    /**
     * cette methode permet d'ajouter 4 listenners a une EditCell
     * le premier est appele si la souris de l'utilisateur passe sur une EditCell
     * le second est appele si la souris de l'utilisateur clique sur une EditCell
     * le troisieme et le quatrieme sont appeles si la souris fait un drag sur les EditCell
     */
    public void makePreview(Node node) {

        node.hoverProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (newValue && !((EditCell) node).getIsAlreadyTaken() && !isRotateDeleteAction) {
                    ((EditCell) node).showBlockPreview();
                } else if (oldValue && !((EditCell) node).getIsAlreadyTaken() && !isRotateDeleteAction){
                    ((EditCell) node).removeBlockPreview();
                }
            }
        });


        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnDragDetected( onDragDetectedEventHandler);
        node.setOnMouseDragEntered(onMouseDragEnteredEventHandler);
    }


    /**
     *  cet eventHandler permet d'ajouter un bloc a la map
     *  et de selectionner ou deselectionner un bloc sur la map
     */
    EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

        EditCell cell = (EditCell) event.getSource();


        if (event.isPrimaryButtonDown() && !cell.getIsAlreadyTaken() && !isRotateDeleteAction) {
            // on enleve l'image de preview avant de mettre la definitive
            cell.removeBlockPreview();
            cell.addBlock();
        } else if (event.isPrimaryButtonDown() && cell.getIsAlreadyTaken() && !isRotateDeleteAction) {
            isRotateDeleteAction = true;
            cell.showSelectionnedGrid();
            rotatingDeletingCell = cell;
        } else if (event.isPrimaryButtonDown() && cell.getIsAlreadyTaken() && isRotateDeleteAction) {
            if(cell.equals(rotatingDeletingCell)) {
                isRotateDeleteAction = false;
                cell.showNormalGrid();
                rotateNumber = 0;
            }
        }

    };


    /**
     * cet eventHandler permet d'initialiser le debut du drag de la souris du joueur
     */
    EventHandler<MouseEvent> onDragDetectedEventHandler = event -> {

        EditCell cell = (EditCell) event.getSource();

        cell.startFullDrag();

    };


    /**
     * cet eventHandler ajoute des bloc lorsque le joueur fait un
     * mouvement de drag avec sa souris sur des cases vides
     */
    EventHandler<MouseEvent> onMouseDragEnteredEventHandler = event -> {

        EditCell cell = (EditCell) event.getSource();

        if(!cell.getIsAlreadyTaken()) {
            cell.addBlock();
        }

    };


    /**
     * cette methode ajoute un eventHandler a un bouton
     * permettant de faire tourner un bloc selectionne
     */
    public void rotateAction(SPButton button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(isRotateDeleteAction) {
                    rotateNumber++;
                    if(rotateNumber > 3) {
                        rotateNumber = 0;
                    }
                    rotatingDeletingCell.rotateBlock(90*rotateNumber);
                }
            }
        });
    }


    /**
     * cette methode ajoute un eventHandler a un bouton
     * permettant de quitter le mode edit map
     */
    public void exitAction(SPButton button, EditMapView editMapView) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isButtonExitPressed) {
                    isButtonExitPressed = true;
                    MenuViewManager menuViewManager = new MenuViewManager();
                    menuViewManager.createNewViewManager(editMapView.getEditStage());
                    quitSettings();
                }
            }
        });
    }


    /**
     * cette methode ajoute un eventHandler a un bouton
     * permettant de sauvegarder la map construite dans un fichier texte
     */
    public void saveMapAction(SPButton button, EditMapView editMapView) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isButtonSavePressed) {
                    SaveMap save = new SaveMap(Grid.getCells());
                    isButtonExitPressed = true;
                    MenuViewManager menuViewManager = new MenuViewManager();
                    menuViewManager.createNewViewManager(editMapView.getEditStage());
                    quitSettings();
                }
            }
        });
    }


    /**
     * cette methode ajoute un eventHandler a un bouton
     * permettant d'effacer' un bloc selectionne
     */
    public void deleteBlockAction(SPButton button) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!isButtonSavePressed) {
                    rotatingDeletingCell.removeBlockPreview();
                    isRotateDeleteAction = false;
                    rotatingDeletingCell.showNormalGrid();
                    rotateNumber = 0;
                }
            }
        });
    }


    /**
     * cette methode reinitialise toutes les variables statiques
     * avant de quitter le mode edit map
     */
    public void quitSettings() {
        isRotateDeleteAction = false;
        isButtonExitPressed = false;
        isButtonSavePressed = false;
        EditCell rotatingDeletingCell = null;
        rotateNumber = 0;
    }


}
