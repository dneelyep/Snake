package com.snake;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

/** A class to represent the snake that the player moves around the screen. */
public class Snake {

    /** A linked list of links that make up this Snake's body. */
    private LinkedList<Link> body = new LinkedList<>();

    /** The Direction this Snake is currently moving. */
    private Directions currentDirection = Directions.Left;

    /** Create a new Snake with a few default links in its body. */
    public Snake() {
        body.addAll(Arrays.asList(
                new Link(new Point(6, 4)),
                new Link(new Point(6, 5)),
                new Link(new Point(6, 6)),
                new Link(new Point(6, 7))));
    }

    /** Get a linked list of all the links that make up this Snake's body. */
    public LinkedList<Link> getBody() {
        return body;
    }

    /** Move this Snake one cell in the provided direction. */
    public void move(Directions direction, Vector<Vector<Cell>> boardCells) {
        // The algorithm for moving:

        // Save the current position of the head of the Snake
        // Move the head of the snake one unit in the desired direction.
        // Move the next link in the body to where the head used to be.
        // Repeat until all body parts are moved.

        Point previousLinkPoint = new Point(body.get(0).getCoordinates().x, body.get(0).getCoordinates().y);

        // Adjust movements when we will overshoot the edge of the board.
        if (body.get(0).getCoordinates().x == 9 && currentDirection == Directions.Right) {
            body.get(0).getCoordinates().x = 0;
        }

        else if (body.get(0).getCoordinates().x == 0 && currentDirection == Directions.Left) {
            body.get(0).getCoordinates().x = 9;
        }

        else if (body.get(0).getCoordinates().y == 9 && currentDirection == Directions.Down) {
            body.get(0).getCoordinates().y = 0;
        }

        else if (body.get(0).getCoordinates().y == 0 && currentDirection == Directions.Up) {
            body.get(0).getCoordinates().y = 9;
        }

        // If none of the above occurred, we should be safe to make a normal move.
        else {
            switch (direction) {
                case Up:
                    body.get(0).getCoordinates().y -= 1; break;
                case Down:
                    body.get(0).getCoordinates().y += 1; break;
                case Left:
                    body.get(0).getCoordinates().x -= 1; break;
                case Right:
                    body.get(0).getCoordinates().x += 1; break;
            }
        }

        for (Link link : body.subList(1, body.size())) {
            // TODO Clean up variable names, clarify this section.
            Point tmpPreviousLinkPoint = new Point();
            tmpPreviousLinkPoint.x = link.getCoordinates().x;
            tmpPreviousLinkPoint.y = link.getCoordinates().y;

            link.getCoordinates().x = previousLinkPoint.x;
            link.getCoordinates().y = previousLinkPoint.y;

            previousLinkPoint.x = tmpPreviousLinkPoint.x;
            previousLinkPoint.y = tmpPreviousLinkPoint.y;

            if (link == body.get(body.size() - 1)) // TODO ...explanation here
                boardCells.get(previousLinkPoint.y).get(previousLinkPoint.x).setIsOccupied(false);
        }
    }

    /** Get the direction this Snake is currently moving in. */
    public Directions getDirection() {
        return currentDirection;
    }

    /** Change the direction this Snake is currently moving in. */
    public void setDirection(Directions direction) {
        if (   (direction == Directions.Up   && currentDirection != Directions.Down)
            || (direction == Directions.Down && currentDirection != Directions.Up)
            || (direction == Directions.Left && currentDirection != Directions.Right)
            || (direction == Directions.Right && currentDirection != Directions.Left)) {
            currentDirection = direction;
        }
    }
}

/** The directions this Snake can move. */
enum Directions {
    Up, Down, Left, Right;
}