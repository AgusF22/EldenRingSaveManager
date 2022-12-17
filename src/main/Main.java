package main;

import javax.swing.JFrame;

import main.controller.Controller;
import main.gui.GUI;

/**
 * Main program class
 * @author AgusF
 */
public class Main {

	/**
	 * Launches the application
	 * @param args Program arguments list
	 */
	public static void main(String[] args) {
		JFrame gui = new GUI(new Controller());
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
	}
	
}
