package com.snake;

import java.awt.*;
import java.awt.event.*;
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

    public static void main(String[] args) {
		new GUI();
	}
	
	public GUI() {
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
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Tick!");
                snake.move(snake.getDirection(), boardCells);
                updateDisplay();
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

    /** Create a new Snake and display it in the GUI. */
    private void addSnake() {
        snake = new Snake();
        for (Link link : snake.getBody())
            boardCells.get(link.getCoordinates().y).get(link.getCoordinates().x).setIsOccupied(true);
    }

    /** Re-draw all UI components to display any updated game state. */
    private void updateDisplay() {
        // TODO This should just happen whenever the Snake moves.
        for (Link link : snake.getBody())
            boardCells.get(link.getCoordinates().y).get(link.getCoordinates().x).setIsOccupied(true);

        for (Vector<Cell> cellVector : boardCells) {
            for (Cell cell : cellVector) {
                ((JLabel) panel.getComponentAt(cell.getCoordinates())).setIcon(cell.getIcon());
            }
        }
        System.out.println(panel.getComponentAt(0, 0));
    }
}