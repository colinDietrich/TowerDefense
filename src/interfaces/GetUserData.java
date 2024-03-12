package interfaces;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public interface GetUserData {

    /**
     * cette methode renvoit un node appartenant a une pane a partir de son id (userData)
     */
    public Node getByUserData(AnchorPane pane, Object data);

}
