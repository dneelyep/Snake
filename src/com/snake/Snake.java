package com.snake;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Snake extends JFrame {
	
	private JLabel[][] boardCells = new JLabel[10][10];
	private JPanel panel = new JPanel();

	public static void main(String[] args) {
		System.out.println("Hello there.");
		new Snake();
	}
	
	public Snake() {
		panel.setLayout(new GridLayout(10, 10));
		add(panel);
		
		for (int outer = 0; outer < 10; outer++) {
			for (int inner = 0; inner < 10; inner++) {
				boardCells[outer][inner] = new JLabel(new ImageIcon("C:/Users/Daniel/workspace/Snake/res/bin/Cell.png"));
				panel.add(boardCells[outer][inner]);
			}
		}		

		setTitle("Snake");
		pack();
		setVisible(true);
	}
}