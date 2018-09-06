package remi.display;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import remi.tile.Tile;

import java.io.IOException;
import java.util.ArrayList;

public class DisplayController {

    @FXML private BorderPane rootPane;
    @FXML private StackPane tilePool;
    @FXML private StackPane playerOneDiscard;
    @FXML private StackPane playerTwoDiscard;
    @FXML private FlowPane playerOneHand;
    @FXML private FlowPane playerTwoHand;
    @FXML private Pane gameTable;

    @FXML
    private void initialize() {
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

    public void removeTile(Tile tile, String location) {

        location = location.toLowerCase();

        switch (location) {
            case "tilepool":
                if(tilePool.getChildren().contains(tile.getPane())) {
                    tilePool.getChildren().remove(tile.getPane());
                }
                break;
            case "playeronehand":
                if(playerOneHand.getChildren().contains(tile.getPane())) {
                    playerOneHand.getChildren().remove(tile.getPane());
                }
                break;
            case "playertwohand":
                if(playerTwoHand.getChildren().contains(tile.getPane())) {
                    playerTwoHand.getChildren().remove(tile.getPane());
                }
                break;
            case "gametable":
                if(gameTable.getChildren().contains(tile.getPane())) {
                    gameTable.getChildren().remove(tile.getPane());
                }
                break;
            case "playeronediscard":
                if(playerOneDiscard.getChildren().contains(tile.getPane())) {
                    playerOneDiscard.getChildren().remove(tile.getPane());
                }
                break;
            case "playertwodiscard":
                if(playerTwoDiscard.getChildren().contains(tile.getPane())) {
                    playerTwoDiscard.getChildren().remove(tile.getPane());
                }
                break;
        }
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

    /**
     * Opens the helpWindow file with the game rules. Called when the help button is clicked
     */
    @FXML
    private void getHelpWindow() throws IOException {

        Pane helpPane = FXMLLoader.load(getClass().getClassLoader().getResource("remi/display/helpWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Help");
        stage.setScene(new Scene(helpPane, 600, 400));
        stage.show();
    }
}
