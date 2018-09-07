package remi.display;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import remi.tile.Tile;

import java.io.IOException;

public class DisplayController {

    @FXML private BorderPane rootPane;
    @FXML private StackPane tilePool;
    @FXML private StackPane playerOneDiscard;
    @FXML private StackPane playerTwoDiscard;
    @FXML private FlowPane playerOneHand;
    @FXML private FlowPane playerTwoHand;
    @FXML private FlowPane gameTable;
    @FXML private Text playerOneScore;
    @FXML private Text playerTwoScore;

    private boolean tileClicked = false;
    private boolean oneDiscardClicked = false;
    private boolean twoDiscardClicked = false;
    private boolean tilePoolClicked = false;
    private int clickCounter = 0;

    @FXML
    private void initialize() {

        playerOneDiscard.setOnMouseClicked(event -> {
            oneDiscardClicked = true;
            mouseClickedHandler();
        });
        playerTwoDiscard.setOnMouseClicked(event -> {
            twoDiscardClicked = true;
            mouseClickedHandler();
        });
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
     *
     * @param tile The tile you wish to remove
     * @param location The location of the given tile
     */
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
     *
     * @param player Which player's score to update
     * @param amount The amount to change the score to
     */
    public void updateScore(String player, int amount) {
        if (player.toLowerCase().equals("playerone")) {
            playerOneScore.setText(String.valueOf(amount));
        }
        /* Else it is player two for this application*/
        else {
            playerTwoScore.setText(String.valueOf(amount));
        }
    }

    /**
     *
     * @param player the player you want the score of
     * @return the score of the given player
     */
    public int getScore(String player) {
        if (player.toLowerCase().equals("playerone")) {
            return Integer.getInteger(playerOneScore.getText());
        }
        /* Else it is player two for this application*/
        else {
            return Integer.getInteger(playerTwoScore.getText());
        }
    }

    public void hideTile(Tile tile) {
        tile.hideTile();
    }

    /**
     * Uses the tilePool base tile as the template tile for all tiles
     * Sets a tile's rectangle display and size properties. The tile number and color are set in the Tile constructor
     */
    private void setTileProperties(Tile tile) {

        Rectangle tileSize = (Rectangle) tile.getPane().getChildren().get(0);
        Rectangle reference = (Rectangle) tilePool.getChildren().get(0);
        tileSize.setWidth(reference.getWidth());
        tileSize.setHeight(reference.getHeight());
        tileSize.setFill(reference.getFill());
        tileSize.setStroke(reference.getStroke());
        tileSize.setStrokeType(reference.getStrokeType());

        tileSize.setOnMouseClicked(event -> {
            clickCounter++;
            System.out.println(clickCounter);
            tileSize.setStroke(Color.LIGHTBLUE);
            tileClicked = true;
            if(playerOneDiscard.getChildren().contains(tile.getPane())) {
                oneDiscardClicked = true;
            }
            else if (playerTwoDiscard.getChildren().contains(tile.getPane())) {
                twoDiscardClicked = true;
            }
            else if (tilePool.getChildren().contains(tile.getPane())) {
                tilePoolClicked = true;
            }
            if(clickCounter >= 2) {
                clickCounter = 0;
                tileSize.setFill(reference.getFill());
                oneDiscardClicked = false;
                twoDiscardClicked = false;
                tilePoolClicked = false;
                clickCounter = 0;
                mouseClickedHandler();
            }
        });
    }

    public void mouseClickedHandler() {

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
