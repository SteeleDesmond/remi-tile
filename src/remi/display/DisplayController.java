package remi.display;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
        placeTile(newTile,"playerOneHand");
        Tile newTile2 = new Tile(13, "yellow", false);
        placeTile(newTile2,"playerTwoHand");
        Tile newTile3 = new Tile(12, "green", false);
        placeTile(newTile3,"gameTable");
        Tile newTile4 = new Tile(8, "red", false);
        placeTile(newTile4,"tilePool");
        Tile newTile5 = new Tile(5, "red", false);
        placeTile(newTile5, "playerOneDiscard");
        Tile newTile6 = new Tile(6, "red", false);
        placeTile(newTile6, "playerTwoDiscard");

    }


    @FXML
    public void placeTile(Tile tile, String location) {

        setTileProperties(tile);

        location = location.toLowerCase();
        switch(location) {
            case "tilepool":
                tilePool.getChildren().add(tile.getPane());
                break;
            case "playeronehand":
                playerOneHand.getChildren().add(tile.getPane());
                break;
            case "playertwohand":
                playerTwoHand.getChildren().add(tile.getPane());
                break;
            case "gametable":
                gameTable.getChildren().add(tile.getPane());
                break;
            case "playeronediscard":
                playerOneDiscard.getChildren().add(tile.getPane());
                break;
            case "playertwodiscard":
                playerTwoDiscard.getChildren().add(tile.getPane());
                break;

        }
//        newTile.getPane().setLayoutX(20);
//        newTile.getPane().setLayoutY(20);
    }

    /**
     * Uses the tilePool base tile as the template tile for all tiles
     * Sets a tile's rectangle display and size properties
     */
    private void setTileProperties(Tile tile) {

        Rectangle tileSize = (Rectangle) tile.getPane().getChildren().get(0);
        Rectangle reference = (Rectangle) tilePool.getChildren().get(0);
        tileSize.setWidth(reference.getWidth());
        tileSize.setHeight(reference.getHeight());
        tileSize.setFill(reference.getFill());
        tileSize.setStroke(reference.getStroke());
        tileSize.setStrokeType(reference.getStrokeType());
    }

    @FXML
    private void getHelpWindow() throws IOException {

        Pane helpPane = FXMLLoader.load(getClass().getClassLoader().getResource("remi/display/helpWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Help");
        stage.setScene(new Scene(helpPane, 600, 400));
        stage.show();
    }
}
