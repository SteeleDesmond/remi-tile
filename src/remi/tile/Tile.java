package remi.tile;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A Tile object representing the tile state. Used by the display.
 */
public class Tile {

    private static int nextId;
    private int id;
    private int number;
    private String color;
    private boolean isJoker;
    private boolean isHidden;
    private boolean isSelected;

    private StackPane pane = new StackPane();
    private Rectangle rectangle = new Rectangle();
    private Text text;

    public Tile(int number, String color, boolean isJoker) {
        this.number = number;
        this.color = color.toLowerCase();
        this.isJoker = isJoker;
        this.id = nextId;
        text = new Text(Integer.toString(number));
        pane.getChildren().addAll(rectangle,text);
        showTile();
        nextId++;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isJoker() {
        return isJoker;
    }

    public void setJoker(boolean joker) {
        isJoker = joker;
    }

    public StackPane getPane() {
        return pane;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Called to make the tile appear face down in JavaFX.
     */
    public void hideTile() {
        rectangle.setFill(Color.CORNFLOWERBLUE);
        text.setVisible(false);
        isHidden = true;
    }

    /**
     * Sets the properties that a tile needs to have to be a tile for JavaFX. Can be ignored for other displays.
     * @return the StackPane for the constructor
     */
    public void showTile() {

        text.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
        switch(color) {
            case "blue":
                text.setFill(Color.DODGERBLUE);
                break;
            case "red":
                text.setFill(Color.RED);
                break;
            case "green":
                text.setFill(Color.DARKGREEN);
                break;
            case "yellow":
                text.setFill(Color.ORANGE);
                break;
        }
        isHidden = false;
    }
}

