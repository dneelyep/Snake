package com.snake;

import java.awt.*;

/** Class to represent a link in the Snake's body. */
public class Link extends Cell {

    /** Create a new Link at the provided coordinates. */
    public Link(Point coordinates) {
        super(coordinates);
        setOccupied("Link");
    }
}
