package com.snake;

import javax.swing.*;
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
    public void move(Directions direction, Vector<Vector<Cell>> boardCells, GUI gui) {
         /* The algorithm for moving:

         Save the current position of the head of the Snake
         Move the head of the snake one unit in the desired direction.
         Move the next link in the body to where the head used to be.
         Repeat until all body parts are moved. */

        Point previousLinkPoint = new Point(body.get(0).getCoordinates().x, body.get(0).getCoordinates().y);

        if (willCollide(boardCells)) {
            lose(gui.getTimer(), boardCells);
        }
        else {
            // Adjust movements when we will overshoot the edge of the board.
            if (body.get(0).getCoordinates().x == 9 && currentDirection == Directions.Right) {
                body.get(0).getCoordinates().x = 0;
                if (boardCells.get(body.get(0).getCoordinates().y).get(0).isFood())
                    addLink();
            }

            else if (body.get(0).getCoordinates().x == 0 && currentDirection == Directions.Left) {
                body.get(0).getCoordinates().x = 9;
                if (boardCells.get(body.get(0).getCoordinates().y).get(9).isFood())
                    addLink();
            }
            else if (body.get(0).getCoordinates().y == 9 && currentDirection == Directions.Down) {
                body.get(0).getCoordinates().y = 0;
                if (boardCells.get(0).get(body.get(0).getCoordinates().x).isFood())
                    addLink(); // LEFTOFFHERE Impleminting this addLink stuff.
            }

            else if (body.get(0).getCoordinates().y == 0 && currentDirection == Directions.Up) {
                body.get(0).getCoordinates().y = 9;
                if (boardCells.get(9).get(body.get(0).getCoordinates().x).isFood())
                    addLink();
            }

            // If none of the above occurred, we should be safe to make a normal move.
            else {
                switch (direction) {
                    case Up:
                        if (boardCells.get(body.get(0).getCoordinates().y - 1).get(body.get(0).getCoordinates().x).isFood())
                            addLink();
                        body.get(0).getCoordinates().y -= 1; break;
                    case Down:
                        if (boardCells.get(body.get(0).getCoordinates().y + 1).get(body.get(0).getCoordinates().x).isFood())
                            addLink();
                        body.get(0).getCoordinates().y += 1; break;
                    case Left:
                        if (boardCells.get(body.get(0).getCoordinates().y).get(body.get(0).getCoordinates().x - 1).isFood())
                            addLink();
                        body.get(0).getCoordinates().x -= 1; break;
                    case Right:
                        if (boardCells.get(body.get(0).getCoordinates().y).get(body.get(0).getCoordinates().x + 1).isFood())
                            addLink();
                        body.get(0).getCoordinates().x += 1; break;
                }
            }

            boardCells.get(body.get(body.size() - 1).getCoordinates().y).get(body.get(body.size() - 1).getCoordinates().x).setOccupied("blank");

            for (Link link : body.subList(1, body.size())) {
                // TODO Clean up variable names, clarify this section.
                Point tmpPreviousLinkPoint = new Point();
                tmpPreviousLinkPoint.x = link.getCoordinates().x;
                tmpPreviousLinkPoint.y = link.getCoordinates().y;

                link.getCoordinates().x = previousLinkPoint.x;
                link.getCoordinates().y = previousLinkPoint.y;

                previousLinkPoint.x = tmpPreviousLinkPoint.x;
                previousLinkPoint.y = tmpPreviousLinkPoint.y;
            }
            //boardCells.get(finalY).get(finalX).setOccupied("blank");

            for (Link link : body)
                boardCells.get(link.getCoordinates().y).get(link.getCoordinates().x).setOccupied("Link");
        }
    }

    /** Get the direction this Snake is currently moving in. */
    public Directions getDirection() {
        return currentDirection;
    }

    /** Change the direction this Snake is currently moving in. */
    public void setDirection(Directions direction) {
        // Make sure the user does not try to move back into where the Snake is coming from.
        if (   (direction == Directions.Up   && currentDirection != Directions.Down)
            || (direction == Directions.Down && currentDirection != Directions.Up)
            || (direction == Directions.Left && currentDirection != Directions.Right)
            || (direction == Directions.Right && currentDirection != Directions.Left)) {
            currentDirection = direction;
        }
    }

    /** Determine if this Snake's next move will cause it to collide with itself. */
    private Boolean willCollide(Vector<Vector<Cell>> boardCells) {
        Point headCoords = body.get(0).getCoordinates();

        if (headCoords.y != 0 && currentDirection == Directions.Up && boardCells.get(headCoords.y - 1).get(headCoords.x).isOccupied() && !boardCells.get(headCoords.y - 1).get(headCoords.x).isFood())
            return true;
        if (headCoords.y != 9 && currentDirection == Directions.Down && boardCells.get(headCoords.y + 1).get(headCoords.x).isOccupied() && !boardCells.get(headCoords.y + 1).get(headCoords.x).isFood())
            return true;
        if (headCoords.x != 0 && currentDirection == Directions.Left && boardCells.get(headCoords.y).get(headCoords.x - 1).isOccupied() && !boardCells.get(headCoords.y).get(headCoords.x - 1).isFood())
            return true;
        if (headCoords.x != 9 && currentDirection == Directions.Right && boardCells.get(headCoords.y).get(headCoords.x + 1).isOccupied() && !boardCells.get(headCoords.y).get(headCoords.x + 1).isFood())
            return true;

        // TODO Is this else statement even needed? I don't think it is.
        else { // Here the player is on an edge of the board.
            if (headCoords.y == 0 && currentDirection == Directions.Up && boardCells.get(9).get(headCoords.x).isOccupied() && !boardCells.get(9).get(headCoords.x).isFood())
                return true;
            if (headCoords.y == 9 && currentDirection == Directions.Down && boardCells.get(0).get(headCoords.x).isOccupied() && !boardCells.get(0).get(headCoords.x).isFood())
                return true;
            if (headCoords.x == 0 && currentDirection == Directions.Left && boardCells.get(headCoords.y).get(9).isOccupied() && !boardCells.get(headCoords.y).get(9).isFood())
                return true;
            if (headCoords.x == 9 && currentDirection == Directions.Right && boardCells.get(headCoords.y).get(0).isOccupied() && !boardCells.get(headCoords.y).get(0).isFood())
                return true;
        }

        return false;
    }

    // TODO Why is this in the Snake class? Why not GUI? Or better yet, a GameUtils class?
    /** Play an animation and end the game when the player loses. */
    private void lose(Timer timer, Vector<Vector<Cell>> boardCells) {
        for (Link link : body)
            boardCells.get(link.getCoordinates().y).get(link.getCoordinates().x).setIcon(new ImageIcon("C:/Users/Daniel/Desktop/Programming/Java/Snake/res/bin/Lose.png"));

        timer.stop();

        Object[] options = {"Restart", "Quit"};

        JOptionPane.showOptionDialog(new JFrame("You lose!"), "You lost! What do you want to do now?", "You lose!",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
    }

    /** Add an extra Link to the end of this Snake's body. */
    private void addLink() {
        int x = body.get(body.size() - 1).getCoordinates().x;
        int y = body.get(body.size() - 1).getCoordinates().y;

        switch (currentDirection) {
            case Up:
                body.add(new Link(new Point(x, y + 1))); break;
            case Down:
                body.add(new Link(new Point(x, y - 1))); break;
            case Left:
                body.add(new Link(new Point(x + 1, y))); break;
            case Right:
                body.add(new Link(new Point(x - 1, y))); break;
        }
    }
}

/** The directions this Snake can move. */
enum Directions {
    Up, Down, Left, Right
}