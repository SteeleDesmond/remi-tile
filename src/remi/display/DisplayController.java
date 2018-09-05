package remi.display;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class DisplayController {

    @FXML BorderPane rootPane;
    @FXML StackPane tilePool;
    @FXML StackPane playerOneDiscard;
    @FXML StackPane playerTwoDiscard;
    @FXML FlowPane playerOneHand;
    @FXML FlowPane playerTwoHand;
    @FXML Pane gameTable;


    @FXML
    private void initialize() {
        Tile newTile = new Tile(1, "blue", false);
        placeTile(newTile,"test");
        Tile newTile2 = new Tile(13, "yellow", false);
        placeTile(newTile2,"test2");
        Tile newTile3 = new Tile(12, "green", false);
        placeTile(newTile3,"test2");
        Tile newTile4 = new Tile(8, "red", false);
        placeTile(newTile4,"test2");
    }


    @FXML
    public void placeTile(Tile tile, String location) {

        setTileProperties(tile);

        playerOneHand.getChildren().add(tile.getPane());
//        newTile.getPane().setLayoutX(20);
//        newTile.getPane().setLayoutY(20);
    }

    /**
     * Uses the tilePool base tile as the template tile for all tiles
     * Sets a tile's rectangle display and size properties
     */
    public void setTileProperties(Tile tile) {

        Rectangle tileSize = (Rectangle) tile.getPane().getChildren().get(0);
        Rectangle reference = (Rectangle) tilePool.getChildren().get(0);
        tileSize.setWidth(reference.getWidth());
        tileSize.setHeight(reference.getHeight());
        tileSize.setFill(reference.getFill());
        tileSize.setStroke(reference.getStroke());
        tileSize.setStrokeType(reference.getStrokeType());
    }
}
