package com.snake;

import javax.swing.*;
import java.awt.*;

/** Represents a space on the game board. */
public class Cell extends JLabel {

    /** Whether or not this Cell currently contains any objects (a Link of the Snake,
     * a piece of food, etc.) */
    private Boolean isOccupied;

    private Boolean isFood = false;

    /** The location of this Cell on the game board. */
    private Point coordinates;

    /** Create a new Cell, stating whether or not it is
     * occupied by any objects. */
    public Cell (Point coordinates) {
        this.coordinates = coordinates;
        setOccupied("blank");
    }

    /** Set whether or not this Cell is occupied. */
    public void setOccupied(String value) {
        // TODO Simplify
        if (value.equals("Link")) {
            this.setIcon(new ImageIcon("C:/Users/Daniel/Desktop/Programming/Java/Snake/res/bin/Link.png"));
            isOccupied = true;
        }
        else if (value.equals("Food")) {
            // LEFTOFFHERE Just added food into the game. Now I need to make the player grow a link when they collide with
            // food, and clean up the food-related code.
            this.setIcon(new ImageIcon("C:/Users/Daniel/Desktop/Programming/Java/Snake/res/bin/Food.png"));
            isOccupied = true;
            isFood = true;
        }
        else {
            this.setIcon(new ImageIcon("C:/Users/Daniel/Desktop/Programming/Java/Snake/res/bin/Cell.png"));
            isOccupied = false;
        }
    }

    /** Return if this Cell is currently occupied. */
    public Boolean isOccupied() {
        return isOccupied;
    }

    public Boolean isFood() {
        return isFood;
    }

    /** Get the location of this Cell. */
    public Point getCoordinates() {
        return coordinates;
    }
}
