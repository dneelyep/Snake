package com.snake;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;

public class GUI extends JFrame {

    /** A 2D vector of Cells that make up the board of the game.
     * The outer row represents rows, the inner vector columns. */
    private Vector<Vector<Cell>> boardCells = new Vector<>(10);

    /** The panel where GUI components are placed. */
	private JPanel panel = new JPanel();

    /** The Snake the player controls to play the game. */
    private Snake snake;

    /** Timer used to control how often to have actions take place in the game. */
    private Timer timer;

    /** A counter used to determine how often to dispense food. */
    private int foodCounter = 1;

    public static void main(String[] args) {
		new GUI();
	}
	
	private GUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel.setLayout(new GridLayout(10, 10));
		add(panel);
		
		for (int row = 0; row < 10; row++) {
            boardCells.add(new Vector<Cell>(10));
			for (int column = 0; column < 10; column++) {
				boardCells.get(row).add(new Cell(new Point(row, column)));
				panel.add(boardCells.get(row).get(column));
			}
		}

		setTitle("Snake");
        setResizable(false);
		pack();
		setVisible(true);

        addSnake();
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Tick!");
                snake.move(snake.getDirection(), boardCells, GUI.this);
                if (foodCounter == 3) {
                    dispenseFood();
                    foodCounter = 1;
                }
                else foodCounter++;

            }
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyText(e.getKeyCode())) {
                    case "Up":    snake.setDirection(Directions.Up); break;
                    case "Down":  snake.setDirection(Directions.Down); break;
                    case "Left":  snake.setDirection(Directions.Left); break;
                    case "Right": snake.setDirection(Directions.Right); break;
                }
            }
        });
	}

    /** Get the Timer used for timing steps in the game. */
    public Timer getTimer() {
        return timer;
    }

    /** Create a new Snake and display it in the GUI. */
    private void addSnake() {
        snake = new Snake();
        for (Link link : snake.getBody())
            boardCells.get(link.getCoordinates().y).get(link.getCoordinates().x).setOccupied("Link");
    }

    /** Add a new piece of food to the board. */
    private void dispenseFood() {
        Boolean dispensed = false;

        while (!dispensed) {
            Random randGenerator = new Random();
            int randX = randGenerator.nextInt(10);
            int randY = randGenerator.nextInt(10);

            if (!boardCells.get(randY).get(randX).isOccupied()) {
                boardCells.get(randY).get(randX).setOccupied("Food");
                dispensed = true;
            }
        }
    }
}