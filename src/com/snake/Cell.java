package com.snake;

import javax.swing.*;
import java.awt.*;

/** Represents a space on the game board. */
public class Cell extends JLabel {

    /** Whether or not this Cell currently contains any objects (a Link of the Snake,
     * a piece of food, etc.) */
    private Boolean isOccupied;

    /** The location of this Cell on the game board. */
    private Point coordinates;

    /** Create a new Cell, stating whether or not it is
     * occupied by any objects. */
    public Cell (Point coordinates) {
        this.coordinates = coordinates;
        setIsOccupied(false);
    }

    /** Get whether or not this Cell is occupied. */
    public Boolean isOccupied() {
        return isOccupied;
    }

    /** Set whether or not this Cell is occupied. */
    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
        if (isOccupied)
            this.setIcon(new ImageIcon("C:/Users/Daniel/Desktop/Programming/Java/Snake/res/bin/Link.png"));
        else
            this.setIcon(new ImageIcon("C:/Users/Daniel/Desktop/Programming/Java/Snake/res/bin/Cell.png"));
    }

    /** Get the location of this Cell. */
    public Point getCoordinates() {
        return coordinates;
    }
}
